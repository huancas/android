package pe.progsistem.jesus.ubeprivado.Frgaments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import pe.progsistem.jesus.ubeprivado.Adaptador.LeadsAdapter;
import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.Repositorios.LeadsRepository;
import pe.progsistem.jesus.ubeprivado.beans.Lead;

public class LeadFragment extends Fragment {
    ListView mLeadsList;
    LeadsAdapter mLeadsAdapter;



    public LeadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lead, container, false);

        // Instancia del ListView.
        mLeadsList = (ListView) root.findViewById(R.id.leads_list);

        // Inicializar el adaptador con la fuente de datos.
        mLeadsAdapter = new LeadsAdapter(getActivity(),
                LeadsRepository.getInstance().getLeads());

        mLeadsList.setAdapter(mLeadsAdapter);

        // Eventos
        mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Lead currentLead = mLeadsAdapter.getItem(position);
                Toast.makeText(getActivity(),
                        "Iniciar screen de detalle para: \n" + currentLead.getName(),
                        Toast.LENGTH_SHORT).show();
                //obtener datos
                String nombre =  currentLead.getName();
                String descripcion =  currentLead.getTitle();
                int img =  currentLead.getImage();



                // ********** para  enviar datos  de un fragment  a otro  fragment *******************
                DetalleFragment dimensionFragment = new DetalleFragment();
                FragmentManager fragmentManager = getFragmentManager();

                Bundle args = new Bundle();
                args.putString("nombre",nombre);
                args.putString("descrip",descripcion);
                args.putInt("img",img);
                dimensionFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, dimensionFragment);
                fragmentTransaction.addToBackStack(null); fragmentTransaction.commit();
                //***********************************************************************************//
            }

        });

        return root;
    }

}
