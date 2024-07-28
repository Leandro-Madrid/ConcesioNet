package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.AutoDAO;
import com.madridramos.concesionet.models.Auto;

public class AutoIndividualActivity extends AppCompatActivity {

    private TextView marcaTextView, modeloTextView, anioTextView, precioTextView;
    private Button verVendedorButton, volverListaButton, volverInicioButton;
    private int idAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_individual);

        // Inicializar vistas
        marcaTextView = findViewById(R.id.marca);
        modeloTextView = findViewById(R.id.modelo);
        anioTextView = findViewById(R.id.anio);
        precioTextView = findViewById(R.id.precio);
        verVendedorButton = findViewById(R.id.verVendedorButton);
        volverListaButton = findViewById(R.id.volverListaButton);
        volverInicioButton = findViewById(R.id.volverInicioButton);

        // Obtener el ID del auto desde el Intent
        idAuto = getIntent().getIntExtra("id_auto", 0);

        // Log para verificar que se ha recibido correctamente el ID del auto
        Log.d("AutoIndividualActivity", "ID Auto recibido: " + idAuto);

        // Cargar los detalles del auto utilizando el método correspondiente
        cargarDatosAuto(idAuto);

        // Configurar el evento de clic para el botón Ver Vendedor
        verVendedorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutoIndividualActivity.this, VerVendedorActivity.class);
                intent.putExtra("id_auto", idAuto);
                startActivity(intent);
            }
        });

        // Configurar el evento de clic para el botón Volver a la lista de autos
        volverListaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la lista de autos
                Intent intent = new Intent(AutoIndividualActivity.this, VerAutosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar el evento de clic para el botón Volver al inicio
        volverInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al inicio y limpiar la pila de actividades
                Intent intent = new Intent(AutoIndividualActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity(); // Cierra todas las actividades de esta aplicación
            }
        });
    }

    // Método para cargar los detalles del auto desde la base de datos
    private void cargarDatosAuto(int idAuto) {
        AutoDAO autoDAO = new AutoDAO(getApplicationContext());
        Auto auto = autoDAO.obtenerAutoPorId(idAuto);

        if (auto != null) {
            // Mostrar los detalles del auto en las vistas
            marcaTextView.setText(auto.getMarca());
            modeloTextView.setText(auto.getModelo());
            anioTextView.setText(String.valueOf(auto.getAnio()));
            precioTextView.setText(String.valueOf(auto.getPrecio()));
        } else {
            // Mostrar un mensaje de error o indicar que no se encontraron datos del auto
            Toast.makeText(this, "No se encontraron datos del auto", Toast.LENGTH_SHORT).show();
        }
    }
}
