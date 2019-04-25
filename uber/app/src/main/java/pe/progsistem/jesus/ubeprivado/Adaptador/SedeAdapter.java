package pe.progsistem.jesus.ubeprivado.Adaptador;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Sede;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;

public class SedeAdapter  extends   RecyclerView.Adapter<SedeAdapter.MyViewHolder>{

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private final SedeAdapter.OnItemClickListener listener;
    private List<Sede> sedeList;
  private Context context;

    public interface OnItemClickListener {
        void onItemClick(Sede item);
    }
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sede;
        public TextView descripcion;
        public TextView cel;

        private Button btnEdit,btnDelete;
        private ImageView ivCat;
        // public Button at;

        public MyViewHolder(View view) {
            super(view);
         /*   contactText = (TextView) view.findViewById(R.id.tv_title);
            birthdayText = (TextView) view.findViewById(R.id.tv_company);

         ImageView logo= (ImageView) view.findViewById(R.id.iv_avatar); */

            sede = (TextView) view.findViewById(R.id.tvSede);
            descripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            cel = (TextView) view.findViewById(R.id.tvCel);
            ivCat = (ImageView) view.findViewById(R.id.ivCat);


            // at =(Button) view.findViewById(R.id.btnAt);



        }
    }

    public SedeAdapter(Context context, List<Sede> contactLists, SedeAdapter.OnItemClickListener listener) {
        this.sedeList = contactLists;
        this.listener = listener;
        this.context =context;
    }

    @Override
    public void onBindViewHolder(SedeAdapter.MyViewHolder holder, final int position) {
        final Sede c = sedeList.get(position);
        holder.sede.setText(c.sede_name);
        holder.descripcion.setText(c.sede_descripcion);
        holder.cel.setText(c.sede_cel);


        String ruta =RUTAIMG+c.sede_cel2;
        Picasso.get().load(ruta).into(holder.ivCat);


       // holder.birthdayText.setText(df.format(c.birthday));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(c);
            }
        });

     /*   holder.at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Agregar " + c.getNom_user())
                        .setMessage("¿esta Seguro que desea agregar?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                              //  deleteHero(hero.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

               // deleteHero(c.getIdu());


            }
        }); */
    }

    @Override
    public int getItemCount() {
        return sedeList.size();
    }

    @Override
    public SedeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                //  .inflate(R.layout.row, parent, false);
                .inflate(R.layout.sede_item, parent, false);

        return new SedeAdapter.MyViewHolder(v);
    }

    private class PerformNetworkRequest2 extends AsyncTask<Void, Void, String> {

        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest2(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressBar.setVisibility(GONE);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();


            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    // refreshPublicacionList(object.getJSONArray("usuarios"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}
