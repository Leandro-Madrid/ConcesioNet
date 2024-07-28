package com.madridramos.concesionet.controllers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.madridramos.concesionet.models.Vendedor;
import com.madridramos.concesionet.models.Auto;

import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {

    private DatabaseHelper dbHelper;

    public VendedorDAO(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    // Método para obtener un vendedor por su ID
    public Vendedor obtenerVendedorPorId(int idVendedor) {
        Vendedor vendedor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_VENDEDOR,
                    new String[]{DatabaseHelper.KEY_VENDEDOR_ID, DatabaseHelper.KEY_NOMBRE, DatabaseHelper.KEY_APELLIDO,
                            DatabaseHelper.KEY_TELEFONO, DatabaseHelper.KEY_PASSWORD},
                    DatabaseHelper.KEY_VENDEDOR_ID + "=?",
                    new String[]{String.valueOf(idVendedor)}, null, null, null);

            // Verificar si el cursor tiene al menos una fila
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas fuera del bloque if para evitar getColumnIndex cuando el cursor esté cerrado
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_VENDEDOR_ID);
                int nombreIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NOMBRE);
                int apellidoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_APELLIDO);
                int telefonoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_TELEFONO);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PASSWORD);

                // Verificar que los índices sean válidos antes de obtener los valores
                if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && passwordIndex != -1) {
                    vendedor = new Vendedor(
                            cursor.getInt(idIndex),
                            cursor.getString(nombreIndex),
                            cursor.getString(apellidoIndex),
                            cursor.getString(telefonoIndex),
                            cursor.getString(passwordIndex)
                    );
                }
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al obtener vendedor por ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return vendedor;
    }

    // Método para actualizar un vendedor
    public boolean actualizarVendedor(Vendedor vendedor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NOMBRE, vendedor.getNombre());
        values.put(DatabaseHelper.KEY_APELLIDO, vendedor.getApellido());
        values.put(DatabaseHelper.KEY_TELEFONO, vendedor.getTelefono());
        values.put(DatabaseHelper.KEY_PASSWORD, vendedor.getPassword());

        int rowsAffected = 0;

        try {
            rowsAffected = db.update(DatabaseHelper.TABLE_VENDEDOR, values,
                    DatabaseHelper.KEY_VENDEDOR_ID + "=?",
                    new String[]{String.valueOf(vendedor.getId())});
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al actualizar vendedor: " + e.getMessage());
        } finally {
            db.close();
        }

        return rowsAffected > 0;
    }

    // Método para eliminar un vendedor por su ID
    public boolean eliminarVendedor(int idVendedor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = 0;

        try {
            rowsAffected = db.delete(DatabaseHelper.TABLE_VENDEDOR,
                    DatabaseHelper.KEY_VENDEDOR_ID + "=?",
                    new String[]{String.valueOf(idVendedor)});
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al eliminar vendedor: " + e.getMessage());
        } finally {
            db.close();
        }

        return rowsAffected > 0;
    }

    // Método para agregar un nuevo vendedor a la base de datos
    public long agregarVendedor(Vendedor vendedor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NOMBRE, vendedor.getNombre());
        values.put(DatabaseHelper.KEY_APELLIDO, vendedor.getApellido());
        values.put(DatabaseHelper.KEY_TELEFONO, vendedor.getTelefono());
        values.put(DatabaseHelper.KEY_PASSWORD, vendedor.getPassword());

        long idNuevoVendedor = -1;

        try {
            idNuevoVendedor = db.insert(DatabaseHelper.TABLE_VENDEDOR, null, values);
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al agregar vendedor: " + e.getMessage());
        } finally {
            db.close();
        }

        return idNuevoVendedor;
    }

    // Método para validar las credenciales de un vendedor
    public boolean validarCredenciales(String nombreUsuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        boolean credencialesValidas = false;

        try {
            // Escapar comillas simples en el password para evitar problemas de SQL injection
            String passwordEscaped = password.replace("'", "''");

            // Construir la consulta SQL
            String sqlQuery = "SELECT " + DatabaseHelper.KEY_VENDEDOR_ID + " FROM " + DatabaseHelper.TABLE_VENDEDOR +
                    " WHERE " + DatabaseHelper.KEY_NOMBRE + "='" + nombreUsuario + "' AND " +
                    DatabaseHelper.KEY_PASSWORD + "='" + passwordEscaped + "'";

            // Ejecutar la consulta
            cursor = db.rawQuery(sqlQuery, null);

            // Verificar si el cursor tiene exactamente un resultado
            if (cursor != null && cursor.getCount() == 1) {
                credencialesValidas = true;
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al validar credenciales: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return credencialesValidas;
    }

    // Método para obtener autos por vendedor
    public List<Auto> obtenerAutosPorVendedor(int idVendedor) {
        List<Auto> listaAutos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("autos",
                    new String[]{"auto_id", "marca", "modelo", "anio", "precio", "id_vendedor"},
                    "id_vendedor=?",
                    new String[]{String.valueOf(idVendedor)}, null, null, null);

            // Verificar si el cursor tiene al menos una fila
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Obtener los índices de las columnas
                    int idIndex = cursor.getColumnIndex("auto_id");
                    int marcaIndex = cursor.getColumnIndex("marca");
                    int modeloIndex = cursor.getColumnIndex("modelo");
                    int anioIndex = cursor.getColumnIndex("anio");
                    int precioIndex = cursor.getColumnIndex("precio");
                    int idVendedorIndex = cursor.getColumnIndex("id_vendedor");

                    // Verificar que los índices sean válidos antes de obtener los valores
                    if (idIndex != -1 && marcaIndex != -1 && modeloIndex != -1 && anioIndex != -1 && precioIndex != -1 && idVendedorIndex != -1) {
                        // Crear un objeto Auto usando los datos del cursor
                        Auto auto = new Auto(
                                cursor.getInt(idIndex),
                                cursor.getString(marcaIndex),
                                cursor.getString(modeloIndex),
                                cursor.getInt(anioIndex),
                                cursor.getDouble(precioIndex),
                                cursor.getInt(idVendedorIndex)
                        );
                        listaAutos.add(auto);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al obtener autos por vendedor: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaAutos;
    }

    // Método para obtener el ID de un vendedor por nombre de usuario y contraseña
    public int obtenerIdVendedor(String nombreUsuario, String password) {
        int idVendedor = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Escapar comillas simples en el password para evitar problemas de SQL injection
            String passwordEscaped = password.replace("'", "''");

            // Construir la consulta SQL
            String sqlQuery = "SELECT " + DatabaseHelper.KEY_VENDEDOR_ID +
                    " FROM " + DatabaseHelper.TABLE_VENDEDOR +
                    " WHERE " + DatabaseHelper.KEY_NOMBRE + " = '" + nombreUsuario + "'" +
                    " AND " + DatabaseHelper.KEY_PASSWORD + " = '" + passwordEscaped + "'";

            // Ejecutar la consulta
            cursor = db.rawQuery(sqlQuery, null);

            // Verificar si el cursor tiene exactamente un resultado
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_VENDEDOR_ID);
                if (idIndex != -1) {
                    idVendedor = cursor.getInt(idIndex);
                } else {
                    Log.e("VendedorDAO", "No se encontró la columna KEY_VENDEDOR_ID en el cursor");
                }
            } else {
                Log.e("VendedorDAO", "El cursor está vacío o no se pudo mover a la primera fila");
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al obtener ID de vendedor: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return idVendedor;
    }

    // Método para obtener el vendedor que ha iniciado sesión (logueado)
    public Vendedor obtenerVendedorLogueado(String nombreUsuario, String password) {
        Vendedor vendedor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Escapar comillas simples en el password para evitar problemas de SQL injection
            String passwordEscaped = password.replace("'", "''");

            // Construir la consulta SQL
            String sqlQuery = "SELECT * FROM " + DatabaseHelper.TABLE_VENDEDOR +
                    " WHERE " + DatabaseHelper.KEY_NOMBRE + " = '" + nombreUsuario + "'" +
                    " AND " + DatabaseHelper.KEY_PASSWORD + " = '" + passwordEscaped + "'";

            // Ejecutar la consulta
            cursor = db.rawQuery(sqlQuery, null);

            // Verificar si el cursor tiene exactamente un resultado
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_VENDEDOR_ID);
                int nombreIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NOMBRE);
                int apellidoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_APELLIDO);
                int telefonoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_TELEFONO);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PASSWORD);

                // Verificar que los índices sean válidos antes de obtener los valores
                if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && passwordIndex != -1) {
                    vendedor = new Vendedor(
                            cursor.getInt(idIndex),
                            cursor.getString(nombreIndex),
                            cursor.getString(apellidoIndex),
                            cursor.getString(telefonoIndex),
                            cursor.getString(passwordIndex)
                    );
                }
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al obtener vendedor logueado: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return vendedor;
    }

    // Método para validar las credenciales de un vendedor y obtener todos sus datos
    public Vendedor validarCredencialesYObtenerDatos(String nombreUsuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Vendedor vendedor = null;

        try {
            // Escapar comillas simples en el password para evitar problemas de SQL injection
            String passwordEscaped = password.replace("'", "''");

            // Construir la consulta SQL
            String sqlQuery = "SELECT * FROM " + DatabaseHelper.TABLE_VENDEDOR +
                    " WHERE " + DatabaseHelper.KEY_NOMBRE + "='" + nombreUsuario + "' AND " +
                    DatabaseHelper.KEY_PASSWORD + "='" + passwordEscaped + "'";

            // Ejecutar la consulta
            cursor = db.rawQuery(sqlQuery, null);

            // Verificar si el cursor tiene exactamente un resultado
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_VENDEDOR_ID);
                int nombreIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NOMBRE);
                int apellidoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_APELLIDO);
                int telefonoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_TELEFONO);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PASSWORD);

                // Verificar que los índices sean válidos antes de obtener los valores
                if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && passwordIndex != -1) {
                    vendedor = new Vendedor(
                            cursor.getInt(idIndex),
                            cursor.getString(nombreIndex),
                            cursor.getString(apellidoIndex),
                            cursor.getString(telefonoIndex),
                            cursor.getString(passwordIndex)
                    );
                }
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al validar credenciales y obtener datos: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return vendedor;
    }
    // Método para obtener el vendedor asociado a un auto por su ID
    public Vendedor obtenerVendedorPorIdAuto(int idAuto) {
        Vendedor vendedor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consulta SQL para obtener el vendedor asociado al auto
            String sqlQuery = "SELECT v.* FROM " + DatabaseHelper.TABLE_VENDEDOR + " v " +
                    "JOIN autos a ON v." + DatabaseHelper.KEY_VENDEDOR_ID + " = a.id_vendedor " +
                    "WHERE a.auto_id = ?";

            // Ejecutar la consulta
            cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(idAuto)});

            // Verificar si el cursor tiene al menos una fila
            if (cursor != null && cursor.moveToFirst()) {
                // Obtener los índices de las columnas
                int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_VENDEDOR_ID);
                int nombreIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NOMBRE);
                int apellidoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_APELLIDO);
                int telefonoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_TELEFONO);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PASSWORD);

                // Verificar que los índices sean válidos antes de obtener los valores
                if (idIndex != -1 && nombreIndex != -1 && apellidoIndex != -1 && telefonoIndex != -1 && passwordIndex != -1) {
                    vendedor = new Vendedor(
                            cursor.getInt(idIndex),
                            cursor.getString(nombreIndex),
                            cursor.getString(apellidoIndex),
                            cursor.getString(telefonoIndex),
                            cursor.getString(passwordIndex)
                    );
                }
            }
        } catch (Exception e) {
            Log.e("VendedorDAO", "Error al obtener vendedor por ID de auto: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return vendedor;
    }





}
