package com.example.wishnote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddListActivity extends AppCompatActivity {

    private EditText name, price, descr;
    private ImageView back, addimg;
    Button save, camera, gallery;
    Uri image;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        name = findViewById(R.id.inputname);
        price = findViewById(R.id.inputprice);
        descr = findViewById(R.id.inputdesc);
        addimg = findViewById(R.id.itemimage);

        camera = findViewById(R.id.btncamera);
        gallery = findViewById(R.id.btngallery);

        save = findViewById(R.id.btnsave);
        back = findViewById(R.id.btnback);

        db = new DBHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nametxt = name.getText().toString();
                String pricetxt = price.getText().toString();
                String descrtxt = descr.getText().toString();

                if (!nametxt.isEmpty()){
                    boolean checkinsertdata = db.insertlistdata(nametxt, pricetxt, descrtxt);
                    if (checkinsertdata) {
                        Toast.makeText(AddListActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddListActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddListActivity.this, "Type different item name", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddListActivity.this, "Empty item name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();
                if (checkCameraPermission()==true){
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
                if (checkStoragePermission()==true){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String nametxt = name.getText().toString();
        String pricetxt = price.getText().toString();
        String descrtxt = descr.getText().toString();

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                    addimg.setImageBitmap(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String x = getPath(selectedImage);
                    System.out.println(selectedImage);

                    if(db.insertlistdata(nametxt, pricetxt, descrtxt))

                        addimg.setImageURI(selectedImage);
                }
                break;
        }
    }

    public String getPath(Uri uri){
        if (uri==null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }return uri.getPath();
    }

    private void requestStoragePermission() {
        requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res1&&res2;
    }

}