package com.madridramos.concesionet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.madridramos.concesionet.adapters.AutoAdapter;
import com.madridramos.concesionet.controllers.AutoDAO;
import com.madridramos.concesionet.models.Auto;

import java.util.List;

public class ListaVendedorActivity extends AppCompatActivity {

    private ListView listViewAutos;
    private Button agregarAutoButton;
    private Button volverInicioButton;
    private AutoDAO autoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vendedor);

        // Inicializar vistas y DAO
        listViewAutos = findViewById(R.id.listViewAutos);
        agregarAutoButton = findViewById(R.id.agregarAuto);
        volverInicioButton = findViewById(R.id.volverInicioButton);
        autoDAO = new AutoDAO(getApplicationContext());

        // Configurar evento de clic en el botón Agregar auto
        agregarAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ListaVendedorActivity.this, "Agregar nuevo auto", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListaVendedorActivity.this, AgregarAutoActivity.class);
                startActivity(intent);
            }
        });

        // Configurar evento de clic en el botón Volver al Inicio
        volverInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí abrir VendedorActivity
                abrirVendedorActivity();
            }
        });

        // Mostrar la lista de autos con opciones de editar y borrar
        mostrarListaAutos();
    }

    private void mostrarListaAutos() {
        // Obtener y mostrar la lista de todos los autos desde la base de datos
        List<Auto> listaAutos = autoDAO.obtenerTodosLosAutos();

        // Adaptador personalizado para la lista de autos con opciones de editar y borrar
        AutoAdapter adapter = new AutoAdapter(ListaVendedorActivity.this, listaAutos, false); // false para mostrar botones de editar y borrar
        listViewAutos.setAdapter(adapter);

        // Configurar evento de clic en un elemento de la lista
        listViewAutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el auto seleccionado
                Auto autoSeleccionado = listaAutos.get(position);

                // Mostrar un mensaje con la marca del auto seleccionado (ejemplo)
                Toast.makeText(ListaVendedorActivity.this, "Has seleccionado: " + autoSeleccionado.getMarca(), Toast.LENGTH_SHORT).show();

                // Ejemplo: Abrir una nueva actividad para editar detalles del auto
                Intent intent = new Intent(ListaVendedorActivity.this, EditarAutoActivity.class);
                intent.putExtra("id_auto", autoSeleccionado.getIdAuto());
                startActivity(intent);
            }
        });
    }

    // Método para abrir VendedorActivity
    private void abrirVendedorActivity() {
        Intent intent = new Intent(this, VendedorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpiar el stack de actividades
        startActivity(intent);
    }
}




