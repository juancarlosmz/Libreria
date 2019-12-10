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

public class MLibro extends AppCompatActivity implements View.OnClickListener{
    public Button RegistrarLibro,BuscarLibro,ModificarLibro,EliminarLibro;
    public EditText txtid,txttitulo,txtautor,txtedicion,txteditorial,txtpaginas,txtano,txtestado,txtBuscarLibro;

    String MyUrl = "http://192.168.0.13:50/Libreria/libros/Libro/";

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
        setContentView(R.layout.activity_mlibro);

        txtid = (EditText)findViewById(R.id.etIDlibro);
        txttitulo = (EditText)findViewById(R.id.etTituloLibro);
        txtautor = (EditText)findViewById(R.id.etAutorLibro);
        txtedicion = (EditText)findViewById(R.id.etEdicionLibro);
        txteditorial = (EditText)findViewById(R.id.etEditorialLibro);
        txtpaginas = (EditText)findViewById(R.id.etNumPaginasLibro);
        txtano = (EditText)findViewById(R.id.etanoLibro);
        txtestado = (EditText)findViewById(R.id.etEstadoLibro);
        txtBuscarLibro = (EditText)findViewById(R.id.etBuscarLibro);

        RegistrarLibro=(Button)findViewById(R.id.btnRegistroLibro);
        RegistrarLibro.setOnClickListener(this);

        BuscarLibro=(Button)findViewById(R.id.btnBuscarLibro);
        ModificarLibro=(Button)findViewById(R.id.btnModificarLibro);
        EliminarLibro=(Button)findViewById(R.id.btnEliminarLibro);

        ModificarLibro.setEnabled(false);
        EliminarLibro.setEnabled(false);

        //evento en los botones
        BuscarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread(){
                    public void run(){
                        final String resultado = buscarIDGET( txtBuscarLibro.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r = obtDatosJSON(resultado);
                                if (r>0){
                                    try {
                                        JSONArray jsonArray = new JSONArray(resultado);
                                        JSONObject obj = jsonArray.getJSONObject(0);
                                        String id = obj.getString("idlibro");
                                        String tit = obj.getString("titulo");
                                        String idautor = obj.getString("idautor");
                                        String edici = obj.getString("edicion");
                                        String edito = obj.getString("editorial");
                                        String numpag = obj.getString("numpaginas");
                                        String year = obj.getString("year");
                                        String estad = obj.getString("estado");
                                        txtid.setText(id);
                                        txttitulo.setText(tit);
                                        txtautor.setText(idautor);
                                        txtedicion.setText(edici);
                                        txteditorial.setText(edito);
                                        txtpaginas.setText(numpag);
                                        txtano.setText(year);
                                        txtestado.setText(estad);

                                        RegistrarLibro.setEnabled(false);
                                        ModificarLibro.setEnabled(true);
                                        EliminarLibro.setEnabled(true);
                                        txtid.setEnabled(false);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"No se encontraron Libros",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    };
                };
                tr.start();
            }
        });
        ModificarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtid.setEnabled(false);
                Thread tr = new Thread(){
                    public void run(){
                        final String resultado = modificardatosGET( txtid.getText().toString(), txttitulo.getText().toString(), txtautor.getText().toString(), txtedicion.getText().toString(), txteditorial.getText().toString(), txtpaginas.getText().toString(), txtano.getText().toString(), txtestado.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("El resultado oficial es : "+resultado);
                                String respuestaEvaluar = resultado;
                                if(respuestaEvaluar.equals("Libro Modificado")){
                                    ModificarLibro.setEnabled(false);
                                    EliminarLibro.setEnabled(false);
                                    RegistrarLibro.setEnabled(true);
                                    txtid.setEnabled(true);
                                    txtid.setText("");
                                    txttitulo.setText("");
                                    txtautor.setText("");
                                    txtedicion.setText("");
                                    txteditorial.setText("");
                                    txtpaginas.setText("");
                                    txtano.setText("");
                                    txtestado.setText("");

                                    Toast.makeText(getApplicationContext(),"Libro modificado",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Libro no modificado",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    };
                };
                tr.start();
            }
        });
        EliminarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarLibro.setEnabled(false);
                txtid.setEnabled(false);
                Thread tr = new Thread(){
                    public void run(){
                        final String resultado = eliminardatosGET( txtid.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("El resultado oficial es : "+resultado);
                                String respuestaEvaluar = resultado;
                                if(respuestaEvaluar.equals("Libro Eliminado")){
                                    ModificarLibro.setEnabled(false);
                                    EliminarLibro.setEnabled(false);
                                    RegistrarLibro.setEnabled(true);
                                    txtid.setEnabled(true);
                                    txtid.setText("");
                                    txttitulo.setText("");
                                    txtautor.setText("");
                                    txtedicion.setText("");
                                    txteditorial.setText("");
                                    txtpaginas.setText("");
                                    txtano.setText("");
                                    txtestado.setText("");
                                    Toast.makeText(getApplicationContext(),"Libro eliminado",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Libro no eliminado",Toast.LENGTH_LONG).show();
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
                final String resultado = enviarDatosGET( txtid.getText().toString(), txttitulo.getText().toString(), txtautor.getText().toString(), txtedicion.getText().toString(), txteditorial.getText().toString(), txtpaginas.getText().toString(), txtano.getText().toString(), txtestado.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El resultado oficial es : "+resultado);
                        String respuestaEvaluar = resultado;
                        if(respuestaEvaluar.equals("Libro Creado")){
                            Toast.makeText(getApplicationContext(),"Libro registrado",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Libro no registrado o ya existe",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };
        tr.start();
    }

    public String enviarDatosGET(String idlibro, String titulo,String idautor, String edicion, String editorial, String numpaginas, String year, String estado){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"RegistroLibro.php?idlibro="+idlibro+"&titulo="+titulo+"&idautor="+idautor+"&edicion="+edicion+"&editorial="+editorial+"&numpaginas="+numpaginas+"&year="+year+"&estado="+estado);
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
    public String modificardatosGET(String idlibro, String titulo,String idautor, String edicion, String editorial, String numpaginas, String year, String estado){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"ModificarLibro.php?idlibro="+idlibro+"&titulo="+titulo+"&idautor="+idautor+"&edicion="+edicion+"&editorial="+editorial+"&numpaginas="+numpaginas+"&year="+year+"&estado="+estado);
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

    public String eliminardatosGET(String idlibro){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"EliminarLibro.php?idlibro="+idlibro);
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

    public String buscarIDGET(String idlibro){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"BuscarLibro.php?idlibro="+idlibro);
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
