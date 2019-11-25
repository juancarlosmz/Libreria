package com.example.libreriamendoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText usuario,password;
    public Button ingresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario=(EditText)findViewById(R.id.etingreso);
        password=(EditText)findViewById(R.id.etpassword);
        ingresa=(Button)findViewById(R.id.btningreso);
        ingresa.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {


        Thread tr = new Thread(){
            public void run(){

                final String resultado = enviarDatosGET( usuario.getText().toString(), password.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("El resultado es : "+obtDatosJSON(resultado));
                        int r = obtDatosJSON(resultado);



                        if (r>0){
                            Intent i = new Intent(getApplicationContext(),Sistema.class);
                            i.putExtra("cod",usuario.getText().toString());
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuario o clave incorrectas",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };

        /*
        Thread tr = new Thread(){
            public void run(){

                String resultado = enviarDatosGET( usuario.getText().toString(), password.getText().toString());

                System.out.println("El resultado es : "+obtDatosJSON(resultado));

            };
        };
        */
        tr.start();
    }

    public String enviarDatosGET(String usu, String pas){

        System.out.println("lo que se envia : "+usu+pas+"lo que se envia");

        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();

        try{
            url = new URL("http://192.168.1.60:50/libros/valida.php?usu="+usu+"&pas="+pas);
            HttpURLConnection connection =(HttpURLConnection )url.openConnection();
            respuesta = connection.getResponseCode();
            //resul = new StringBuilder();
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
