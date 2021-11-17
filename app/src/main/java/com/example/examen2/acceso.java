package com.example.examen2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.examen2.utilidades.Utilidades;

public class acceso extends Activity {

    Button backBtn;
    Button addBtn;
    Button delBtn;
    Button closeBtn;
    EditText etId;
    EditText etName;
    TextView listTv;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_acceso);

        ConSQLite conn = new ConSQLite(this, "db_usuarios", null, 1);
        db = conn.getWritableDatabase();

        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        listTv = (TextView) findViewById(R.id.listTv);

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPI_ID, etId.getText().toString());
                values.put(Utilidades.CAMPO_NOMBRE, etName.getText().toString());
                db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPI_ID, values);
                etId.setText("");
                etName.setText("");
                updateList();
            }
        });

        delBtn = (Button) findViewById(R.id.delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(Utilidades.TABLA_USUARIO, Utilidades.CAMPI_ID + " = " + etId.getText().toString(), null);
                etId.setText("");
                etName.setText("");
                updateList();
            }
        });

        closeBtn = (Button) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateList() {
        String id, nombre;
        Cursor c = db.rawQuery("SELECT " + Utilidades.CAMPI_ID + "," +
                Utilidades.CAMPO_NOMBRE + " FROM " + Utilidades.TABLA_USUARIO, null);
        listTv.setText("");
        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                nombre = c.getString(1);
                listTv.append(" " + id + "\t" + nombre + "\n");
            } while (c.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}