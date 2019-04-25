package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Adaptador.MyAdapter1;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Atencion;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment {

  private String idAt ="";
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ArrayList<Atencion> listaAtencion ;

    GridView gridView=null;
     MyAdapter1 myAdapter1 =null;
     private String idu="",cargo="";


    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


             listaAtencion = new ArrayList<Atencion>();


        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = (GridView) root.findViewById(R.id.gridView);
        // Datos a mostrar



       idu = getArguments().getString("fidu");
         cargo = getArguments().getString("fcargo");


      /*  // Enlazamos con nuestro adaptador personalizado
        myAdapter1 = new MyAdapter1(getContext(), R.layout.activity_grid_item, names);
        gridView.setAdapter(myAdapter1); */
      readAtencion(idu,cargo);
         return root;
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
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                  refreshAtencionList(object.getJSONArray("atenciones"));
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

    private void readAtencion(String idu,String cargo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("idu", idu);
        params.put("cargo",cargo);

        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_READ_ATENCION, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void refreshAtencionList(JSONArray heroes) throws JSONException {
        listaAtencion.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            listaAtencion.add(new Atencion(
                    obj.getString("id"),
                    obj.getString("fecha"),
                    obj.getString("tipo"),
                    obj.getString("trabajador"),
                    obj.getString("cliente"),
                    obj.getString("trabajador"),
                    obj.getString("bol")

            ));
        }

      /*  MyAdapter1 adapter = new MyAdapter1(listaAtencion);
        listView.setAdapter(adapter); */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View
                    view, int position, long id) {
                Toast.makeText(getContext(), "Clicked: " +
                        listaAtencion.get(position).getUsuario_trabajador(), Toast.LENGTH_LONG).show();
             idAt =  listaAtencion.get(position).getId_at();
             String bool    =  listaAtencion.get(position).getBool_calif();
             if(!bool.equals("SI")) {
                 createCustomDialog().show();
             }
            }
        });

        myAdapter1 = new MyAdapter1(getContext(), R.layout.activity_grid_item,listaAtencion);
        gridView.setAdapter(myAdapter1);
    }

    public AlertDialog createCustomDialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.dialog_signin, null);
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        Button btnFire = (Button) v.findViewById(R.id.btn_fire);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);
        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons
        btnFire.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       int rating = (int) ratingBar.getRating();
                       String rat = String.valueOf(rating);
                       createRating(idAt,rat);
                        // Aceptar
                        alertDialog.dismiss();
                    }
                }
        );
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                }
        );
        return alertDialog;
    }

    private void createRating(String idat,String rating){
        HashMap<String, String> params = new HashMap<>();
       params.put("idat", idat);
        params.put("rat",rating);
       PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_CREATE_RATING, params, CODE_POST_REQUEST);
        request.execute();
        readAtencion(idu,cargo);

    }
}
