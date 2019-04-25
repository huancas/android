package pe.progsistem.jesus.ubeprivado.Activities;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Random;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;
import pe.progsistem.jesus.ubeprivado.util.MyReceiver;
import pe.progsistem.jesus.ubeprivado.util.VerificarRegistro;

public class RegistroPrincipal extends AppCompatActivity implements MyReceiver.Listener {
    private TextView tvAviso,tvError;
    private EditText etDato;
    private Button btnSiguiente;
    private  String email;
    private  int ale =0;
   private static final  int MY_PERMISSIONS_REQUEST_READ_SMS =1;
    private static final  int MY_PERMISSIONS_REQUEST_RECIVE_SMS =2;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    JSONArray jsonUser;

    boolean id_exist = false;

    private static RegistroPrincipal ins;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
      String  bes =verfificarPreferencia();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_principal);


        ins = this;

        /*  verficar Prefencia      */


        etDato = (EditText) findViewById(R.id.etDato);
        tvAviso = (TextView) findViewById(R.id.tvSugerencia);
        tvError = (TextView) findViewById(R.id.tvMensaje);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        tvAviso.setText("Ingrese su numero de Celular");



        btnSiguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              /*  Intent intentReg = new Intent(PerfilActivity.this,DetalleAtencionActivity.class);
                intentReg.putExtra("cod_usu",idu);
                intentReg.putExtra("cargo",nom_cargo);
                PerfilActivity.this.startActivity(intentReg); */
                if (!btnSiguiente.getText().equals("Verificar")) {
                    if (etDato.getText().toString().length() == 9) {


                        String memail = etDato.getText().toString();
                        if (TextUtils.isEmpty(memail)) {
                            etDato.setError("Please fill the field");
                            etDato.requestFocus();
                            return;
                        }

                        /*************************Mensajes ***********************/
                        verificarPermisoLeerMensaje();
                        verificarPermisoRecibeMensaje();

                        enviarMensaje();
                    } else {
                        tvError.setText("El numero debe tener 9 digitos");
                    }


                }
                if (btnSiguiente.getText().equals("Verificar")) {

                    if (String.valueOf(ale).equals(etDato.getText().toString())) {
                        if (!id_exist) {
                            Intent intentReg = new Intent(RegistroPrincipal.this, MenuRegistroActivity.class);
                            RegistroPrincipal.this.startActivity(intentReg);
                           


                        }
                        if (id_exist) {
                            // refreshUserList(object.getJSONArray("user");
                            VerificarRegistro.existe = true;
                            VerificarRegistro vr = new VerificarRegistro(getApplicationContext(),RegistroPrincipal.this);
                            try {
                                vr.Verificar(jsonUser);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        Toast.makeText(getApplicationContext(), "boolena "+id_exist, Toast.LENGTH_SHORT).show();

                     /*   AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPrincipal.this);

                        builder.setTitle("Configuracion")
                                .setMessage("Su clave Temporal sera " + ale)
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

                                */


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPrincipal.this);

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

        /*************************Mensajes ***********************/
        verificarPermisoLeerMensaje();
        verificarPermisoRecibeMensaje();

      /*  MyReceiver myObj = new MyReceiver();
        //Set the listener on the object. Created as anonymous
        myObj.setListener(new MyReceiver.Listener() {
            @Override
            public void onInterestingEvent() {

            }

            @Override
            public void mostrarMensaje(String mensaje) {

            }

            // myMethod();
        });  */

        etDato.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              /*  Toast.makeText(Principal.this,"letra "+s,
                        Toast.LENGTH_SHORT).show();
                if(btnSiguiente.getText()) */
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    //  geo_autocomplete_clear.setVisibility(View.VISIBLE);
                    if(s.length()==4 && btnSiguiente.getText().toString() =="Verificar"){
                        btnSiguiente.performClick();
                    }
                }
                else
                {
                    //geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });





    }

    public static RegistroPrincipal  getInstace(){
        return ins;
    }


    @Override
    public void mostrarMensaje(Context con,String mensaje) {

        Toast.makeText(con, mensaje, Toast.LENGTH_SHORT).show();
     // etDato.setText(mensaje);



    }


    public void updateTheTextView(final String t) {
        RegistroPrincipal.this.runOnUiThread(new Runnable() {
            public void run() {

                etDato.setText(t);
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
           Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                    if(!btnSiguiente.getText().equals("Verificar")) {

                        id_exist = object.getBoolean("codigo");

                       String info = object.getString("correo");
                        if(info.equals("EXITO")){
                            tvAviso.setText("Ingrese el codigo que le hemos enviado");
                            etDato.setText("");
                            btnSiguiente.setText("Verificar");
                            tvError.setText("");
                         }
                        if(info.equals("NOEXISTE")){
                            tvError.setText("El numero no existe");
                            etDato.setText("");
                        }
                        if(info.equals("ERROR")){
                            tvError.setText("Fallo el envio");
                        }
                        if(id_exist){
                            // refreshUserList(object.getJSONArray("user");

                           jsonUser = object.getJSONArray("user");
                        }

                    }

                    if(btnSiguiente.getText().equals("Verificar")){

                    }



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

    private class PerformNetworkRequest2 extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest2(String url, HashMap<String, String> params, int requestCode) {
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



                      // refreshUserList(object.getJSONArray("user");

                            jsonUser = object.getJSONArray("user");

                    VerificarRegistro vr = new VerificarRegistro(getApplicationContext(),RegistroPrincipal.this);
                    try {
                        vr.Verificar(jsonUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



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

 /*private void refreshUserList(JSONArray heroes) throws JSONException {


        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            tvusername.setText( obj.getString("id"));
            etname.setText(obj.getString("nombre"));
       }

    } */

    private void enviarMensaje() {

        ale  = generarAleatorio();
        String alea= String.valueOf(ale);
        email = etDato.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("numero",email);
        params.put("codigo",alea);
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_ENVIAR_MENSAJE, params, CODE_POST_REQUEST);
       request.execute();
       etDato.setText("");
    }

    private void obtenerUser(String idu) {


        HashMap<String, String> params = new HashMap<>();
        params.put("idu",idu);
       PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_USER_BY_ID, params, CODE_POST_REQUEST);
        request.execute();

    }

    private void updateClaveRec() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("codigo",String.valueOf(ale));
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_CLAVE_REC, params, CODE_POST_REQUEST);
        request.execute();
    }

  //  MyReceiver miRes = new MyReceiver().onReceive(getApplicationContext(),);

    MyReceiver broadcastReceiver = new MyReceiver();
     //   MyReceiver.setMainActivityHandler(this);    // Le pasamos este activity para vincularlos
    IntentFilter callInterceptorIntentFilter = new IntentFilter("android.intent.action.ANY_ACTION");
   // registerReceiver(broadcastReceiver, callInterceptorIntentFilter);

    private void verificarPermisoLeerMensaje(){
        if (ContextCompat.checkSelfPermission(RegistroPrincipal.this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistroPrincipal.this,
                    Manifest.permission.READ_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistroPrincipal.this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
    private void verificarPermisoRecibeMensaje(){
        if (ContextCompat.checkSelfPermission(RegistroPrincipal.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistroPrincipal.this,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistroPrincipal.this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECIVE_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                  /*  Intent i=new Intent("android.provider.Telephony.SMS_RECEIVED");
                    i.setClass(this, MyReceiver.class);
                    this.sendBroadcast(i); */

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_RECIVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                   Intent i=new Intent("android.provider.Telephony.SMS_RECEIVED");
                    i.setClass(this, MyReceiver.class);

                    this.sendBroadcast(i);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private String verfificarPreferencia(){
          SharedPreferences propi = getSharedPreferences("IDvalue", 0);
        boolean estado = propi.getBoolean("estado",false);
        String id_user = propi.getString("user_idu","");
        if(estado==true){
           obtenerUser(id_user);
        }

        //  this.setTitle("Empleos Peru");
        return "si";

    }




}
