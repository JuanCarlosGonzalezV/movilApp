package com.juan.appmovilgrupo4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_regresar = findViewById(R.id.btn_volver);


    }


    //metodo que llama el boton para ir a la vista de prestamos en el onclick
    public void prestamo(View v){
        Intent intent = new Intent(getApplicationContext(), ActivityPrestamo.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    public void nosotros(View v){
        Intent intent = new Intent(getApplicationContext(), ActivityNosotros.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}