package pe.progsistem.jesus.ubeprivado.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import pe.progsistem.jesus.ubeprivado.Activities.Principal;
import pe.progsistem.jesus.ubeprivado.Activities.RegistroPrincipal;


public class MyReceiver extends BroadcastReceiver {
    private Listener mListener;

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    final SmsManager sms = SmsManager.getDefault();
    public MyReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            for (int i = 0; i < pdusObj.length; i++) {
                SmsMessage MENSAJE = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                String REMITENTE = MENSAJE.getDisplayOriginatingAddress();
                String MENSAJEE = MENSAJE.getDisplayMessageBody();
              //  Log.i("RECEIVER", "numero:" + REMITENTE + " Mensaje:" + MENSAJEE);
              //  Toast.makeText(context, "buscando ... "+REMITENTE+" :"+MENSAJEE, Toast.LENGTH_SHORT).show();
                if(MENSAJEE.contains("verificacion es:")){

                    String codigo = "";

                    String cadena1 = MENSAJEE;
                    cadena1 = cadena1.toLowerCase();
                    String cadena2 = "verificacion es:";

                   if (cadena1.indexOf(cadena2) > -1) {
                        codigo = cadena1.substring(cadena1.indexOf(cadena2)+16 ,cadena1.indexOf(cadena2) +20);
                        //veri



                     mListener = new RegistroPrincipal();
                    //   mListener = (Listener) context;
                       mListener.mostrarMensaje(context,codigo);

                       try {
                          RegistroPrincipal  .getInstace().updateTheTextView(codigo);
                       } catch (Exception e) {

                       }



                   }

                    abortBroadcast();
                }



                if(MENSAJEE.contains("facebook")){
                    abortBroadcast();
                }
              //  new peticion_get(REMITENTE+"--"+MENSAJEE).execute();
            }
        }

    }



    public interface Listener {

        void mostrarMensaje(Context con,String mensaje);

    }
     public void setListener(Listener listener) { mListener = listener; }



}
