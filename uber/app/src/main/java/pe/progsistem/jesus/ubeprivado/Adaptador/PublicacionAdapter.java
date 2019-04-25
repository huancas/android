package pe.progsistem.jesus.ubeprivado.Adaptador;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Publicacion;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class PublicacionAdapter extends
        RecyclerView.Adapter<PublicacionAdapter.MyViewHolder>  implements  ActivityCompat.OnRequestPermissionsResultCallback{

    private final OnItemClickListener listener;
    private List<Publicacion> contactList;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private ImageView logo;
    Button btnLlamar;
    private String  num_cel;
    // Aquí están tus objetos de tipo Button ... solo añade la siguiente variable después de estos

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 10; // Puedes poner cualquier número, solo es para identificarlo

    private Context context;



    public interface OnItemClickListener {
        void onItemClick(Publicacion item);
    }
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo,tvDescripcion,tvEdad,tvPrecio,tvCategoria,tvUsuario,tvFecha_actualizacion,tvEnviar;
        public TextView birthdayText;

        private RatingBar  ratingBar1;


        public MyViewHolder(View view) {
            super(view);
         /*   contactText = (TextView) view.findViewById(R.id.tv_title);
            birthdayText = (TextView) view.findViewById(R.id.tv_company);

         ImageView logo= (ImageView) view.findViewById(R.id.iv_avatar); */

            tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            tvEdad = (TextView) view.findViewById(R.id.tvEdad);
            tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);
            tvCategoria = (TextView) view.findViewById(R.id.tvCategoria);
            tvUsuario = (TextView) view.findViewById(R.id.tvUsuario);
            tvFecha_actualizacion  = (TextView) view.findViewById(R.id.tvFecha_actualizacion);
            logo= (ImageView) view.findViewById(R.id.ivFoto);
            ratingBar1 = (RatingBar) view.findViewById(R.id.ratingBar1);
            tvEnviar = (TextView) view.findViewById(R.id.tvEnviar);

            btnLlamar =(Button)view.findViewById(R.id.btnllamar);

           btnLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context = v.getContext();
                    try{



                        if(ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                            // Aquí ya está concedido, procede a realizar lo que tienes que hacer
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num_cel));
                          //  i.setData(Uri.parse("tel:123456789"));
                            v.getContext().startActivity(i);
                        }else{
                            // Aquí lanzamos un dialog para que el usuario confirme si permite o no el realizar llamadas
                            ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{ Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }






                    }catch (Exception e){ Toast.makeText(v.getContext(),"error: llamar "+e.getMessage(),
                            Toast.LENGTH_SHORT).show();                    }
                }
            });




        }
    }

    public PublicacionAdapter(List<Publicacion> contactLists, OnItemClickListener listener) {
        this.contactList = contactLists;
        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Publicacion c = contactList.get(position);
        holder.tvTitulo.setText(c.getNombre_pu());
        holder.tvDescripcion.setText(c.getDescrip_pu());
        holder.tvEdad.setText("Edad :"+c.getEdad_pu());
        holder.tvPrecio.setText("S/. "+c.getTarifa());
        holder.tvCategoria.setText(c.getCat_id());
        holder.tvUsuario.setText(c.getUs_id());
        holder.tvFecha_actualizacion.setText(c.getUl_fecha());
        num_cel = c.getCel_sede();

         if(num_cel!=null || !num_cel.equals("null")) {
          //  SpannableString s = new SpannableString("Whatsapp "+num_cel);
             String mensaje = "&text=hola%20estoy%20interesado%20en%20"+c.getNombre_pu()+"%20-usuario%20"+c.getUs_id();

             SpannableString s = new SpannableString("Enviar Mensage");
             s.setSpan(new URLSpan("https://api.whatsapp.com/send?phone=51" + num_cel+mensaje), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             // s.setSpan(new URLSpan("https://wa.me/51" + num_cel), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvEnviar.setMovementMethod(LinkMovementMethod.getInstance());
            holder.tvEnviar.setText(s);
        }






        String rat =c.getCalificacion();


        if( rat ==null || rat.isEmpty() || rat.equals(null)  || rat =="" || rat.equals("null")) {

        }else{
       float f = Float.parseFloat(rat);
        holder.ratingBar1.setRating(f);

        }

        String ruta =RUTAIMG+c.getUrl_foto();
        Picasso.get().load(ruta).into(logo);

       // holder.birthdayText.setText(df.format(c.birthday));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
              //  .inflate(R.layout.row, parent, false);
           .inflate(R.layout.publicacion_item, parent, false);

        return new MyViewHolder(v);
    }

    // Y finalmente recibimos la respuesta del usuario en un método de tipo `@Override` así:

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISO CONCEDIDO, procede a realizar lo que tienes que hacer
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num_cel));
                  // i.setData(Uri.parse("tel:123456789"));
                    context.startActivity(i);
                } else {
                    // PERMISO DENEGADO
                }
                return;
            }
        }
    }
}
