package com.madridramos.concesionet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.VendedorDAO;
import com.madridramos.concesionet.models.Vendedor;

public class EditarVendedorActivity extends AppCompatActivity {

    private EditText nombreEditText, apellidoEditText, telefonoEditText;
    private Button guardarButton;
    private VendedorDAO vendedorDAO;
    private int idVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vendedor);

        // Inicializar vistas y DAO
        nombreEditText = findViewById(R.id.nombre);
        apellidoEditText = findViewById(R.id.apellido);
        telefonoEditText = findViewById(R.id.telefono);
        guardarButton = findViewById(R.id.guardarvendedor);
        vendedorDAO = new VendedorDAO(getApplicationContext());

        // Obtener ID del vendedor actual
        idVendedor = obtenerIdVendedorActual();

        // Obtener y mostrar datos del vendedor
        Vendedor vendedor = vendedorDAO.obtenerVendedorPorId(idVendedor);
        if (vendedor != null) {
            nombreEditText.setText(vendedor.getNombre());
            apellidoEditText.setText(vendedor.getApellido());
            telefonoEditText.setText(vendedor.getTelefono());
        }

        // Configurar evento de clic para el botón Guardar
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos datos del vendedor
                String nuevoNombre = nombreEditText.getText().toString().trim();
                String nuevoApellido = apellidoEditText.getText().toString().trim();
                String nuevoTelefono = telefonoEditText.getText().toString().trim();

                // Verificar que los campos no estén vacíos
                if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoTelefono.isEmpty()) {
                    Toast.makeText(EditarVendedorActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Actualizar los datos del vendedor en la base de datos utilizando VendedorDAO
                Vendedor vendedorActualizado = new Vendedor(idVendedor, nuevoNombre, nuevoApellido, nuevoTelefono, vendedor.getPassword());
                boolean actualizado = vendedorDAO.actualizarVendedor(vendedorActualizado);

                if (actualizado) {
                    Toast.makeText(EditarVendedorActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(EditarVendedorActivity.this, "Error al actualizar los datos. Inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int obtenerIdVendedorActual() {
        //
        return 1;
    }
}
