package pe.progsistem.jesus.ubeprivado.Activities;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Sede;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.IP;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class MipublicacionActivity extends AppCompatActivity implements View.OnClickListener{
   String cod_idu ="";
    //otro
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    List<Sede> sedesList;

    /*    fecha     */
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;

    /*  fecha fin */
    /*  subir imagen */
    private Button btnBuscar;

    private Button btnSubir;

    private ImageView imageView;

    private EditText editTextName,etEdad,etDescripcion,etTarifa1,etTarifa2,etTarifa3,etUrl_video;

    private TextView title;

    private Bitmap bitmap;


    private int PICK_IMAGE_REQUEST = 1;


    private int PICK_REQUEST = 0;

    private String UPLOAD_URL ="http://"+IP+"/olivos/imagen/upload2.php";

    private String KEY_IMAGEN = "foto";

    private String KEY_NOMBRE = "nombre";
    //SPINER
    private Spinner spnacionalidad,spestado,spsede;


    /*  fin subir  imagen  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mipublicacion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cod_idu = getIntent().getExtras().getString("cod_usu");

        /* fecha     */
        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha


        title = (TextView) findViewById(R.id.tv_titulo);
        //Evento setOnClickListener - clic

        /*   fin fecha */
        /*    subir imagen  */

        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        btnSubir = (Button) findViewById(R.id.btnSubir);

        editTextName = (EditText) findViewById(R.id.txtName);
        etEdad = (EditText) findViewById(R.id.txtEdad);
        etDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        etTarifa1 = (EditText) findViewById(R.id.txtTarifa1);
        etTarifa2 = (EditText) findViewById(R.id.txtTarifa2);
        etTarifa3 = (EditText) findViewById(R.id.txtTarifa3);
        etUrl_video = (EditText) findViewById(R.id.txtUrlvideo);


        imageView  = (ImageView) findViewById(R.id.imageView);


        btnBuscar.setOnClickListener(this);

        btnSubir.setOnClickListener(this);

        title.setOnClickListener(this);
        // spinner

        spestado = (Spinner) findViewById(R.id.spinnerEstado);
        spnacionalidad = (Spinner) findViewById(R.id.spinnerNacionalidad);
        spsede = (Spinner) findViewById(R.id.spinnerSede);

        //segunda opcion
        sedesList = new ArrayList<>();
        readSedes();
        readNacionalidad();
        readPublicacion();


        Toast.makeText(getApplicationContext(),cod_idu, Toast.LENGTH_SHORT).show();
    }



    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(MipublicacionActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(MipublicacionActivity.this, volleyError.getMessage().toString()+"error", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                String   imagen = getStringImagen(bitmap);
                //Obtener el nombre de la imagen
                String nombre = editTextName.getText().toString().trim();
           //     String mApellido_p=  etApp.getText().toString();

                String mFecha_nac= etFecha.getText().toString();
            /*    String mCelular=  etcelular.getText().toString();
                String mCelular2=  etcelular2.getText().toString();
                String mUsuario=  etusuario.getText().toString();
                String mEmail=  etemail.getText().toString();
                String mPassword=  etpassword.getText().toString();*/



                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_NOMBRE, nombre);
             //   params.put("app", mApellido_p);

                params.put("fecha_nac",mFecha_nac);
            /*    params.put("cel", mCelular);
                params.put("cel2", mCelular2);
                params.put("user", mUsuario);
                params.put("email", mEmail);
                params.put("password",mPassword); */
                params.put(KEY_IMAGEN, imagen);
                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
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
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
        if(v == btnBuscar ){
            showFileChooser("foto");
        }
        if(v == btnSubir){
           // uploadImage();
            createPublicacion();
        }
        if(v == title){
            Intent intentReg = new Intent(MipublicacionActivity.this,Inicio.class);
            MipublicacionActivity.this.startActivity(intentReg);
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


    /////////////////////////////////////segunda opcion
    private void readSedes() {
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_READ_SEDES, null, CODE_GET_REQUEST);
        request.setObjeto("sedes");
        request.setSp(spsede);
        request.execute();
    }

    private void readNacionalidad() {
        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_READ_NACIONALIDAD, null, CODE_GET_REQUEST);
        request.setObjeto("naciones");
        request.setSp(spnacionalidad);
        request.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        private String objeto;
        private Spinner sp;

        public Spinner getSp() {
            return sp;
        }

        public void setSp(Spinner sp) {
            this.sp = sp;
        }

        public String getObjeto() {
            return objeto;
        }

        public void setObjeto(String objeto) {
            this.objeto = objeto;
        }

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
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressBar.setVisibility(GONE);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray(objeto),sp);
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

    private void refreshHeroList(JSONArray heroes,Spinner sp) throws JSONException {
        sedesList.clear();

        List<String> values = new ArrayList<String>();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);


         /*   heroList.add(new Sede(
                    obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("realname"),
                    obj.getString("rating"),
                    obj.getString("teamaffiliation")
            )); */
            values.add( obj.getString("name"));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

    }

    private void createPublicacion() {
        String name = editTextName.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String descrip = etDescripcion.getText().toString().trim();
        String tarifa = etTarifa1.getText().toString().trim();
        String tarifa2 = etTarifa2.getText().toString().trim();
        String tarifa3 = etTarifa3.getText().toString().trim();


        String nacionalidad= spnacionalidad.getSelectedItem().toString();
        String sede= spsede.getSelectedItem().toString();
        String estado= spestado.getSelectedItem().toString();
        String url  = etUrl_video.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edad)) {
            etEdad.setError("Please enter edad");
            etEdad.requestFocus();
            return;
        }


        String imagen;
        if(bitmap==null){
            imagen="nulo";
        }else{
            imagen = getStringImagen(bitmap);
        }



        HashMap<String, String> params = new HashMap<>();
        params.put("idu", cod_idu);
        params.put("nombre", name);
        params.put("edad", edad);
        params.put("descripcion",descrip);
        params.put("tarifa",tarifa);
        params.put("tarifa2",tarifa2);
        params.put("tarifa3",tarifa3);
        params.put("nacionalidad",nacionalidad);
        params.put("sede",sede);
        params.put("imagen",imagen);
        params.put("estado",estado);
        params.put("url",url);


        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_CREATE_PUBLICACION, params, CODE_POST_REQUEST);
        request.execute();
    }

    ///////////////////////get   Publicacion /////////////////////////////////////////////////////////////////////////////
    private void readPublicacion() {
        HashMap<String, String> params = new HashMap<>();
        params.put("idu", cod_idu);
        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_PUBLICACION+"&idu="+cod_idu, params, CODE_GET_REQUEST);

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
                    refreshPublicacionList(object.getJSONArray("publicacion"));
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
        sedesList.clear();



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
           String nombre = obj.getString("nombre");
           String edad = obj.getString("edad");
           String descrip = obj.getString("descrip");
           String estado = obj.getString("estado");
           String tarifa = obj.getString("tarifa");
           String tarifa2 = obj.getString("tarifa2");
           String tarifa3 = obj.getString("tarifa3");
           String url_video = obj.getString("url_video");
           String url_pri = obj.getString("url_pri");

          String sede = obj.getString("sede");
           String cat = obj.getString("categoria");

        selectValue(spsede, sede);
         selectValue(spnacionalidad, cat);


           editTextName.setText(nombre);
           etEdad.setText(edad);
           etDescripcion.setText(descrip);

        etTarifa1.setText(tarifa);
        etTarifa2.setText(tarifa2);
        etTarifa3.setText(tarifa3);
        etUrl_video.setText(url_video);

          selectValue(spestado,estado);

           String ruta =RUTAIMG+url_pri;
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
