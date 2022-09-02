package com.example.life;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.life.Webview.websiteActivity;
import com.example.life.ml.Fruitveg;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CalorieActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button classfy, selectPic;
    private TextView confidencs, classifiedas;
    private Bitmap bitmapimg;
    private static final int CAMERA_PERMS_CODE = 55;
    int imageSize = 224;
    Uri uri;
    Uri imgUri;
    Uri imageUri;
    private static final int CODE = 665;
    private static final int CAMERA_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calorie);

        initViews();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imageView.setOnClickListener(this);
        classfy.setOnClickListener(this);
        classifiedas.setOnClickListener(this);
        selectPic.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(CalorieActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CalorieActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, CAMERA_PERMS_CODE);
        }

    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        classfy = findViewById(R.id.buttonclassify);
        confidencs = findViewById(R.id.confidence);
        classifiedas = findViewById(R.id.result);
        selectPic = findViewById(R.id.selectPic);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectPic:
                SelectPicOption();
                break;
            case R.id.buttonclassify:
                if (uri != null) {
                    bitmapimg = Bitmap.createScaledBitmap(bitmapimg, imageSize, imageSize, false);
                    classfy(bitmapimg);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Select Fruit or Vegetable Image",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.result:
                String query = classifiedas.getText().toString();
                Intent intent1 = new Intent(getApplicationContext(), websiteActivity.class);
                intent1.putExtra("fruitveg", query);
                startActivity(intent1);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Not menu option",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void classfy(Bitmap bitmapimg) {
        try {
            Fruitveg model = Fruitveg.newInstance(getApplicationContext());

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
            Fruitveg.Outputs outputs = model.process(inputFeature0);
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
            String[] classes = {"apple", "banana", "beetroot", "bell pepper",
                    "cabbage", "capiscum", "carrot", "cauliflower", "chilli pepper",
                    "corn", "cucumber", "eggplant", "garlic", "ginger", "grapes",
                    "jalepeno", "kiwi", "lemon", "lettuce", "mango", "onion", "orange",
                    "paprika", "pear", "peas", "pineapple", "pomegrante", "potato", "raddish",
                    "soya beans", "spinach", "sweetcorn", "sweetpotato", "tomato", "turnip", "watermelon"};

            classifiedas.setText(classes[maxPos]);

            String nick = "";

            for (int i = 0; i < classes.length; i++) {
                if (i == maxPos) {
                    nick += String.format("%s: %.1f%%", classes[i], confidences[i] * 100);
                }
            }
            confidencs.setTextColor(Color.parseColor("#DF0D0D"));
            confidencs.setText(nick);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SelectPicOption() {
        final String[] langs = {"Gallery", "Camera"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CalorieActivity.this);
        mBuilder.setTitle("Preference");
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
        Uri imgPath = createImage();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgPath);
        startActivityForResult(intent, CAMERA_PERMS_CODE);
    }

    private void galleryPic() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choose Fruit/vegetable Image"), CODE);
    }

    private Uri createImage() {
        Uri uri = null;
        ContentResolver resolver = getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);//DCIM, Pictures
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        String imgName = String.valueOf(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/");
        Uri finalUri = resolver.insert(uri, contentValues);
        imageUri = finalUri;

        return finalUri;
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
        } else if (requestCode == CAMERA_PERMS_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Image Saved Successfully", Toast.LENGTH_SHORT).show();
                // imgView.setImageURI(imageUri);
                try {
                    bitmapimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageView.setImageBitmap(bitmapimg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}