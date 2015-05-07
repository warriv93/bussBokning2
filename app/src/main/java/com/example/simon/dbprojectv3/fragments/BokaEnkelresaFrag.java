package com.example.simon.dbprojectv3.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.dbprojectv3.MainActivity;
import com.example.simon.dbprojectv3.R;
import com.example.simon.dbprojectv3.customs.CustomList;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BokaEnkelresaFrag extends Fragment {
    private CustomList adapter;
    private ListView resList;
    private String [] bussTurInfo;
    private int PersonID, BussID;

    public BokaEnkelresaFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boka_enkelresa, container, false);
        startStuff(view);
        return view;
    }

    private void startStuff(View view) {
        resList = (ListView) view.findViewById(R.id.resList);
        Button btUpdateList = (Button) view.findViewById(R.id.btUpdateList);
        btUpdateList.setOnClickListener(new  btClicklistener());


        /**************** Create Custom Adapter *********/
        adapter = new CustomList(getActivity(), ((MainActivity)getActivity()).getResor());
        resList.setAdapter(adapter);
        resList.setOnItemClickListener(new ItemClickListener());
    }

    private void updatelist(){
        resList.destroyDrawingCache();
        resList.setVisibility(ListView.INVISIBLE);
        resList.setVisibility(ListView.VISIBLE);
        adapter.notifyDataSetChanged();
        adapter = new CustomList(getActivity(),((MainActivity)getActivity()).getResor() );
        resList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private class btClicklistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
             updatelist();
        }
    }

    private class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ListView Clicked item index
            int itemPosition     = position;

            //open selected TUR and show more information
           final Dialog d = new Dialog(getActivity());
            d.setTitle("Resa: "+ itemPosition);
            d.setCanceledOnTouchOutside(true);
            //inserting xml file in Dialog
            LayoutInflater factory = LayoutInflater.from(getActivity());
            View infoLayout = factory.inflate(R.layout.boka_layout, null);

           //init variables
            TextView tv = (TextView) infoLayout.findViewById(R.id.bussTurInfotextView);
            Button btn_cancel = (Button) infoLayout.findViewById(R.id.btn_cancel);
            Button btn_save = (Button) infoLayout.findViewById(R.id.btn_save);

            //hämtar bussTurInfo baserat på vilken TurID och sparar i en String []
           bussTurInfo = ((MainActivity)getActivity()).getBussTurInfo(itemPosition);

           //hämtar info om resenären
            String[] Tempresenar = ((MainActivity)getActivity()).getResenar();
            PersonID = Integer.parseInt(Tempresenar[1]);
            BussID = Integer.parseInt(bussTurInfo[0]);

            //Hämtar den angivna inmatade transaktionen från databasen
            String text = "BussID: "+bussTurInfo[0].toString()+"\n"+
                    "Pris: "+bussTurInfo[1].toString() +" SEK\n"+
                    "Dag: "+bussTurInfo[2].toString() +"\n"+
                    "Avgang: "+bussTurInfo[3].toString() +"\n"+
                    "Ankomst: "+bussTurInfo[4].toString() +"\n";

            tv.setText(text);

            //setting up the buttons
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if(((MainActivity)getActivity()).sharedpreferences.contains("name")){
                        //send itemPosition (TurID) to a method to show this travel in the list

                         //set bokningar
                         ((MainActivity)getActivity()).addReserverarB(PersonID, BussID);
//                         Log.i("TEST", PersonID +"      "+BussID );
                         Toast.makeText(getActivity(), "Your trip is now in the VisaMinaBokningar Section! ", Toast.LENGTH_LONG).show();
                  }else {
                         Toast.makeText(getActivity(), "It seems like your not registered...", Toast.LENGTH_LONG).show();
                         d.dismiss();
                         ((MainActivity)getActivity()).startRegDialog();
                     }
                    d.dismiss();
                }
            });
            d.setContentView(infoLayout);
            d.show();
        }
    }


//    public void onItemClick(int mPosition)
//    {
//        ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);
//
//
//        // SHOW ALERT
//
//        Toast.makeText(CustomListView,
//                ""+tempValues.getCompanyName()
//                        +"
//                Image:"+tempValues.getImage()
//            +"
//        Url:"+tempValues.getUrl(),
//        Toast.LENGTH_LONG)
//        .show();
//    }
}
