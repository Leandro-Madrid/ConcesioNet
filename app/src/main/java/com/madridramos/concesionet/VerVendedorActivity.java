package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.VendedorDAO;
import com.madridramos.concesionet.models.Vendedor;

public class VerVendedorActivity extends AppCompatActivity {

    private TextView nombreTextView, apellidoTextView, telefonoTextView;
    private Button volverListaButton, volverInicioButton;
    private VendedorDAO vendedorDAO;
    private int idAuto; // ID del auto del cual se quiere ver el vendedor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_vendedor);

        // Inicializar DAO y obtener ID de auto
        vendedorDAO = new VendedorDAO(this);
        idAuto = getIntent().getIntExtra("id_auto", 0);

        // Log para verificar que se ha recibido correctamente el ID del auto
        Log.d("VerVendedorActivity", "ID Auto recibido: " + idAuto);

        // Referenciar vistas
        nombreTextView = findViewById(R.id.nombre);
        apellidoTextView = findViewById(R.id.apellido);
        telefonoTextView = findViewById(R.id.telefono);
        volverListaButton = findViewById(R.id.volverListaButton);
        volverInicioButton = findViewById(R.id.volverInicioButton);

        // Cargar datos del vendedor asociado al auto
        cargarDatosVendedor();

        // Configurar listeners de botones
        volverListaButton.setOnClickListener(v -> volverAListaAutos());
        volverInicioButton.setOnClickListener(v -> volverAlInicio());
    }


    private void cargarDatosVendedor() {
        // Obtener el vendedor asociado al auto
        Vendedor vendedor = vendedorDAO.obtenerVendedorPorIdAuto(idAuto);

        // Mostrar los datos del vendedor en la interfaz gráfica
        if (vendedor != null) {
            nombreTextView.setText(getString(R.string.nombre_formato, vendedor.getNombre()));
            apellidoTextView.setText(getString(R.string.apellido_formato, vendedor.getApellido()));
            telefonoTextView.setText(getString(R.string.telefono_formato, vendedor.getTelefono()));
        } else {
            // Manejar el caso donde no se encuentra el vendedor
            nombreTextView.setText(getString(R.string.nombre_no_encontrado));
            apellidoTextView.setText(getString(R.string.apellido_no_encontrado));
            telefonoTextView.setText(getString(R.string.telefono_no_encontrado));
        }
    }

    private void volverAListaAutos() {
        // Implementar lógica para volver a la lista de autos disponibles
        Intent intent = new Intent(this, VerAutosActivity.class);
        startActivity(intent);
        finish();
    }

    private void volverAlInicio() {
        // Implementar lógica para volver al inicio
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
