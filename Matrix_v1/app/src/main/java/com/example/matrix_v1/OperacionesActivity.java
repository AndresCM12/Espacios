package com.example.matrix_v1;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

import io.github.kexanie.library.MathView;
import spencerstudios.com.bungeelib.Bungee;

public class OperacionesActivity extends AppCompatActivity {
    static int contador;
    TextView txtRenglonUno_1,txtRenglonUno_2,txtRenglonUno_3,txtRenglonUno_4,txtRenglonUno_5, txtRenglonUno_a;
    TextView txtRenglonDos_1,txtRenglonDos_2,txtRenglonDos_3,txtRenglonDos_4,txtRenglonDos_5, txtRenglonDos_b;
    TextView txtRenglonTres_1,txtRenglonTres_2,txtRenglonTres_3,txtRenglonTres_4,txtRenglonTres_5, txtRenglonTres_c;
    TextView txtResultado_1,txtResultado_2;
    Context context;
    SharedPreferences sharprefs;
    float[] obtenerDatos, apy;
    static float[] matriz;
    TextView txtPy;

    boolean ajuste3x3 = false;
    float ajuste = 0.0f;

    Bundle parametros;
    Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String resultado =(String)msg.obj;
            TextView txtPy = findViewById(R.id.txtPy);
            txtPy.setText("Ecuaciones: ");
            txtPy.append("\n"+resultado);
            if(txtPy.getText().toString().equals("Ecuaciones: \n")){

            }
            else{

                TextView txtVwEcu = findViewById(R.id.txtVwEcu);
                txtVwEcu.setVisibility(View.INVISIBLE);
                Button btnPasos = findViewById(R.id.btnPasos);
                //btnPasos.setVisibility(View.INVISIBLE);

            }
        }
    };

    public void onClickTxtVw(View v){

        final Dialog dlgMiCuadroDialogo = new Dialog(this);
        dlgMiCuadroDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //asignamos el layout
        dlgMiCuadroDialogo.setContentView(R.layout.pasosecu);
        //vincular widgets
        Button ok;

        ok = dlgMiCuadroDialogo.findViewById(R.id.btnOk);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgMiCuadroDialogo.cancel();
            }
        });
        dlgMiCuadroDialogo.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_operaciones);
        String titulo;
        String descripcion;
        apy = new float[9];
        context = this;
        txtRenglonUno_1 = findViewById(R.id.txt1);
        txtRenglonUno_2 = findViewById(R.id.txt2);
        txtRenglonUno_3 = findViewById(R.id.txt3);

        Button btnPasos = findViewById(R.id.btnPasos);
        Boolean conjunto = false;
        txtRenglonUno_a = findViewById(R.id.txtA);

        txtRenglonDos_1 = findViewById(R.id.txt6);
        txtRenglonDos_2 = findViewById(R.id.txt7);
        txtRenglonDos_3 = findViewById(R.id.txt8);

        txtRenglonDos_b = findViewById(R.id.txtB);

        txtRenglonTres_1 = findViewById(R.id.txt11);
        txtRenglonTres_2 = findViewById(R.id.txt12);
        txtRenglonTres_3 = findViewById(R.id.txt13);

        txtRenglonTres_c = findViewById(R.id.txtC);


        txtResultado_1 = findViewById(R.id.txtResultado);
        txtResultado_2 = findViewById(R.id.txtResultado3);

       parametros = this.getIntent().getExtras();

       SharedPreferences shar = getSharedPreferences("datos", context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = shar.edit();

        sharprefs = getSharedPreferences("datos", context.MODE_PRIVATE);
            if(parametros !=null) {

                //si es conjunto
                conjunto = parametros.getBoolean("conjunto");
                if(conjunto){
                    ConstraintLayout consta = findViewById(R.id.constraint);
                    TextView textuno = findViewById(R.id.txtUno);
                    consta.setVisibility(View.INVISIBLE);
                    textuno.setVisibility(View.INVISIBLE);
                    btnPasos.setVisibility(View.INVISIBLE);

                    TextView txtpy = findViewById(R.id.txtPy);
                    txtpy.setVisibility(View.VISIBLE);
                    TextView txted = findViewById(R.id.txtVwEcu);
                    txtpy.setVisibility(View.VISIBLE);
                    //Toast.makeText(this, "Es conjunto", Toast.LENGTH_SHORT).show();
                }else{
                    TextView txtpy = findViewById(R.id.txtPy);
                    txtpy.setVisibility(View.INVISIBLE);
                    TextView txted = findViewById(R.id.txtVwEcu);
                    txtpy.setVisibility(View.INVISIBLE);
                }


                contador = parametros.getInt("contador");
                try{
                    editor.putInt("contador", contador);
                    editor.commit();
                }catch (Exception e){

                }


                titulo = parametros.getString("titulo");
                try{
                    editor.putString("titulo",titulo);
                    editor.commit();
                }catch (Exception e){

                }


                descripcion = parametros.getString("descripcion");
                try{
                    editor.putString("descripcion",descripcion);
                    editor.commit();
                }catch (Exception e){

                }


                String comprobacion = parametros.getString("comp");
                try{
                    editor.putString("comp", comprobacion);
                    editor.commit();
                }catch (Exception e){

                }


                matriz = parametros.getFloatArray("arreglo2");
                apy = parametros.getFloatArray("arreglo3");

                //hilo de python//
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(parametros.getInt("key")==1){
                                try{
                                    editor.putInt("key", 1);
                                    editor.commit();
                                }catch (Exception e){

                                }

                                // parte de python ////

                                Python py = Python.getInstance();
                                PyObject pyobj = py.getModule("matrix");
                                PyObject function = pyobj.callAttr("main",apy,contador);


                                String entry = function.toString();

                                entry = entry.replace("\n", ",");
                                entry = entry.replace("{", "");
                                entry = entry.replace("}","");
                                String[] output = entry.split(",");
                                String respy = "";
                                if(contador==3){
                                    try {
                                        respy = ""+output[0]+"\n"+output[1]+"\n"+output[2];
                                    }
                                    catch (Exception e){
                                        respy = "";
                                    }

                                }else {
                                    try {
                                       respy = ""+output[0]+"\n"+output[1];
                                    }catch (Exception e){

                                    }

                                }

                                Message message = handler.obtainMessage(100, respy);
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){

                        }



                    }
                };
                Thread hilopy = new Thread(runnable);
                hilopy.start();



                String comprobacionGuardada = sharprefs.getString("comp", "error");
                String tituloGuardado = sharprefs.getString("titulo", "error");
                txtResultado_1.setText(comprobacionGuardada +tituloGuardado);
                String desGuardada = sharprefs.getString("descripcion", "error");
                txtResultado_2.setText(desGuardada);
                interpretarContador(contador);

            }else {

                String comprobacionGuardada = sharprefs.getString("comp", "error");
                String tituloGuardado = sharprefs.getString("titulo", "error");
                txtResultado_1.setText(comprobacionGuardada + tituloGuardado);
                String desGuardada = sharprefs.getString("descripcion", "error");
                txtResultado_2.setText(desGuardada);
                contador = sharprefs.getInt("contador", 2);
                interpretarRegreso(contador);

                for (int ap = 0; ap < apy.length; ap++) {
                    apy[ap] = sharprefs.getFloat("balor" + ap, 0);
                }

                if (sharprefs.getInt("key",0) == 1) {

                // parte de python ////
                if (!Python.isStarted()) {
                    Python.start(new AndroidPlatform(this));
                }

                Python py = Python.getInstance();
                PyObject pyobj = py.getModule("matrix");
                PyObject function = pyobj.callAttr("main", apy, contador);


                String entry = function.toString();

                entry = entry.replace("\n", ",");
                String[] output = entry.split(",");

                TextView txtPy = findViewById(R.id.txtPy);


                if (contador == 3) {
                    try {
                        txtPy.setText("Ecuaciones: \n");
                        txtPy.setText("" + output[0] + "\n" + output[1] + "\n" + output[2]);
                    } catch (Exception e) {

                    }

                } else {
                    try {
                        txtPy.setText("Ecuaciones: \n");
                        txtPy.setText("" + output[0] + "\n" + output[1]);
                    } catch (Exception e) {

                    }
                }



            }

            }



        final Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(OperacionesActivity.this, MainActivity.class);
                startActivity(next);
                finish();
                Bungee.slideRight(OperacionesActivity.this);
            }
        });


        btnPasos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent pasos = new Intent(OperacionesActivity.this, pasosActivity.class);
                pasos.putExtra("valorarreglo",matriz);
                pasos.putExtra("contador",contador);
                int flag = 0;
                pasos.putExtra("flag",flag);
                startActivity(pasos);
                finish();
            }
        } );
    }

    public void interpretarContador (int i){
        context=this;
        SharedPreferences shar = getSharedPreferences("datos", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shar.edit();

        obtenerDatos = getIntent().getExtras().getFloatArray("arreglo");
        for(int x = 0; x<obtenerDatos.length; x++) {
            try {
                editor.putFloat("valor" + x, obtenerDatos[x]);
                editor.commit();
            } catch (Exception e) {

            }
        }
        apy = getIntent().getExtras().getFloatArray("arreglo3");
        for(int x = 0; x<apy.length; x++) {
            try {
                editor.putFloat("balor" + x, apy[x]);
                editor.commit();
            } catch (Exception e) {

            }
        }

        switch (i){
            case (2):
                TextView[] txtViews2x2 = {txtRenglonUno_1,txtRenglonUno_2, txtRenglonUno_a, txtRenglonDos_1,txtRenglonDos_2, txtRenglonDos_b};
                for(int x = 0; x<txtViews2x2.length; x++){
                    txtViews2x2[x].setVisibility(View.VISIBLE);
                }

                if (obtenerDatos != null) {
                    txtRenglonUno_1.setText(sharprefs.getFloat("valor0",0) + "");
                    txtRenglonUno_2.setText(sharprefs.getFloat("valor1",0) + "");
                    txtRenglonUno_a.setText(sharprefs.getFloat("valor2",0) + "");

                    txtRenglonDos_1.setText(sharprefs.getFloat("valor3",0) + "");
                    txtRenglonDos_2.setText(sharprefs.getFloat("valor4",0) + "");
                    txtRenglonDos_b.setText(sharprefs.getFloat("valor5",0) + "");
                    for(int a = 0; a<txtViews2x2.length; a++){
                        if(txtViews2x2[a].getText().toString().equals("-0.0")){
                            txtViews2x2[a].setText("0.0");
                        }else if(txtViews2x2[a].getText().toString().equals("-0.0")){
                            txtViews2x2[a].setText("0.0");
                        }
                    }
                }
                break;

            case (3):
                TextView[] txtViews3x3 = {txtRenglonUno_1,txtRenglonUno_2, txtRenglonUno_3, txtRenglonUno_a, txtRenglonDos_1,txtRenglonDos_2, txtRenglonDos_3, txtRenglonDos_b,
                txtRenglonTres_1, txtRenglonTres_2, txtRenglonTres_3, txtRenglonTres_c};
                txtRenglonUno_1.setVisibility(View.VISIBLE);
                txtRenglonUno_2.setVisibility(View.VISIBLE);
                txtRenglonUno_3.setVisibility(View.VISIBLE);
                txtRenglonDos_1.setVisibility(View.VISIBLE);
                txtRenglonDos_2.setVisibility(View.VISIBLE);
                txtRenglonDos_3.setVisibility(View.VISIBLE);
                txtRenglonTres_1.setVisibility(View.VISIBLE);
                txtRenglonTres_2.setVisibility(View.VISIBLE);
                txtRenglonTres_3.setVisibility(View.VISIBLE);
                txtRenglonUno_a.setVisibility(View.VISIBLE);
                txtRenglonDos_b.setVisibility(View.VISIBLE);
                txtRenglonTres_c.setVisibility(View.VISIBLE);

                obtenerDatos = getIntent().getExtras().getFloatArray("arreglo");
                if(obtenerDatos != null) {
                    txtRenglonUno_1.setText(obtenerDatos[0] + "");
                    txtRenglonUno_2.setText(obtenerDatos[1] + "");
                    txtRenglonUno_3.setText(obtenerDatos[2] + "");
                    txtRenglonUno_a.setText("" + obtenerDatos[3]+"");

                    txtRenglonDos_1.setText(obtenerDatos[4] + "");
                    txtRenglonDos_2.setText(obtenerDatos[5] + "");
                    txtRenglonDos_3.setText(obtenerDatos[6] + "");
                    txtRenglonDos_b.setText(obtenerDatos[7] + "");

                    txtRenglonTres_1.setText(obtenerDatos[8] + "");
                    txtRenglonTres_2.setText(obtenerDatos[9] + "");
                    txtRenglonTres_3.setText(obtenerDatos[10] + "");
                    txtRenglonTres_c.setText(obtenerDatos[11] + "");
                    for(int a = 0; a<txtViews3x3.length; a++){
                        if(txtViews3x3[a].getText().toString().equals("-0.0")){
                            txtViews3x3[a].setText("0.0"+ "");
                        }else if(txtViews3x3[a].getText().toString().equals("-0.0")){
                            txtViews3x3[a].setText("0.0");
                        }
                    }
                    if(txtRenglonUno_3.getText().toString().equals("0.0") && txtRenglonDos_3.getText().toString().equals("0.0") && txtRenglonTres_3.getText().toString().equals("0.0")){
                        txtRenglonUno_3.setVisibility(View.GONE);
                        txtRenglonDos_3.setVisibility(View.GONE);
                        txtRenglonTres_3.setVisibility(View.GONE);
                    }
                }
                break;

        }
    }

    public void interpretarRegreso (int i){

        switch (i){
            case (2):
                TextView[] txtViews2x2 = {txtRenglonUno_1,txtRenglonUno_2, txtRenglonUno_a, txtRenglonDos_1,txtRenglonDos_2, txtRenglonDos_b};
                for(int x = 0; x<txtViews2x2.length; x++){
                    txtViews2x2[x].setVisibility(View.VISIBLE);
                }

                    txtRenglonUno_1.setText(sharprefs.getFloat("valor0",0) + "");
                    txtRenglonUno_2.setText(sharprefs.getFloat("valor1",0) + "");
                    txtRenglonUno_a.setText(sharprefs.getFloat("valor2",0) + "");

                    txtRenglonDos_1.setText(sharprefs.getFloat("valor3",0) + "");
                    txtRenglonDos_2.setText(sharprefs.getFloat("valor4",0) + "");
                    txtRenglonDos_b.setText(sharprefs.getFloat("valor5",0) + "");

                for(int a = 0; a<txtViews2x2.length; a++){
                    if(txtViews2x2[a].getText().toString().equals("-0.0")){
                        txtViews2x2[a].setText("0.0"+ "");
                    }else if(txtViews2x2[a].getText().toString().equals("-0.0")){
                        txtViews2x2[a].setText("0.0");
                    }
                }

                break;

            case (3):
                TextView[] txtViews3x3 = {txtRenglonUno_1,txtRenglonUno_2, txtRenglonUno_3, txtRenglonUno_a, txtRenglonDos_1,txtRenglonDos_2, txtRenglonDos_3, txtRenglonDos_b,
                        txtRenglonTres_1, txtRenglonTres_2, txtRenglonTres_3, txtRenglonTres_c};
                txtRenglonUno_1.setVisibility(View.VISIBLE);
                txtRenglonUno_2.setVisibility(View.VISIBLE);
                txtRenglonUno_3.setVisibility(View.VISIBLE);
                txtRenglonDos_1.setVisibility(View.VISIBLE);
                txtRenglonDos_2.setVisibility(View.VISIBLE);
                txtRenglonDos_3.setVisibility(View.VISIBLE);
                txtRenglonTres_1.setVisibility(View.VISIBLE);
                txtRenglonTres_2.setVisibility(View.VISIBLE);
                txtRenglonTres_3.setVisibility(View.VISIBLE);
                txtRenglonUno_a.setVisibility(View.VISIBLE);
                txtRenglonDos_b.setVisibility(View.VISIBLE);
                txtRenglonTres_c.setVisibility(View.VISIBLE);


                    txtRenglonUno_1.setText(sharprefs.getFloat("valor0",0) + "");
                    txtRenglonUno_2.setText(sharprefs.getFloat("valor1",0) + "");
                    txtRenglonUno_3.setText(sharprefs.getFloat("valor2",0) + "");
                    txtRenglonUno_a.setText("" + sharprefs.getFloat("valor3",0));

                    txtRenglonDos_1.setText(sharprefs.getFloat("valor4",0) + "");
                    txtRenglonDos_2.setText(sharprefs.getFloat("valor5",0) + "");
                    txtRenglonDos_3.setText(sharprefs.getFloat("valor6",0) + "");
                    txtRenglonDos_b.setText(sharprefs.getFloat("valor7",0) + "");

                    txtRenglonTres_1.setText(sharprefs.getFloat("valor8",0) + "");
                    txtRenglonTres_2.setText(sharprefs.getFloat("valor9",0) + "");
                    txtRenglonTres_3.setText(sharprefs.getFloat("valor10",0) + "");
                    txtRenglonTres_c.setText(sharprefs.getFloat("valor11",0) + "");

                for(int a = 0; a<txtViews3x3.length; a++){
                    if(txtViews3x3[a].getText().toString().equals("-0.0")){
                        txtViews3x3[a].setText("0.0"+ "");
                    }else if(txtViews3x3[a].getText().toString().equals("-0.0")){
                        txtViews3x3[a].setText("0.0");
                    }
                }
                if(txtRenglonUno_3.getText().toString().equals("0.0") && txtRenglonDos_3.getText().toString().equals("0.0") && txtRenglonTres_3.getText().toString().equals("0.0")){
                    txtRenglonUno_3.setVisibility(View.GONE);
                    txtRenglonDos_3.setVisibility(View.GONE);
                    txtRenglonTres_3.setVisibility(View.GONE);
                }
                break;
        }
    }


}
