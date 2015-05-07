package com.example.simon.dbprojectv3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.dbprojectv3.customs.Bokningar;
import com.example.simon.dbprojectv3.customs.Resor;
import com.example.simon.dbprojectv3.customs.database;
import com.example.simon.dbprojectv3.fragments.BokaEnkelresaFrag;
import com.example.simon.dbprojectv3.fragments.BokapaketFrag;
import com.example.simon.dbprojectv3.fragments.VisaMinaBokningarFrag;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public SharedPreferences sharedpreferences;
    private database db;

    private Dialog reg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startstuff();

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    private void startstuff() {
        db = new database(this);
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("name")){

        }else{
            startRegDialog();
        }
        addBussturer();

    }

    /****** Function to set data in ArrayList *************/
    public List<Resor> getResor(){
       List<Resor> resor = new ArrayList<Resor>();
        db.open();
        Cursor c = db.getTravel();
        if(c.moveToFirst()) {
            do {
                Resor r = new Resor();
                //adds the objects from the db to the Resor object
                r.setTurID(c.getInt(0));
                r.setFran(c.getString(1).toString());
                r.setTill(c.getString(2).toString());

                //adds every item to the ArrayList
                resor.add(r);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return resor;
    }
    public List<Bokningar> getBokningar(int PersonID){
        List<Bokningar> bokningar = new ArrayList<>();
        db.open();
//        Resa.Till, Resa.Fran, Busstur.Avgang, Busstur.Ankomst, Busstur.Dag
        Cursor c =  db.getBokningarEnkel(PersonID);
        if(c.moveToFirst()) {
            do {
                Bokningar b = new Bokningar();
                //adds the objects from the db to the Resor object
                b.setTill(c.getString(0).toString());
                b.setFran(c.getString(1).toString());
                b.setAvgang(c.getString(2).toString());
                b.setAnkomst(c.getString(3).toString());
                b.setDag(c.getString(4).toString());
                //adds every item to the ArrayList
                bokningar.add(b);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return bokningar;
    }

    public void addResenar(String name, String address, int pnr, String country, int phone, String mail) {
        db.open();
        db.addResenar(name, address, pnr, country, phone, mail);
        db.close();
    }
    public void addReserverarB(int PersonID, int BussID) {
        db.open();
        db.addReserverarB(PersonID, BussID);
        db.close();
    }


    public void addBussturer(){
        db.open();
        db.addBusstur( 1, 1,  100, "onsdag", 1050, 1430);
        db.addTravel(1, "Blomstermåla", "Stockholm");

        db.addBusstur(2, 2, 200, "torsdag", 1530, 1920);
        db.addTravel(2, "Blomstermåla", "Köpenhamn");

        db.addBusstur(3, 2, 400, "torsdag", 2030, 2300);
        db.addTravel(3, "Köpenhamn", "Berlin");

        db.addBusstur(4, 2, 600, "Fredag", 1030, 1800);
        db.addTravel(4, "Berlin", "Paris");

        db.addBusstur(5, 2, 300, "Fredag", 2200, 1000);
        db.addTravel(5, "Paris", "Bryssel");

        db.addBusstur(6, 2, 800, "Lördag", 1000, 1900);
        db.addTravel(6, "Bryssel", "Blomstermåla");

        db.addBusstur( 7, 1,  100, "onsdag", 2030, 2400);
        db.addTravel(7, "Stockholm", "Blomstermåla");

        db.close();
    }

    public String[] getResenar(){
        db.open();
        Cursor c =  db.getResenar();
        String[] resenar = new String[2];
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            resenar[0] = c.getString(0);

            //maybe can only store a single value :S
            resenar[1] = String.valueOf(c.getInt(1));
//           Toast.makeText(getApplication(),  resenar[1] , Toast.LENGTH_LONG).show();
        }
        c.close();
        db.close();
        return resenar;
    }

    public String[] getBussTurInfo(int TurID){
        db.open();
        Cursor c =  db.getBussturer(TurID);
        String[] bussTurInfo = new String[5];
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            bussTurInfo[0] = c.getString(0);
            bussTurInfo[1] = c.getString(1);
            bussTurInfo[2] = c.getString(2);
            bussTurInfo[3] = c.getString(3);
            bussTurInfo[4] = c.getString(4);
        }
        c.close();
        db.close();
        return bussTurInfo;
    }

    public void startRegDialog (){
        reg = new Dialog(this);
        reg.setTitle("Kund Registrering");
        reg.setCanceledOnTouchOutside(true);

        //inserting xml file in Dialog
        LayoutInflater factory = LayoutInflater.from(this);
        View layout = factory.inflate(R.layout.register_fragment, null);

        final EditText etName = (EditText)layout.findViewById(R.id.editText);
        final EditText etAddress = (EditText)layout.findViewById(R.id.editText2);
        final EditText etPnr = (EditText)layout.findViewById(R.id.editText3);
        final EditText etCountry = (EditText)layout.findViewById(R.id.editText4);
        final EditText etPhone = (EditText)layout.findViewById(R.id.editText5);
        final EditText etMail = (EditText)layout.findViewById(R.id.editText6);

        etName.setText(sharedpreferences.getString("name", ""));

        Button btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        Button btn_save = (Button) layout.findViewById(R.id.btn_save);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String country = etCountry.getText().toString();
                String  mail = etMail.getText().toString();

                //if name or Pnr fields are empty the user will be shown the RegDialog again
                if(name.isEmpty() || address.isEmpty() || country.isEmpty() || mail.isEmpty() || etPnr.equals("") || etPhone.equals("") ) {
                    Toast.makeText(getApplication(), "You need to enter atleast a Name and Pnr.", Toast.LENGTH_LONG).show();
                    startRegDialog();
                }else {
                    int pnr = Integer.parseInt(etPnr.getText().toString());
                    int  phone = Integer.parseInt(etPhone.getText().toString());
                    Toast.makeText(getApplication(), "Welcome: "+ etName.getText().toString(), Toast.LENGTH_LONG).show();

                    //saves the name in sharedpreferences
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("name", etName.getText().toString());

                    editor.commit();

                    //saves the information given to the database
                    addResenar(name, address, pnr, country, phone, mail);

                reg.dismiss();
                }
            }
        });
        reg.setContentView(layout);
        reg.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //HÄR SÄTTS FRAGMENTEN IN I TABSEN
            switch (position) {
                case 0:
                    return new BokaEnkelresaFrag();
                case 1:
                    return new BokapaketFrag();
                case 2:
                    return new VisaMinaBokningarFrag();
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
