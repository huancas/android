package pe.progsistem.jesus.ubeprivado.Activities;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pe.progsistem.jesus.ubeprivado.Frgaments.CarroFragment;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.util.DelayAutoCompleteTextView;

public class Mapszona extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {
    // private Dao dao;

    ArrayList<LatLng> markerPoints;

    SQLiteDatabase mDatabase;

    private GoogleMap mMap;
    private Circle circle;
    private Marker marcador;
    String tipolugar;
    private LatLng linicio,lpartida;
    private TextView tvmiUbicacion;


    private Integer THRESHOLD = 2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private ImageView geo_autocomplete_clear;

    private double lat=0.0,lng=0.0;

    //permisos de la app

    private static final int COARSE_LOCATION_REQUEST_CODE = 2;

    private static final int FINE_PERMISSION_REQUEST_CODE = 3;

    // Estas variables las declaramos al principio de nuestra clase, justo debajo
// de la cabecera:
    private final static int PARTIDA = 0;
    private final static int DESTINO = 1;

    LatLng ubicacion;
    LatLng ubic_click;
    Double latitude,longitude;

    private Button boton,btRuta;

    private TextView input,et_destino;
    private TextView ubic,map;

// <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mostrar action bar
        // getSupportActionBar().hide();

   /* ActivityCompat.requestPermissions(Mapszona.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                COARSE_LOCATION_REQUEST_CODE); */
        ActivityCompat.requestPermissions(Mapszona.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_PERMISSION_REQUEST_CODE);


        ubicacion = new LatLng(-34, 151);
        setContentView(R.layout.activity_mapszona);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        et_destino = (TextView) findViewById(R.id.et_destino);
        input = (TextView) findViewById(R.id.editText);

        boton = (Button)findViewById(R.id.button);

        btRuta = (Button)findViewById(R.id.bt_ruta);

        tvmiUbicacion = (TextView) findViewById(R.id.tvUbic);


