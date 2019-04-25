package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Sede;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class FixturesTabs extends Fragment {
    ArrayList<Sede> listaSedes;
    String datos,d1,d2;

    private ArrayList<Sede> sedeList =new ArrayList<Sede>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {





        View view = inflater.inflate(R.layout.fragment_fixtures_tabs,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        try {
            setupViewPager(viewPager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);


       // datos = getArguments() != null ? getArguments().getString("fdatos") : "";
       // d1 = getArguments() != null ? getArguments().getString("fuser") : "";
       // d2 = getArguments() != null ? getArguments().getString("fuser1") : "";
        d1="los olivos";
        d2="lince";




      //  ArrayList<Sede> lista = (ArrayList<Sede>) getArguments().getSerializable("flistaSede");
        //obtenemos las listas de datos
      /*  ArrayList a=new ArrayList();
        final Bundle bundle = getArguments();
        if (bundle != null) {
            a=  bundle.getParcelableArrayList("flistaSede");
        }else {

            a.add(new Sede("1","los olivos","dserdghth","9294444162","9294444162"));
            a.add(new Sede("2","los canchas","dserdghth","9294444162","9294444162"));
        } */
     // sedeList=a;




      // sedeList = new ArrayList<Sede>(); sedeList=a;




        return view;

    }

    /*public ArrayList<String> getFoo() {
        ArrayList a=new ArrayList();
        final Bundle bundle = getArguments();
        if (bundle != null) {
          a=  bundle.getParcelableArrayList("mi_llave");
        }
        return  a;
    } */


    // Add Fragments to Tabs
    public void setSedeList(ArrayList<Sede> sedeList){
        this.sedeList = sedeList;
       // listaSedes =sedeList;
    }
    private void setupViewPager(ViewPager viewPager) throws JSONException {
        datos = getArguments() != null ? getArguments().getString("fdatos") : "";
        d1 = getArguments() != null ? getArguments().getString("fuser") : "";
         d2 = getArguments() != null ? getArguments().getString("fuser1") : "";
         //otra opcion

        sedeList = (ArrayList<Sede>) getArguments().getSerializable("flistaSede");


          Fragment fragment = null;
        //creamos un bundle
        Bundle args = new Bundle();
        // Colocamos el String   y  guardamos los datos
        args.putString("sede",d1);
         fragment = new PublicacionFragment();
        fragment.setArguments(args);

        Fragment fragment2 = null;
        //creamos un bundle
        Bundle args2 = new Bundle();
        // Colocamos el String   y  guardamos los datos
        args2.putString("sede",d2);
        fragment2 = new PublicacionFragment();
        fragment2.setArguments(args2);

        Adapter adapter = new Adapter(getChildFragmentManager());  //encima del otro adapter

        Bundle bund ;
        Fragment frag;

       // ArrayList<Fragment> lisFra = new ArrayList<Fragment>();
       // sedeList = new ArrayList<Sede>();
       // sedeList.add(new Sede("1","los olivos","dserdghth","9294444162","9294444162"));
        //sedeList.add(new Sede("2","los canchas","dserdghth","9294444162","9294444162"));

      for(int i=0;i<sedeList.size();i++){
             String sedeid = sedeList.get(i).getSede_id();
            String sedename =sedeList.get(i).getSede_name();
            String descripcion = sedeList.get(i).getSede_descripcion();
            String cel = sedeList.get(i).getSede_cel();
            String cel2 =sedeList.get(i).getSede_cel2();

            frag = new PublicacionFragment();
            bund= new Bundle();
            bund.putString("id",sedeid);
            bund.putString("sede",sedename);
            bund.putString("descripcion",descripcion);
            bund.putString("cel",cel);
            bund.putString("cel2",cel2);
            bund.putString("criterio","");
            frag.setArguments(bund);
            adapter.addFragment(frag,sedename);
            //lisFra.add(frag);

           }




    /*   JSONObject obj = new JSONObject(datos);
        JSONArray sedes  = obj.getJSONArray("sedes");



        for (int i = 0; i < sedes.length(); i++) {
            obj = sedes.getJSONObject(i);

            String sedeid=    obj.getString("id");
                 String sedename=   obj.getString("sede");
            String descripcion =  obj.getString("descripcion");
            String  cel =   obj.getString("cel");
            String cel2  =    obj.getString("cel2");

            frag = new PublicacionFragment();
            bund= new Bundle();
            bund.putString("id",sedeid);
            bund.putString("sede",sedename);
            bund.putString("descripcion",descripcion);
            bund.putString("cel",cel);
            bund.putString("cel2",cel2);
            frag.setArguments(bund);
            adapter.addFragment(frag,sedename);



        } */



        //adapter.addFragment(fragment, "Today"); //nombre del tab
         //  adapter.addFragment(fragment2, "Month");
       // adapter.addFragment(fragment,d1);
       // adapter.addFragment(fragment2,d2);
         viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}