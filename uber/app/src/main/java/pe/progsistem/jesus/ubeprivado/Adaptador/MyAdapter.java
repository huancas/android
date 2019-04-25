package pe.progsistem.jesus.ubeprivado.Adaptador;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pe.progsistem.jesus.ubeprivado.Activities.Mapszona;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Direccion;

import static android.app.Activity.RESULT_OK;
import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Direccion> values;

    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;
        public ImageView ivIcon;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            ivIcon = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Direccion item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Direccion> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String fname = values.get(position).getFormatted_address();
        final String name = values.get(position).getName();
        //latitud lomgitud
        final  String lat = values.get(position).getGeometrylocationlat();
        final  String lng = values.get(position).getGeometrylocationlng();
        holder.txtHeader.setText(name);
        holder.txtFooter.setText(fname);

        String ruta = values.get(position).getIcono();
        Picasso.get().load(ruta).into(holder.ivIcon);

        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

           // remove(position);
               /* Mapszona map = new Mapszona();
                map.MostrarToast(); */

                // Guardamos el texto del EditText en un String.
                String resultado =fname;
                // Recogemos el intent que ha llamado a esta actividad.
             /*  Intent i = getIntent();
                // Le metemos el resultado que queremos mandar a la
                // actividad principal.
                i.putExtra("RESULTADO", resultado);
                // Establecemos el resultado, y volvemos a la actividad
                // principal. La variable que introducimos en primer lugar
                // "RESULT_OK" es de la propia actividad, no tenemos que
                // declararla nosotros.
                setResult(RESULT_OK, i);

                // Finalizamos la Activity para volver a la anterior
                finish(); */

                Intent intent = ((Activity) context).getIntent();
                intent.putExtra("RESULTADO",resultado);
                intent.putExtra("LAT",lat);
                intent.putExtra("LNG",lng);
                ((Activity) context).setResult(((Activity) context).RESULT_OK,
                        intent);
                ((Activity) context).finish();

            }
        });


    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}