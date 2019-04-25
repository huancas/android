package pe.progsistem.jesus.ubeprivado.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.progsistem.jesus.ubeprivado.Activities.Principal;


public class VerificarRegistro {
    private Context context;
    private Activity activity;
    public static boolean existe;
    private ArrayList<String> lista;

    public VerificarRegistro() {
    }

    public VerificarRegistro(Context context,Activity activity) {
        this.context = context;
        this.activity =activity;
    }


    public  void Verificar(JSONArray objeto) throws JSONException {

      //  JSONObject object = objeto;

       if(0 < objeto.length()) {
            JSONObject object = objeto.getJSONObject(0);
            // Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
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

           lista = new ArrayList<String>();
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



           //SharedPreferences propiedades = getPreferences(Context.MODE_PRIVATE);
           SharedPreferences mPrefs = context.getSharedPreferences("IDvalue", 0);
           SharedPreferences.Editor editor = mPrefs.edit();
           editor.putBoolean("estado",true);
           //  String useru = et_usuario.getText().toString();
           //   String passu = et_password.getText().toString();
           editor.putString("user_idu",idu);
           // editor.putString("pass",pass);
           editor.commit();



                             /*
                                    // SharedPreferences propiedades = getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences mPrefs = context.getSharedPreferences("IDvalue", 0);
                                    SharedPreferences.Editor editor = mPrefs.edit();
                                    editor.putBoolean("estado",false);
                                    editor.commit();
                                    */


           if(estado_activo.equalsIgnoreCase("ACTIVO")) {

               try {
                   Intent intentReg = new Intent(context, Principal.class);
                   intentReg.putExtra("lista", lista);
                   activity.startActivity(intentReg);
                   //intentReg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                                /*  Intent intentReg = new Intent(Inicio.this, Mapszona.class);
                                  intentReg.putExtra("lista", lista);
                                  Inicio.this.startActivity(intentReg); */
                   activity.finish();
               }catch (Exception e){
                   Toast.makeText(context, "Eroore "+e.getMessage(), Toast.LENGTH_LONG).show();
               }

           }else{
               ////////////////////mostramos mensaje
               AlertDialog.Builder builder = new AlertDialog.Builder(context);

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


       }




}
