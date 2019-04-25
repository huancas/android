package pe.progsistem.jesus.ubeprivado.Activities;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;

public class AtencionActivity extends AppCompatActivity  {
    String nombre;
    private ImageView img;
    Spinner sptipo;
    Button btn_reg;

    String cod_idu ="",cod_cli="";
    //otro
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

        sptipo = (Spinner) findViewById(R.id.spinnersexo);


        TextView tvnombre, tvname;
        //para flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("listaDetalle");
        cod_cli = lista.get(0);
        nombre = lista.get(1);
        String url_pri = lista.get(2);
        cod_idu = lista.get(3);
        // If your minSdkVersion is 11 or higher, instead use:
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        tvname = (TextView) findViewById(R.id.tvNombre);
        tvnombre = (TextView) findViewById(R.id.tvUsuario);
        img = (ImageView) findViewById(R.id.ivFoto);
        btn_reg =(Button)  findViewById(R.id.btnRegAte);
        tvname.setText(nombre);
        String ruta = RUTAIMG + url_pri;
        Picasso.get().load(ruta).into(img);
        sptipo = (Spinner) findViewById(R.id.spinnerTipo);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AtencionActivity.this);

                builder.setTitle("Agregar "+nombre)
                        .setMessage("¿esta seguro que deseas agregar?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               createAtencion();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
              //  Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                   // refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void createAtencion(){
       HashMap<String, String> params = new HashMap<>();
            String mtipo = String.valueOf(sptipo.getSelectedItem());
            params.put("idu", cod_idu);
            params.put("idc",cod_cli);
            params.put("tipo",mtipo);
           PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_CREATE_ATENCION, params, CODE_POST_REQUEST);
            request.execute();

        }

}
