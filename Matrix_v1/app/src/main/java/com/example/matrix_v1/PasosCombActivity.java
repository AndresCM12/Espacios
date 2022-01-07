package com.example.matrix_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import spencerstudios.com.bungeelib.Bungee;

public class PasosCombActivity extends AppCompatActivity {
    int contador;
    GridLayout[] arrayGrids;
    TextView[] txt2;
    TextView[] uno;
    TextView[] arrayTxt;
    static float [][] matriz;
    Bundle parametros;
    float [] valormatriz;
    Button regresar;
    float[]resul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pasoscomb);


        parametros = this.getIntent().getExtras();
        resul= new float[3];
        if(parametros!=null){
            valormatriz = parametros.getFloatArray("valorarreglo");
            matriz = new float[contador][contador+1];
            arrayTxt = new TextView[9];
            txt2= new TextView[9];
            uno = new TextView[contador+1];
            arrayGrids = new GridLayout[9];




        }else{

        }

        acomodarArreglo(valormatriz);
        int n = (contador), flag = 0;


        flag = PerformOperation(matriz, n);

        if (flag == 1)
            flag = CheckConsistency(matriz, n, flag);


        try {
            PrintResult(matriz, n, flag);
        }catch (Exception e){
            int duration= Toast.LENGTH_SHORT;
            Toast.makeText(PasosCombActivity.this, "No imprime el resultado",duration).show();
        }

        regresar = findViewById(R.id.btnVolver4);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(PasosCombActivity.this, CombLinealActivity.class);
                volver.putExtra("valorarreglo",valormatriz);
                startActivity(volver);
                Bungee.slideRight(PasosCombActivity.this);
                finish();
            }
        });

    }

    public void acomodarArreglo(float[] arreglo){
        int i=0;
        for (int x=0; x<contador; x++){
            for (int y=0; y<contador+1; y++){
                matriz[x][y]= arreglo[i];
                i++;
            }
        }
    }

    public  int PerformOperation(float a[][], int n) {
        int i, j, k = 0, c, flag = 0, m = 0, grid=0, view=0,view2=0;
        float pro = 0;


        LinearLayout base = findViewById(R.id.layoutBase3);
        base.setBackgroundResource(R.drawable.rect2);


        for (i = 0; i < n; i++)
        {
            if (a[i][i] == 0)
            {
                c = 1;
                while ((i + c) < n && a[i + c][i] == 0)
                    c++;
                if ((i + c) == n)
                {
                    flag = 1;
                    break;
                }
                for (j = i, k = 0; k <= n; k++)
                {
                    float temp =a[j][k];
                    a[j][k] = a[j+c][k];
                    a[j+c][k] = temp;
                }
            }

            for (j = 0; j < n; j++)
            {



                if (i != j)
                {


                    float p = a[j][i] / a[i][i];

                    for (k = 0; k <= n; k++)
                        a[j][k] = a[j][k] - (a[i][k]) * p;
                }

                arrayTxt[view2]= new TextView(this);
                arrayTxt[view2].setText("Iteración"+j+1);
                arrayTxt[view2].setPadding(0,50,0,50);

                base.addView(arrayTxt[view2]);

                arrayGrids[grid] = new GridLayout(this);
                arrayGrids[grid].setColumnCount((contador+1));
                arrayGrids[grid].setPadding(275,150,0,0);
                arrayGrids[grid].setBackgroundResource(R.drawable.rect2);

                for (int idos = 0; idos < n; idos++){
                    for (int jdos = 0; jdos <= n+1; jdos++) {


                        txt2[view] = new TextView(this);
                       txt2[view].setText(""+a[idos][jdos]);
                       arrayGrids[grid].addView(txt2[view]);
                       view++;
                    }
                }
                view=0;
                view2++;
                base.addView(arrayGrids[grid]);
                grid++;
            }
        }
        return flag;
    }

    static int CheckConsistency(float a[][], int n, int flag) {
        int i, j;
        float sum;


        flag = 3;
        for (i = 0; i < n; i++)
        {
            sum = 0;
            for (j = 0; j < n; j++)
                sum = sum + a[i][j];
            if (sum == a[i][j])
                flag = 2;
        }
        return flag;
    }

    public void PrintResult(float a[][], int n, int flag) {

        int duration= Toast.LENGTH_SHORT;
        if (flag == 2)
            Toast.makeText(PasosCombActivity.this, "Los escalares tienen soluciones infinitas",duration).show();

        else if (flag == 3)
            Toast.makeText(PasosCombActivity.this, "Los escalares no tienen solución",duration).show();




        else {
            for (int i = 0; i < n; i++)
                resul[i] = (a[i][n] / a[i][i]);
        }
    }



}
