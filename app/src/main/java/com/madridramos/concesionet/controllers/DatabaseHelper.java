package com.madridramos.concesionet.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "concesionet.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas para autos
    public static final String TABLE_AUTO = "autos";
    public static final String KEY_AUTO_ID = "id";
    public static final String KEY_MARCA = "marca";
    public static final String KEY_MODELO = "modelo";
    public static final String KEY_ANIO = "anio";
    public static final String KEY_PRECIO = "precio";
    public static final String KEY_ID_VENDEDOR_FK = "id_vendedor";

    // Nombre de la tabla y columnas para vendedores
    public static final String TABLE_VENDEDOR = "vendedores";
    public static final String KEY_VENDEDOR_ID = "id";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_PASSWORD = "password";

    // Sentencia SQL para crear la tabla de autos
    private static final String CREATE_TABLE_AUTO = "CREATE TABLE " + TABLE_AUTO + "("
            + KEY_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MARCA + " TEXT,"
            + KEY_MODELO + " TEXT,"
            + KEY_ANIO + " INTEGER,"
            + KEY_PRECIO + " REAL,"
            + KEY_ID_VENDEDOR_FK + " INTEGER"
            + ")";


    // Sentencia SQL para crear la tabla de vendedores
    private static final String CREATE_TABLE_VENDEDOR = "CREATE TABLE " + TABLE_VENDEDOR + "("
            + KEY_VENDEDOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOMBRE + " TEXT,"
            + KEY_APELLIDO + " TEXT,"
            + KEY_TELEFONO + " TEXT,"
            + KEY_PASSWORD + " TEXT"
            + ")";

    private static DatabaseHelper instance;

    // Método estático para obtener una instancia única de DatabaseHelper
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Constructor privado para evitar instancias directas
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método llamado al crear la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de autos
        db.execSQL(CREATE_TABLE_AUTO);

        // Crear la tabla de vendedores
        db.execSQL(CREATE_TABLE_VENDEDOR);
    }

    // Método llamado cuando se necesita actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas existentes si hay cambios en la estructura
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDEDOR);
        onCreate(db);
    }
}

