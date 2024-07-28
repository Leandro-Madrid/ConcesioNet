package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.VendedorDAO;
import com.madridramos.concesionet.models.Vendedor;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombreEditText, apellidoEditText, telefonoEditText, passwordEditText;
    private Button registrarButton, volverInicioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        nombreEditText = findViewById(R.id.nombre);
        apellidoEditText = findViewById(R.id.apellido);
        telefonoEditText = findViewById(R.id.telefono);
        passwordEditText = findViewById(R.id.password);
        registrarButton = findViewById(R.id.registrarButton);
        volverInicioButton = findViewById(R.id.volverInicioButton);

        // Configurar evento de clic para el botón Registrar
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de los campos de texto
                String nombre = nombreEditText.getText().toString().trim();
                String apellido = apellidoEditText.getText().toString().trim();
                String telefono = telefonoEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validar los datos
                if (validarDatosRegistro(nombre, apellido, telefono, password)) {
                    // Crear un nuevo objeto Vendedor con los datos ingresados por el usuario
                    Vendedor nuevoVendedor = new Vendedor(nombre, apellido, telefono, password);

                    // Guardar el nuevo vendedor en la base de datos utilizando VendedorDAO
                    VendedorDAO vendedorDAO = new VendedorDAO(getApplicationContext());
                    long idVendedor = vendedorDAO.agregarVendedor(nuevoVendedor);

                    if (idVendedor != -1) {
                        Toast.makeText(RegistroActivity.this, "Registro exitoso. ID: " + idVendedor, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Finaliza RegistroActivity para evitar volver atrás con el botón de atrás
                    } else {
                        Toast.makeText(RegistroActivity.this, "Error al registrar. Inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar evento de clic para el botón Volver al inicio
        volverInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validarDatosRegistro(String nombre, String apellido, String telefono, String password) {

        return !nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && !password.isEmpty();
    }
}
