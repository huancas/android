package pe.progsistem.jesus.ubeprivado.Frgaments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.Activities.PerfilActivity;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static android.app.Activity.RESULT_OK;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class Perfiles extends Fragment {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private String cod_idu;


    TextView art, tvname, tvapp, tvemail, tvusuario, tvusername, tvcel, tvcel2;

    Button btnEdit, btnPass;
    ImageView logo;
    private Bitmap bitmap;

    private Button btnBuscar;
    private int PICK_IMAGE_REQUEST = 1;

    public ViewImageExtended viewImageExtended;
    private String url_foto;


    public Perfiles() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  ************solo  para recibir datos de una actividad  *************************
        // extraemos  el nombre fcod_id
        final String cod_id = getArguments().getString("fcod_id");
        cod_idu = cod_id;
        final String username = getArguments().getString("fuser");
        final String name = getArguments().getString("fname");
        final String app = getArguments().getString("fapp");
        final String cel = getArguments().getString("fcel");
        final String cel2 = getArguments().getString("fcel2");
        final String email = getArguments().getString("femail");
        final String fperfil = getArguments().getString("fperfil");
        // int edad = getArguments().getInt("age");


        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        tvusername = (TextView) root.findViewById(R.id.tvUsername);
        tvname = (TextView) root.findViewById(R.id.tvUser);
        tvcel = (TextView) root.findViewById(R.id.tvCel);
        tvcel2 = (TextView) root.findViewById(R.id.tvCel2);
        tvemail = (TextView) root.findViewById(R.id.tvEmail);
        btnEdit = (Button) root.findViewById(R.id.btnEdit);
        btnPass = (Button) root.findViewById(R.id.btnPass);
        btnBuscar = (Button) root.findViewById(R.id.btnBuscar);


        tvusername.setText(username);
        tvname.setText(name + " " + app);
        tvcel.setText(cel);
        tvcel2.setText(cel2);
        tvemail.setText(email);

        //tvapp.setText(edad);
        logo = (ImageView) root.findViewById(R.id.imagenProfile);
        //  logo.setImageResource(R.drawable.lady);
        // downloadFile(imageHttpAddress);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bund = new Bundle();
                bund.putString("idu", cod_id);
                bund.putString("cargo", "USUARIO");

                Intent i = new Intent(getContext(), PerfilActivity.class);
                i.putExtras(bund);
                startActivity(i);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser("foto");
            }
        });



        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomDialog().show();
            }
        });

        /*String ruta = RUTAIMG + fperfil;
        Picasso.get().load(ruta).into(logo); */
        readPerfil();

        return root;
    }

    public AlertDialog createCustomDialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.dialog_clave, null);
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        final EditText etAntiguo = (EditText) v.findViewById(R.id.etCActual);
        final EditText etNuevo = (EditText) v.findViewById(R.id.etCNueva);
        final EditText etRepite = (EditText) v.findViewById(R.id.etCRepite);
        Button btnFire = (Button) v.findViewById(R.id.btn_fire);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);
        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons
        btnFire.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String cAntiguo = etAntiguo.getText().toString();
                        String cNuevo = etNuevo.getText().toString();
                        String cRepite = etRepite.getText().toString();

                        if (TextUtils.isEmpty(cAntiguo)) {
                            etAntiguo.setError(" ingrese clave");
                            etAntiguo.requestFocus();
                            return;
                        }

                        if (cNuevo.equals(cRepite)) {

                            // createRating(idAt,rat);
                            // Aceptar
                            alertDialog.dismiss();
                            updateClave(cAntiguo, cNuevo);

                        } else {
                            etRepite.setError("Las claves no coinciden");
                            etRepite.requestFocus();

                        }


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
            //  progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //   progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                     refreshPerfil(object.getJSONArray("perfil"));
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

    private void updateClave(String antiguo, String nuevo) {
        String id = cod_idu;
        String cantiguo = antiguo;
        String cnuevo = nuevo;


        HashMap<String, String> params = new HashMap<>();
        params.put("idu", id);
        params.put("cantiguo", cantiguo);
        params.put("cnuevo", cnuevo);


        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_CLAVE, params, CODE_POST_REQUEST);
        request.execute();


    }

    private void showFileChooser(String bot) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (bot.equals("foto")) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                logo.setImageBitmap(bitmap);
                updatePerfil();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void updatePerfil() {
        String imagen;
        if (bitmap == null) {
            imagen = "nulo";
            Toast.makeText(getContext(), "Debe seleccionar imagen", Toast.LENGTH_SHORT).show();
        } else {
            imagen = getStringImagen(bitmap);
            HashMap<String, String> params = new HashMap<>();
            params.put("idu", cod_idu);
            params.put("imagen", imagen);
            PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_PERFIL, params, CODE_POST_REQUEST);
            request.execute();
            //readPerfil();
        }
    }

    private void refreshPerfil(JSONArray heroes) throws JSONException {


        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            String url = obj.getString("foto_perfil");

          url_foto =url;

            String ruta = RUTAIMG + url;
            Picasso.get().load(ruta).into(logo);

        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agrandar(url_foto);
            }
        });


    }

    private void readPerfil() {
        HashMap<String, String> params = new HashMap<>();
        params.put("idu", cod_idu);
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_GET_PERFIL, params, CODE_POST_REQUEST);
        request.execute();
    }

    public void agrandar(String url){
        // Esto es para evitar crear cuadros de diálogos varias veces
        if(viewImageExtended == null || viewImageExtended.getDialog() == null || !viewImageExtended.getDialog().isShowing()){

            // Si estas en un activity
            FragmentManager fm = getActivity().getSupportFragmentManager();

            // Si estas en un fragment y pasaste el activity en el constructor
            //FragmentManager fm = this.activity.getSupportFragmentManager();

            Bundle arguments = new Bundle();

            // Aqui le pasas el bitmap de la imagen
            // arguments.putParcelable("PROFILE_PICTURE", bitmap);
            arguments.putString("furl",url);
            viewImageExtended = ViewImageExtended.newInstance(arguments);
            viewImageExtended.show(fm, "ViewImageExtended");
        }
    }
}
