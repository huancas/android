package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pe.progsistem.jesus.ubeprivado.R;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class PromocionFragment extends Fragment {

    TextView art,tvname,tvapp,tvemail,tvusuario,tvusername,tvcel,tvcel2;


    ImageView logo;

    public ViewImageExtended viewImageExtended;
    private String url_foto;


    public PromocionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  ************solo  para recibir datos de una actividad  *************************
        // extraemos  el nombre

        final String username = getArguments().getString("ftitulo");
        final String name = getArguments().getString("fdescripcion");
        final String app = getArguments().getString("fpromo_img");
        final String fecha = getArguments().getString("ffecha");
        url_foto =app;




        View root = inflater.inflate(R.layout.fragment_promocion, container, false);
        tvusername =(TextView) root.findViewById(R.id.tvUsername);
        tvname =(TextView) root.findViewById(R.id.tvUser);
        tvcel =(TextView) root.findViewById(R.id.tvCel);


        tvusername.setText(username);
        tvname.setText(name);
        tvcel.setText(fecha);

        //tvapp.setText(edad);
        logo= (ImageView) root.findViewById(R.id.imagenProfile);
        //  logo.setImageResource(R.drawable.lady);
        // downloadFile(imageHttpAddress);

        String ruta =RUTAIMG+app;
        Picasso.get().load(ruta).into(logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agrandar(url_foto);
            }
        });

        return root;
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
