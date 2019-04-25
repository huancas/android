package pe.progsistem.jesus.ubeprivado.Activities;

import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.progsistem.jesus.ubeprivado.Adaptador.MyAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Direccion;
import pe.progsistem.jesus.ubeprivado.beans.Fotos;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText etBusqueda;

    //otro
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
   List<Direccion> listaDir ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       ArrayList<Address> lista = new ArrayList<>();

    /*   recibir lista de otra activity
       Bundle bund = new Bundle();
        bund = getIntent().getBundleExtra("listaaddress");
        lista = (ArrayList<Address>) bund.getSerializable("faddress");

        int size = getIntent().getIntExtra("size",0);

       /* bund.putSerializable("faddress", as);

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("listaaddress",bund); */



        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        etBusqueda = (EditText) findViewById(R.id.etBusqueda);

      /*  List<String> inpute = new ArrayList<>();
        inpute.add("--Total Resultados --: "+size);
        inpute.add(" Cercado de lima");
        inpute.add(" la victoria ");
        inpute.add(" sta anita ");

        for (int i = 0; i < lista.size(); i++) {
            inpute.add("Test "+i+" " + lista.get(i).getFeatureName());
        }// define an adapter

        */
       // mAdapter = new MyAdapter(inpute);
      //   recyclerView.setAdapter(mAdapter);

        etBusqueda.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Toast.makeText(MainActivity.this,"letra "+s,
                    //    Toast.LENGTH_SHORT).show();
                readFotos( etBusqueda.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    //  geo_autocomplete_clear.setVisibility(View.VISIBLE);
                }
                else
                {
                    //geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });

    }

    public class PerformNetworkRequest2 extends AsyncTask<Void, Void, String> {

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
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressBar.setVisibility(GONE);
           // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


            try {
               JSONObject object = new JSONObject(s);
               // JSONArray jsonarray = new JSONArray(s);

               // if (!object.getBoolean("error")) {
                 //   Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    // refreshPublicacionList(object.getJSONArray("publicacion"));
              /* JSONArray arr =   object.getJSONArray("results");
                if(arr.length()>0){
                    JSONObject obj = arr.getJSONObject(0);
                    if(obj.getString("status").equals("OK")){
                        refreshFotosList(object.getJSONArray("results"));
                    }
                } */


                   refreshFotosList(object.getJSONArray("results"));


              //  }
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

    private void refreshFotosList(JSONArray heroes) throws JSONException {
       // sedesList.clear();
        listaDir = new ArrayList<>();



        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            Direccion dir = new Direccion();

            String address = obj.getString("name");
            String fadress = obj.getString("formatted_address");
            String ico = obj.getString("icon");

            JSONObject geometryJObject = obj.getJSONObject("geometry");
            JSONObject locationJObject = geometryJObject.getJSONObject("location");
            String lat = locationJObject.getString("lat");
            String lng = locationJObject.getString("lng");


            dir.setName(address);
            dir.setFormatted_address(fadress);
            dir.setIcono(ico);
            dir.setGeometrylocationlat(lat);
            dir.setGeometrylocationlng(lng);

           listaDir.add(dir);


           mAdapter = new MyAdapter(listaDir);
         ((MyAdapter) mAdapter).setContext(MainActivity.this);
            recyclerView.setAdapter(mAdapter);


            // String ruta =RUTAIMG+url_pri;
            // Picasso.get().load(ruta).into(imageView);


        }


    }

    private void readFotos(String texto) {
        HashMap<String, String> params = new HashMap<>();
      String  textB = texto.replace(" ", "%20");
       // text.replaceAll(",","\n");
        params.put("query", textB);
       PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_DIR+"&query="+textB, null, CODE_GET_REQUEST);

        request.execute();
    }







}
