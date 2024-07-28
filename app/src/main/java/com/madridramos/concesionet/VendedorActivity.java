package com.madridramos.concesionet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.VendedorDAO;
import com.madridramos.concesionet.models.Vendedor;


public class VendedorActivity extends AppCompatActivity {

    private TextView nombreTextView, apellidoTextView, telefonoTextView;
    private Button misAutosButton, editarVendedorButton, eliminarCuentaButton, cerrarSesionButton;
    private Vendedor vendedor;
    private VendedorDAO vendedorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);

        // Inicializar vistas
        nombreTextView = findViewById(R.id.nombre);
        apellidoTextView = findViewById(R.id.apellido);
        telefonoTextView = findViewById(R.id.telefono);
        misAutosButton = findViewById(R.id.misAutos);
        editarVendedorButton = findViewById(R.id.editarVendedor);
        eliminarCuentaButton = findViewById(R.id.eliminarCuenta);
        cerrarSesionButton = findViewById(R.id.cerrarSesion);

        // Inicializar DAO para acceder a la base de datos
        vendedorDAO = new VendedorDAO(getApplicationContext());

        // Obtener credenciales del usuario almacenadas en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String nombreUsuario = sharedPreferences.getString("nombreUsuario", null);
        String password = sharedPreferences.getString("password", null);

        if (nombreUsuario != null && password != null) {
            // Validar las credenciales y obtener los datos del vendedor
            vendedor = vendedorDAO.validarCredencialesYObtenerDatos(nombreUsuario, password);

            if (vendedor != null) {
                // Mostrar los datos del vendedor en los TextView
                nombreTextView.setText(vendedor.getNombre());
                apellidoTextView.setText(vendedor.getApellido());
                telefonoTextView.setText(vendedor.getTelefono());
            } else {
                // Mostrar mensaje de error y redirigir a LoginActivity
                Toast.makeText(this, "Error: no se pudo obtener la información del vendedor.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VendedorActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            // No hay credenciales válidas, redirigir a LoginActivity
            Intent intent = new Intent(VendedorActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Configurar eventos de clic para los botones

        // Botón Mis Autos
        misAutosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir actividad para ver los autos del vendedor (ListaVendedorActivity)
                Intent intent = new Intent(VendedorActivity.this, ListaVendedorActivity.class);
                intent.putExtra("vendedor", vendedor); // Pasar el vendedor actual al siguiente Activity
                startActivity(intent);
            }
        });


        // Botón Editar Vendedor
        editarVendedorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir actividad para editar los datos del vendedor (EditarVendedorActivity)
                Intent intent = new Intent(VendedorActivity.this, EditarVendedorActivity.class);
                startActivity(intent);
            }
        });

        // Botón Eliminar Cuenta
        eliminarCuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar cuenta del vendedor y todos los datos asociados
                eliminarCuentaVendedor();
            }
        });

        // Botón Cerrar Sesión
        cerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión y redirigir a LoginActivity
                cerrarSesion();
            }
        });
    }

    // Método para eliminar la cuenta del vendedor y todos los datos asociados
    private void eliminarCuentaVendedor() {
        // Verificar que se haya obtenido el vendedor correctamente
        if (vendedor != null) {
            boolean exitoEliminar = vendedorDAO.eliminarVendedor(vendedor.getId());

            if (exitoEliminar) {
                // Mostrar mensaje de éxito
                Toast.makeText(VendedorActivity.this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();

                // Redirigir a LoginActivity después de eliminar la cuenta
                cerrarSesion();
            } else {
                // Mostrar mensaje de error
                Toast.makeText(VendedorActivity.this, "Error al eliminar cuenta. Inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(VendedorActivity.this, "Error: no se pudo obtener la información del vendedor.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para cerrar sesión y redirigir a LoginActivity
    private void cerrarSesion() {
        // Limpiar SharedPreferences u otras acciones necesarias al cerrar sesión
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Limpiar todas las preferencias
        editor.apply();

        Intent intent = new Intent(VendedorActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
