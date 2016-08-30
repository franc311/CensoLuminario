package com.franzhezco.censoluminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VentanaPrincipalActivity extends AppCompatActivity {

    private Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCensoActivity(view);
            }
        });
    }

    public void mostrarCensoActivity(View view) {
        Intent censoIntent = new Intent(this, CensoActivity.class);
        startActivity(censoIntent);
    }
}
