package com.madridramos.concesionet.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.madridramos.concesionet.models.Auto;

import java.util.ArrayList;
import java.util.List;

public class AutoDAO {

    private DatabaseHelper dbHelper;

    public AutoDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    // Método para abrir la conexión a la base de datos
    public void open() {
        dbHelper.getWritableDatabase();
    }

    // Método para cerrar la conexión a la base de datos
    public void close() {
        dbHelper.close();
    }

    // Método para obtener todos los autos
    public List<Auto> obtenerTodosLosAutos() {
        List<Auto> autos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_AUTO,
                    new String[]{DatabaseHelper.KEY_AUTO_ID, DatabaseHelper.KEY_MARCA, DatabaseHelper.KEY_MODELO,
                            DatabaseHelper.KEY_ANIO, DatabaseHelper.KEY_PRECIO, DatabaseHelper.KEY_ID_VENDEDOR_FK},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Auto auto = new Auto();
                    auto.setIdAuto(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_AUTO_ID)));
                    auto.setMarca(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MARCA)));
                    auto.setModelo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MODELO)));
                    auto.setAnio(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ANIO)));
                    auto.setPrecio(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_PRECIO)));
                    auto.setIdVendedor(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID_VENDEDOR_FK)));

                    autos.add(auto);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al obtener autos: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return autos;
    }

    // Método para obtener un auto por su ID
    public Auto obtenerAutoPorId(int idAuto) {
        Auto auto = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_AUTO,
                    new String[]{DatabaseHelper.KEY_AUTO_ID, DatabaseHelper.KEY_MARCA, DatabaseHelper.KEY_MODELO,
                            DatabaseHelper.KEY_ANIO, DatabaseHelper.KEY_PRECIO, DatabaseHelper.KEY_ID_VENDEDOR_FK},
                    DatabaseHelper.KEY_AUTO_ID + "=?",
                    new String[]{String.valueOf(idAuto)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                auto = new Auto();
                auto.setIdAuto(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_AUTO_ID)));
                auto.setMarca(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MARCA)));
                auto.setModelo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MODELO)));
                auto.setAnio(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ANIO)));
                auto.setPrecio(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_PRECIO)));
                auto.setIdVendedor(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID_VENDEDOR_FK)));
            }
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al obtener auto por ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return auto;
    }

    // Método para actualizar un auto
    public boolean actualizarAuto(Auto auto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_MARCA, auto.getMarca());
        values.put(DatabaseHelper.KEY_MODELO, auto.getModelo());
        values.put(DatabaseHelper.KEY_ANIO, auto.getAnio());
        values.put(DatabaseHelper.KEY_PRECIO, auto.getPrecio());
        values.put(DatabaseHelper.KEY_ID_VENDEDOR_FK, auto.getIdVendedor());

        int rowsAffected = 0;

        try {
            rowsAffected = db.update(DatabaseHelper.TABLE_AUTO, values,
                    DatabaseHelper.KEY_AUTO_ID + "=?",
                    new String[]{String.valueOf(auto.getIdAuto())});
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al actualizar auto: " + e.getMessage());
        } finally {
            db.close();
        }

        return rowsAffected > 0;
    }

    // Método para eliminar un auto por su ID
    public boolean eliminarAuto(int idAuto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = 0;

        try {
            rowsAffected = db.delete(DatabaseHelper.TABLE_AUTO,
                    DatabaseHelper.KEY_AUTO_ID + "=?",
                    new String[]{String.valueOf(idAuto)});
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al eliminar auto: " + e.getMessage());
        } finally {
            db.close();
        }

        return rowsAffected > 0;
    }

    // Método para agregar un nuevo auto a la base de datos
    public long agregarAuto(Auto auto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_MARCA, auto.getMarca());
        values.put(DatabaseHelper.KEY_MODELO, auto.getModelo());
        values.put(DatabaseHelper.KEY_ANIO, auto.getAnio());
        values.put(DatabaseHelper.KEY_PRECIO, auto.getPrecio());
        values.put(DatabaseHelper.KEY_ID_VENDEDOR_FK, auto.getIdVendedor());

        long idNuevoAuto = -1;

        try {
            idNuevoAuto = db.insert(DatabaseHelper.TABLE_AUTO, null, values);
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al agregar auto: " + e.getMessage());
        } finally {
            db.close();
        }

        return idNuevoAuto;
    }

    // Método para obtener autos por ID de vendedor
    public List<Auto> obtenerAutosPorVendedor(int idVendedor) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Auto> listaAutos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_AUTO,
                    new String[]{DatabaseHelper.KEY_AUTO_ID, DatabaseHelper.KEY_MARCA, DatabaseHelper.KEY_MODELO,
                            DatabaseHelper.KEY_ANIO, DatabaseHelper.KEY_PRECIO, DatabaseHelper.KEY_ID_VENDEDOR_FK},
                    DatabaseHelper.KEY_ID_VENDEDOR_FK + "=?",
                    new String[]{String.valueOf(idVendedor)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Auto auto = new Auto();
                    auto.setIdAuto(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_AUTO_ID)));
                    auto.setMarca(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MARCA)));
                    auto.setModelo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_MODELO)));
                    auto.setAnio(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ANIO)));
                    auto.setPrecio(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_PRECIO)));
                    auto.setIdVendedor(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID_VENDEDOR_FK)));

                    listaAutos.add(auto);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("AutoDAO", "Error al obtener autos por vendedor: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaAutos;
    }
}
