package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.adapters.AutoAdapter;
import com.madridramos.concesionet.controllers.AutoDAO;
import com.madridramos.concesionet.models.Auto;

import java.util.List;

public class VerAutosActivity extends AppCompatActivity {

    private ListView listViewAutos;
    private Button volverInicioButton;
    private AutoDAO autoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_autos);

        // Inicializar vistas y DAO
        listViewAutos = findViewById(R.id.listViewAutos);
        volverInicioButton = findViewById(R.id.volverInicioButton);
        autoDAO = new AutoDAO(getApplicationContext());

        // Configurar evento de clic en el botón Volver al Inicio
        volverInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVendedorActivity();
            }
        });

        // Mostrar la lista de autos con opción de ver detalles
        mostrarListaAutos();
    }

    private void mostrarListaAutos() {
        // Obtener y mostrar la lista de todos los autos desde la base de datos
        List<Auto> listaAutos = autoDAO.obtenerTodosLosAutos();

        // Adaptador personalizado para la lista de autos con opción de ver detalles
        AutoAdapter adapter = new AutoAdapter(VerAutosActivity.this, listaAutos, true); // true para mostrar botón de ver detalles
        listViewAutos.setAdapter(adapter);

        // Configurar evento de clic en un elemento de la lista
        listViewAutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el auto seleccionado
                Auto autoSeleccionado = listaAutos.get(position);

                // Abrir AutoIndividualActivity para ver detalles del auto seleccionado
                Intent intent = new Intent(VerAutosActivity.this, AutoIndividualActivity.class);
                intent.putExtra("id_auto", autoSeleccionado.getIdAuto());
                startActivity(intent);
            }
        });
    }

    // Método para abrir VendedorActivity
    private void abrirVendedorActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpiar el stack de actividades
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (autoDAO != null) {
            autoDAO.close();
        }
    }
}


