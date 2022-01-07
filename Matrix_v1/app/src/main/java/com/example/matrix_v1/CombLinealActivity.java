package com.example.matrix_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import spencerstudios.com.bungeelib.Bungee;

public class CombLinealActivity extends AppCompatActivity {

    static float[][] matriz, base;
    static float[] valoresmatriz;
    float resul[];
    int comp3x3 = 0;
    Button btnPasosComb;

    TextView txtRespuesta1, txtRespuesta2, txtRespuesta3;

    static int M = 10;

    TextView txtRenglonUno_1,txtRenglonUno_2,txtRenglonUno_3,txtRenglonUno_4;
    TextView txtRenglonDos_1,txtRenglonDos_2,txtRenglonDos_3,txtRenglonDos_4;
    TextView txtRenglonTres_1,txtRenglonTres_2,txtRenglonTres_3,txtRenglonTres_4;
    Bundle parametros;

    int contador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comblineal);

        contador=3;

        txtRespuesta1 = findViewById(R.id.txtResultado4);
        txtRespuesta3 = findViewById(R.id.txtResultado5);
        txtRespuesta2 = findViewById(R.id.txtResultado6);

        txtRenglonUno_1 = findViewById(R.id.txt1);
        txtRenglonUno_2 = findViewById(R.id.txt2);
        txtRenglonUno_3 = findViewById(R.id.txt3);
        txtRenglonUno_4 = findViewById(R.id.txtA);

        txtRenglonDos_1 = findViewById(R.id.txt6);
        txtRenglonDos_2 = findViewById(R.id.txt7);
        txtRenglonDos_3 = findViewById(R.id.txt8);
        txtRenglonDos_4 = findViewById(R.id.txtB);


        txtRenglonTres_1 = findViewById(R.id.txt11);
        txtRenglonTres_2 = findViewById(R.id.txt12);
        txtRenglonTres_3 = findViewById(R.id.txt13);
        txtRenglonTres_4 = findViewById(R.id.txtC);
        btnPasosComb = findViewById(R.id.pasosComb);



        parametros = this.getIntent().getExtras();

        if(parametros!=null){
            valoresmatriz = new float[12];
            valoresmatriz = parametros.getFloatArray("valorarreglo");
            resul = new float[contador];
            interpretarContadorTextViews(contador);
            llenarTextViews();
            llenarMatriz();
            try{


            int n = (contador), flag = 0;
            flag = PerformOperation(matriz, n);
            if (flag == 1)
                flag = CheckConsistency(matriz, n, flag);


            try {
                PrintResult(matriz, n, flag);
            }catch (Exception e){
                int duration= Toast.LENGTH_SHORT;
                Toast.makeText(CombLinealActivity.this, "No imprime el resultado",duration).show();
            }
            comprobarLineal(resul,base);

            }catch (Exception e){
                int duracion = Toast.LENGTH_SHORT;
                Toast.makeText(CombLinealActivity.this, "Introduzca los valores",duracion);
            }

        }else{
            interpretarContadorTextViews(contador);
        }


        btnPasosComb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent pasos = new Intent(CombLinealActivity.this, pasosActivity.class);
                pasos.putExtra("valorarreglo",valoresmatriz);
                pasos.putExtra("contador",contador);
                int flag = 1;
                pasos.putExtra("flag",flag);
                startActivity(pasos);
                finish();


            }
        });

        final Button btnComp = findViewById(R.id.btnComprobar);
        btnComp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                try {
                llenarMatriz();
                }catch (Exception e){
                    int duration= Toast.LENGTH_SHORT;
                    Toast.makeText(CombLinealActivity.this, "Introduce bien los datos",duration).show();
                }

                int n = (contador), flag = 0;

                flag = PerformOperation(matriz, n);

                if (flag == 1)
                    flag = CheckConsistency(matriz, n, flag);

                    PrintResult(matriz, n, flag);

                    comprobarLineal(resul,base);


            }
        });

        final Button btnRegresar = findViewById(R.id.btnVolver3);
        btnRegresar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            Intent volver = new Intent(CombLinealActivity.this, MainActivity.class);
            startActivity(volver);
                Bungee.slideRight(CombLinealActivity.this);
            finish();

            }
        });
    }
    public void llenarTextViews(){

        txtRenglonUno_1.setText(""+valoresmatriz[0]);
        txtRenglonUno_2.setText(""+valoresmatriz[1]);
        txtRenglonUno_3.setText(""+valoresmatriz[2]);
        txtRenglonUno_4.setText(""+valoresmatriz[3]);

        txtRenglonDos_1.setText(""+valoresmatriz[4]);
        txtRenglonDos_2.setText(""+valoresmatriz[5]);
        txtRenglonDos_3.setText(""+valoresmatriz[6]);
        txtRenglonDos_4.setText(""+valoresmatriz[7]);


        txtRenglonTres_1.setText(""+valoresmatriz[8]);
        txtRenglonTres_2.setText(""+valoresmatriz[9]);
        txtRenglonTres_3.setText(""+valoresmatriz[10]);
        txtRenglonTres_4.setText(""+valoresmatriz[11]);
    }

    public void comprobarLineal(float a[], float matrix[][]){


            float aux=0;
            float[] comp1 = new float[contador];
            String[] compDatos= new String[contador];


            for (int x=0; x<contador; x++)
            {
                for (int y=0; y<contador; y++)
                {
                    aux =aux+ (a[y]*matrix[x][y]);
                }
                comp1[x]=aux;
                aux=0;

            }

            for(int xy=0; xy<contador; xy++){
                compDatos[xy] =""+Math.round(base[xy][3]);
            }

//Math.round((resul[0]*base[2][0]+resul[1]*base[2][1]+resul[2]*base[2][2]))

            String[] comp = new String[contador];
            int comprobador=0;
            for(int b=0; b<a.length; b++){
                comp[b]= ""+Math.round(comp1[b]);
                if( comp[b].equals(compDatos[b]) ){
                    comprobador++;

                }else{

                }

                txtRespuesta1.setVisibility(View.VISIBLE);
                txtRespuesta2.setVisibility(View.VISIBLE);
                txtRespuesta3.setVisibility(View.VISIBLE);


            }if (comprobador==3){
                btnPasosComb.setVisibility(View.VISIBLE);
                txtRespuesta1.setText("Es combinación lineal");
                txtRespuesta2.setText("Los escalares son: \n"+a[0]+" | "+a[1]+" | "+a[2]+"."+"\n"+" \n"+"Una combinación lineal de dos o más vectores es el vector que se obtiene al sumar esos vectores multiplicados por escalares.\n"+
                        "En este caso la sumatoria de los vectores multiplicados por un escalar dan: \n"+comp[0]+" = "+compDatos[0]+"\n"+
                        comp[1]+" = "+compDatos[1]+"\n"+
                        comp[2]+" = "+compDatos[2]+"\n");
            }else{
                txtRespuesta1.setText("No es combinación lineal");

                txtRespuesta2.setText("Los escalares son: \n"+a[0]+" | "+a[1]+" | "+a[2]+"."+"\n"+" \n"+"Una combinación lineal de dos o más vectores es el vector que se obtiene al sumar esos vectores multiplicados por escalares.\n"+
                        "En este caso la sumatoria de los vectores multiplicados por un escalar dan: \n"+comp[0]+" != "+compDatos[0]+"\n"+
                        comp[1]+" != "+compDatos[1]+"\n"+
                        comp[2]+" != "+compDatos[2]+"\n");
            }




        }



    public  void llenarMatriz(){


        float uno3x3 = Float.parseFloat(txtRenglonUno_1.getText().toString());
        float dos3x3 = Float.parseFloat(txtRenglonUno_2.getText().toString());
        float tres3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());


        float cuatro3x3 = Float.parseFloat(txtRenglonDos_1.getText().toString());
        float cinco3x3 = Float.parseFloat(txtRenglonDos_2.getText().toString());
        float seis3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());



        if(txtRenglonUno_4.getText().toString().equals("") && txtRenglonDos_4.getText().toString().equals("") &&
                txtRenglonTres_4.getText().toString().equals("") ){

            float a3x3 = Float.parseFloat(txtRenglonUno_3.getText().toString());
            float b3x3 = Float.parseFloat(txtRenglonDos_3.getText().toString());
            float c3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());

            matriz[0][3] = a3x3;
            matriz[1][3] = b3x3;
            matriz[2][3] = c3x3;

            float siete3x3 = Float.parseFloat(txtRenglonTres_1.getText().toString());
            float ocho3x3 = Float.parseFloat(txtRenglonTres_2.getText().toString());
            float nueve3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());

            tres3x3 = 0;
            seis3x3 = 0;
            nueve3x3 = 0;

            matriz[0][2] = tres3x3;
            matriz[1][2] = seis3x3;
            matriz[2][2] = nueve3x3;

            matriz[2][0]= siete3x3;
            matriz[2][1]= ocho3x3;

            comp3x3 =1;
        }

        else if (txtRenglonTres_1.getText().toString().equals("") && txtRenglonTres_2.getText().toString().equals("")
                && txtRenglonTres_3.getText().toString().equals("") && txtRenglonTres_4.getText().toString().equals("")) {
            float siete3x3 = 0;
            float ocho3x3 = 0;
            float nueve3x3 = 0;
            float c3x3 = 0;


            float a3x3 = Float.parseFloat(txtRenglonUno_4.getText().toString());
            float b3x3 = Float.parseFloat(txtRenglonDos_4.getText().toString());

            matriz[0][2] = tres3x3;
            matriz[1][2] = seis3x3;
            matriz[2][0] = siete3x3;
            matriz[2][1] = ocho3x3;
            matriz[2][2] = nueve3x3;
            matriz[2][3] = c3x3;

            matriz[0][3] = a3x3;
            matriz[1][3] = b3x3;

            comp3x3 =1;

        }else {

            // try{
            float a3x3 = Float.parseFloat(txtRenglonUno_4.getText().toString());
            float b3x3 = Float.parseFloat(txtRenglonDos_4.getText().toString());
            float siete3x3 = Float.parseFloat(txtRenglonTres_1.getText().toString());
            float ocho3x3 = Float.parseFloat(txtRenglonTres_2.getText().toString());
            float nueve3x3 = Float.parseFloat(txtRenglonTres_3.getText().toString());
            float c3x3 = Float.parseFloat(txtRenglonTres_4.getText().toString());

            matriz[0][2] = tres3x3;
            matriz[1][2] = seis3x3;
            matriz[2][0] = siete3x3;
            matriz[2][1] = ocho3x3;
            matriz[2][2] = nueve3x3;
            matriz[2][3] = c3x3;
            matriz[0][3] = a3x3;
            matriz[1][3] = b3x3;

            comp3x3 =0;
            //} catch (Exception e){
            //Toast.makeText(MainActivity.this, "Introduzca los valores", Toast.LENGTH_SHORT).show();
            // }

        }


        matriz[0][0] = uno3x3;
        matriz[0][1] = dos3x3;



        matriz[1][0] = cuatro3x3;
        matriz[1][1] = cinco3x3;






        int i=0;
        valoresmatriz = new float[12];
        for (int x=0; x<contador; x++){
            for (int y=0; y<contador+1; y++){

                valoresmatriz[i]=matriz[x][y];
                base[x][y]=matriz[x][y];
                i++;
            }
        }


    }

    public void interpretarContadorTextViews(int cont){
        matriz = new float[cont][cont+1];
        base = new float[contador][contador+1];
        resul = new float[cont];

                txtRenglonUno_3.setVisibility(View.VISIBLE);
                txtRenglonDos_3.setVisibility(View.VISIBLE);
                txtRenglonUno_1.setVisibility(View.VISIBLE);
                txtRenglonUno_2.setVisibility(View.VISIBLE);

                txtRenglonDos_1.setVisibility(View.VISIBLE);
                txtRenglonDos_2.setVisibility(View.VISIBLE);

                txtRenglonUno_3.setVisibility(View.VISIBLE);
                txtRenglonDos_3.setVisibility(View.VISIBLE);

                txtRenglonTres_1.setVisibility(View.VISIBLE);
                txtRenglonTres_2.setVisibility(View.VISIBLE);
                txtRenglonTres_3.setVisibility(View.VISIBLE);

                txtRenglonUno_1.setEnabled(true);
                txtRenglonUno_2.setEnabled(true);
                txtRenglonDos_1.setEnabled(true);
                txtRenglonDos_2.setEnabled(true);
                txtRenglonUno_3.setEnabled(true);
                txtRenglonDos_3.setEnabled(true);
                txtRenglonTres_1.setEnabled(true);
                txtRenglonTres_2.setEnabled(true);
                txtRenglonTres_3.setEnabled(true);


                txtRenglonUno_4.setVisibility(View.VISIBLE);
                txtRenglonDos_4.setVisibility(View.VISIBLE);
                txtRenglonTres_4.setVisibility(View.VISIBLE);

                txtRenglonUno_4.setEnabled(true);
                txtRenglonDos_4.setEnabled(true);
                txtRenglonTres_4.setEnabled(true);

    }

    static int PerformOperation(float a[][], int n) {
        int i, j, k = 0, c, flag = 0, m = 0;
        float pro = 0;

        // Performing elementary operations
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

                // Excluding all i == j
                if (i != j)
                {

                    // Converting Matrix to reduced row
                    // echelon form(diagonal matrix)
                    float p = a[j][i] / a[i][i];

                    for (k = 0; k <= n; k++)
                        a[j][k] = a[j][k] - (a[i][k]) * p;
                }
            }
        }
        return flag;
    }

    public void PrintResult(float a[][], int n, int flag) {
        int duration= Toast.LENGTH_SHORT;
        if (flag == 2) {
            Toast.makeText(CombLinealActivity.this, "Los escalares tienen soluciones infinitas", duration).show();
            btnPasosComb.setVisibility(View.VISIBLE);
        }
        else if (flag == 3) {
            Toast.makeText(CombLinealActivity.this, "Los escalares no tienen solución", duration).show();
            btnPasosComb.setVisibility(View.VISIBLE);
        }
        else {
            for (int i = 0; i < n; i++)
                resul[i] = (a[i][n] / a[i][i]);
            btnPasosComb.setVisibility(View.VISIBLE);
        }
        btnPasosComb.setVisibility(View.VISIBLE);
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



}
