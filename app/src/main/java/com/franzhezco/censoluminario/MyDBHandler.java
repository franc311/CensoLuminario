package com.franzhezco.censoluminario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ryner on 13/07/2016.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "censo_db.db";
    //table censos
    public static final String TABLE_CENSOS = "censos";
    public static final String COLUMN_ID = "censo_id";
    public static final String COLUMMN_CENSONAME = "censo_name";
    //table puntos
    public static final String TABLE_PUNTOS = "puntos";
    public static final String PUNTO_COLMUN_ID = "punto_id";
    public static final String PUNTO_COLMUN_SERIE = "serie";
    public static final String PUNTO_COLUMN_LATITUDE = "latitude";
    public static final String PUNTO_COLUMN_LONGITUDE = "longitude";
    //puntero indicar
    public static final String TABLE_PUNTEROS = "punteros";
    public static final String PUNTEROS_COLMUN_ID = "puntero_id";
    public static final String PUNTEROS_COLUMN_IDENTIFICADOR = "puntero";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CENSOS_TABLE = "CREATE TABLE " + TABLE_CENSOS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY," + COLUMMN_CENSONAME + " TEXT)";
        db.execSQL(CREATE_CENSOS_TABLE);
        String CREATE_PUNTOS_TABLE = "CREATE TABLE " + TABLE_PUNTOS + "(" + PUNTO_COLMUN_ID +
                " INTEGER PRIMARY KEY," + PUNTO_COLMUN_SERIE + " TEXT,"
                + PUNTO_COLUMN_LATITUDE + " DOUBLE," + PUNTO_COLUMN_LONGITUDE +
                " DOUBLE," + COLUMN_ID + " INTEGER, FOREIGN KEY (" + COLUMN_ID + ") REFERENCES " +
                TABLE_CENSOS + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_PUNTOS_TABLE);
        String CREATE_PUNTEROS_TABLE = "CREATE TABLE " + TABLE_PUNTEROS + "(" + PUNTEROS_COLMUN_ID +
                " INTEGER PRIMARY KEY," + PUNTEROS_COLUMN_IDENTIFICADOR + " INTEGER)";
        db.execSQL(CREATE_PUNTEROS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CENSOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNTEROS);
        onCreate(db);
    }

    public void addCenso(Censo censo) {
        ContentValues values = new ContentValues();
        values.put(COLUMMN_CENSONAME, censo.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CENSOS, null, values);
        db.close();
    }

    public ArrayList<Censo> getListCenso() {
        ArrayList<Censo> censoLista = new ArrayList<Censo>();
        String query = "SELECT * FROM " + TABLE_CENSOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Censo censo = new Censo();
                censo.setID(Integer.parseInt(cursor.getString(0)));
                censo.setName(cursor.getString(1));
                censoLista.add(censo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return censoLista;
    }

    public boolean deleteCenso(Censo censo) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_CENSOS + " WHERE " + COLUMN_ID
                + "=\"" + censo.getID() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.delete(TABLE_CENSOS, COLUMN_ID + "=?", new String[]{String.valueOf(censo.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean isPuntero() {
        boolean resultado = false;
        String query = "SELECT * FROM " + TABLE_PUNTEROS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            resultado = true;
            cursor.close();
        }
        db.close();
        return resultado;
    }

    public void addPuntero() {
        ContentValues values = new ContentValues();
        values.put(PUNTEROS_COLUMN_IDENTIFICADOR, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PUNTEROS, null, values);
        db.close();
    }

    public void updatePuntero(int id) {
        String query = "UPDATE " + TABLE_PUNTEROS + " SET " + PUNTEROS_COLUMN_IDENTIFICADOR +
                "=" + id + " WHERE " + PUNTEROS_COLMUN_ID + "=1";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public String getPuntero() {
        String query = "SELECT " + PUNTEROS_COLUMN_IDENTIFICADOR + " FROM " +
                TABLE_PUNTEROS + " WHERE " +PUNTEROS_COLMUN_ID + "=1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return "error (Censo no registrado)";
    }
    //--------------------Puntos-------------------------------------
    public void addPunto(Punto punto) {
        ContentValues values = new ContentValues();
        values.put(PUNTO_COLMUN_SERIE, punto.getSerie());
        values.put(PUNTO_COLUMN_LATITUDE, punto.getLatitude());
        values.put(PUNTO_COLUMN_LONGITUDE, punto.getLongitude());
        values.put(COLUMN_ID, punto.getIdCenso());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PUNTOS, null, values);
        db.close();
    }

    public ArrayList<Punto> getListPunto() {
        ArrayList<Punto> puntoLista = new ArrayList<Punto>();
        String query = "SELECT * FROM " + TABLE_PUNTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Punto punto = new Punto();
                punto.setId(cursor.getInt(0));
                punto.setSerie(cursor.getString(1));
                punto.setLatitude(cursor.getDouble(2));
                punto.setLongitude(cursor.getDouble(3));
                punto.setIdCenso(cursor.getInt(4));
                puntoLista.add(punto);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return puntoLista;
    }
}