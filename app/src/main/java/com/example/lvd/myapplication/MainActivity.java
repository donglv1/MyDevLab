package com.example.lvd.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends ActionBarActivity {
    Button b, b1, b2;
    final String LASTPOINTFILE = "last_point_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Data
        if (!isFileExisted(LASTPOINTFILE)){

        }

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a new intent
                Intent memoriesIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(memoriesIntent);
            }
        });

        b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int lastPoint = 0;
            try {
                //Read last point file
                FileInputStream fos = openFileInput(LASTPOINTFILE);
                if (fos != null){
                    lastPoint = fos.read();
                }

                Intent detailActivityIntent = new Intent(getApplication(), DetailActivity.class);

                //Send data to other screen
                detailActivityIntent.putExtra("position", lastPoint);
                startActivity(detailActivityIntent);

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });
        /*final int aboutUs = R.raw.author;
        final InputStream isAuthor = this.getResources().openRawResource(aboutUs);*/
    }

    public boolean isFileExisted(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public int iniData(){
        ContentValues[] bulkValues = new ContentValues[110];

        for (int i = 0; i <= 109; i++){
            ContentValues values = new ContentValues();
            values.put(RatingMemoryProvider.ITEM, i);
            values.put(RatingMemoryProvider.RATING, 0);
            bulkValues[i] = values;
        }
        int numberInserted = getContentResolver().bulkInsert(RatingMemoryProvider.CONTENT_URI, bulkValues);

        /*ContentValues values1 = new ContentValues();
        values1.put(RatingMemoryProvider.ITEM, "----------");
        getContentResolver().update(RatingMemoryProvider.CONTENT_URI, values1,"name='Dong'", null);*/
        return numberInserted;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //finish();
            //startActivity(getIntent());
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}