        tipolugar="Peligroso";



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
                try{
                    //  buscar();
                    // obtenerdatos();
                    if(!input.getText().equals("") || !et_destino.getText().equals("")) {
                        TrazarRuta();
                    }
                    Toast.makeText(Mapszona.this,"Buscando  "+input.getText()+" ...",
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){ Toast.makeText(Mapszona.this,"error: menu "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();                    }
            }
        });


        //abrir la ruta
        btRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // buscardir();

                    Intent aboutIntent = new Intent(Mapszona.this, WebViewActivity.class);
                    String a =  input.getText().toString();
                    String b=  et_destino.getText().toString();
                    aboutIntent.putExtra("dir",a);
                    aboutIntent.putExtra("des",b);
                    startActivity(aboutIntent);


                }catch (Exception e){ Toast.makeText(Mapszona.this,"error: menu "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();                    }
            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rellenarPartida( view);
            }
        });
        et_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rellenarDestino( view);
            }
        });

        tvmiUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  rellenarPartida( view);
                //ObtenerNombremiUbicacion();
                checkIfLocationOpened();
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
                Toast.makeText(Mapszona.this,"letra "+s,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    //  geo_autocomplete_clear.setVisibility(View.VISIBLE);
                }
                else
                {
                    //geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });

        cambiarFragment();



    }

    // Método que se ejecuta al pulsar el botón btNombre:
    public void rellenarPartida(View v) {
        Intent i = new Intent(this, MainActivity.class);
        // Iniciamos la segunda actividad, y le indicamos que la iniciamos
        // para rellenar el nombre:
        startActivityForResult(i,PARTIDA);
    }
    // Método que se ejecuta al pulsar el botón btNombre:
    public void rellenarDestino(View v) {
        Intent i = new Intent(this, MainActivity.class);
        // Iniciamos la segunda actividad, y le indicamos que la iniciamos
        // para rellenar el nombre:
        startActivityForResult(i,DESTINO);
    }

    public void buscar(){

        Geocoder geo = new Geocoder(Mapszona.this); //locale.getDefault
        int maxResultados = 10;
        List<Address> adress = null;
        try {
            //convertir la ubicacion de texto a latitud y longitud
            adress = geo.getFromLocationName(input.getText().toString(), maxResultados);

            /*          pasar objetos y resultados a un activity  */
            Bundle bund = new Bundle();
            ArrayList<Address> as = new ArrayList<Address>(adress);
            bund.putSerializable("faddress", as);

            for ( Address a : adress )
                mMap.addMarker( new MarkerOptions().position( new LatLng( a.getLatitude(), a.getLongitude() ) ).title( "Hello "+a.getFeatureName() ).snippet( "Description about me!" +a.getLocality()) );
            //pasar objeto

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("listaaddress",bund);
            intent.putExtra("size",adress.size());

            startActivity(intent);

        } catch (IOException e) {
            Toast.makeText(Mapszona.this,"Error "+e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ubicacion = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
        adress.clear();//borrar la busqueda anterior
        // mMap.clear();//borrar los el marcador anteriro //caso contrario se va mostrando todos los marcadores
        // mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker in "+input.getText()));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("STDS S.A.C en "+input.getText()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));





    }
    public void buscardir(){
        try{
            Geocoder geo = new Geocoder(Mapszona.this);
            int maxResultados = 1;
            List<Address> adress = null;
            List<Address> adress1 = null;
            try {
                //conertir la ubicacion de texto a latitud y longitud
                adress = geo.getFromLocationName(input.getText().toString(), maxResultados);
                adress1 = geo.getFromLocationName(et_destino.getText().toString(), maxResultados);

            } catch (IOException e) {
                Toast.makeText(Mapszona.this,"Error obteniendo ubicacion "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            double lat= adress.get(0).getLatitude();
            double lng= adress.get(0).getLongitude();
            double lat1= adress1.get(0).getLatitude();
            double lng1= adress1.get(0).getLongitude();
            showDirections(lat,lng,lat1,lng1);
        }catch (Exception e){ Toast.makeText(Mapszona.this,"error buscar dir "+e.getMessage(),
                Toast.LENGTH_SHORT).show();                    }

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
                Intent aboutIntent = new Intent(Mapszona.this, MainActivity.class);
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
        if (marcador!=null)marcador.remove();
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
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locListener);

    }

    private void ObtenerNombremiUbicacion() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        Geocoder geo = new Geocoder(Mapszona.this);
        List<Address> listaUbicName;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Toast.makeText(Mapszona.this,"location  "+location,
                Toast.LENGTH_SHORT).show();

        if (location != null) {

            try {
                listaUbicName = geo.getFromLocation (location.getLatitude(),location.getLongitude(), 1);

                linicio = new LatLng(location.getLatitude(),location.getLongitude());

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
                String dire = listaUbicName.get(0).getThoroughfare()+" "+listaUbicName.get(0).getFeatureName()+", "+listaUbicName.get(0).getLocality()+" ,"+listaUbicName.get(0).getCountryName();
                Toast.makeText(Mapszona.this,"location  "+dire,
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

    LocationListener locListener = new LocationListener() {
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
    };




    @Override
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
        }
    }

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


        Toast.makeText(Mapszona.this,"lats "+lng+";"+lat+" - "+lat1+";"+lng1,
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
            // De lo contrario, recogemos el resultado de la segunda actividad.
            String resultado = data.getExtras().getString("RESULTADO");
            String rlat = data.getExtras().getString("LAT");
            String rlng = data.getExtras().getString("LNG");


            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            switch (requestCode) {
                case PARTIDA:
                    input.setText(resultado);
                    linicio = new LatLng(Double.parseDouble(rlat),Double.parseDouble(rlng));
                    break;
                case DESTINO:
                    et_destino.setText(resultado);
                    lpartida = new LatLng(Double.parseDouble(rlat),Double.parseDouble(rlng));
                    break;
            }
        }
    }

    public void calcular(){
        Location location = new Location("localizacion 1");
        location.setLatitude(0.00000);  //latitud
        location.setLongitude(0.00000); //longitud
        Location location2 = new Location("localizacion 2");
        location2.setLatitude(0.00000);  //latitud
        location2.setLongitude(0.00000); //longitud
        double distance = location.distanceTo(location2);

    }
    public void TrazarRuta(){
        mMap.addMarker(new MarkerOptions().position(linicio).title("Partida "));
        mMap.addMarker(new MarkerOptions().position(lpartida).title("Destino "));



        mMap.addPolyline(new PolylineOptions()
                .add(lpartida,linicio)
                .width(5)
                .color(Color.RED));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(lpartida));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lpartida,14f));


        // line.setTag("A");
    }

    public void cambiarFragment(){
        Fragment fragment = null;
        fragment = new CarroFragment();
        Bundle bund = new Bundle();
        bund.putString("id", "");
        bund.putString("sede","TODOS");
        bund.putString("descripcion", "");
        bund.putString("cel", "");
        bund.putString("cel2", "");
        fragment.setArguments(bund);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void checkIfLocationOpened() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        System.out.println("Provider contains=> " + provider);
        // if (provider.contains("gps") || provider.contains("network")){
        if (provider.contains("gps")){

            ObtenerNombremiUbicacion();
        }
        else{
            AlertNoGps();
        }
    }



    private void AlertNoGps() {

        AlertDialog alert = null;
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

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






}
