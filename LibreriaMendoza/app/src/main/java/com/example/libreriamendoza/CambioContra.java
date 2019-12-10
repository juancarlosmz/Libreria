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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CambioContra extends AppCompatActivity implements View.OnClickListener{
    public Button Cambiocontra;
    public EditText txtcantigua,txtcnueva,txtcconfirmar;
    String MyUrl = "http://192.168.0.13:50/Libreria/libros/";
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_contra);
        txtcantigua = (EditText)findViewById(R.id.etCantigua);
        txtcnueva = (EditText)findViewById(R.id.etCnueva);
        txtcconfirmar = (EditText)findViewById(R.id.etCconfirmar);
        Cambiocontra=(Button)findViewById(R.id.btnCambiocontra);
        Cambiocontra.setOnClickListener(this);

        Bundle datos = this.getIntent().getExtras();
        usuario = datos.getString("cod");

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
    public void onClick(View view) {
        Thread tr = new Thread(){
            public void run(){
                final String resultado = enviarDatosGET( usuario,txtcantigua.getText().toString(), txtcnueva.getText().toString(), txtcconfirmar.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("El resultado oficial es : "+resultado);
                        String respuestaEvaluar = resultado;
                        if(respuestaEvaluar.equals("Usuario modificado")){
                            Toast.makeText(getApplicationContext(),"Contraceña cambiada",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),Sistema.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Contraceña incorrecta vuelva a intentar",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            };
        };
        tr.start();
    }

    public String enviarDatosGET(String usu, String cantigua, String cnueva,String cconfirmar){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = new StringBuilder();
        try{
            url = new URL(MyUrl+"CambioContra.php?usu="+usu+"&cantigua="+cantigua+"&cnueva="+cnueva+"&cconfirmar="+cconfirmar);
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
