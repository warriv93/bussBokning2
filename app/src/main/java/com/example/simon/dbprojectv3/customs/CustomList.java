package com.example.simon.dbprojectv3.customs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.simon.dbprojectv3.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by simon on 24-Feb-15.
 */
public class CustomList extends ArrayAdapter<Resor> {
    private  Activity context;
    private static LayoutInflater inflater=null;
    private  List<Resor> list = null;


    /*************  CustomAdapter Constructor *****************/
    public CustomList(Activity context, List<Resor> list) {
        super(context, R.layout.list_single, list);
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
        public TextView TurID;
        public TextView txtFran;
        public TextView txtTill;
    }

    /******  Create each ListView row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("test", "customlist creating rows");
        View vi = convertView;
        ViewHolder holder;

        if (vi == null) {

            /****** Inflate list_single.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate( R.layout.list_single, null);

            /****** View Holder Object to contain list_single.xml file elements ******/
            holder = new ViewHolder();
            holder.TurID = (TextView) vi.findViewById(R.id.txtID);
            holder.txtFran = (TextView) vi.findViewById(R.id.txtFran);
            holder.txtTill = (TextView) vi.findViewById(R.id.txtTill);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );

        }else
            holder=(ViewHolder)vi.getTag();

        if(list.size()<=0){
            holder.TurID.setText("No Data");
        }else{
            /************  Set the values in Holder elements ***********/
            holder.TurID.setText("TurID: "+list.get(position).getTurID());
            holder.txtFran.setText("From: "+list.get(position).getFran().toString());
            holder.txtTill.setText("To: "+list.get(position).getTill().toString());


//            /******** Set Item Click Listener for LayoutInflater for each row *******/
//            vi.setOnClickListener(new OnItemClickListener( position ));
        }

        return vi;
    }

//    @Override
//    public void onClick(View v) {
//        Log.v("CustomAdapter", "=====Row button clicked=====");
//    }
//
//    /********* Called when Item click in ListView ************/
//    private class OnItemClickListener  implements OnClickListener{
//        private int mPosition;
//
//        OnItemClickListener(int position){
//            mPosition = position;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//
//
//            CustomListViewAndroidExample sct = (CustomListViewAndroidExample)activity;
//
//            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
//
//            sct.onItemClick(mPosition);
//        }
//    }


}
