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

import java.util.List;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Atencion;


public class MyAdapter1 extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Atencion> names;
    public MyAdapter1(Context context, int layout, List<Atencion> names)
    {
        this.context = context;
        this.layout = layout;
        this.names = names;
    }
    @Override
    public int getCount() {
        return this.names.size();
    }
    @Override
    public Object getItem(int position) {
        return this.names.get(position);
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
            holder.nameTextView = (TextView)convertView.findViewById(R.id.textView); //Creamos holder
            holder.fechaTextView = (TextView)convertView.findViewById(R.id.tvFecha); //Creamos holder
            holder.codigoTextView = (TextView)convertView.findViewById(R.id.tvCodigo); //Creamos holder
            holder.ivEstrella = (ImageView)convertView.findViewById(R.id.ivEstrella); //Creamos holder
            convertView.setTag(holder);
        } else {
            //creamos holder   // vuscamos con getTag
            holder = (ViewHolder) convertView.getTag();
        }
        // Nos traemos el valor actual dependiente de la posición
        String currentName = names.get(position).getUsuario_trabajador();
        String currentFecha = names.get(position).getFecha_at();
        String currentCodigo = names.get(position).getId_at();
        String currentTipo = names.get(position).getTipo_at();
        //currentName = (String) getItem(position);
        // Referenciamos el elemento a modificar y lo rellenamos
        holder.nameTextView.setText(currentName);
        holder.fechaTextView.setText(currentFecha);
        holder.codigoTextView.setText(currentCodigo);
        if(currentTipo.equals("PAGADO")) {
            holder.ivEstrella.setImageResource(R.drawable.es1);
        }else{
            holder.ivEstrella.setImageResource(R.drawable.es2);
         }
        // devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }
    static class ViewHolder { //agregar mas elementos y modificarlo
        private TextView nameTextView;
        private TextView fechaTextView;
        private TextView codigoTextView;
        private ImageView ivEstrella;
    }
}
