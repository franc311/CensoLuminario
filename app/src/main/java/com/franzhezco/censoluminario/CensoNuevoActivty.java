package com.franzhezco.censoluminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CensoNuevoActivty extends AppCompatActivity {

    EditText txt;
    Button btnAceptar, btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_censo_nuevo_activty);
        txt = (EditText) findViewById(R.id.censo_nuevo_editText);
        btnAceptar = (Button) findViewById(R.id.censo_nuevo_button_aceptar);
        btnCancelar = (Button) findViewById(R.id.censo_nuevo_button_cancelar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    private void aceptar() {
        if(txt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Escriba el nombre de censo", Toast.LENGTH_SHORT).show();
            txt.findFocus();
            return;
        }
        Intent i = getIntent();
        i.putExtra("returnCensoName", txt.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }

    private void cancelar() {
        Intent i = getIntent();
        setResult(RESULT_CANCELED, i);
        finish();
    }
}
