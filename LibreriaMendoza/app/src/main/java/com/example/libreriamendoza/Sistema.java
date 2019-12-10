package com.example.libreriamendoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class Sistema extends AppCompatActivity  {
    TextView txtusuario,txtnombres,txtapater,txtamater,txtcorreo,txtcel;
    String MyUrl = "http://192.168.0.13:50/Libreria/libros/";
    public Button ver;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistema);

        txtusuario= (TextView) findViewById(R.id.etusuario);

        txtnombres = (TextView)findViewById(R.id.etnombres);
        txtapater = (TextView)findViewById(R.id.etapater);
        txtamater = (TextView)findViewById(R.id.etamater);
        txtcorreo = (TextView)findViewById(R.id.etcorreo);
        txtcel = (TextView)findViewById(R.id.etcel);

/*
        ver=(Button)findViewById(R.id.btnver);
        ver.setOnClickListener(this);
*/
        Bundle datos = this.getIntent().getExtras();
        //int recuperamos_variable_integer = datos.getInt("variable_integer");
        usuario = datos.getString("cod");
        txtusuario.setText(usuario);
        //float recuperamos_variable_float = datos.getFloat("objeto_float");



        Thread tr = new Thread(){
            public void run(){
                final String resultado = enviarDatosGET( usuario);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJSON(resultado);
                        if (r>0){
                            try {
                                JSONArray jsonArray = new JSONArray(resultado);
                                JSONObject obj = jsonArray.getJSONObject(0);
                                String apater = obj.getString("apater");
                                String amater = obj.getString("amater");
                                String nombres = obj.getString("nombres");
                                String correo = obj.getString("correo");
                                String cel = obj.getString("cel");

                                txtnombres.setText(nombres);
                                txtapater.setText(apater);
                                txtamater.setText(amater);
                                txtcorreo.setText(correo);
                                txtcel.setText(cel);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Error de consulta",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };



        tr.start();




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnuprincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.mnuCambiocontra) {
            Intent i = new Intent(this, CambioContra.class );
            i.putExtra("cod",txtusuario.getText().toString());
            startActivity(i);
        }
        if (id==R.id.mnuMAutor) {
            Intent i = new Intent(this, MAutor.class );
            i.putExtra("cod",txtusuario.getText().toString());
            startActivity(i);
        }

        if (id==R.id.mnuMLibro) {
            Intent i = new Intent(this, MLibro.class );
            i.putExtra("cod",txtusuario.getText().toString());
            startActivity(i);
        }

        if (id==R.id.mnuSalir) {
            Toast.makeText(this,"Se seleccionó la opción Salir",Toast.LENGTH_LONG).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    public void onClick(View view) {

        Thread tr = new Thread(){
            public void run(){
                final String resultado = enviarDatosGET( usuario);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJSON(resultado);
                        if (r>0){
                            try {
                                JSONArray jsonArray = new JSONArray(resultado);
                                JSONObject obj = jsonArray.getJSONObject(0);
                                String apater = obj.getString("apater");
                                String amater = obj.getString("amater");
                                String nombres = obj.getString("nombres");
                                String correo = obj.getString("correo");
                                String cel = obj.getString("cel");

                                txtnombres.setText(nombres);
                                txtapater.setText(apater);
                                txtamater.setText(amater);
                                txtcorreo.setText(correo);
                                txtcel.setText(cel);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Error de consulta",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };



        tr.start();
    }
*/
    public String enviarDatosGET(String cod){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();

        try{
            url = new URL(MyUrl+"ver.php?cod="+cod);
            HttpURLConnection connection =(HttpURLConnection )url.openConnection();
            respuesta = connection.getResponseCode();
            if(respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea=reader.readLine()) != null){
                    resul.append(linea);
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
