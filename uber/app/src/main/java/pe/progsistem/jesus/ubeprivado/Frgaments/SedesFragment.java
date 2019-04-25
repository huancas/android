package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Adaptador.SedeAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Sede;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static android.app.Activity.RESULT_OK;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class SedesFragment extends Fragment {

    private String cargo_name,criterio,cod_usu="",cargo_real,id_sede;

    ArrayList<Sede> sedeList ;
    private EditText etDescripcion,etSede,etCel;
    private Button btnSave,btnRegistrar,btnDelete,btnSeleccion;
    private ImageView ivCategoria;

    boolean actualizando;

    RecyclerView rv;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    //imagen
    private int PICK_IMAGE_REQUEST = 1;
    private String url_foto;
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sedes, container, false);

       etDescripcion =(EditText) root.findViewById(R.id.etDescripcion);
        etSede =(EditText) root.findViewById(R.id.etSede);
        etCel =(EditText) root.findViewById(R.id.etCel);

        btnSave =(Button) root.findViewById(R.id.btnSave);
        btnRegistrar =(Button) root.findViewById(R.id.btnRegistrar);
        btnDelete =(Button) root.findViewById(R.id.btnDelete);
        btnSeleccion =(Button) root.findViewById(R.id.btnSeleccion);

        ivCategoria =(ImageView) root.findViewById(R.id.ivCategoria);

       sedeList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv = (RecyclerView) root.findViewById(R.id.recycler_view);



        // https://wa.me/51925599914
        btnSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser("foto");
            }
        });

        // Eventos
        rv.setLayoutManager(mLayoutManager);
        readCategorias();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String sede =etSede.getText().toString();

                    if (TextUtils.isEmpty(sede)) {
                        etSede.setError("Please enter sede");
                        etSede.requestFocus();
                        return;
                    }

                  createSedes();
                }catch (Exception e){ Toast.makeText(getActivity(),"error: menu "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();                    }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String sede =etSede.getText().toString();

                    if (TextUtils.isEmpty(sede)) {
                        etSede.setError("Please enter sede");
                        etSede.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(id_sede)) {
                        etSede.setError("Please select a sede");
                        etSede.requestFocus();
                        return;
                    }

                    updateSedes();
                }catch (Exception e){ Toast.makeText(getActivity(),"error: menu "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();                    }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{



                    String sede =etSede.getText().toString();

                    if (TextUtils.isEmpty(sede)) {
                        etSede.setError("Please enter sede");
                        etSede.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(id_sede)) {
                        etSede.setError("Please select a sede");
                        etSede.requestFocus();
                        return;
                    }


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("borrar "+sede)
                            .setMessage("¿esta seguro que desea Eliminar? \nSi elimina se eliminaran las publicaciones asociadas a esta sede")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteSedes();
                                    readCategorias();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();


                }catch (Exception e){ Toast.makeText(getActivity(),"error: menu "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();                    }
            }
        });


        // Inflate the layout for this fragment
        return root;


    }

    private void readCategorias() {

        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_READ_CATEGORIA, null, CODE_GET_REQUEST);

        request.execute();
    }
    private void updateSedes() {
        String imagen;
        if(bitmap==null){
            imagen="nulo";
            Toast.makeText(getContext(), "Debe seleccionar imagen", Toast.LENGTH_SHORT).show();
        }else {
            imagen = getStringImagen(bitmap);

        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id_sede);
        params.put("name", etSede.getText().toString());
        params.put("descripcion", etDescripcion.getText().toString());
        params.put("porcentaje", etCel.getText().toString());
        params.put("img", imagen);
      PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_UPDATE_CATEGORIA, params, CODE_POST_REQUEST);

        request.execute();
        readCategorias();
    }

    private void deleteSedes() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id_sede);
      PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_DELETE_CATEGORIA, params, CODE_POST_REQUEST);
         etSede.setText("");
        etDescripcion.setText("");
        etCel.setText("");
        ivCategoria.setImageDrawable(getResources().getDrawable(R.drawable.img_default));
        request.execute();
        id_sede ="";
        readCategorias();
    }
    private void createSedes() {


        String imagen;
        if(bitmap==null){
            imagen="nulo";
            Toast.makeText(getContext(), "Debe seleccionar imagen", Toast.LENGTH_SHORT).show();
        }else{
            imagen = getStringImagen(bitmap);

             HashMap<String, String> params = new HashMap<>();
            params.put("name",etSede.getText().toString());
            params.put("descripcion",etDescripcion.getText().toString());
            params.put("porcentaje",etCel.getText().toString());
            params.put("img",imagen);

            PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_CREATE_CATEGORIA, params, CODE_POST_REQUEST);

            request.execute();
            readCategorias();
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
                    refreshPublicacionList(object.getJSONArray("categorias"));
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
        sedeList.clear();


        sedeList = new ArrayList<>();
        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


            sedeList.add(new Sede(
                    obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("descripcion"),
                    obj.getString("porcentaje"),
                    obj.getString("img")

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

        rv.setAdapter(new SedeAdapter(getContext(),sedeList, new SedeAdapter.OnItemClickListener() {
            @Override public void onItemClick(Sede item) {
                Toast.makeText(getActivity(), "Contact Clicked:" + item.sede_cel2, Toast.LENGTH_SHORT).show();
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

                id_sede = item.sede_id;
                String name = item.sede_name;
                String descripcion = item.sede_descripcion;
                String cel = item.sede_cel;
                String cel2 = item.sede_cel2;

                etSede.setText(name);
                etDescripcion.setText(descripcion);
                etCel.setText(cel);
                Picasso.get().load(RUTAIMG+cel2).into(ivCategoria);


             /*    ArrayList<String> lista = new ArrayList<String>();

                lista.add(id_sede);
                lista.add(name);
                lista.add(descripcion);
                lista.add(cod_usu);
                //  Intent intent = new Intent(getActivity(),DetalleActivity.class);



                    Intent intent = new Intent(getActivity(), SedeActivity.class);
                    intent.putExtra("listaDetalle", lista);
                    startActivity(intent);

                 */



            }
        }));


    }

   ///////////////imagen ***********************
    private void showFileChooser(String bot) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (bot.equals("foto")) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ivCategoria.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }




}
