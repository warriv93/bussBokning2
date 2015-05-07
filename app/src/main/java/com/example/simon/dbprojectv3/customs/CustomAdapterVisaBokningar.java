package com.example.simon.dbprojectv3.customs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.simon.dbprojectv3.R;

import java.util.List;

/**
 * Created by Iceman on 2015-03-19.
 */
public class CustomAdapterVisaBokningar extends ArrayAdapter<Bokningar> {
        private Activity context;
        private static LayoutInflater inflater=null;
        private List<Bokningar> list = null;


        /*************  CustomAdapter Constructor *****************/
        public CustomAdapterVisaBokningar(Activity context, List<Bokningar> list) {
            super(context, R.layout.row_visabokningar, list);
            this.context = context;
            this.list = list;

            //  Layout inflator to call custom xml layout ()
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public int getCount() {
            if(list.size()<=0)
                return 1;
            return list.size();
        }

        /********* Create a holder Class to contain inflated xml file elements *********/
        //tror inte detta behövs egentligen
        public static class ViewHolder{
            public TextView txtDag;
            public TextView txtFran;
            public TextView txtTill;
            public TextView txtAnkomst;
            public TextView txtAvgang;
        }

        /******  Create each ListView row *****/
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("test", "customlist creating rows");
            View vi = convertView;
            ViewHolder holder;

            //if the row is created
            if (vi == null) {
                /****** Inflate list_single.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate( R.layout.list_single, null);

                /****** View Holder Object to contain list_single.xml file elements ******/
                holder = new ViewHolder();
                holder.txtDag = (TextView) vi.findViewById(R.id.txtID);
                holder.txtFran = (TextView) vi.findViewById(R.id.txtFran);
                holder.txtTill = (TextView) vi.findViewById(R.id.txtTill);
                holder.txtAnkomst = (TextView) vi.findViewById(R.id.txtAnkomst);
                holder.txtAvgang = (TextView) vi.findViewById(R.id.txtAvgang);

                /************  Set holder with LayoutInflater ************/
                vi.setTag( holder );

            }else
                holder=(ViewHolder)vi.getTag();

            if(list.size()<=0){
                holder.txtFran.setText("No Data");
            }else{
                /************  Set the values in Holder elements ***********/
                holder.txtDag.setText("Dag: "+list.get(position).getDag());
                holder.txtFran.setText("Från: "+list.get(position).getFran().toString());
                holder.txtTill.setText("Till: "+list.get(position).getTill().toString());
                holder.txtAnkomst.setText("Ankomst: "+list.get(position).getAnkomst().toString());
                holder.txtAvgang.setText("Avgang: "+list.get(position).getAvgang().toString());


//            /******** Set Item Click Listener for LayoutInflater for each row *******/
//            vi.setOnClickListener(new OnItemClickListener( position ));
            }

            return vi;
        }
}
