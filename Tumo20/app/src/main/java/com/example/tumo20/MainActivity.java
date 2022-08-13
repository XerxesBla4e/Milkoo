package com.example.tumo20;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tumo20.Authentication.Login;
import com.example.tumo20.SessionManager.SessionManager;
import com.example.tumo20.ml.Tumor;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button classfy;
    private TextView confidencs, classifiedas;
    private Bitmap bitmapimg;
    int imageSize = 224;
    Uri uri;
    private static final int CODE = 665;
    private static final int CAMERA_PERMISSION_CODE = 89;
    private static final int CAMERA_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        imageView.setOnClickListener(this);
        classfy.setOnClickListener(this);
        classifiedas.setOnClickListener(this);


        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        classfy = findViewById(R.id.buttonclassify);
        confidencs = findViewById(R.id.confidence);
        classifiedas = findViewById(R.id.classified);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tumo_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear:
                imageView.equals("");
                break;
            case R.id.logout:
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.removeSession();

                moveToLogin();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Not menu option", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToLogin() {
        Intent xerxes = new Intent(MainActivity.this, Login.class);
        xerxes.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(xerxes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                SelectPicOption();
                break;
            case R.id.buttonclassify:
                if (uri != null) {
                    bitmapimg = Bitmap.createScaledBitmap(bitmapimg, imageSize, imageSize, false);
                    classfyTumor(bitmapimg);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Select MRI image first",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.classified:
                String query = classifiedas.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.google.com/search?q=" + query));
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Not menu option",
                        Toast.LENGTH_SHORT).show();
        }
    }

    private void classfyTumor(Bitmap bitmapimg) {
        try {
            Tumor model = Tumor.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            bitmapimg.getPixels(intValues, 0, bitmapimg.getWidth(), 0, 0, bitmapimg.getWidth(), bitmapimg.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++];//RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Tumor.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"meningioma", "pituitary", "glioma", "healthy_brain"};

            classifiedas.setText(classes[maxPos]);

            String nick = "";
            for (int i = 0; i < classes.length; i++) {
                nick += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            confidencs.setText(nick);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SelectPicOption() {
        final String[] langs = {"Gallery", "Camera"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Select Option");
        mBuilder.setSingleChoiceItems(langs, -1, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int xer) {
                if (xer == 0) {
                    galleryPic();
                } else if (xer == 1) {
                        cameraPic();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mBuilder.show();
    }

    private void cameraPic() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void galleryPic() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choose MRI Image"), CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            if (resultCode == RESULT_OK && data.getData() != null) {
                uri = data.getData();

                imageView.setImageURI(uri);

                try {
                    bitmapimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data.getData() != null) {
                bitmapimg = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmapimg);

            }

        }


    }


}