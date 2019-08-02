package com.example.chanj.timeblocks;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import android.widget.Toolbar;

import android.view.View;

import java.io.FileReader;

import android.os.Build;

public class MainActivity extends AppCompatActivity {

    public EditText editText8am, editText9am, editText10am, editText11am, editText12pm, editText1pm, editText2pm, editText3pm, editText4pm, editText5pm, editText6pm, editText7pm, editText8pm;
    public Button buttonSave, buttonLoad;
    public FileWriter theFile;
    public PrintWriter theFileData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText8am = (EditText) findViewById(R.id.editText8am);
        editText9am = (EditText) findViewById(R.id.editText9am);
        editText10am = (EditText) findViewById(R.id.editText10am);
        editText11am = (EditText) findViewById(R.id.editText11am);
        editText12pm = (EditText) findViewById(R.id.editText12pm);
        editText1pm = (EditText) findViewById(R.id.editText1pm);
        editText2pm = (EditText) findViewById(R.id.editText2pm);
        editText3pm = (EditText) findViewById(R.id.editText3pm);
        editText4pm = (EditText) findViewById(R.id.editText4pm);
        editText5pm = (EditText) findViewById(R.id.editText5pm);
        editText6pm = (EditText) findViewById(R.id.editText6pm);
        editText7pm = (EditText) findViewById(R.id.editText7pm);
        editText8pm = (EditText) findViewById(R.id.editText8pm);

        /////Internal storage write permission
        boolean storagePermission = false;
        while (storagePermission == false) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("DEBUG", "Permission is granted");
                    storagePermission = true;
                } else {
                    Log.e("DEBUG", "Permission Denied");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    storagePermission = false;
                }
            } else {
                Log.e("DEBUG", "Permission Granted");
                storagePermission = true;
            }
        }

        /////Auto Load

        File sdCard = Environment.getExternalStorageDirectory();
        File folderFile = new File(sdCard + File.separator + "timeBlocks", "timeBlocksData.txt");
        //StringBuilder text = new StringBuilder();
        String textFileContent[] = new String[14];
        int readCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(folderFile));
            String line;

            while ((line = br.readLine()) != null) {
                textFileContent[readCount] = line;
                Log.e("DEBUG", "Text Loaded #" + readCount + ": " + textFileContent[readCount]);
                readCount++;
            }

            editText8am.setText(textFileContent[0]);
            editText9am.setText(textFileContent[1]);
            editText10am.setText(textFileContent[2]);
            editText11am.setText(textFileContent[3]);
            editText12pm.setText(textFileContent[4]);
            editText1pm.setText(textFileContent[5]);
            editText2pm.setText(textFileContent[6]);
            editText3pm.setText(textFileContent[7]);
            editText4pm.setText(textFileContent[8]);
            editText5pm.setText(textFileContent[9]);
            editText6pm.setText(textFileContent[10]);
            editText7pm.setText(textFileContent[11]);
            editText8pm.setText(textFileContent[12]);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DEBUG", "Load: IO" + e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("State", "Application Paused");

        //Auto Save
        try {
            String fileName = "timeBlocksData.txt";
            File folderFile = new File(Environment.getExternalStorageDirectory(), "timeBlocks");
            if (!folderFile.exists()) {
                folderFile.mkdirs();
                //Log.e("DEBUG", "Created dir in: "+Environment.getExternalStorageDirectory());
            }
            File txtFile = new File(folderFile, fileName);
            FileWriter writer = new FileWriter(txtFile, false);

            //Loading Array with editText data
            String fileContents[] = new String[13];
            fileContents[0] = editText8am.getText().toString();
            fileContents[1] = editText9am.getText().toString();
            fileContents[2] = editText10am.getText().toString();
            fileContents[3] = editText11am.getText().toString();
            fileContents[4] = editText12pm.getText().toString();
            fileContents[5] = editText1pm.getText().toString();
            fileContents[6] = editText2pm.getText().toString();
            fileContents[7] = editText3pm.getText().toString();
            fileContents[8] = editText4pm.getText().toString();
            fileContents[9] = editText5pm.getText().toString();
            fileContents[10] = editText6pm.getText().toString();
            fileContents[11] = editText7pm.getText().toString();
            fileContents[12] = editText8pm.getText().toString();

            //Write to txt file
            int contentWriterCount;
            for (contentWriterCount = 0; contentWriterCount < 13; contentWriterCount++) {
                writer.append(fileContents[contentWriterCount] + "\n");
                Log.e("DEBUG", "Text Saved #" + contentWriterCount + ": " + fileContents[contentWriterCount]);
            }
            writer.close();

            Log.e("DEBUG", "Saved");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DEBUG", "Save: IO" + e);
        }
    }
}
