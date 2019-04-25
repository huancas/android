package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Activities.AtencionActivity;
import pe.progsistem.jesus.ubeprivado.Activities.PerfilActivity;
import pe.progsistem.jesus.ubeprivado.Adaptador.UserAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Contact;
import pe.progsistem.jesus.ubeprivado.beans.User;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;


public class UsuariosFragment extends Fragment {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private String cargo_name,criterio,cod_usu="",cargo_real;

    ArrayList<User> usersList ;

    RecyclerView rv;

    UserAdapter mPublicacionsAdapter;



    public UsuariosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        //main2
        ArrayList<Contact> contactList = Contact.GenerateContacts() ;
        usersList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv = (RecyclerView) root.findViewById(R.id.recycler_view);

        final String name = "hola";
        cargo_name = getArguments().getString("fcargo");
        cargo_real = cargo_name;

        if(cargo_name.equals("TRABAJADOR")){
            cargo_name="CLIENTE";
        }else if(cargo_name.equals("CLIENTE")){
            cargo_name="TRABAJADOR";
        }else if(cargo_name.equals("ADMINISTRADOR")){
            cargo_name="ADMINISTRADOR";
        }

        criterio = getArguments().getString("fcriterio");
        cod_usu = getArguments().getString("fcod_usu");
        final String descripcion = "haha";
        final String cel ="92559914";
        final String cel2 = "92559914";



        // https://wa.me/51925599914


        // Eventos
        rv.setLayoutManager(mLayoutManager);
        readPublicacion(cargo_name,criterio);


        return root;
    }

    private void readPublicacion(String name,String criterio) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cargo", name);
        params.put("criterio",criterio);
        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_USUARIOS, params, CODE_POST_REQUEST);
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
                    refreshPublicacionList(object.getJSONArray("usuarios"));
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

    private void refreshPublicacionList(JSONArray heroes) throws JSONException {
        usersList.clear();


        usersList = new ArrayList<>();
        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


            usersList.add(new User(
                    obj.getString("id"),
                    obj.getString("nombre"),
                    obj.getString("sexo"),
                    obj.getString("fenac"),
                    obj.getString("celular"),
                    obj.getString("celular1"),
                    obj.getString("username"),
                    obj.getString("email"),
                    obj.getString("foto_perfil"),
                    obj.getString("estado"),
                    obj.getString("fecha_in"),
                    obj.getString("ul_vez"),
                    obj.getString("cargo")

            ));

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

        rv.setAdapter(new UserAdapter(getContext(),usersList, new UserAdapter.OnItemClickListener() {
            @Override public void onItemClick(User item) {
                Toast.makeText(getActivity(), "Contact Clicked:" + item.getNom_user(), Toast.LENGTH_SHORT).show();
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

                String id_cli = item.getIdu();
                String name = item.getNom_user();
                String url_pri = item.getFoto_perfil();



                ArrayList<String> lista = new ArrayList<String>();

                lista.add(id_cli);
                lista.add(name);
                lista.add(url_pri);
                lista.add(cod_usu);
              //  Intent intent = new Intent(getActivity(),DetalleActivity.class);

                if(cargo_real.equals("TRABAJADOR")) {

                    Intent intent = new Intent(getActivity(), AtencionActivity.class);
                    intent.putExtra("listaDetalle", lista);
                    startActivity(intent);

                }

                if(cargo_real.equals("ADMINISTRADOR")){
                     Bundle bund = new Bundle();
                    bund.putString("idu",id_cli);
                    bund.putString("cargo","ADMINISTRADOR");

                    Intent i = new Intent(getContext(),PerfilActivity.class);
                    i.putExtras(bund);
                    startActivity(i);


                   }

            }
        }));


    }


}

