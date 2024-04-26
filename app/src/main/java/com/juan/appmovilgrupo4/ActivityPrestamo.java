package com.juan.appmovilgrupo4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityPrestamo extends AppCompatActivity {
    private TextView resultadoText;
    private EditText txtNombre, txtIdentificacion, txtmonto;
    private Spinner plazoSpinner;
    private Button btnCapturar, btnCalcular, btnmostrar, btnLimpiar;
    ImageButton btn_back, btn_salir;

    private Prestamo prestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);

        //hago referencia a los elementos graficos de la app en xml
        resultadoText = (TextView) findViewById(R.id.resultadoText);
        txtNombre = (EditText) findViewById(R.id.textNombre);
        txtIdentificacion = (EditText) findViewById(R.id.txtIdentificacion);
        txtmonto = (EditText) findViewById(R.id.txtMonto);
        plazoSpinner = (Spinner) findViewById(R.id.plazoSpinner);

        //botones
        btnCapturar = (Button) findViewById(R.id.btnCapturar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnmostrar = (Button) findViewById(R.id.btnMostrar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        btn_back = (ImageButton) findViewById(R.id.btnBack);
        btn_salir = findViewById(R.id.btnSalir);

        btnCalcular.setEnabled(false);
        btnmostrar.setEnabled(false);
        btnLimpiar.setEnabled(false);

        //creo una animacion de color en los recusrsos
        AnimatorSet colorAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.text_color_anim);

        //adapter para llenar spinner plazos-array es un recurso donde estan los numeros 6,12, 18
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.plazos_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plazoSpinner.setAdapter(adapter);


        //-----------------------------------------------------------
        //funcionalidad con los botones
        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                capturarInformacion();
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCuaotasMes();
            }
        });

        btnmostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarinformacion();
                colorAnimation.setTarget(resultadoText);
                colorAnimation.start();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombre.setText("");
                txtIdentificacion.setText("");
                txtmonto.setText("");
                resultadoText.setText("");

                btnCalcular.setEnabled(false);
                btnmostrar.setEnabled(false);
                btnLimpiar.setEnabled(false);
            }
        });
        //-------------- fin botones

        Log.d("MainActivity","----------------------");
        Log.d("MainActivity","Hola Bastardos jajaja");
        Log.d("MainActivity","----------------------");
    }

    private void capturarInformacion() {

        try {

            String nombre = txtNombre.getText().toString();
            String identificacion = txtIdentificacion.getText().toString();
            Double monto = Double.parseDouble(txtmonto.getText().toString());
            int plazo = Integer.parseInt(plazoSpinner.getSelectedItem().toString());

            if(!nombre.isEmpty() & !identificacion.isEmpty() & !monto.equals(null)){
                prestamo = new Prestamo(nombre,identificacion,monto,plazo);
                btnCalcular.setEnabled(true);
                btnmostrar.setEnabled(true);
                btnLimpiar.setEnabled(true);
            }else {
                Toast.makeText(this,"",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this,"Juan Carlos informa: debes Ingresar todos los datos",Toast.LENGTH_LONG).show();
        }
        //String valor = String.valueOf(monto);
        //Log.i("MainActivity",nombre);
        //resultadoTextView.setText(valor);


    }

    public void calcularCuaotasMes(){
        if (prestamo != null){
            resultadoText.setText("interes por mes : "+String.valueOf(prestamo.CalcuarCuotas()));
        }
    }

    public void mostrarinformacion(){
        resultadoText.setText(prestamo.mostrarInformacion());
        //resultadoTextView.setText(prestamo.mostrarInformacion().toString());
    }

    public void regresar(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void salir(View v){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Deseas cerrar sesión ?");
        alert.setMessage("Volveras a la pagina de inicio de sesión");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancelado",Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();

    }
}