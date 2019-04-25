package pe.progsistem.jesus.ubeprivado.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.conexion.FuncionesApp;

public class RegistroChofer extends AppCompatActivity  implements View.OnClickListener{

    /*    fecha     */
    private static final String CERO = "0";
    private static final String BARRA = "-";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    ImageButton ibObtenerFecha;
    /*  fecha fin */
    /*  subir imagen */
    private Button btnBuscar ,btnSoatf,btnSoatp,btnTarjeta,btnLicencia;

    private Button btnSubir;

    private ImageView imageView,ivSoatf,ivSoatp,ivTarjeta,ivLicencia;

    private EditText editTextName,etusuario,etemail,etpassword,etcelular,etcelular2;
    private TextView title,tvOlvido;

    private Bitmap bitmap ,bitmap_soatf,bitmap_soatp,bitmap_tarjeta,bitmap_licencia;


    private int PICK_IMAGE_REQUEST = 1;

      private int PICK_IMAGE_SOATF = 2;
    private int PICK_IMAGE_SOATP = 3;
    private int PICK_IMAGE_TARJETA = 4;
    private int PICK_IMAGE_LICENCIA = 5;



    private String KEY_IMAGEN = "foto";

    private String KEY_NOMBRE = "nombre";
    //SPINER
    private Spinner spgenero;


    /*  fin subir  imagen  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_chofer);

        /* fecha     */
        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);

        title = (TextView) findViewById(R.id.tv_titulo);

        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(RegistroChofer.this);
        /*   fin fecha */
        /*    subir imagen  */

        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        btnSoatf = (Button) findViewById(R.id.btnSoatF);
        btnSoatp = (Button) findViewById(R.id.btnSoatP);
        btnTarjeta = (Button) findViewById(R.id.btnTarjeta);
        btnLicencia = (Button) findViewById(R.id.btnLicencia);

        btnSubir = (Button) findViewById(R.id.btnSubir);

        editTextName = (EditText) findViewById(R.id.txtName);


        etusuario = (EditText) findViewById(R.id.txtUsername);
        etemail = (EditText) findViewById(R.id.txtEmail);
        etcelular = (EditText) findViewById(R.id.txtCelular);
        etcelular2= (EditText) findViewById(R.id.txtCelular2);


        etpassword= (EditText) findViewById(R.id.txtPassword);


        imageView  = (ImageView) findViewById(R.id.imageView);
        ivSoatf  = (ImageView) findViewById(R.id.ivSoatF);
        ivSoatp = (ImageView) findViewById(R.id.ivSoatP);
        ivTarjeta  = (ImageView) findViewById(R.id.ivTarjeta);
        ivLicencia  = (ImageView) findViewById(R.id.ivLicencia);


        btnBuscar.setOnClickListener(this);
        btnSoatf.setOnClickListener(this);
        btnSoatp.setOnClickListener(this);
        btnTarjeta.setOnClickListener(this);
        btnLicencia.setOnClickListener(this);

        btnSubir.setOnClickListener(this);


        title.setOnClickListener(this);

        spgenero = (Spinner) findViewById(R.id.spinnersexo);
    }



    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST,FuncionesApp.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(RegistroChofer.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(RegistroChofer.this, volleyError.getMessage().toString()+"error", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);
                String imagen_soatf = getStringImagen(bitmap_soatf);
                String imagen_soatp = getStringImagen(bitmap_soatp);
                String imagen_tarjeta = getStringImagen(bitmap_tarjeta);
                String imagen_licencia = getStringImagen(bitmap_licencia);

                //Obtener el nombre de la imagen
                String nombre = editTextName.getText().toString().trim();
                String mApellido_p=  "";
                String sexo =   String.valueOf(spgenero.getSelectedItem());
                String mFecha_nac= etFecha.getText().toString();
                String mCelular=  etcelular.getText().toString();
                String mCelular2=  etcelular2.getText().toString();
                String mUsuario=  etusuario.getText().toString();
                String mEmail=  etemail.getText().toString();
                String mPassword=  etpassword.getText().toString();


                String mGenero = String.valueOf(spgenero.getSelectedItem());
                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_NOMBRE, nombre);
                params.put("cargo", "CHOFER");
                params.put("app", mApellido_p);
                params.put("sexo", sexo);
                params.put("provincia", "");
                params.put("fecha_nac",mFecha_nac);
                params.put("cel", mCelular);
                params.put("cel2", mCelular2);
                params.put("user", mUsuario);
                params.put("email", mEmail);
                params.put("password",mPassword);
                params.put(KEY_IMAGEN, imagen);
                params.put("foto_soatf", imagen_soatf);
                params.put("foto_soatp", imagen_soatp);
                params.put("foto_tarjeta", imagen_tarjeta);
                params.put("foto_licencia", imagen_licencia);

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
        if(bot.equals("foto_soatf")){
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SOATF);}
        if(bot.equals("foto_soatp")){
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SOATP);}
        if(bot.equals("foto_tarjeta")){
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_TARJETA);}
        if(bot.equals("foto_licencia")){
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_LICENCIA);}

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
        if (requestCode == PICK_IMAGE_SOATF && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap_soatf = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ivSoatf.setImageBitmap(bitmap_soatf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_SOATP && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap_soatp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ivSoatp.setImageBitmap(bitmap_soatp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_TARJETA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap_tarjeta = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ivTarjeta.setImageBitmap(bitmap_tarjeta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_LICENCIA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap_licencia = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ivLicencia.setImageBitmap(bitmap_licencia);
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
        if(v == btnSoatf){
            showFileChooser("foto_soatf");
        }
        if(v == btnSoatp ){
            showFileChooser("foto_soatp");
        }
        if(v == btnTarjeta ){
            showFileChooser("foto_tarjeta");
        }
        if(v == btnLicencia ){
            showFileChooser("foto_licencia");
        }

        if(v == btnSubir){
            String mUsuario=  etusuario.getText().toString();
            String mEmail=  etemail.getText().toString();
            String mPassword=  etpassword.getText().toString();

            if (TextUtils.isEmpty(mUsuario)) {
                etusuario.setError("Please enter username ");
                etusuario.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(mEmail)) {
                etemail.setError("Please enter email");
                etemail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(mPassword)) {
                etpassword.setError("Please enter password");
                etpassword.requestFocus();
                return;
            }
            if(isValidEmail(mEmail)) {

                uploadImage();

            }else{
                etemail.setError("Please enter a valid email");
                etemail.requestFocus();
            }

        }
        if(v == title){
            Intent intentReg = new Intent(RegistroChofer.this,Inicio.class);
            RegistroChofer.this.startActivity(intentReg);
        }

    }
}
