package com.sample.frairwebnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import 	android.widget.TextView;
import 	android.widget.ImageView;


public class Main2Activity extends AppCompatActivity {

    TextView title, content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    title = findViewById(R.id.title);
    content = findViewById(R.id.content);
    image = findViewById(R.id.imageView);





    }
}
