package pe.progsistem.jesus.ubeprivado.util;

import android.os.AsyncTask;

public class TestAsync extends AsyncTask<Void, Integer, String>
{
    String TAG = getClass().getSimpleName();

    protected void onPreExecute (){
        super.onPreExecute();
       // Log.d(TAG + " PreExceute","On pre Exceute......");
    }

    protected String doInBackground(Void...arg0) {
      //  Log.d(TAG + " DoINBackGround","On doInBackground...");

        for(int i=0; i<10; i++){
            Integer in = new Integer(i);
            publishProgress(i);
        }
        return "You are at PostExecute";
    }

    protected void onProgressUpdate(Integer...a){
        super.onProgressUpdate(a);
       // Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
      //  Log.d(TAG + " onPostExecute", "" + result);
    }
}