package pe.progsistem.jesus.ubeprivado.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Publicacion;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<Publicacion> contactList= new ArrayList<>() ;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

     /* rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(new PublicacionAdapter(contactList, new PublicacionAdapter.OnItemClickListener() {
            @Override public void onItemClick(Publicacion item) {
                Toast.makeText(Main2Activity.this, "Contact Clicked:" + item.getNombre_pu(), Toast.LENGTH_LONG).show();
            }
        })); */
    }
}
