package com.example.sdfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt=(TextView) findViewById(R.id.txt);
        boolean a=isExternalStorageWritable();
        boolean b=isExternalStorageReadable();
        String str="外部存储卡是否可读写:"+a+"\n"+"是否能够读取外部存储卡:"+b;
        txt.setText(str);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        Button btnWrite=(Button) findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream out=null;
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = Environment.getExternalStorageDirectory();
                        File myfile = new File(file.getCanonicalPath() + "/MyFile");
                        FileOutputStream fileOutputStream = new FileOutputStream(myfile);
                        out = new BufferedOutputStream(fileOutputStream);
                        String content = "2017011200 lgy";
                        try{
                            out.write(content.getBytes(StandardCharsets.UTF_8));
                            Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                        } finally {
                            if (out != null)
                                out.close();                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button btnRead=(Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in=null;
                try {
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = Environment.getExternalStorageDirectory();
                        File myfile = new File(file.getCanonicalPath() + "/MyFile");
                        FileInputStream fileInputStream = new FileInputStream(myfile);
                        in = new BufferedInputStream(fileInputStream);
                        int c;
                        StringBuilder stringBuilder = new StringBuilder("");
                        try {
                            while ((c = in.read()) != -1) {
                                stringBuilder.append((char) c);
                            }
                            Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
                        } finally {
                            if (in != null)
                            in.close();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    //检查外部存储卡是否可读写，返回true表示可读写，返回false不可读写
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //检查是否能够读取外部存储卡，返回true表示可读，返回false不可读
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}
