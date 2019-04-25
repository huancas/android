package pe.progsistem.jesus.ubeprivado.Activities;
import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import pe.progsistem.jesus.ubeprivado.Frgaments.CarroFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.FixturesTabs;
import pe.progsistem.jesus.ubeprivado.Frgaments.GridFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.MapaFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.Perfiles;
import pe.progsistem.jesus.ubeprivado.Frgaments.PromocionFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.PublicacionFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.SedesFragment;
import pe.progsistem.jesus.ubeprivado.Frgaments.UsuariosFragment;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Sede;
import pe.progsistem.jesus.ubeprivado.util.DelayAutoCompleteTextView;
import pe.progsistem.jesus.ubeprivado.util.LocationService;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.IP;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;
public class Principal extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener ,GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private String UPLOAD_URL = "http://" + IP + "/olivos/imagen/lista_oferta.php";
    private String LISTA_SEDE = "http://" + IP + "/olivos/imagen/listarSede.php";

    String nombre = "vacio", app = "", apm = "", user = "", cel = "", cel2 = "", email = "", perfil = "", cargo = "", cod_id = "", cargo_idc = "";
    String finicio = "", provincia = "", casa = "", trabajo = "", cestudio = "", otro = "", descuento = "";

    TextView tvusernamep, tvemailp;
    ImageView ivperfilp;
    //listas
    ArrayList<Sede> sedesList;


    /*******************************************mapa  *****************************************/

    // private Dao dao;


    ArrayList<LatLng> markerPoints;

    SQLiteDatabase mDatabase;

    private GoogleMap mMap;
    private Circle circle;
    private Marker marcador;
    String tipolugar;
    private LatLng linicio, lpartida;
    private TextView tvmiUbicacion;
    private LinearLayout lheader;


    private Integer THRESHOLD = 2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private ImageView geo_autocomplete_clear;

    private double lat = 0.0, lng = 0.0;

    //permisos de la app

    private static final int COARSE_LOCATION_REQUEST_CODE = 2;

    private static final int FINE_PERMISSION_REQUEST_CODE = 3;

    // Estas variables las declaramos al principio de nuestra clase, justo debajo
// de la cabecera:
    private final static int PARTIDA = 0;
    private final static int DESTINO = 1;

    LatLng ubicacion;
    LatLng ubic_click;
    Double latitude, longitude;

    private Button boton, btRuta;

    private TextView input, et_destino;
    private TextView ubic, map;

