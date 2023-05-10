package com.example.projekt4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.projekt4.R;
import com.example.projekt4.database.Picture;
import com.example.projekt4.viewModels.PictureViewModel;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button buttonRed;
    private Button buttonBlue;
    private Button buttonYellow;
    private Button buttonCLear;
    private PaintView paintView;
    private PictureViewModel pictureViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonRed = findViewById(R.id.button_red);
        buttonBlue = findViewById(R.id.button_blue);
        buttonYellow = findViewById(R.id.button_yellow);
        buttonCLear = findViewById(R.id.button_clear);

        paintView = findViewById(R.id.paintView);

        pictureViewModel = new ViewModelProvider(this).get(PictureViewModel.class);

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.changeColor(Color.RED);
            }
        });

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.changeColor(Color.BLUE);
            }
        });

        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.changeColor(Color.YELLOW);
            }
        });

        buttonCLear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.clearCanva();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_picture:
                Bitmap bitmap = paintView.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                byte[] byteArray = stream.toByteArray();
                Picture p = new Picture("nazwa1", "ja", new Date(System.currentTimeMillis()), byteArray);
                pictureViewModel.insert(p);
                break;
            case R.id.show_pictures:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}