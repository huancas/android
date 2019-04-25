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
import pe.progsistem.jesus.ubeprivado.beans.Carro;


import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class CarroAdapter extends
        RecyclerView.Adapter<CarroAdapter.MyViewHolder>  implements  ActivityCompat.OnRequestPermissionsResultCallback{

    private final OnItemClickListener listener;
    private List<Carro> contactList;
   private ImageView logo;

    private String  num_cel;
    // Aquí están tus objetos de tipo Button ... solo añade la siguiente variable después de estos

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 10; // Puedes poner cualquier número, solo es para identificarlo

    private Context context;



    public interface OnItemClickListener {
        void onItemClick(Carro item);
    }
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo,tvDescripcion,tvPrecio;
        public TextView birthdayText;

        private RatingBar  ratingBar1;


        public MyViewHolder(View view) {
            super(view);
         /*   contactText = (TextView) view.findViewById(R.id.tv_title);
            birthdayText = (TextView) view.findViewById(R.id.tv_company);

          */

            tvTitulo = (TextView) view.findViewById(R.id.tvCategoria);
            tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            logo= (ImageView) view.findViewById(R.id.ivFoto);
            tvPrecio= (TextView) view.findViewById(R.id.tvPrecio);







        }
    }

    public CarroAdapter(List<Carro> contactLists, OnItemClickListener listener) {
        this.contactList = contactLists;
        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Carro c = contactList.get(position);
        holder.tvTitulo.setText(c.getCategoria());
        holder.tvDescripcion.setText(c.getDescripcion());
        holder.tvPrecio.setText("s/. "+c.getPorcentage());









        String ruta =RUTAIMG+c.getImg_carro();
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
           .inflate(R.layout.item_carro, parent, false);

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
