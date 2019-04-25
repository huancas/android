package pe.progsistem.jesus.ubeprivado.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import pe.progsistem.jesus.ubeprivado.Menu;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.IP;



public class Inicio extends AppCompatActivity {
    TextView tv_registrar,tvOlvido;
    EditText et_usuario, et_password;
    Button btn_log;
    private String user,pass;

    boolean estado;

    Switch simpleSwitch ;

    // check current state of a Switch (true or false).







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
        setContentView(R.layout.activity_inicio);

            // Make sure this is before calling super.onCreate
            setTheme(R.style.AppTheme);


        et_usuario = (EditText) findViewById(R.id.TV_usu);
        et_password = (EditText) findViewById(R.id.TV_pas);
        btn_log = (Button) findViewById(R.id.Btn_log);
        tv_registrar = (TextView) findViewById(R.id.tv_registrar);
        //switch button
            simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
            //set the current state of a Switch
            simpleSwitch.setChecked(false);
            tvOlvido = (TextView) findViewById(R.id.tv_olvido);



        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(Inicio.this,Menu.class);
                Inicio.this.startActivity(intentReg);
            }
        });
            tvOlvido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentReg = new Intent(Inicio.this,RecuperacionActivity.class);
                    Inicio.this.startActivity(intentReg);
                }
            });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try{
                 String us = et_usuario.getText().toString().trim();
                String pas = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(us)) {
                    et_usuario.setError("Please enter username o email");
                    et_usuario.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pas)) {
                    et_password.setError("Please enter clave");
                    et_password.requestFocus();
                    return;
                }
                loguear();
            }catch (Exception e){ Toast.makeText(Inicio.this,"error: menu "+e.getMessage(),
                    Toast.LENGTH_SHORT).show();                    }
            }
        });
    }catch (Exception e){ Toast.makeText(Inicio.this,"error: menu "+e.getMessage(),
            Toast.LENGTH_SHORT).show();                    }

        verfificarPreferencia();

      // et_usuario.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.user, 0);
        getSupportActionBar().hide();

    }
    private void verfificarPreferencia(){


        SharedPreferences propi = getSharedPreferences("IDvalue", 0);
        estado = propi.getBoolean("estado",false);
         if(estado==true){
             user =  propi.getString("user","");
             pass =  propi.getString("pass","");
             simpleSwitch.setChecked(true);
             loguear();

         }

      //  this.setTitle("Empleos Peru");

    }

    private void loguear(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"verificando...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FuncionesApp.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                     Toast.makeText(Inicio.this, s , Toast.LENGTH_LONG).show();

                        try {
                            JSONObject object = new JSONObject(s);
                          Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();


                          if (object.getBoolean("succes")) {
                               // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                              String idu = object.getString("id");
                             String name = object.getString("name");
                              String app = object.getString("app");
                              String cel = object.getString("cel");
                              String cel2 = object.getString("cel2");
                              String email = object.getString("email");
                             String perfil = object.getString("perfil");

                              String finicio = object.getString("inicio");
                              String descuento = object.getString("descuento");
                              String provincia = object.getString("provincia");

                              String casa = object.getString("casa");
                              String estudio = object.getString("cestudio");
                              String trabajo = object.getString("trabajo");
                              String otro = object.getString("otro");

                              String cod_idc = object.getString("cargo_idc");
                              String cargo = object.getString("cargo_nom");

                              String estado_activo = object.getString("estado_activo");





                            ArrayList<String> lista = new ArrayList<String>();
                              lista.add(idu);
                              lista.add(name);
                              lista.add(app);
                              lista.add(cel);
                              lista.add(cel2);
                              lista.add(email);
                              lista.add(perfil);
                              lista.add(finicio);
                              lista.add(descuento);
                              lista.add(provincia);
                              lista.add(casa);
                              lista.add(estudio);
                              lista.add(trabajo);
                              lista.add(otro);

                              lista.add(cod_idc);
                              lista.add(cargo);


                              if(simpleSwitch.isChecked()){
                                 //SharedPreferences propiedades = getPreferences(Context.MODE_PRIVATE);
                                  SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                                  SharedPreferences.Editor editor = mPrefs.edit();
                                  editor.putBoolean("estado",true);
                                //  String useru = et_usuario.getText().toString();
                               //   String passu = et_password.getText().toString();
                                  editor.putString("user",user);
                                  editor.putString("pass",pass);
                                  editor.commit();



                                }else{
                                 // SharedPreferences propiedades = getPreferences(Context.MODE_PRIVATE);
                                  SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
                                  SharedPreferences.Editor editor = mPrefs.edit();
                                  editor.putBoolean("estado",false);
                                  editor.commit();

                                }

                              if(estado_activo.equalsIgnoreCase("ACTIVO")) {

                                Intent intentReg = new Intent(Inicio.this, Principal.class);
                                  intentReg.putExtra("lista", lista);
                                  Inicio.this.startActivity(intentReg);


                                /*  Intent intentReg = new Intent(Inicio.this, Mapszona.class);
                                  intentReg.putExtra("lista", lista);
                                  Inicio.this.startActivity(intentReg); */
                                 finish();
                              }else{
                                  ////////////////////mostramos mensaje
                                  AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);

                                  builder.setTitle("Cuenta Suspendida")
                                          .setMessage("Su cuenta ha sido Suspendida por favor contactese con el administrador")
                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int which) {
                                                 // deleteHero(hero.getId());
                                              }
                                          })
                                          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int which) {

                                              }
                                          })
                                          .setIcon(android.R.drawable.ic_dialog_alert)
                                          .show();

                                }
                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Inicio.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Obtener datos del editex


                if(estado==false){
                    user = et_usuario.getText().toString();
                    pass = et_password.getText().toString();
                    pass =  convertToMD5(pass);
                 }

                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put("user", user);
                params.put("pass", pass);

               //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    public String convertToMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }

}
