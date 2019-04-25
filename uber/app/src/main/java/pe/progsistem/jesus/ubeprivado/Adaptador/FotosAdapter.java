package pe.progsistem.jesus.ubeprivado.Adaptador;

/**
 * Created by user on 16/10/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Fotos;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class FotosAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private Fotos foto;
    private List<Fotos> listaFotos;
    public FotosAdapter(Context context, int layout, List<Fotos> fotos)
    {
        this.context = context;
        this.layout = layout;
        this.listaFotos = fotos;
    }
    @Override
    public int getCount() {
        return this.listaFotos.size();
    }
    @Override
    public Object getItem(int position) {
        return this.listaFotos.get(position);
    }
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            viewGroup) {
        // View Holder Pattern
        ViewHolder holder;
        if (convertView == null) {

            // Inflamos la vista que nos ha llegado con nuestro layout    personalizado
            LayoutInflater layoutInflater =
                    LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);
            holder = new ViewHolder();
            // Referenciamos el elemento a modificar y lo rellenamos
          //  holder.nameTextView = (TextView)convertView.findViewById(R.id.textView); //Creamos holder
            holder.img = (ImageView) convertView.findViewById(R.id.ivFoto); //Creamos holder

            convertView.setTag(holder);
        } else {
            //creamos holder   // vuscamos con getTag
            holder = (ViewHolder) convertView.getTag();
        }
        // Nos traemos el valor actual dependiente de la posición
        //foto = new Fotos();
        foto  = listaFotos.get(position);
        //currentName = (String) getItem(position);
        // Referenciamos el elemento a modificar y lo rellenamos
      //  holder.nameTextView.setText(foto.getUrl_foto());

        String ruta =RUTAIMG+foto.getUrl_foto();
        Picasso.get().load(ruta).into(holder.img);
        // devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }
    static class ViewHolder { //agregar mas elementos y modificarlo
        private TextView nameTextView;
        private ImageView img;
    }
}
