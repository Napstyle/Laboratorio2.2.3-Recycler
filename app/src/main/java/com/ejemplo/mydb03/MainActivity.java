package com.ejemplo.mydb03;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ejemplo.mydb03.utilidades.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnnew;
    private ImageButton btndel;
    private ImageButton btnupdate;
    private ImageButton btnlist;

    private EditText clave;
    private EditText nombre;
    private EditText sueldo;
    private EditText resultado;

    SQLiteDatabase db;
    ConexionSQLiteHelper con;

    ArrayList<Item> ListaDatos;
    Adapter adapter;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=new ConexionSQLiteHelper(MainActivity.this);

        init();

        //listardatos();


    }

    @Override
    protected void onStart() {
        super.onStart();
        con.openDB();

    }

    @Override
    protected void onStop() {
        super.onStop();
        con.closeDB();
    }

    private void init() {
        btnnew = findViewById(R.id.imageButton);
        btndel = findViewById(R.id.imageButton2);
        btnupdate = findViewById(R.id.imageButton3);
        btnlist = findViewById(R.id.imageButton4);

        clave=findViewById(R.id.edt1);
        nombre=findViewById(R.id.edt2);
        sueldo=findViewById(R.id.edt3);


        btnnew.setOnClickListener(btnListener);
        btndel.setOnClickListener(btnListener);
        btnupdate.setOnClickListener(btnListener);
        btnlist.setOnClickListener(btnListener);

        recycler=(RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
               case R.id.imageButton:
                    Long registros;
                    registros = registrarUsuario();

                    if (registros==-1){
                        Toast.makeText(MainActivity.this,"NOT ADD ROW ",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(MainActivity.this,"ADD ROW "+ registros,Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.imageButton2:
                    Toast.makeText(MainActivity.this,"DEL ROW",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton3:
                    Toast.makeText(MainActivity.this,"UPDATE ROW",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageButton4:
                    Toast.makeText(MainActivity.this,"LIST ALL",Toast.LENGTH_SHORT).show();
                    listardatos();
                    break;

            }

        }
    };

    private void listardatos() {
        ListaDatos=new ArrayList<>();

        ListaDatos=con.consulta();

        adapter=new Adapter(this,ListaDatos);

        recycler.setAdapter(adapter);
    }

    public Long registrarUsuario() {

        return con.insert(clave.getText().toString(), nombre.getText().toString(), sueldo.getText().toString());



    }




}
