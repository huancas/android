package pe.progsistem.jesus.ubeprivado.Adaptador;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.User;
import pe.progsistem.jesus.ubeprivado.conexion.Conexion;
import pe.progsistem.jesus.ubeprivado.conexion.RequestHandler;


public class UserAdapter extends
        RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private final OnItemClickListener listener;
    private List<User> contactList;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private ImageView logo;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(User item);
    }
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView name;
       // public Button at;

        public MyViewHolder(View view) {
            super(view);
         /*   contactText = (TextView) view.findViewById(R.id.tv_title);
            birthdayText = (TextView) view.findViewById(R.id.tv_company);

         ImageView logo= (ImageView) view.findViewById(R.id.iv_avatar); */

            username = (TextView) view.findViewById(R.id.tvUsername);
            name = (TextView) view.findViewById(R.id.tvName);
            logo= (ImageView) view.findViewById(R.id.ivFoto);

           // at =(Button) view.findViewById(R.id.btnAt);



          }
    }

    public UserAdapter(Context context, List<User> contactLists, OnItemClickListener listener) {
        this.contactList = contactLists;
        this.listener = listener;
        this.context =context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User c = contactList.get(position);
        holder.username.setText(c.getNom_user());
        holder.name.setText(c.getNombre());
        String ruta =Conexion.RUTAIMG+c.getFoto_perfil();
        Picasso.get().load(ruta).into(logo);

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
        return contactList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
              //  .inflate(R.layout.row, parent, false);
           .inflate(R.layout.user_item, parent, false);

        return new MyViewHolder(v);
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
