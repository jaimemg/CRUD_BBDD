package com.example.crud_bbdd;

import android.provider.BaseColumns;

public class Estructura {

    public static final String TABLE_NAME = "datosPersonales";
    public static final String COLUMN_NAME_1 = "id";
    public static final String COLUMN_NAME_2 = "Nombre";
    public static final String COLUMN_NAME_3 = "Apellido";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura.TABLE_NAME + " (" +
                    Estructura.COLUMN_NAME_1 + " INTEGER PRIMARY KEY," +
                    Estructura.COLUMN_NAME_2 + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_3 + TEXT_TYPE + " ) ";



    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Estructura.TABLE_NAME;

    private Estructura() {

    }

    public static String getSqlCreateEntries(){
        return SQL_CREATE_ENTRIES;
    }
    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

}
