package com.example.examen2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnTouchListener {

    private int x, y;
    private Lienzo l;
    TextView eventTV;
    ArrayList<Integer> puntosUsados = new ArrayList<Integer>();
    ArrayList<Integer> patronGuardado = new ArrayList<Integer>();
    int[][] puntosCoordenadas = new int[9][2];
    Button saveBtn;
    Button accessBtn;
    int col[]= new int[3];

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        col[0] = width/5;
        col[1] = width/2;
        col[2] = width*4/5;

        //Fila 1
        puntosCoordenadas[0][0] = col[0];
        puntosCoordenadas[1][0] = col[1];
        puntosCoordenadas[2][0] = col[2];
        puntosCoordenadas[0][1] = 1000;
        puntosCoordenadas[1][1] = 1000;
        puntosCoordenadas[2][1] = 1000;

        //Fila 2
        puntosCoordenadas[3][0] = col[0];
        puntosCoordenadas[4][0] = col[1];
        puntosCoordenadas[5][0] = col[2];
        puntosCoordenadas[3][1] = 1400;
        puntosCoordenadas[4][1] = 1400;
        puntosCoordenadas[5][1] = 1400;

        //Fila 3
        puntosCoordenadas[6][0] = col[0];
        puntosCoordenadas[7][0] = col[1];
        puntosCoordenadas[8][0] = col[2];
        puntosCoordenadas[6][1] = 1800;
        puntosCoordenadas[7][1] = 1800;
        puntosCoordenadas[8][1] = 1800;

        x = 100;
        y = 100;
        ConstraintLayout cl = findViewById(R.id.xl1);
        l = new Lienzo(this);
        l.setOnTouchListener(this);
        cl.addView(l);
        eventTV = (TextView) findViewById(R.id.eventTV);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        accessBtn = (Button) findViewById(R.id.accessBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                if (puntosUsados.size() > 1) {
                    patronGuardado = puntosUsados;
                    str = "Patron guardado!"+patronGuardado;
                    eventTV.setText(patronGuardado+"");
                } else {
                    str = "No has pulsado nada";
                }
                puntosUsados.clear();
                l.invalidate();
                Toast t = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
            }
        });
        accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean itsEqual = true;
                String str = "";
                if (patronGuardado.size() > 0) {
                    if (puntosUsados == patronGuardado) {
                        str = "Acediendo";
                    } else {
                        str = "Los patrones no son iguales!\n"+patronGuardado+"\n"+puntosUsados;
                        itsEqual = false;
                    }
                } else {
                    str = "No hay ningun patron guardado!";
                    itsEqual = false;
                }
                if (itsEqual) {
//                    Intent i = new Intent(getApplicationContext(), acceso.class);
//                    startActivity(i);
                    str = "Es igual\n"+patronGuardado+"\n"+puntosUsados;
                }
                Toast t = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
                t.show();
                puntosUsados.clear();
                l.invalidate();
            }
        });
    }

    public boolean onTouch(View v, MotionEvent e) {
        x = (int) e.getX();
        y = (int) e.getY();
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            puntosUsados.clear();
            eventTV.setText("");
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (puntosUsados.size() == 1) {
                puntosUsados.clear();
            }
            if (puntosUsados.size()>=2){
                x = getCoord(puntosUsados.get(puntosUsados.size()-1),0);
                y = getCoord(puntosUsados.get(puntosUsados.size()-1),1);
                eventTV.setText("");
            }
        }
        l.invalidate();
        stickyLine(x, y);
        return true;
    }

    private void stickyLine(int xPos, int yPos) {
        if (nearTo(xPos, col[0])) {
            if (nearTo(yPos, 1000) && puntosUsados.indexOf(0) == -1) {
                eventTV.setText("Punto 1");
                puntosUsados.add(0);
            }
            if (nearTo(yPos, 1400) && puntosUsados.indexOf(3) == -1) {
                eventTV.setText("Punto 4");
                puntosUsados.add(3);
            }
            if (nearTo(yPos, 1800) && puntosUsados.indexOf(6) == -1) {
                eventTV.setText("Punto 7");
                puntosUsados.add(6);
            }
        }
        if (nearTo(xPos, col[1])) {
            if (nearTo(yPos, 1000) && puntosUsados.indexOf(1) == -1) {
                eventTV.setText("Punto 2");
                puntosUsados.add(1);
            }
            if (nearTo(yPos, 1400) && puntosUsados.indexOf(4) == -1) {
                eventTV.setText("Punto 5");
                puntosUsados.add(4);
            }
            if (nearTo(yPos, 1800) && puntosUsados.indexOf(7) == -1) {
                eventTV.setText("Punto 8");
                puntosUsados.add(7);
            }
        }
        if (nearTo(xPos, col[2])) {
            if (nearTo(yPos, 1000) && puntosUsados.indexOf(2) == -1) {
                eventTV.setText("Punto 3");
                puntosUsados.add(2);
            }
            if (nearTo(yPos, 1400) && puntosUsados.indexOf(5) == -1) {
                eventTV.setText("Punto 6");
                puntosUsados.add(5);
            }
            if (nearTo(yPos, 1800) && puntosUsados.indexOf(8) == -1) {
                eventTV.setText("Punto 9");
                puntosUsados.add(8);
            }
        }
    }

    public boolean nearTo(int pos, int destino) {
        int allowedRange = 30;
        if (pos > destino - allowedRange && pos < destino + allowedRange) {
            return true;
        }
        return false;
    }

    public int getCoord(int arrayPos, int plano) {
        return puntosCoordenadas[arrayPos][plano];
    }

    class Lienzo extends View {
        public Lienzo(Context c) {
            super(c);
        }

        protected void onDraw(Canvas c) {
            Paint p = new Paint();
            p.setARGB(255, 55, 200, 75);
            p.setStrokeWidth(10);
            p.setStyle(Paint.Style.STROKE);
            c.drawCircle(puntosCoordenadas[0][0], puntosCoordenadas[0][1], 20, p);
            c.drawCircle(puntosCoordenadas[1][0], puntosCoordenadas[1][1], 20, p);
            c.drawCircle(puntosCoordenadas[2][0], puntosCoordenadas[2][1], 20, p);

            c.drawCircle(puntosCoordenadas[3][0], puntosCoordenadas[3][1], 20, p);
            c.drawCircle(puntosCoordenadas[4][0], puntosCoordenadas[4][1], 20, p);
            c.drawCircle(puntosCoordenadas[5][0], puntosCoordenadas[5][1], 20, p);

            c.drawCircle(puntosCoordenadas[6][0], puntosCoordenadas[6][1], 20, p);
            c.drawCircle(puntosCoordenadas[7][0], puntosCoordenadas[7][1], 20, p);
            c.drawCircle(puntosCoordenadas[8][0], puntosCoordenadas[8][1], 20, p);

            if (puntosUsados.size() >=1) {
                c.drawLine(getCoord(puntosUsados.get(puntosUsados.size()-1), 0), getCoord(puntosUsados.get(puntosUsados.size()-1), 1),
                        x, y ,p);
                if (puntosUsados.size() >= 2) {
                    for (int i = 1; i < puntosUsados.size(); i++) {

                        c.drawLine(getCoord(puntosUsados.get(i - 1), 0), getCoord(puntosUsados.get(i - 1), 1),
                                getCoord(puntosUsados.get(i), 0), getCoord(puntosUsados.get(i), 1), p);

                    }
                }
            }
        }
    }
}