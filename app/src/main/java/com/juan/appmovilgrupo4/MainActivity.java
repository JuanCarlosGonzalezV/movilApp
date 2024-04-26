package com.juan.appmovilgrupo4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICATION";
    private final static int NOTIFICATION_ID = 0;

    Button btnIngresa, btnRegistrar;
    EditText txtusuario, txtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtusuario = findViewById(R.id.textUsuario);
        txtpass = findViewById(R.id.textpass);

        btnRegistrar = (Button) findViewById(R.id.btn_volver);
        btnIngresa = findViewById(R.id.btnEntrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Su registro fue exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), registrar_activity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });


    }
    public void ingresa(View v){
        String usuarioIngresado = txtusuario.getText().toString();
        String contraseñaIngresada = txtpass.getText().toString();

        if (validarCredenciales(usuarioIngresado, contraseñaIngresada)) {
            // Las credenciales son válidas
            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            createNotificationChannel();
            crerNotificacion();

            // Luego puedes iniciar la actividad principal o realizar otras acciones necesarias
            Intent mainIntent = new Intent(this, MenuActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else {
            // Las credenciales son inválidas
            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validarCredenciales(String usuario, String contraseña) {
        try {
            FileInputStream fileInputStream = openFileInput("credenciales.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuilder.append(linea).append('\n');
            }
            fileInputStream.close();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String usuarioJSON = jsonObject.getString("usuario");
                String contraseñaJSON = jsonObject.getString("contraseña");

                // Compara el usuario y contraseña ingresados con los almacenados en el JSON
                if (usuario.equals(usuarioJSON) && contraseña.equals(contraseñaJSON)) {
                    return true; // Credenciales válidas
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Credenciales inválidas
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void crerNotificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_cake_24);
        builder.setContentTitle("Notificacion Grupo 4");
        builder.setContentText("Has ingresado a Nuestro canal");
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
}