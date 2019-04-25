package pe.progsistem.jesus.ubeprivado.Activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;


public class RecuperacionActivity extends AppCompatActivity {
    private TextView tvAviso;
    public EditText etDato;
    private Button btnSiguiente;
    private  String email;
    private  int ale =0;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);

        etDato =(EditText)findViewById(R.id.etDato);
        tvAviso =(TextView) findViewById(R.id.tvAviso);
        btnSiguiente =(Button) findViewById(R.id.btnSiguiente);

        tvAviso.setText("Ingrese su email");

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intentReg = new Intent(PerfilActivity.this,DetalleAtencionActivity.class);
                intentReg.putExtra("cod_usu",idu);
                intentReg.putExtra("cargo",nom_cargo);
                PerfilActivity.this.startActivity(intentReg); */
             if(!btnSiguiente.getText().equals("Verificar")) {
                 getIdUser();

                }
                if(btnSiguiente.getText().equals("Verificar")){
                  if(String.valueOf(ale).equals(etDato.getText().toString())){

                      AlertDialog.Builder builder = new AlertDialog.Builder(RecuperacionActivity.this);

                      builder.setTitle("Configuracion")
                              .setMessage("Su clave Temporal sera "+ale)
                              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      updateClaveRec();
                                  }
                              })
                              .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {

                                  }
                              })
                              .setIcon(android.R.drawable.ic_dialog_info)
                              .show();


                  }else{
                      AlertDialog.Builder builder = new AlertDialog.Builder(RecuperacionActivity.this);

                      builder.setTitle("Error ")
                              .setMessage("El codigo no es valido ")
                              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {

                                  }
                              })
                             /* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {

                                  }
                              }) */
                              .setIcon(android.R.drawable.ic_dialog_info)
                              .show();
                  }

                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private int generarAleatorio(){
        Random aleatorio = new Random(System.currentTimeMillis());
// Producir nuevo int aleatorio entre 0 y 99
        int intAletorio = aleatorio.nextInt(8999)+1000;
// Más código

// Refrescar datos aleatorios
        aleatorio.setSeed(System.currentTimeMillis());
        return  intAletorio;
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
            // Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                    if(!btnSiguiente.getText().equals("Verificar")) {

                        boolean id = object.getBoolean("codigo");
                        boolean correo = object.getBoolean("correo");


                        if (id) {

                            if (correo) {
                                tvAviso.setText("Ingrese el codigo que le hemos enviado a su email");
                                etDato.setText("");
                                btnSiguiente.setText("Verificar");

                            } else {
                                Toast.makeText(getApplicationContext(), "no se ha podido enviar  a su correo", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            etDato.setError("el correo no pertenece a ninguna cuenta");
                            etDato.requestFocus();
                        }
                    }

                    if(btnSiguiente.getText().equals("Verificar")){

                    }


                   // refreshHeroList(object.getJSONArray("usuarios"));
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

  /*  private void refreshHeroList(JSONArray heroes) throws JSONException {


        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            tvusername.setText( obj.getString("id"));
            etname.setText(obj.getString("nombre"));
       }

    } */

    private void getIdUser() {

        String memail = etDato.getText().toString();
        if (TextUtils.isEmpty(memail)) {
            etDato.setError("Please fill the field");
            etDato.requestFocus();
            return;
        }


            ale  = generarAleatorio();
         String alea= String.valueOf(ale);
         email = etDato.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("codigo",alea);
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_GET_ID_USER, params, CODE_POST_REQUEST);
        request.execute();
     }

    private void updateClaveRec() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("codigo",String.valueOf(ale));
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_CLAVE_REC, params, CODE_POST_REQUEST);
        request.execute();
    }

}
