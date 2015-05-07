package com.example.simon.dbprojectv3.fragments;



import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simon.dbprojectv3.MainActivity;
import com.example.simon.dbprojectv3.R;
import com.example.simon.dbprojectv3.customs.CustomAdapterVisaBokningar;
import com.example.simon.dbprojectv3.customs.CustomList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class VisaMinaBokningarFrag extends Fragment {
    private Button btn_reg;
    private Dialog reg;
    private CustomAdapterVisaBokningar adapter;
    private ListView resList;

   private String resenar[];
    public static TextView tv_user;

    public VisaMinaBokningarFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visa_mina_bokningar, container, false);
        startStuff(view);
        return view;
    }

    private void startStuff(View view) {
        tv_user = (TextView)view.findViewById(R.id.tv_user);
        btn_reg = (Button) view.findViewById(R.id.btUpdateList3);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).startRegDialog();
            }
        });

        resenar = ((MainActivity)getActivity()).getResenar();
//        tv_user.setText("Namn: "+resenar[0] +", Pnr: " + resenar[1]);

        resList = (ListView) view.findViewById(R.id.resList3);
        Button btUpdateList = (Button) view.findViewById(R.id.btUpdateList3);
        btUpdateList.setOnClickListener(new  btClicklistener());


        /**************** Create Custom Adapter *********/
        adapter = new CustomAdapterVisaBokningar(getActivity(), ((MainActivity)getActivity()).getBokningar(Integer.parseInt(resenar[1])));
        resList.setAdapter(adapter);
//        resList.setOnItemClickListener(new ItemClickListener());
    }

    private void updatelist(){
        resList.destroyDrawingCache();
        resList.setVisibility(ListView.INVISIBLE);
        resList.setVisibility(ListView.VISIBLE);
        adapter.notifyDataSetChanged();
        adapter = new CustomAdapterVisaBokningar(getActivity(),((MainActivity)getActivity()).getBokningar(Integer.parseInt(resenar[1])) );
        resList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private class btClicklistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            updatelist();
        }
    }
}
