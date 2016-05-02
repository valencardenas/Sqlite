package com.example.valentina.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText eNombre, eIndentificacion, eNota;
    private Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eNombre = (EditText) findViewById(R.id.eNombre);
        eIndentificacion = (EditText) findViewById(R.id.eIdentificaion);
        eNota = (EditText) findViewById(R.id.eNota);

    }


    //funciones


    public void guardar (View view) {
        BaseDeDatos estudiante = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = estudiante.getWritableDatabase();

        String nombre = eNombre.getText().toString();
        String identificacion = eIndentificacion.getText().toString();
        String nota = eNota.getText().toString();

        ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
        registro.put("identificacion", identificacion);//tag debe aparecer igual que en la clase BaseDeDatos
        registro.put("nombre", nombre);
        registro.put("nota", nota);

        bd.insert("estudiantes",null, registro);
        bd.close();// cerrar para que guarde

        eNombre.setText("");
        eIndentificacion.setText("");
        eNota.setText("");
        Toast.makeText(this, "Se guardaron los datos de la persona",
                Toast.LENGTH_SHORT).show();

    }


    public void consultar (View view){
        BaseDeDatos estudiante = new BaseDeDatos(this,"basededatos",null,1);
        SQLiteDatabase bd = estudiante.getWritableDatabase();

        String identificacion = eIndentificacion.getText().toString();

        c = bd.rawQuery("select nombre, nota from estudiantes where identificacion=" + identificacion, null);
        if(c.moveToFirst()==true){

                eNombre.setText(c.getString(0));
                eNota.setText(c.getString(1));


        }else{
            Toast.makeText(this, "No existe estudiante con dicha identificaion" ,
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void modificar(View v) {
        BaseDeDatos estudiante = new BaseDeDatos(this,"basededatos", null, 1);
        SQLiteDatabase bd = estudiante.getWritableDatabase();

        String identificacion = eIndentificacion.getText().toString();
        String nombre = eNombre.getText().toString();
        String nota = eNota.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("nota", nota);

        int cant = bd.update("estudiantes", registro, "identificacion=" + identificacion, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe estudiante con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }

    public void eliminar(View v) {
        BaseDeDatos admin = new BaseDeDatos(this,"basededatos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String identificacion = eIndentificacion.getText().toString();
        int cant = bd.delete("estudiantes", "identificacion=" + identificacion, null);
        bd.close();
        eIndentificacion.setText("");
        eNombre.setText("");
        eNota.setText("");

        if (cant == 1)
            Toast.makeText(this, "Se borró el estudiante con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe estudiante con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }


    public void limpiar (View v){
        eIndentificacion.setText("");
        eNombre.setText("");
        eNota.setText("");
    }




}
