package pe.progsistem.jesus.ubeprivado.Frgaments;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Activities.DetalleActivity;
import pe.progsistem.jesus.ubeprivado.Adaptador.CarroAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Carro;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;


public class CarroFragment extends Fragment {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    ArrayList<Carro> carroList ;

    RecyclerView rv;
    private double precio;
    private String idu;




    public CarroFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_carro, container, false);

        precio = getArguments().getDouble("precio");
        idu = getArguments().getString("id");


        carroList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rv = (RecyclerView) root.findViewById(R.id.recycler_view);

        // https://wa.me/51925599914


        // Eventos
        rv.setLayoutManager(mLayoutManager);
        rv.setHasFixedSize(true);
        readcarro();


        return root;
    }

    private void readcarro() {


        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_CATEGORIAS, null, CODE_GET_REQUEST);
        request.execute();
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
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressBar.setVisibility(GONE);
          Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();


            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshcarroList(object.getJSONArray("categorias"));
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

    private void refreshcarroList(JSONArray heroes) throws JSONException {
        //carroList.clear();


        carroList = new ArrayList<>();
     for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


            Carro bean = new Carro();
            bean.setIdca(obj.getString("id"));
            bean.setCategoria(obj.getString("name"));
            bean.setImg_carro(obj.getString("img"));

                 double porcentaje = Double.parseDouble(obj.getString("porcentaje"));

                      double calc = (precio + (precio *porcentaje))/10;

        double calr = formatearDecimales(calc, 2);

           bean.setPorcentage(String.valueOf(calr));


            bean.setDescripcion(obj.getString("descripcion"));


            // bean.setSede(new Sede(obj.getString("sede_id"),obj.getString("sede_nom")));

            carroList.add(bean);

            // values.add( obj.getString("name"));
            //
          /*  String id = obj.getString("id");
            String nombre = obj.getString("nombre");
            String edad = obj.getString("edad");
            String descrip = obj.getString("descrip");
            String estado = obj.getString("estado");
            String tarifa = obj.getString("tarifa");
            String tarifa2 = obj.getString("tarifa2");
            String tarifa3 = obj.getString("tarifa3");
            String url_video = obj.getString("url_video");
            String url_pri = obj.getString("url_pri");

            editTextName.setText(nombre);
            etEdad.setText(edad);
            etDescripcion.setText(descrip);

            etTarifa1.setText(tarifa);
            etTarifa2.setText(tarifa2);
            etTarifa3.setText(tarifa3);
            etUrl_video.setText(url_video);

            String ruta =RUTAIMG+url_pri;
            Picasso.get().load(ruta).into(imageView); */


        }

      /*  Carro bean = new Carro();
        bean.setIdca("1");
        bean.setCategoria("POOL");
        bean.setImg_carro("img");
       // bean.setPorcentage("porce");
        bean.setDescripcion("servicio Compartido");
        bean = new Carro();
        bean.setIdca("2");
        bean.setCategoria("X");
        bean.setImg_carro("img");
        // bean.setPorcentage("porce");
        bean.setDescripcion("servicio privado");
        bean = new Carro();
        bean.setIdca("3");
        bean.setCategoria("Balck");
        bean.setImg_carro("img");
        // bean.setPorcentage("porce");
        bean.setDescripcion("servicio de lujo");
        carroList.add(bean);  */

        rv.setAdapter(new CarroAdapter(carroList, new CarroAdapter.OnItemClickListener() {
            @Override public void onItemClick(Carro item) {
                Toast.makeText(getActivity(), "Contact Clicked:" + item.getCategoria(), Toast.LENGTH_SHORT).show();
            /*    // ********** para  enviar datos  de un fragment  a otro  fragment *******************
                DetalleFragment dimensionFragment = new DetalleFragment();
                FragmentManager fragmentManager = getFragmentManager();

                Bundle args = new Bundle();
                args.putString("nombre", item.name);
                args.putString("descrip",item.name);
               // args.putInt("img",img);
                dimensionFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             //   fragmentTransaction.replace(R.id.content_frame, dimensionFragment);
                fragmentTransaction.replace(R.id.content_frame, dimensionFragment);
                fragmentTransaction.addToBackStack(null); fragmentTransaction.commit();
                //***********************************************************************************/
                Carro pu ;

                pu = new Carro();
                pu.setIdca(item.getIdca());
                pu.setCategoria(item.getCategoria());
                pu.setImg_carro(item.getImg_carro());
                pu.setDescripcion(item.getDescripcion());
                // pu.setSede(new Sede(item.getSede().getSede_id(),item.getSede().getSede_name()));



                ArrayList<Carro> lista = new ArrayList<Carro>();
                lista.add(pu);

          /*  lista.add(name);
                lista.add(url_pri); */
                Bundle bund = new Bundle();
                bund.putSerializable("detalle", (Serializable) pu);
                bund.putString("idu",idu);
              Intent intent = new Intent(getActivity(),DetalleActivity.class);
                intent.putExtra("listadetalle",bund);
                startActivity(intent);

            }
        }));


    }

    public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }







}
