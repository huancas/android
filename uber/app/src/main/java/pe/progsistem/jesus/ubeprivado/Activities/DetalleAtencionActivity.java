package pe.progsistem.jesus.ubeprivado.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;



import pe.progsistem.jesus.ubeprivado.Frgaments.GridFragment;
import pe.progsistem.jesus.ubeprivado.R;

public class DetalleAtencionActivity extends AppCompatActivity {
    private Button btnEstrella;
    private String cod_idu,nom_cargo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_atencion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cod_idu = getIntent().getExtras().getString("cod_usu");
        nom_cargo = getIntent().getExtras().getString("cargo");
        nom_cargo ="CLIENTE";



        Bundle args = new Bundle();
        // Colocamos el String   y  guardamos los datos
        args.putString("fidu", cod_idu);
        args.putString("fcargo", nom_cargo);
        Fragment fragment = null;
        fragment = new GridFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();



    }

    //flecha atras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
               // Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
