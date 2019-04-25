package pe.progsistem.jesus.ubeprivado.Activities;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class PromocionActivity extends AppCompatActivity implements View.OnClickListener {
    String cod_idu ="";
    //otro
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;



    private Button btnBuscar;

    private Button btnSubir;

    private ImageView imageView;

    private EditText etTitulo,etDescripcion;
    private Bitmap bitmap;


    private int PICK_IMAGE_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cod_idu = getIntent().getExtras().getString("cod_usu");





        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        btnSubir = (Button) findViewById(R.id.btnSubir);


        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etTitulo = (EditText) findViewById(R.id.etTitulo);


        imageView  = (ImageView) findViewById(R.id.imageView);


        btnBuscar.setOnClickListener(this);

        btnSubir.setOnClickListener(this);

       readPromocion();


        Toast.makeText(getApplicationContext(),cod_idu, Toast.LENGTH_SHORT).show();
    }




    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    private void showFileChooser(String bot) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(bot.equals("foto")){
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onClick(View v) {

        if(v == btnBuscar ){
            showFileChooser("foto");
        }
        if(v == btnSubir){
            // uploadImage();
            createPublicacion();
        }
    }
    //flecha atras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
              //  Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }










    private void createPublicacion() {
        String titulo = etTitulo.getText().toString().trim();
        String descrip = etDescripcion.getText().toString().trim();

        String imagen;
        if(bitmap==null){
            imagen="nulo";
        }else{
            imagen = getStringImagen(bitmap);
        }



        HashMap<String, String> params = new HashMap<>();
        params.put("titulo",titulo);
        params.put("descripcion", descrip);
       params.put("imagen",imagen);

       PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_CREATE_PROMOCION, params, CODE_POST_REQUEST);
        request.execute();
    }

    ///////////////////////get   Publicacion /////////////////////////////////////////////////////////////////////////////
    private void readPromocion() {

        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_PROMOCION, null, CODE_GET_REQUEST);

        request.execute();
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
          //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshPublicacionList(object.getJSONArray("promocion"));
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


        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


         /*   heroList.add(new Sede(
                    obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("realname"),
                    obj.getString("rating"),
                    obj.getString("teamaffiliation")
            )); */
            // values.add( obj.getString("name"));
            //
            String id = obj.getString("id");
            String nombre = obj.getString("titulo");
            String descrip = obj.getString("descripcion");
            String url_img = obj.getString("url_img");


            etTitulo.setText(nombre);
            etDescripcion.setText(descrip);

            String ruta =RUTAIMG+url_img;
            Picasso.get().load(ruta).into(imageView);


        }




    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}
