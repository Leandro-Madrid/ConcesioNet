package com.madridramos.concesionet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.AutoDAO;
import com.madridramos.concesionet.models.Auto;

public class EditarAutoActivity extends AppCompatActivity {

    private EditText marcaEditText, modeloEditText, anioEditText, precioEditText;
    private Button guardarButton;
    private int idAuto;
    private int idVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_auto);

        // Inicializar vistas
        marcaEditText = findViewById(R.id.marca);
        modeloEditText = findViewById(R.id.modelo);
        anioEditText = findViewById(R.id.anio);
        precioEditText = findViewById(R.id.precio);
        guardarButton = findViewById(R.id.guardarauto);

        // Obtener ID del auto a editar desde el Intent
        idAuto = getIntent().getIntExtra("id_auto", 0);
        // Obtener ID del vendedor desde el Intent
        idVendedor = getIntent().getIntExtra("id_vendedor", 0);

        // Configurar evento de clic para el botón Guardar
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String marca = marcaEditText.getText().toString().trim();
                String modelo = modeloEditText.getText().toString().trim();
                int anio = Integer.parseInt(anioEditText.getText().toString().trim());
                double precio = Double.parseDouble(precioEditText.getText().toString().trim());

                // Crear el objeto Auto actualizado
                Auto autoActualizado = new Auto(idAuto, marca, modelo, anio, precio, idVendedor);

                // Inicializar el DAO y actualizar el auto
                AutoDAO autoDAO = new AutoDAO(getApplicationContext());
                boolean actualizado = autoDAO.actualizarAuto(autoActualizado);

                if (actualizado) {
                    Toast.makeText(EditarAutoActivity.this, "Auto actualizado correctamente", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EditarAutoActivity.this, "Error al actualizar el auto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
