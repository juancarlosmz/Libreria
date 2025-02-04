package com.example.libreriamendoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    public Button Registrar;
    public EditText txtclave,txtdni,txtnombres,txtapater,txtamater,txtcorreo,txtcel;
    String MyUrl = "http://192.168.0.13:50/Libreria/libros/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtdni = (EditText)findViewById(R.id.etdni);
        txtnombres = (EditText)findViewById(R.id.etnombres);
        txtapater = (EditText)findViewById(R.id.etapater);
        txtamater = (EditText)findViewById(R.id.etamater);
        txtcorreo = (EditText)findViewById(R.id.etcorreo);
        txtcel = (EditText)findViewById(R.id.etcel);
        txtclave = (EditText)findViewById(R.id.etclave);
        Registrar=(Button)findViewById(R.id.btnregistro);
        Registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Thread tr = new Thread(){
            public void run(){

                final String resultado = enviarDatosGET( txtdni.getText().toString(), txtapater.getText().toString(), txtamater.getText().toString(), txtnombres.getText().toString(), txtcorreo.getText().toString(), txtcel.getText().toString(), txtclave.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El resultado oficial es : "+resultado);
                        String respuestaEvaluar = resultado;
                        if(respuestaEvaluar.equals("Usuario Creado")){
                            Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuario no registrado o ya existe",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };
        tr.start();
    }

    public String enviarDatosGET(String dni, String apater,String amater, String nombres, String correo, String cel, String clave){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"Registro.php?dni="+dni+"&apater="+apater+"&amater="+amater+"&nombres="+nombres+"&correo="+correo+"&cel="+cel+"&clave="+clave);
            HttpURLConnection connection =(HttpURLConnection )url.openConnection();
            respuesta = connection.getResponseCode();
            System.out.println("--------------respuesta1: "+respuesta+"--------------------------------");
            if(respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea=reader.readLine()) != null){
                    resul.append(linea);
                    System.out.println("--------------respuesta2: "+resul.toString()+"--------------------------------");
                }
            }
        }catch (Exception e){
            System.out.println("--------------ERROR------------");
        }
        return resul.toString();
    }

    public int obtDatosJSON(String response){
        int res = 0;
        try{
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length()>0){
                res=1;
            }
        }catch (Exception e){

        }
        return res;
    }


}
