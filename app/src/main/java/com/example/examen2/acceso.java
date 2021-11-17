package com.example.examen2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class acceso extends Activity {

    Button backBtn;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_acceso);

        ConSQLite conn = new ConSQLite(this, "db_usuarios", null, 1);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}