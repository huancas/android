package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.progsistem.jesus.ubeprivado.R;


public class DetalleFragment2 extends Fragment {


        TextView art, tvname, tvapp, tvemail, tvusuario, tvusername, tvcel, tvcel2;


        ImageView logo;


        public DetalleFragment2() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout for this fragment

            /***************para  recibir datos de otro fragment    ***************************/
            final String nombre = getArguments() != null ? getArguments().getString("nombre") : "Anonimo";
            final String descripcion = getArguments() != null ? getArguments().getString("descrip") : "";
            // final double precio = getArguments() != null ? getArguments().getDouble("precio") : 100.00;
            //  int pos = getArguments() != null ? getArguments().getInt("pos") : 0;
          //  int img = getArguments() != null ? getArguments().getInt("img") : 0;

            //String posicion =String.valueOf(pos);
            // String posicion = Integer.toString(pos);
            /*********************************************************************************/

            //donde fragment_artista es la referencia a tu archivo XML con el layout del Fragment.
            // inflamos  el  layout para este fragmento
            View v = inflater.inflate(R.layout.fragment_detalle_fragment2, container, false);

            //  ************solo  para recibir datos de una actividad  *************************
            // extraemos  el nombre
     /*   final String texto = getArguments().getString("nombre");
       int pos = getArguments().getInt("posicion");
        String posicion =String.valueOf(pos);     */
            //**********************************************************************************


            //  mostramos el texto en este fragmento
            TextView des = (TextView) v.findViewById(R.id.tvDescripcion);
            TextView titulo = (TextView) v.findViewById(R.id.tvNombre);
            //  TextView pr = (TextView) v.findViewById(R.id.tvPrecio);


            des.setText("" + descripcion);
            titulo.setText(nombre);
            //   pr.setText(" S/. " + String.valueOf(precio));
            ImageView logo = (ImageView) v.findViewById(R.id.ivFoto);

           // Glide.with(getContext()).load(img).into(logo);
       /* if(pos ==0){ logo.setImageResource(R.drawable.lady);}
        if(pos ==1){ logo.setImageResource(R.drawable.kathy);}
        if(pos ==2){ logo.setImageResource(R.drawable.rihana);}
        if(pos ==3){ logo.setImageResource(R.drawable.shakira);}  */


            ///  funcion  al hacer clic  a la imagen
     /*   logo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Toast.makeText(getActivity(),
                        "Iniciar  pack  de " + nombre,
                        Toast.LENGTH_SHORT).show();


                //Aqui metemos el código que queramos

            }
        }); */

            return v;
        }
    }

