package com.madridramos.concesionet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.controllers.VendedorDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button buttonLogin;
    private VendedorDAO vendedorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas y DAO
        editTextUsuario = findViewById(R.id.nombre);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.ingresar);
        vendedorDAO = new VendedorDAO(getApplicationContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = editTextUsuario.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validar las credenciales del vendedor
                boolean credencialesValidas = vendedorDAO.validarCredenciales(nombreUsuario, password);

                if (credencialesValidas) {
                    // Guardar nombre de usuario y contraseña en SharedPreferences para uso posterior
                    guardarCredenciales(nombreUsuario, password);

                    // Abrir la actividad del vendedor
                    abrirActividadVendedor(nombreUsuario, password);
                } else {
                    // Mostrar mensaje de error de autenticación
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón "Volver al Inicio"
        Button volverInicioButton = findViewById(R.id.volverInicioButton);
        volverInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAlInicio();
            }
        });
    }

    // Método para guardar las credenciales en SharedPreferences
    private void guardarCredenciales(String nombreUsuario, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombreUsuario", nombreUsuario);
        editor.putString("password", password);
        editor.apply();
    }

    // Método para abrir la actividad del vendedor
    private void abrirActividadVendedor(String nombreUsuario, String password) {
        Intent intent = new Intent(LoginActivity.this, VendedorActivity.class);
        intent.putExtra("nombreUsuario", nombreUsuario);
        intent.putExtra("password", password);
        startActivity(intent);
        finish(); // Finalizar LoginActivity para que no se pueda volver atrás con el botón de retroceso
    }

    // Método para volver al inicio
    private void volverAlInicio() {
        // Aquí puedes implementar la lógica para volver al inicio
        // Por ejemplo, abrir una actividad de inicio o cerrar sesión
        // Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
        // startActivity(intent);
        finish(); // Finalizar LoginActivity si es necesario
    }
}
