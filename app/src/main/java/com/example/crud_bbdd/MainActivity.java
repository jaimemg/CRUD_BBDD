package com.example.crud_bbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button botonInsertar, botonBorrar, botonActualizar, botonBuscar;
    EditText textoId, textoNombre, textoApellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInsertar = (Button)findViewById(R.id.insertar);
        botonBorrar = (Button)findViewById(R.id.borrar);
        botonActualizar = (Button)findViewById(R.id.actualizar);
        botonBuscar = (Button)findViewById(R.id.buscar);

        textoId = (EditText)findViewById(R.id.id);
        textoNombre = (EditText)findViewById(R.id.nombre);
        textoApellidos = (EditText)findViewById(R.id.apellidos);

        final FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

        botonInsertar.setOnClickListener(new View.OnClickListener(){    //ponemos todos los botones a la escucha

            @Override
            public void onClick(View view) {
                // Gets the data repository in write mode
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura.COLUMN_NAME_1, textoId.getText().toString()); //capturamos el valor introducido por el usuario en el cuadro de texto ID
                values.put(Estructura.COLUMN_NAME_2, textoNombre.getText().toString());
                values.put(Estructura.COLUMN_NAME_3, textoApellidos.getText().toString());

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura.TABLE_NAME, null, values);

                Toast.makeText(getApplicationContext(), "Se ha guardado el registro con clave: " +
                        newRowId, Toast.LENGTH_LONG).show();

                textoId.setText("");
                textoNombre.setText("");
                textoApellidos.setText("");
                
            }
        });
        botonActualizar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // New value for one column

                ContentValues values = new ContentValues();
                values.put(Estructura.COLUMN_NAME_2, textoNombre.getText().toString());
                values.put(Estructura.COLUMN_NAME_3, textoApellidos.getText().toString());

                // Which row to update, based on the title
                String selection = Estructura.COLUMN_NAME_1 + " LIKE ?";
                String[] selectionArgs = {textoId.getText().toString()};

                int count = db.update(
                        Estructura.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(), "Se ha actualizado el registro con clave: " +
                        textoId.getText().toString(), Toast.LENGTH_LONG).show();

            }

        });

        botonBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        Estructura.COLUMN_NAME_2,
                        Estructura.COLUMN_NAME_3
                };

                // Filter results
                String selection = Estructura.COLUMN_NAME_1 + " = ?";
                String[] selectionArgs = {textoId.getText().toString()};

                // How you want the results sorted in the resulting Cursor
               /* String sortOrder =
                        FeedEntry.COLUMN_NAME_SUBTITLE + " DESC"; */

                try {

                    Cursor cursor = db.query(       //método que ejecuta la consulta y genera la tabla virtual
                            Estructura.TABLE_NAME,   // The table to query
                            projection,             // The array of columns to return (pass null to get all)
                            selection,              // The columns for the WHERE clause
                            selectionArgs,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null               // The sort order
                    );

                    cursor.moveToFirst();   //movemos al cursor al primer registro
                    textoNombre.setText(cursor.getString(0));   //escribimos en el cuadro de  texto nombre lo que encuentra en la primera columna de la consulta
                    textoApellidos.setText(cursor.getString(1));    //en la segunda columna se encuentra el apellido

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "No se ha encontrado el registro", Toast.LENGTH_LONG).show();

                }
            }



        });
        botonBorrar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Define 'where' part of query.
                String selection = Estructura.COLUMN_NAME_1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = {textoId.getText().toString()};
                // Issue SQL statement.
                int deletedRows = db.delete(Estructura.TABLE_NAME, selection, selectionArgs);


                Toast.makeText(getApplicationContext(), "Se ha borrado el registro con clave: " +
                        textoId.getText().toString(), Toast.LENGTH_LONG).show();

                textoId.setText("");
                textoNombre.setText("");
                textoApellidos.setText("");
            }
        });
    }
}