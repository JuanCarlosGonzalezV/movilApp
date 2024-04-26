package com.juan.appmovilgrupo4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class registrar_activity extends AppCompatActivity {
    private EditText textUsuario, passTxt;
    Button btnVovler;

    private List<String> usuarios = new ArrayList<>();
    private List<String> contraseñas = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);



        //campos de texto
        textUsuario  = findViewById(R.id.textUsuario);
        passTxt = findViewById(R.id.textpass);

        //inicializo las variables de texto

        //boton enviar
        btnVovler = findViewById(R.id.btn_volver);

        btnVovler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario= textUsuario.getText().toString();
                String contraseña=passTxt.getText().toString();

                usuarios.add(usuario);
                contraseñas.add(contraseña);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                crearArchivoJSON(usuarios,contraseñas);
                //leerArchivoJSON();


            }

            // Método para crear un archivo JSON con la lista de usuarios y contraseñas
            void crearArchivoJSON(List<String> listaUsuarios, List<String> listaContraseñas) {
                try {
                    // Crear un JSONArray para almacenar la lista de usuarios
                    JSONArray jsonArray = new JSONArray();

                    // Recorrer las listas de usuarios y contraseñas y agregar cada par al JSONArray
                    for (int i = 0; i < listaUsuarios.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("usuario", listaUsuarios.get(i));
                        jsonObject.put("contraseña", listaContraseñas.get(i));
                        jsonArray.put(jsonObject);
                    }

                    // Guardar el JSONArray en un archivo en el almacenamiento interno
                    String jsonString = jsonArray.toString();
                    FileOutputStream fileOutputStream = openFileOutput("credenciales.json", Context.MODE_PRIVATE);
                    fileOutputStream.write(jsonString.getBytes());
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(), "Uusario  creado exitosamente", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al crear el archivo JSON", Toast.LENGTH_LONG).show();
                }
            }

            // Método para leer el archivo JSON y obtener la lista de usuarios
            private void leerArchivoJSON() {
                try {
                    // Leer el archivo JSON desde el almacenamiento interno
                    FileInputStream fileInputStream = openFileInput("credenciales.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String linea;
                    while ((linea = bufferedReader.readLine()) != null) {
                        stringBuilder.append(linea).append('\n');
                    }
                    fileInputStream.close();

                    // Convertir el JSON en un JSONArray
                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                    // Recorrer el JSONArray y obtener cada usuario
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String usuario = jsonObject.getString("usuario");
                        String contraseña = jsonObject.getString("contraseña");
                        Log.d("Usuario: ", usuario);
                        Log.d("Contraseña: ", contraseña);
                    }
                    Toast.makeText(getApplicationContext(), "Archivo JSON leído exitosamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al leer el archivo JSON", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}