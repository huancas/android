package pe.progsistem.jesus.ubeprivado.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class PerfilActivity extends AppCompatActivity {

    TextView tvusername ,tvcargo,tvestado ,tvCar,tvEs;
    EditText  etname,etemail,etusuario,etcel,etcel2,etFecha;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private Spinner spinnerSexo,spinnerEstado,spinnerCargo;
    ImageView ivPerfil;
    private String cargo_usu,cargo_act,estado_act;

    private String idu,nom_cargo;

    private Button btnSave,btnEstrella;


    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent recibir = getIntent();
         // nom_cargo = recibir.getStringExtra("cargo");


        idu = recibir.getStringExtra("idu");
        String cargo_frag =recibir.getStringExtra("cargo");
        nom_cargo =cargo_frag;
        cargo_usu =cargo_frag;





        tvusername =(TextView) findViewById(R.id.tvUsername);
        tvcargo =(TextView) findViewById(R.id.tvCargo);
        tvestado =(TextView) findViewById(R.id.tvEstado);

        tvCar =(TextView) findViewById(R.id.tvCar);
        tvEs =(TextView) findViewById(R.id.tvEs);

        etname =(EditText)findViewById(R.id.et_user);
        etcel =(EditText) findViewById(R.id.et_cel);
        etcel2 =(EditText) findViewById(R.id.et_cel2);
        etemail =(EditText)findViewById(R.id.et_email);
        etFecha =(EditText)findViewById(R.id.et_fecha);

        spinnerEstado =(Spinner) findViewById(R.id.spinnerEstado);
        spinnerSexo =(Spinner) findViewById(R.id.spinnersexo);
        spinnerCargo =(Spinner) findViewById(R.id.spinnerCargo);

        ivPerfil =(ImageView)  findViewById(R.id.ivProfile);
        btnSave =(Button)  findViewById(R.id.btnSave);
        btnEstrella =(Button)  findViewById(R.id.btnEstrella);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 updateHero();
            }
        });

      /*  tvusername.setText(username);
        tvname.setText(name+" "+app);
        tvcel.setText(cel);
        tvcel2.setText(cel2);
        tvemail.setText(email); */

        //tvapp.setText(edad);
        logo = (ImageView) findViewById(R.id.imagenProfile);
        //  logo.setImageResource(R.drawable.lady);
        // downloadFile(imageHttpAddress);

       /* String ruta =RUTAIMG+fperfil;
        Picasso.get().load(ruta).into(logo); */
        btnEstrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(PerfilActivity.this,DetalleAtencionActivity.class);
                intentReg.putExtra("cod_usu",idu);
                intentReg.putExtra("cargo",nom_cargo);
                PerfilActivity.this.startActivity(intentReg);


            }
        });


        Toast.makeText(getApplicationContext(),"iniciando perfil activity", Toast.LENGTH_SHORT).show();
       readHeroes();

       if(cargo_frag.equalsIgnoreCase("ADMINISTRADOR")){
           spinnerEstado.setVisibility(View.VISIBLE);
           spinnerEstado.setVisibility(View.VISIBLE);
           tvCar.setVisibility(View.VISIBLE);
           tvEs.setVisibility(View.VISIBLE);

       }else{
           spinnerCargo.setVisibility(View.GONE);
           spinnerEstado.setVisibility(View.GONE);
           tvCar.setVisibility(View.GONE);
           tvEs.setVisibility(View.GONE);
       }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //flecha atras
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



    private void readHeroes() {
        HashMap<String, String> params = new HashMap<>();
        params.put("idu", idu);
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_GET_USUARIO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void updateHero() {
        String id = idu;
        String name = etname.getText().toString().trim();
        String cel = etcel.getText().toString().trim();
        String cel2 = etcel2.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String sexo = spinnerSexo.getSelectedItem().toString();


         if(cargo_usu.equalsIgnoreCase("ADMINISTRADOR")) {
             estado_act = spinnerEstado.getSelectedItem().toString();
             cargo_act = spinnerCargo.getSelectedItem().toString();
         }


        HashMap<String, String> params = new HashMap<>();

        params.put("nombre",name);
        params.put("sexo", sexo);
        params.put("fecha", fecha);
        params.put("cel", cel);
        params.put("cel2", cel2);
        params.put("email",email);
        params.put("cargo_nom",cargo_act);
        params.put("estado",estado_act);
        params.put("idu", id);



        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_USUARIO, params, CODE_POST_REQUEST);
        request.execute();

       /* buttonAddUpdate.setText("Add");

        editTextName.setText("");
        editTextRealname.setText("");
        ratingBar.setRating(0);
        spinnerTeam.setSelection(0);

        isUpdating = false; */
    }


    private void refreshHeroList(JSONArray heroes) throws JSONException {


        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);
            tvusername.setText( obj.getString("id"));
            etname.setText(obj.getString("nombre"));
            etcel.setText(obj.getString("celular"));
            etcel2.setText(obj.getString("celular1"));
            etemail.setText(obj.getString("email"));
            tvusername.setText(obj.getString("username"));
            tvestado.setText(obj.getString("estado"));
            tvcargo.setText(obj.getString("cargo"));
            etFecha.setText(obj.getString("fenac"));
            String url =obj.getString("foto_perfil");
            String sex =obj.getString("sexo");
            String estado =obj.getString("estado");
            String cargo =obj.getString("cargo");
            cargo_act =cargo;
            estado_act = estado;


           selectValue(spinnerSexo, sex);
            selectValue(spinnerEstado,estado);
            selectValue(spinnerCargo, cargo);





            String ruta =RUTAIMG+url;
            Picasso.get().load(ruta).into(ivPerfil);

        }



    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
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
          //  progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("usuarios"));
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
}
