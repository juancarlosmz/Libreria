package com.example.libreriamendoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MAutor extends AppCompatActivity implements View.OnClickListener{

    public Button RegistrarAutor,BuscarAutor,ModificarAutor,EliminarAutor;
    public EditText txtid,txtnombres,txtapellidos,txtpais,txtcorreo,txtBuscarID;
    String MyUrl = "http://192.168.0.13:50/Libreria/libros/Autor/";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnuprincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.mnuCambiocontra) {
            Toast.makeText(this,"Cambio de contraceña",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, CambioContra.class );
            startActivity(i);
            finish();
        }
        if (id==R.id.mnuMAutor) {
            Intent i = new Intent(this, MAutor.class );
            startActivity(i);
            finish();
        }

        if (id==R.id.mnuMLibro) {
            Intent i = new Intent(this, MLibro.class );
            startActivity(i);
            finish();
        }

        if (id==R.id.mnuSalir) {
            Toast.makeText(this,"Se seleccionó la opción Salir",Toast.LENGTH_LONG).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mautor);

        txtid = (EditText)findViewById(R.id.etID);
        txtnombres = (EditText)findViewById(R.id.etNombresAutor);
        txtapellidos = (EditText)findViewById(R.id.etApellidosAutor);
        txtpais = (EditText)findViewById(R.id.etPais);
        txtcorreo = (EditText)findViewById(R.id.etCorreoAutor);
        txtBuscarID = (EditText)findViewById(R.id.etBuscarID);

        RegistrarAutor=(Button)findViewById(R.id.btnRegistroAutor);
        RegistrarAutor.setOnClickListener(this);

        BuscarAutor=(Button)findViewById(R.id.btnBuscarAutor);
        ModificarAutor=(Button)findViewById(R.id.btnModificarAutor);
        EliminarAutor=(Button)findViewById(R.id.btnEliminarAutor);

        ModificarAutor.setEnabled(false);
        EliminarAutor.setEnabled(false);

        //evento en los botones
        BuscarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread tr = new Thread(){
                    public void run(){


                        final String resultado = buscarIDGET( txtBuscarID.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r = obtDatosJSON(resultado);
                                if (r>0){
                                    try {
                                        JSONArray jsonArray = new JSONArray(resultado);
                                        JSONObject obj = jsonArray.getJSONObject(0);
                                        String id = obj.getString("idautor");
                                        String nombres = obj.getString("nombres");
                                        String apellidos = obj.getString("apellidos");
                                        String pais = obj.getString("pais");
                                        String correo = obj.getString("correo");
                                        txtid.setText(id);
                                        txtnombres.setText(nombres);
                                        txtapellidos.setText(apellidos);
                                        txtpais.setText(pais);
                                        txtcorreo.setText(correo);

                                        RegistrarAutor.setEnabled(false);
                                        ModificarAutor.setEnabled(true);
                                        EliminarAutor.setEnabled(true);
                                        txtid.setEnabled(false);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"No se encontraron Autores",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    };
                };
                tr.start();
            }
        });
        ModificarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtid.setEnabled(false);
                Thread tr = new Thread(){
                    public void run(){
                        final String resultado = modificardatosGET( txtid.getText().toString(), txtnombres.getText().toString(), txtapellidos.getText().toString(), txtpais.getText().toString(), txtcorreo.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("El resultado oficial es : "+resultado);
                                String respuestaEvaluar = resultado;
                                if(respuestaEvaluar.equals("Autor Modificado")){

                                    ModificarAutor.setEnabled(false);
                                    EliminarAutor.setEnabled(false);
                                    RegistrarAutor.setEnabled(true);
                                    txtid.setEnabled(true);
                                    txtid.setText("");
                                    txtnombres.setText("");
                                    txtapellidos.setText("");
                                    txtpais.setText("");
                                    txtcorreo.setText("");

                                    Toast.makeText(getApplicationContext(),"Autor modificado",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Autor no modificado",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    };
                };
                tr.start();
            }
        });
        EliminarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarAutor.setEnabled(false);
                txtid.setEnabled(false);
                Thread tr = new Thread(){
                    public void run(){
                        final String resultado = eliminardatosGET( txtid.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("El resultado oficial es : "+resultado);
                                String respuestaEvaluar = resultado;
                                if(respuestaEvaluar.equals("Autor Eliminado")){

                                    ModificarAutor.setEnabled(false);
                                    EliminarAutor.setEnabled(false);
                                    RegistrarAutor.setEnabled(true);
                                    txtid.setEnabled(true);
                                    txtid.setText("");
                                    txtnombres.setText("");
                                    txtapellidos.setText("");
                                    txtpais.setText("");
                                    txtcorreo.setText("");

                                    Toast.makeText(getApplicationContext(),"Autor eliminado",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Autor no eliminado",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    };
                };
                tr.start();
            }
        });

    }

    @Override
    public void onClick(View view) {
        Thread tr = new Thread(){
            public void run(){
                final String resultado = enviarDatosGET( txtid.getText().toString(), txtnombres.getText().toString(), txtapellidos.getText().toString(), txtpais.getText().toString(), txtcorreo.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El resultado oficial es : "+resultado);
                        String respuestaEvaluar = resultado;
                        if(respuestaEvaluar.equals("Autor Creado")){
                            Toast.makeText(getApplicationContext(),"Autor registrado",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Autor no registrado o ya existe",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };
        tr.start();
    }
    public String enviarDatosGET(String id, String nombres,String apellidos, String pais, String correo){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"RegistroAutor.php?id="+id+"&nombres="+nombres+"&apellidos="+apellidos+"&pais="+pais+"&correo="+correo);
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
    public String modificardatosGET(String id, String nombres,String apellidos, String pais, String correo){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"ModificarAutor.php?id="+id+"&nombres="+nombres+"&apellidos="+apellidos+"&pais="+pais+"&correo="+correo);
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

    public String eliminardatosGET(String id){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"EliminarAutor.php?id="+id);
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

    public String buscarIDGET(String id){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"BuscarAutor.php?id="+id);
            HttpURLConnection connection =(HttpURLConnection )url.openConnection();
            respuesta = connection.getResponseCode();
            System.out.println("--------------respuestabuscar1: "+respuesta+"--------------------------------");
            if(respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea=reader.readLine()) != null){
                    resul.append(linea);
                    System.out.println("--------------respuestabuscar2: "+resul.toString()+"--------------------------------");
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
