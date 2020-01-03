package com.kh.androidqr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kh.androidqr.util.Camera_Preview;

public class Result extends AppCompatActivity {

    private String code_Value;
    private TextView result_text;
    private long time= 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        Intent intent = getIntent();
        code_Value = intent.getExtras().getString("Data");
        result_text = findViewById(R.id.tx_result);
        result_text.setText(code_Value);

        Camera_Preview.barcodeContents = null;

    }

    @Override
    public void onBackPressed(){
        this.finish();
    }
}
