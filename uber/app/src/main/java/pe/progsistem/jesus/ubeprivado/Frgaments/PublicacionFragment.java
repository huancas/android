package pe.progsistem.jesus.ubeprivado.Frgaments;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Activities.DetalleActivity;
import pe.progsistem.jesus.ubeprivado.Adaptador.PublicacionAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Contact;
import pe.progsistem.jesus.ubeprivado.beans.Publicacion;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

public class PublicacionFragment extends Fragment {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private String sede_name,criterio="";
    ListView mPublicacionsList;
    ArrayList<Publicacion> publicacionList ;

    RecyclerView rv;

    PublicacionAdapter mPublicacionsAdapter;
  TextView tvSede,mLink,mLink2,tvDescrip;
  private String num_cel;


    public PublicacionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publicacion, container, false);

           //main2
        ArrayList<Contact> contactList = Contact.GenerateContacts() ;
                           publicacionList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
         rv = (RecyclerView) root.findViewById(R.id.recycler_view);

        final String name = getArguments().getString("sede");
              sede_name = getArguments().getString("sede");
        final String descripcion = getArguments().getString("descripcion");
        final String cel = getArguments().getString("cel");
           num_cel =cel;
        final String cel2 = getArguments().getString("cel2");
        criterio = getArguments().getString("criterio");



      //  tvSede =(TextView) root.findViewById(R.id.tvSede);
      //  tvSede.setText(name);
        tvDescrip =(TextView) root.findViewById(R.id.tvDescrip);
        if(!descripcion.equals("null")) {
            tvDescrip.setText(descripcion);
        }
        if(descripcion.equals("null")) {
            tvDescrip.setVisibility(View.GONE);
        }



        mLink = (TextView) root.findViewById(R.id.link);
        if(cel!=null ) {
            if(!cel.equals("null")) {
                SpannableString s = new SpannableString("Whatsapp " + cel);
                s.setSpan(new URLSpan("https://wa.me/51" + cel), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mLink.setMovementMethod(LinkMovementMethod.getInstance());
                mLink.setText(s);
            } if(cel.equals("null")){
                mLink.setVisibility(View.GONE);
            }

        }


        mLink2 = (TextView) root.findViewById(R.id.link2);
        if(cel2!=null ) {
            if(!cel2.equals("null")) {
                SpannableString s2 = new SpannableString("Whatsapp " + cel2);
                s2.setSpan(new URLSpan("https://wa.me/51" + cel2), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mLink2.setMovementMethod(LinkMovementMethod.getInstance());
                mLink2.setText(s2);
            }
            if(cel2.equals("null")){
                mLink2.setVisibility(View.GONE);
            }
        }

       // https://wa.me/51925599914


        // Eventos
        rv.setLayoutManager(mLayoutManager);
        readPublicacion(sede_name,criterio);


         return root;
    }

    private void readPublicacion(String name,String criterio) {
        HashMap<String, String> params = new HashMap<>();
        params.put("sede", name);
        params.put("criterio",criterio);
        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_PUBLICACIONES, params, CODE_POST_REQUEST);
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
           //  Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();


            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshPublicacionList(object.getJSONArray("publicaciones"));
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
        publicacionList.clear();


        publicacionList = new ArrayList<>();
        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


           Publicacion bean = new Publicacion(
                    obj.getString("id"),
                    obj.getString("nombre"),
                    obj.getString("edad"),
                    obj.getString("descrip"),
                    obj.getString("estado"),
                    obj.getString("tarifa"),
                    obj.getString("tarifa2"),
                    obj.getString("tarifa3"),
                    obj.getString("url_video"),
                    obj.getString("url_pri"),
                    obj.getString("sede_id"),
                    obj.getString("cat_id"),
                   obj.getString("id_usu"),
                    obj.getString("nom_usu"),
                    obj.getString("url_pri"),
                    obj.getString("ul_fecha"),
                    obj.getString("sede_nom"),
                   obj.getString("sede_cel"),
                    obj.getString("calif")
            );
          // bean.setSede(new Sede(obj.getString("sede_id"),obj.getString("sede_nom")));

            publicacionList.add(bean);

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

        rv.setAdapter(new PublicacionAdapter(publicacionList, new PublicacionAdapter.OnItemClickListener() {
            @Override public void onItemClick(Publicacion item) {
                Toast.makeText(getActivity(), "Contact Clicked:" + item.getNombre_pu(), Toast.LENGTH_SHORT).show();
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
                Publicacion pu ;

               pu = new Publicacion(
                        item.getId_pu(),
                        item.getNombre_pu(),
                        item.getEdad_pu(),
                        item.getDescrip_pu(),
                        item.getEstado_disponible(),
                       item.getTarifa(),
                       item.getTarifa2(),
                       item.getTarifa3(),
                       item.getUrl_video(),
                       item.getUrl_foto(),
                      item.getSede_id(),
                       item.getCat_id(),
                        item.getUsu_id(),
                        item.getUs_id(),
                       item.getUrl_foto(),
                        item.getUl_fecha(),
                       item.getNombre_sede(),
                       item.getCel_sede(),
                       item.getCalificacion()

                       );
              // pu.setSede(new Sede(item.getSede().getSede_id(),item.getSede().getSede_name()));



                 ArrayList<Publicacion> lista = new ArrayList<Publicacion>();
                                        lista.add(pu);


              /*  lista.add(name);
                lista.add(url_pri); */
                Bundle bund = new Bundle();
                bund.putSerializable("detalle",pu);
                Intent intent = new Intent(getActivity(),DetalleActivity.class);
                intent.putExtra("listadetalle",bund);
                startActivity(intent);

            }
        }));


    }


}
