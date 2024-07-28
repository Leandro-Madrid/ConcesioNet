package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.AutoDAO;
import com.madridramos.concesionet.models.Auto;

public class AgregarAutoActivity extends AppCompatActivity {

    private EditText marcaEditText, modeloEditText, anioEditText, precioEditText;
    private Button guardarAutoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);

        // Inicializar vistas
        marcaEditText = findViewById(R.id.marca);
        modeloEditText = findViewById(R.id.modelo);
        anioEditText = findViewById(R.id.anio);
        precioEditText = findViewById(R.id.precio);
        guardarAutoButton = findViewById(R.id.guardarauto);

        // Configurar evento de clic para el botón Guardar
        guardarAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para guardar el nuevo auto
                String marca = marcaEditText.getText().toString().trim();
                String modelo = modeloEditText.getText().toString().trim();
                int anio = Integer.parseInt(anioEditText.getText().toString().trim());
                double precio = Double.parseDouble(precioEditText.getText().toString().trim());

                // Crear un nuevo objeto Auto
                Auto nuevoAuto = new Auto(marca, modelo, anio, precio);

                // Guardar el nuevo auto en la base de datos utilizando AutoDAO
                AutoDAO autoDAO = new AutoDAO(getApplicationContext());
                long idAuto = autoDAO.agregarAuto(nuevoAuto);

                if (idAuto != -1) {
                    Toast.makeText(AgregarAutoActivity.this, "Auto agregado correctamente con ID: " + idAuto, Toast.LENGTH_SHORT).show();

                    // Volver a VendedorActivity después de guardar el auto
                    Intent intent = new Intent(AgregarAutoActivity.this, VendedorActivity.class);
                    startActivity(intent);
                    finish(); // Finalizar AgregarAutoActivity para volver al flujo anterior
                } else {
                    Toast.makeText(AgregarAutoActivity.this, "Error al agregar el auto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