// <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    /******************************************************************************************/
    //ubicacion usionada
    boolean requestingLocationUpdates;
    private LocationCallback locationCallback;
    LocationManager fusedLocationClient = null;

    //intent
    private GoogleApiClient googleApiClient;

    //mapa fused ultimo
    private Location location;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("KEY",
                requestingLocationUpdates);
        // ...
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(this);


        ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("lista");
        cod_id = lista.get(0);
        nombre = lista.get(1);
        app = lista.get(2);
        user = lista.get(3);
        cel = lista.get(4);
        cel2 = lista.get(4);
        email = lista.get(5);
        perfil = lista.get(6);
        finicio = lista.get(7);
        descuento = lista.get(8);
        provincia = lista.get(9);
        casa = lista.get(10);
        cestudio = lista.get(11);
        trabajo = lista.get(12);
        cargo_idc = lista.get(13);
        cargo = lista.get(15);


        /*************************************************mapa *****************************************************/
        /* ActivityCompat.requestPermissions(Mapszona.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                COARSE_LOCATION_REQUEST_CODE); */
        ActivityCompat.requestPermissions(Principal.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_PERMISSION_REQUEST_CODE);


        ubicacion = new LatLng(-34, 151);
        // setContentView(R.layout.activity_mapszona);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        et_destino = (TextView) findViewById(R.id.et_destino);
        input = (TextView) findViewById(R.id.editText);

        boton = (Button) findViewById(R.id.button);

        btRuta = (Button) findViewById(R.id.bt_ruta);

        tvmiUbicacion = (TextView) findViewById(R.id.tvUbic);
        lheader = (LinearLayout) findViewById(R.id.llheader);


        tipolugar = "Peligroso";



       /* ActivityCompat.requestPermissions(Mapszona.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                CAMERA_PERMISSION_REQUEST_CODE); */

        //Si el permiso no se encuentra concedido se solicita
      /*  ActivityCompat.requestPermissions(Mapszona.this,
                new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE); */
        //buscando  un lugar

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //  buscar();
                    // obtenerdatos();
                    if (!input.getText().equals("") || !et_destino.getText().equals("")) {
                        TrazarRuta();
                    }
                    Toast.makeText(Principal.this, "Buscando  " + input.getText() + " ...",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Principal.this, "error: menu " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //abrir la ruta
        btRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // buscardir();


                    Intent aboutIntent = new Intent(Principal.this, WebViewActivity.class);
                    String a =  input.getText().toString();
                    String b=  et_destino.getText().toString();
                    aboutIntent.putExtra("dir",a);
                    aboutIntent.putExtra("des",b);
                    startActivity(aboutIntent);


                } catch (Exception e) {
                    Toast.makeText(Principal.this, "error: menu " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rellenarPartida(view);
            }
        });
        et_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rellenarDestino(view);
            }
        });

        tvmiUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  rellenarPartida( view);
                //ObtenerNombremiUbicacion();
                checkIfLocationOpened();
                startLocationUpdates();
            }
        });


        //creating a database

      /*  mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        dao = new Dao(this,mDatabase);
        dao.createlugaresTable(); */
        // obtenerdatos();

        ///////////////////////////////// autocomplete


        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(Principal.this, "letra " + s,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    //  geo_autocomplete_clear.setVisibility(View.VISIBLE);
                } else {
                    //geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });

        cambiarFragmentInterior();

        /************************************************mapa/**********************************************************/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //ocultamos el boton fabbutoon
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //ocultar item
        Menu nav_Menu = navigationView.getMenu();
        if (cargo.equals("TRABAJADOR")) {
            nav_Menu.findItem(R.id.nav_mipub).setVisible(true);
            nav_Menu.findItem(R.id.nav_fotos).setVisible(true);
            nav_Menu.findItem(R.id.nav_atencion).setVisible(false);
            nav_Menu.findItem(R.id.nav_publicaciones).setVisible(false);
            nav_Menu.findItem(R.id.nav_sede).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_mipub).setVisible(false);
            nav_Menu.findItem(R.id.nav_fotos).setVisible(false);
            nav_Menu.findItem(R.id.nav_atencion).setVisible(true);

        }
        if (cargo.equalsIgnoreCase("ADMINISTRADOR")) {
            nav_Menu.findItem(R.id.nav_promociones).setVisible(true);
            nav_Menu.findItem(R.id.nav_sede).setVisible(true);

        } else {
            nav_Menu.findItem(R.id.nav_promociones).setVisible(false);
            nav_Menu.findItem(R.id.nav_sede).setVisible(false);
        }
        //cargar datos en el drawer
        View hView = navigationView.getHeaderView(0);
        tvemailp = (TextView) hView.findViewById(R.id.tvEmailp);
        tvusernamep = (TextView) hView.findViewById(R.id.tvUsernamep);
        ivperfilp = (ImageView) hView.findViewById(R.id.ivPerfilp);
        //hView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        //llenamos los datos
        tvusernamep.setText(user);
        tvemailp.setText(email);
        String ruta = RUTAIMG + perfil;
        Picasso.get().load(ruta).into(ivperfilp);

        // listarOferta();

        // cambiarFragment();
        //////////////////////////7ubicacion fusionada//////////////


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentTransaction = false;
        Fragment fragment = null;

        if (id == R.id.nav_camera) {
            this.setTitle("Inicio");
            //listarOferta();
            lheader.setVisibility(View.VISIBLE);
            cambiarFragment();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            this.setTitle("Mi Perfil");
            //creamos un bundle
            lheader.setVisibility(View.GONE);
            Bundle args = new Bundle();
            // Colocamos el String   y  guardamos los datos
            args.putString("fcod_id", cod_id);
            args.putString("fuser", user);
            args.putString("fname", nombre);
            args.putString("fapp", app);
            args.putString("fcel", cel);
            args.putString("fcel2", cel2);
            args.putString("femail", email);
            args.putString("fperfil", perfil);

            fragment = new Perfiles();
            fragment.setArguments(args);
            fragmentTransaction = true;

        } else if (id == R.id.nav_mipub) {
            this.setTitle("Mi Publicacion");
            Intent intentReg = new Intent(Principal.this, MipublicacionActivity.class);
            intentReg.putExtra("cod_usu", cod_id);
            Principal.this.startActivity(intentReg);

        } else if (id == R.id.nav_fotos) {
            this.setTitle("Mis Fotos");
            Intent intentReg = new Intent(Principal.this, FotosActivity.class);
            intentReg.putExtra("cod_usu", cod_id);
            Principal.this.startActivity(intentReg);
              /*  fragment = new FotosFragment();
               fragmentTransaction = true; */

        } else if (id == R.id.nav_publicaciones) {
            this.setTitle("Publicaciones");
            lheader.setVisibility(View.GONE);
            listarSede();

        } else if (id == R.id.nav_atencion) {
            this.setTitle("Mis Records");
            Bundle args = new Bundle();
            // Colocamos el String   y  guardamos los datos
            args.putString("fidu", cod_id);
            args.putString("fcargo", cargo);

            fragment = new GridFragment();
            fragment.setArguments(args);
            fragmentTransaction = true;

        } else if (id == R.id.nav_promociones) {
            //  fragment = new FixturesTabs();
            // fragmentTransaction = true;
            this.setTitle("Promocion");
           /* fragment = new LeadFragment();
            fragmentTransaction = true; */

            Intent intentReg = new Intent(Principal.this, PromocionActivity.class);
            intentReg.putExtra("cargo", cod_id);
            Principal.this.startActivity(intentReg);


        } else if (id == R.id.nav_sede) {
            //  fragment = new FixturesTabs();
            // fragmentTransaction = true;
            lheader.setVisibility(View.GONE);
            this.setTitle("Sedes");
           /* fragment = new LeadFragment();
            fragmentTransaction = true; */
            fragment = new SedesFragment();
            fragmentTransaction = true;


        } else if (id == R.id.nav_share) {
            String msg = "Hola descargate esta aplicacion esta muy buena : https://play.google.com/store/apps/details?id=com.google.android.apps.plus";

            /* Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,msg
                    );
            sendIntent.setType("text/plain");
            startActivity(sendIntent); */

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My App Name");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(sharingIntent, "Compartir Aplicacion Via :"));


        } else if (id == R.id.nav_send) {
            SharedPreferences mPrefs = getSharedPreferences("IDvalue", 0);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean("estado", false);
            editor.putString("user_idu", "");
            editor.commit();
            Intent intentReg = new Intent(Principal.this, RegistroPrincipal.class);
            Principal.this.startActivity(intentReg);
            finish();
        }
        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            //  menuItem.setChecked(true);
            //  getSupportActionBar().setTitle(menuItem.getTitle());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**************************************funcion select ****************************************************/
    private void listarOferta() {
        //Mostrar el diálogo de progreso

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        //Mostrando el mensaje de la respuesta
                        //  Toast.makeText(Principal.this, s , Toast.LENGTH_LONG).show();

                        try {
                            JSONObject object = new JSONObject(s);
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            if (object.getBoolean("succes")) {
                                // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                String titulo = object.getString("titulo");
                                String descripcion = object.getString("descripcion");
                                String fecha = object.getString("fecha");
                                String promo_img = object.getString("promo_img");


                                ArrayList<String> listaOferta = new ArrayList<String>();
                                listaOferta.add(titulo);
                                listaOferta.add(descripcion);
                                listaOferta.add(fecha);
                                listaOferta.add(promo_img);
                                /******* fragment llenar  ***************/
                                Fragment fragment = null;
                                //creamos un bundle
                                Bundle args = new Bundle();
                                // Colocamos el String   y  guardamos los datos
                                args.putString("ftitulo", titulo);
                                args.putString("fdescripcion", descripcion);
                                args.putString("fpromo_img", promo_img);
                                args.putString("ffecha", fecha);

                                fragment = new PromocionFragment();
                                fragment.setArguments(args);


                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();
                                /************ fin fragment lleno     */

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso


                        //Showing toast
                        Toast.makeText(Principal.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Obtener datos del editex
               /* String user = et_usuario.getText().toString();
                String pass = et_password.getText().toString(); */

                Map<String, String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put("lista", "lista");
              /*  params.put("pass", pass);
                //Parámetros de retorno */
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    /************************************** listar sede ***************************************************************/
    /**************************************funcion select ****************************************************/
    private void listarSede() {
        //Mostrar el diálogo de progreso

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LISTA_SEDE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        //Mostrando el mensaje de la respuesta
                        // Toast.makeText(Principal.this, s , Toast.LENGTH_LONG).show();

                        try {
                            JSONObject object = new JSONObject(s);
                            //   Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            //     if (object.getBoolean("succes")) {
                            // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                             /*   String sede = object.getString("titulo");
                                String descripcion = object.getString("descripcion");
                                String cel = object.getString("cel");
                                String cel2 = object.getString("cel2"); */


                         /*       JSONArray sedes = new JSONArray(s);
                            JSONObject obj = new JSONObject(s); */
                            // JSONArray sedes=   new JSONArray(s);
                            //    JSONArray sedes=   object.getJSONArray("sede");

                            JSONObject obj = new JSONObject(s);
                            JSONArray sedes = obj.getJSONArray("sedes");
                            sedesList = new ArrayList<Sede>();


                            for (int i = 0; i < sedes.length(); i++) {
                                obj = sedes.getJSONObject(i);


                                sedesList.add(new Sede(
                                        obj.getString("id"),
                                        obj.getString("sede"),
                                        obj.getString("descripcion"),
                                        obj.getString("cel"),
                                        obj.getString("cel2")

                                ));

                                System.err.print("sede  ********++  " + obj.getString("sede"));

                                //  Log.d("error","sede  ********++  "+obj.getString("sede"));

                            }

                            /******* fragment llenar  ***************/
                            Fragment fragment = null;
                            //creamos un bundle
                            /*    Bundle args = new Bundle();
                                args.putSerializable("flistaSede",sedesList);

                                fragment.setArguments(args); */


                            // Colocamos el String   y  guardamos los datos
                            // args.putSerializable("flistaSede", sedesList);
                            // args.putParcelableArrayList("flistaSede", sedesList);

                            Bundle bundle = new Bundle();
                            //  bundle.putParcelableArrayList("flistaSede", sedesList);
                            bundle.putSerializable("flistaSede", sedesList);
                            bundle.putString("fdatos", s);
                            bundle.putString("fuser", "los olivoso");
                            bundle.putString("fuser1", "lince");
                            fragment = new FixturesTabs();
                            fragment.setArguments(bundle);


                            //   fragment.setArguments("vdata", (Serializable) sedesList);


                            //fragment.setSedeList(sedesList);
                            // ((FixturesTabs) fragment).setSedeList(sedesList);


                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                            /************ fin fragment lleno     */


                            //  }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso


                        //Showing toast
                        Toast.makeText(Principal.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Obtener datos del editex
               /* String user = et_usuario.getText().toString();
                String pass = et_password.getText().toString(); */

                Map<String, String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put("lista", "lista");
              /*  params.put("pass", pass);
                //Parámetros de retorno */
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this, "buscando ... " + s, Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        // Colocamos el String   y  guardamos los datos
        Fragment fragment = null;
        if (cargo.equals("TRABAJADOR")) {
            args.putString("fcargo", cargo);
            args.putString("fcriterio", s);
            args.putString("fcod_usu", cod_id);

            fragment = new UsuariosFragment();
            fragment.setArguments(args);
        }
        if (cargo.equals("ADMINISTRADOR")) {
            args.putString("fcargo", cargo);
            args.putString("fcriterio", s);
            args.putString("fcod_usu", cod_id);

            fragment = new UsuariosFragment();
            fragment.setArguments(args);
        }
        if (cargo.equals("CLIENTE")) {

            fragment = new PublicacionFragment();
            Bundle bund = new Bundle();
            bund.putString("id", "");
            bund.putString("sede", "TODOS");
            bund.putString("descripcion", "");
            bund.putString("cel", "");
            bund.putString("cel2", "");
            bund.putString("criterio", s);
            fragment.setArguments(bund);
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        return false;
    }

    public void cambiarFragment() {
        Fragment fragment = null;
        //creamos un bundle


        fragment = new MapaFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        /************ fin fragment lleno     */
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    /**********************************************************mapa***************************************************/
    // Método que se ejecuta al pulsar el botón btNombre:
    public void rellenarPartida(View v) {
        Intent i = new Intent(this, MainActivity.class);
        // Iniciamos la segunda actividad, y le indicamos que la iniciamos
        // para rellenar el nombre:
        startActivityForResult(i, PARTIDA);
    }

    // Método que se ejecuta al pulsar el botón btNombre:
    public void rellenarDestino(View v) {
        Intent i = new Intent(this, MainActivity.class);
        // Iniciamos la segunda actividad, y le indicamos que la iniciamos
        // para rellenar el nombre:
        startActivityForResult(i, DESTINO);
    }

    public void buscar() {

        Geocoder geo = new Geocoder(Principal.this); //locale.getDefault
        int maxResultados = 10;
        List<Address> adress = null;
        try {
            //convertir la ubicacion de texto a latitud y longitud
            adress = geo.getFromLocationName(input.getText().toString(), maxResultados);

            /*          pasar objetos y resultados a un activity  */
            Bundle bund = new Bundle();
            ArrayList<Address> as = new ArrayList<Address>(adress);
            bund.putSerializable("faddress", as);

            for (Address a : adress)
                mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title("Hello " + a.getFeatureName()).snippet("Description about me!" + a.getLocality()));
            //pasar objeto

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("listaaddress", bund);
            intent.putExtra("size", adress.size());

            startActivity(intent);

        } catch (IOException e) {
            Toast.makeText(Principal.this, "Error " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ubicacion = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
        adress.clear();//borrar la busqueda anterior
        // mMap.clear();//borrar los el marcador anteriro //caso contrario se va mostrando todos los marcadores
        // mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker in "+input.getText()));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("STDS S.A.C en " + input.getText()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));


    }

    public void buscardir() {
        try {
            Geocoder geo = new Geocoder(Principal.this);
            int maxResultados = 1;
            List<Address> adress = null;
            List<Address> adress1 = null;
            try {
                //conertir la ubicacion de texto a latitud y longitud
                adress = geo.getFromLocationName(input.getText().toString(), maxResultados);
                adress1 = geo.getFromLocationName(et_destino.getText().toString(), maxResultados);

            } catch (IOException e) {
                Toast.makeText(Principal.this, "Error obteniendo ubicacion " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            double lat = adress.get(0).getLatitude();
            double lng = adress.get(0).getLongitude();
            double lat1 = adress1.get(0).getLatitude();
            double lng1 = adress1.get(0).getLongitude();
            showDirections(lat, lng, lat1, lng1);
        } catch (Exception e) {
            Toast.makeText(Principal.this, "error buscar dir " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        // LatLng re = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                Intent aboutIntent = new Intent(Principal.this, MainActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.help:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);


        // Add a marker in Sydney and move the camera
        /*LatLng lima = new LatLng(-12.055027, -77.038794);
        mMap.addMarker(new MarkerOptions().position(lima).title("Cercado de Lima"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));*/

        // Add a marker in Sydney and move the camera
       /* mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion)); */

        // mMap = map;
      /*  circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-12.055027, -77.038794))
                .radius(1000)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

        */

        //
        // obtenerdatos();
        miUbicacion();

    }

    /*******************         ubicacion actual  **********************/
    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Ubicación Actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_persona_ubicacion)));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        //arrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr leal
      /*  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener); */

    }

    private void ObtenerNombremiUbicacion(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Geocoder geo = new Geocoder(Principal.this);
        List<Address> listaUbicName;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Toast.makeText(Principal.this, "location  " + location,
                Toast.LENGTH_SHORT).show();

        if (location != null) {

            try {
                listaUbicName = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                linicio = new LatLng(location.getLatitude(), location.getLongitude());

                /*Toast.makeText(Mapszona.this,"location  "+listaUbicName.get(0).getFeatureName()+" "+listaUbicName.get(0).getSubAdminArea()+" "+
                                listaUbicName.get(0).getSubLocality()+" "+listaUbicName.get(0).getThoroughfare(),
                        Toast.LENGTH_SHORT).show(); */
                //locale lnaguje prefijo es
                //TROUFFARE AV.
                //FEATURE NUMERO
                //subAdminArea provincia
                //sublocality nul
                //country name
                //PREMISES NULL
                //admin area gobierno regional de lima --gobierno
                //locaclity distrito
                //country name peru
                String dire = listaUbicName.get(0).getThoroughfare() + " " + listaUbicName.get(0).getFeatureName() + ", " + listaUbicName.get(0).getLocality() + " ," + listaUbicName.get(0).getCountryName();
                Toast.makeText(Principal.this, "location  " + dire,
                        Toast.LENGTH_SHORT).show();

                input.setText(dire);


            } catch (IOException e) {


                e.printStackTrace();
            }

        }


      /*  Geocoder geo = new Geocoder(Mapszona.this);
        List<Address> listaUbicName;
        try {
            listaUbicName = geo.getFromLocation (location.getLatitude(),location.getLongitude(), 1);
            String nameUbic = listaUbicName.get(0).getLocality();
            input.setText(nameUbic);

        } catch (IOException e) {

            e.printStackTrace();
        } */
    }
    //arrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr leal

  /*  LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };  */


  /*  @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
           /*
            case COARSE_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(Mapszona.this,"Permiso concedido localizacion coarse",
                            Toast.LENGTH_SHORT).show();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case FINE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Mapszona.this,"Permiso concedido localizacion fine",
                            Toast.LENGTH_SHORT).show();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }  */

            // other 'case' lines to check for other
            // permissions this app might request
    /*    }
    }  */

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        ubic.setText(String.valueOf(latLng));

        // String[] parts = latLng.split(",");
        // String part1 = parts[0]; //obtiene: 19
        //  String part2 = parts[1]; //obtiene: 19-A
        //  String parr2 = part2.substring(0,part2.length()-1);

        latitude = latLng.latitude;
        //verificamos si el radiobuton esta en peligroso,seguro o inseguro
        longitude = latLng.longitude;
        /*
        if(rbp.isChecked()==true) {
            tipolugar="Peligroso";
            circle = mMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(1000)
                    .strokeWidth(10)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(128, 255, 0, 0))
                    .clickable(true));

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {
                    // Flip the r, g and b components of the circle's
                    // stroke color.
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
                }
            });

        }
        else
        if(rbs.isChecked()==true) {
            tipolugar="Seguro";
            circle = mMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(1000)
                    .strokeWidth(10)
                    .strokeColor(Color.YELLOW)
                    .fillColor(Color.GREEN)
                    .clickable(true));

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {
                    // Flip the r, g and b components of the circle's
                    // stroke color.
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
                }
            });
        }
        else
        if(rbi.isChecked()==true) {
            tipolugar="Inseguro";
            circle = mMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(1000)
                    .strokeWidth(10)
                    .strokeColor(Color.GREEN)
                    //color del relleno del circulo
                    .fillColor(Color.YELLOW)
                    .clickable(true));

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {
                    // Flip the r, g and b components of the circle's
                    // stroke color.
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
                }
            });
        }
         */

        //guardamos en la bd

        // dao.addlugar(latitude,longitude,tipolugar);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        map.setText(mMap.getCameraPosition().toString());

    }

    //mostrar ruta
    public void showDirections(double lat, double lng, double lat1, double lng1) {


        Toast.makeText(Principal.this, "lats " + lng + ";" + lat + " - " + lat1 + ";" + lng1,
                Toast.LENGTH_SHORT).show();

        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + lat + "," + lng + "&daddr=" + lat1 + "," +
                lng1));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }

   /* public void obtenerdatos() {
        try{
            //obtenemos los datos de la bd y lo pintamos
            List<Lugar>  lista = new ArrayList<>();
            lista = dao.showUbicacionFromDatabase();

            for(int i=0;i<lista.size();i++){
                lista.get(i).getId();
                double lat=   lista.get(i).getLat();
                double longi=   lista.get(i).getLongi();
                String tip=   lista.get(i).getTipo();

                //LatLng as =(LatLng) lat;

                if(tip.equals("Peligroso")) {

                    circle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat, longi))
                            .radius(1000)
                            .strokeWidth(10)
                            .strokeColor(Color.GREEN)
                            .fillColor(Color.argb(128, 255, 0, 0))
                            .clickable(true));

                    mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                        @Override
                        public void onCircleClick(Circle circle) {
                            // Flip the r, g and b components of the circle's
                            // stroke color.
                            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                            circle.setStrokeColor(strokeColor);
                        }
                    });

                }

                if(tip.equals("Seguro")) {

                    circle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat, longi))
                            .radius(1000)
                            .strokeWidth(10)
                            .strokeColor(Color.YELLOW)
                            .fillColor(Color.GREEN)
                            .clickable(true));

                    mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                        @Override
                        public void onCircleClick(Circle circle) {
                            // Flip the r, g and b components of the circle's
                            // stroke color.
                            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                            circle.setStrokeColor(strokeColor);
                        }
                    });
                }
                if(tip.equals("Inseguro")) {

                    circle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat, longi))
                            .radius(1000)
                            .strokeWidth(10)
                            .strokeColor(Color.GREEN)
                            //color del relleno del circulo
                            .fillColor(Color.YELLOW)
                            .clickable(true));

                    mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                        @Override
                        public void onCircleClick(Circle circle) {
                            // Flip the r, g and b components of the circle's
                            // stroke color.
                            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                            circle.setStrokeColor(strokeColor);
                        }
                    });
                }

            }

        }catch (Exception e){ Toast.makeText(Mapszona.this,"error obteniendo datos  "+e.getMessage(),
                Toast.LENGTH_LONG).show();                    }




    } */


    // Este método nos trae la información de para qué se llamó la segunda actividad,
// cuál fue el resultado ("OK" o "CANCELED"), y el intent que nos traerá la
// información que necesitamos de la segunda actividad.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        } else {


            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case PARTIDA:
                    // De lo contrario, recogemos el resultado de la segunda actividad.
                    String resultado = data.getExtras().getString("RESULTADO");
                    String rlat = data.getExtras().getString("LAT");
                    String rlng = data.getExtras().getString("LNG");
                    input.setText(resultado);
                    linicio = new LatLng(Double.parseDouble(rlat), Double.parseDouble(rlng));
                    break;
                case DESTINO:
                    // De lo contrario, recogemos el resultado de la segunda actividad.
                    String resultad = data.getExtras().getString("RESULTADO");
                    String rla = data.getExtras().getString("LAT");
                    String rln = data.getExtras().getString("LNG");
                    et_destino.setText(resultad);
                    lpartida = new LatLng(Double.parseDouble(rla), Double.parseDouble(rln));
                    break;
            }
        }
    }

    public double calcular() {

        if (linicio != null && lpartida != null) {
            Location location = new Location("localizacion 1");
            location.setLatitude(linicio.latitude);  //latitud
            location.setLongitude(linicio.longitude); //longitud
            Location location2 = new Location("localizacion 2");
            location2.setLatitude(lpartida.latitude);  //latitud
            location2.setLongitude(lpartida.longitude); //longitud
            double distance = location.distanceTo(location2);


            return distance;


        } else {
            return 0;
        }
    }

    public double calcularPrecio() {
        double precio = 0;
        //distancia en metros
        //una cuadar 1000 metros
        double distancia = calcular();


        //cuadra 100 mteros
        //15 cuadras s/ 5.00
       /* if(distancia<=15000){
             precio = 5.00;
        }
        if(distancia>15000){
            precio = distancia * 0.0003;
        } */
        precio = distancia * 0.003;
        if(precio<5.00){
            precio =5.00;
        }

        double prec = formatearDecimales(precio, 2);

        Toast.makeText(getApplicationContext(), String.valueOf("Precio " + prec + "  distancia " + distancia), Toast.LENGTH_SHORT).show();
        return prec;
    }

    public void TrazarRuta() {
        mMap.addMarker(new MarkerOptions().position(linicio).title("Partida "));
        mMap.addMarker(new MarkerOptions().position(lpartida).title("Destino "));


        mMap.addPolyline(new PolylineOptions()
                .add(lpartida, linicio)
                .width(5)
                .color(Color.RED));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(lpartida));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lpartida, 14f));
        // line.setTag("A");
        cambiarFragmentInterior();
    }

    public void cambiarFragmentInterior() {


        //  double precio =Double.parseDouble(String.format("%.2f", calcularPrecio()));

        Fragment fragment = null;
        fragment = new CarroFragment();
        Bundle bund = new Bundle();
        bund.putString("id", cod_id);
        bund.putDouble("precio", calcularPrecio());
        fragment.setArguments(bund);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

    }

    private void checkIfLocationOpened() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        System.out.println("Provider contains=> " + provider);
        // if (provider.contains("gps") || provider.contains("network")){
        if (provider.contains("gps")) {

            ObtenerNombremiUbicacion();
        } else {
            AlertNoGps();
        }
    }


    private void AlertNoGps() {

        AlertDialog alert = null;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }


    /*****************************************************************************************************************/


    public void requestLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest()
                .setInterval(5 * 1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Intent intent = new Intent(this, LocationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, pendingIntent);
    }

  /*  @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    } */

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }


   /* @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
    } */

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    /*****************************************+mapas  ultimo fused location*************************************************************/
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
         //   locationTv.setText("You need to install Google Play Services to use the App properly");


            input.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
           // locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
            //lapa
            List<Address> listaUbicName;
            Geocoder geo = new Geocoder(Principal.this);

           // input.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());

            try {
                listaUbicName = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
               //  linicio = new LatLng(location.getLatitude(), location.getLongitude());

                /*Toast.makeText(Mapszona.this,"location  "+listaUbicName.get(0).getFeatureName()+" "+listaUbicName.get(0).getSubAdminArea()+" "+
                                listaUbicName.get(0).getSubLocality()+" "+listaUbicName.get(0).getThoroughfare(),
                        Toast.LENGTH_SHORT).show(); */
                //locale lnaguje prefijo es
                //TROUFFARE AV.
                //FEATURE NUMERO
                //subAdminArea provincia
                //sublocality nul
                //country name
                //PREMISES NULL
                //admin area gobierno regional de lima --gobierno
                //locaclity distrito
                //country name peru
                String dire = listaUbicName.get(0).getThoroughfare() + " " + listaUbicName.get(0).getFeatureName() + ", " + listaUbicName.get(0).getLocality() + " ," + listaUbicName.get(0).getCountryName();
                Toast.makeText(Principal.this, "location  " + dire,
                        Toast.LENGTH_SHORT).show();

              //  input.setText(dire);

                agregarMarcador(location.getLatitude(), location.getLongitude());


            } catch (IOException e) {


                e.printStackTrace();
            }
        }

       // startLocationUpdates();
    }

  private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }
     LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            List<Address> listaUbicName;
            Geocoder geo = new Geocoder(Principal.this);
           try {
                listaUbicName = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                linicio = new LatLng(location.getLatitude(), location.getLongitude());

                String dire = listaUbicName.get(0).getThoroughfare() + " " + listaUbicName.get(0).getFeatureName() + ", " + listaUbicName.get(0).getLocality() + " ," + listaUbicName.get(0).getCountryName();
                Toast.makeText(Principal.this, "location  " + dire,
                        Toast.LENGTH_SHORT).show();

                input.setText(dire);


            } catch (IOException e) {


                e.printStackTrace();
            }

           //input.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(Principal.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }
}







