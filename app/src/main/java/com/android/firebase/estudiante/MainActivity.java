package com.android.firebase.estudiante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.firebase.estudiante.Modelo.Estudiante;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText _etNombre, _etApellido, _etCorreo, _etTelefono, _etCi;
    ListView _lvLista;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Estudiante> listEstudiante = new ArrayList<Estudiante>();
    ArrayAdapter<Estudiante> arrayAdapterEstudiante;

    Estudiante estudianteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _etNombre=findViewById(R.id.etNombre);
        _etApellido=findViewById(R.id.etApellido);
        _etCorreo=findViewById(R.id.etCorreo);
        _etTelefono=findViewById(R.id.etTelefono);
        _etCi=findViewById(R.id.etCi);
        _lvLista=findViewById(R.id.lvLista);

        inicializarFirebase();

        listarDatos();

        _lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                estudianteSeleccionado = (Estudiante) parent.getItemAtPosition(position);
                _etNombre.setText(estudianteSeleccionado.getNombre());
                _etApellido.setText(estudianteSeleccionado.getApellido());
                _etCorreo.setText(estudianteSeleccionado.getCorreo());
                _etTelefono.setText(estudianteSeleccionado.getTelefono());
                _etCi.setText(estudianteSeleccionado.getCi());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = _etNombre.getText().toString();
        String apellido = _etApellido.getText().toString();
        String correo = _etCorreo.getText().toString();
        String telefono = _etTelefono.getText().toString();
        String ci = _etCi.getText().toString();

        switch (item.getItemId()){
            case R.id.item_insertar:{
                if (nombre.equals("")||apellido.equals("")||correo.equals("")||telefono.equals("")||ci.equals("")){
                    validar();
                } else {
                    Estudiante estudiante=new Estudiante();
                    estudiante.setIdEstudiante(UUID.randomUUID().toString());
                    estudiante.setNombre(_etNombre.getText().toString());
                    estudiante.setApellido(_etApellido.getText().toString());
                    estudiante.setCorreo(_etCorreo.getText().toString());
                    estudiante.setTelefono(_etTelefono.getText().toString());
                    estudiante.setCi(_etCi.getText().toString());
                    databaseReference.child("Estudiante").child(estudiante.getIdEstudiante()).setValue(estudiante);
                    Toast.makeText(this, "Estudiante agregado correctamente", Toast.LENGTH_LONG).show();
                    limpiarEdittext();
                }
                break;
            }
            case R.id.item_actualizar:{
                Estudiante estudiante=new Estudiante();
                estudiante.setIdEstudiante(estudianteSeleccionado.getIdEstudiante());
                estudiante.setNombre(_etNombre.getText().toString());
                estudiante.setApellido(_etApellido.getText().toString());
                estudiante.setCorreo(_etCorreo.getText().toString());
                estudiante.setTelefono(_etTelefono.getText().toString());
                estudiante.setCi(_etCi.getText().toString());
                databaseReference.child("Estudiante").child(estudiante.getIdEstudiante()).setValue(estudiante);
                Toast.makeText(this,"Estudiante actualizado correctamente", Toast.LENGTH_LONG).show();
                limpiarEdittext();
                break;
            }
            case R.id.item_eliminar:{
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(estudianteSeleccionado.getIdEstudiante());
                databaseReference.child("Estudiante").child(estudiante.getIdEstudiante()).removeValue();
                Toast.makeText(this,"Estudiante eliminado correctamente", Toast.LENGTH_LONG).show();
                limpiarEdittext();
                break;
            }
            default:break;
        }
        return true;
    }

    private void validar() {
        String nombre = _etNombre.getText().toString();
        String apellido = _etApellido.getText().toString();
        String correo = _etCorreo.getText().toString();
        String telefono = _etTelefono.getText().toString();
        String ci = _etCi.getText().toString();

        if(nombre.equals("")){
            _etNombre.setError("Falta");
        }
        else if (apellido.equals("")){
            _etApellido.setError("Falta");
        }
        else if (correo.equals("")){
            _etCorreo.setError("Falta");
        }
        else if (telefono.equals("")){
            _etTelefono.setError("Falta");
        }
        else if (ci.equals("")){
            _etCi.setError("Falta");
        }
    }

    private void listarDatos() {
        databaseReference.child("Estudiante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEstudiante.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Estudiante p = objSnaptshot.getValue(Estudiante.class);
                    listEstudiante.add(p);
                    arrayAdapterEstudiante = new ArrayAdapter<Estudiante>(MainActivity.this, android.R.layout.simple_list_item_1, listEstudiante);
                    _lvLista.setAdapter(arrayAdapterEstudiante);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiarEdittext()
    {
        _etNombre.setText("");
        _etApellido.setText("");
        _etCorreo.setText("");
        _etTelefono.setText("");
        _etCi.setText("");
    }
}
