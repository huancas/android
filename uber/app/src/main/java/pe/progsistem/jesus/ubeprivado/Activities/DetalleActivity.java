package pe.progsistem.jesus.ubeprivado.Activities;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import pe.progsistem.jesus.ubeprivado.Frgaments.FragmentViewVideoExtended;
import pe.progsistem.jesus.ubeprivado.Frgaments.ViewImageExtended;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Carro;
import pe.progsistem.jesus.ubeprivado.beans.Fotos;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class DetalleActivity extends AppCompatActivity implements View.OnClickListener  {
    String cod_idu ="",cod_cat,precio;
    LinearLayout rl;
    LinearLayout lprog;

    //otro
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    public ViewImageExtended viewImageExtended;
    public FragmentViewVideoExtended viewVideoExtended;
   // List<Sede> sedesList;

    String nombre;
    private TextView  tvNombre, tvDescripcion, tvSend,tvPrecio;

    ImageView imageView;


    /***************************** widget ****************************/
    private static final String CERO = "0";
    private static final String BARRA = "/";

    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    //Calendario para obtener fecha & hora
   //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    //Widgets
    EditText etFecha;
    ImageButton ibObtenerFecha;

    //Widgets
    EditText etHora;
    ImageButton ibObtenerHora;
    Switch showProg;
    /*****************************************************************************/

   Spinner spCantidad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        //para flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // If your minSdkVersion is 11 or higher, instead use:
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        ///    /flecha atras

       // ArrayList<Publicacion> lista = (ArrayList<Publicacion>) getIntent().getSerializableExtra("listaDetalle");


      Carro pu = new Carro();
      Bundle bund = new Bundle();
       bund = getIntent().getBundleExtra("listadetalle");

       Carro objeto = (Carro) bund.getSerializable("detalle");
       cod_idu = bund.getString("idu");


         //   pu  = lista.get(0);
        pu = objeto;
      String  url_pri = pu.getImg_carro();
              nombre = pu.getCategoria();
              precio = pu.getPorcentage();
              cod_cat = pu.getIdca();

        String descrip = pu.getDescripcion();
        String edad = pu.getPorcentage();
        String nacion = pu.getIdca();
        String usuario = pu.getIdca();
        String sede = pu.getPorcentage();
       String nom_sede = "";
          String nom_usuario = "";
          String cel = "";





        tvSend = (TextView) findViewById(R.id.tvSend);




       tvNombre = (TextView) findViewById(R.id.tvNombre);
       tvDescripcion =(TextView) findViewById(R.id.tvDescripcion);



                tvSend=(TextView) findViewById(R.id.tvSend);
        tvPrecio=(TextView) findViewById(R.id.tvPrecio);
        tvPrecio.setText(precio);



        imageView =(ImageView) findViewById(R.id.imageView);
        tvNombre.setText(nombre);
        tvDescripcion.setText(descrip);
        final String url_foto = pu.getImg_carro();

        String rat =pu.getIdca();


        String rut = RUTAIMG + url_foto;
        Picasso.get().load(rut).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                agrandar(url_foto);

            }
        });

         rl = (LinearLayout) findViewById(R.id.LinearLay);

        readFotos();
        spCantidad=(Spinner) findViewById(R.id.spinnerCantidad);





        lprog= (LinearLayout) findViewById(R.id.llProg);
        lprog.setVisibility(View.GONE);


        //String ruta =RUTAIMG+url_pri;
       // Picasso.get().load(ruta).into(img);
        /*****************************************+widget ******************************/
        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);
        //hora
        //Widget EditText donde se mostrara la hora obtenida
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la hora
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
        //Evento setOnClickListener - clic
        ibObtenerHora.setOnClickListener(this);

        showProg =(Switch)findViewById(R.id.simpleSwitch);
        showProg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(showProg.isChecked()){
                    lprog.setVisibility(View.VISIBLE);
                }else{
                    lprog.setVisibility(View.GONE);
                }
            }
        });







    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                //Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////get   Publicacion /////////////////////////////////////////////////////////////////////////////
    private void readFotos() {
        HashMap<String, String> params = new HashMap<>();
        params.put("idu", cod_idu);
        PerformNetworkRequest2 request = new PerformNetworkRequest2(FuncionesApp.URL_GET_FOTOS, params, CODE_POST_REQUEST);

        request.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }

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
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    // refreshPublicacionList(object.getJSONArray("publicacion"));
                    refreshFotosList(object.getJSONArray("categorias"));
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

    private void refreshFotosList(JSONArray heroes) throws JSONException {



      //  fotosList = new ArrayList<Fotos>();
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
            Fotos foto = new Fotos();
            String id = obj.getString("id");
            String url = obj.getString("url_foto");
            String id_pub = obj.getString("id_pub");

            foto.setFoto_id(id);
            foto.setUrl_foto(url);
            foto.setPub_id(id_pub);

           // fotosList.add(foto);


            // Initialize a new ImageView widget
            ImageView iv = new ImageView(getApplicationContext());

            // Set an image for ImageView
            //iv.setImageDrawable(getDrawable(R.drawable.animal));
           String ruta = RUTAIMG + url;
            final String ur = url;
            Picasso.get().load(ruta).into(iv);

            // Create layout parameters for ImageView
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL);

            // Add rule to layout parameters
            // Add the ImageView below to Button
            // lp.addRule(RelativeLayout.BELOW,iv.getId());
            iv.setLayoutParams(lp);

            iv.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    agrandar(ur);

                }
            });


            // Finally, add the ImageView to layout
            rl.addView(iv);


            // String ruta =RUTAIMG+url_pri;
            // Picasso.get().load(ruta).into(imageView);


        }

        // Datos a mostrar




    }

    public void agrandar(String url){
        // Esto es para evitar crear cuadros de diálogos varias veces
        if(viewImageExtended == null || viewImageExtended.getDialog() == null || !viewImageExtended.getDialog().isShowing()){

            // Si estas en un activity
            FragmentManager fm = this.getSupportFragmentManager();

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

    public void agrandarVideo(String url){
        // Esto es para evitar crear cuadros de diálogos varias veces
        if(viewVideoExtended == null || viewVideoExtended.getDialog() == null || !viewVideoExtended.getDialog().isShowing()){

            // Si estas en un activity
            FragmentManager fm = this.getSupportFragmentManager();

            // Si estas en un fragment y pasaste el activity en el constructor
            //FragmentManager fm = this.activity.getSupportFragmentManager();

            Bundle arguments = new Bundle();

            // Aqui le pasas el bitmap de la imagen
            // arguments.putParcelable("PROFILE_PICTURE", bitmap);
            arguments.putString("furlVideo",url);

            viewVideoExtended = FragmentViewVideoExtended.newInstance(arguments);

            viewVideoExtended.show(fm, "viewVideoExtended");
        }
    }
    /*************************** widget methosd **/
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

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    private void updateHero() {

    }
    private  void  createSolicitud(){
        String cantidad = spCantidad.getSelectedItem().toString();
        String fecha ="";
        String hora ="";
        if(showProg.isChecked()){
            fecha= etFecha.getText().toString();
            hora=  etHora.getText().toString();

        }

        HashMap<String, String> params = new HashMap<>();
        params.put("idu", cod_idu);
        params.put("idcat",cod_cat);
        params.put("cantidad",cantidad);
        params.put("precio",precio);
        params.put("fecha",fecha);
        params.put("hora",hora);

        PerformNetworkRequest request = new PerformNetworkRequest(FuncionesApp.URL_UPDATE_USUARIO, params, CODE_POST_REQUEST);
        request.execute();


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
            // progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                  //  refreshHeroList(object.getJSONArray("usuarios"));
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



}
