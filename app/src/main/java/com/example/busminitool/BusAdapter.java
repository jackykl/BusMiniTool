package com.example.busminitool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends ArrayAdapter<Bus> implements Filterable {

    private List<Bus> busList;
    private Context context;
    private Filter busFilter;
    private List<Bus> origBusList;

    public BusAdapter(List<Bus> busList, Context ctx) {
        super(ctx, R.layout.fragment_search_list, busList);
        this.busList = busList;
        this.context = ctx;
        this.origBusList = busList;
    }

    public int getCount() {
        return busList.size();
    }

    public Bus getItem(int position) {
        return busList.get(position);
    }

    public long getItemId(int position) {
        return busList.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        BusHolder holder = new BusHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fragment_search_list, null);
            // Now we can fill the layout with the right values
            TextView txtBusNum = (TextView) v.findViewById(R.id.txtBusNum);
            TextView txtBusINFO = (TextView) v.findViewById(R.id.txtbusINFO);
            ImageView imgBusIcon = (ImageView) v.findViewById(R.id.imgBusIcon);


            holder.BusNumView = txtBusNum;
            holder.busINFO = txtBusINFO;
            holder.BusICON = imgBusIcon;

            v.setTag(holder);
        } else
            holder = (BusHolder) v.getTag();

        Bus b = busList.get(position);
        holder.BusNumView.setText(b.getBusNum());
        holder.busINFO.setText(b.getBusINFO());
        holder.BusICON.setImageResource(b.getBusICON());

        return v;
    }

    public void resetData() {
        busList = origBusList;
    }

	
	/* *********************************
     * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    @Override
    public Filter getFilter() {
        if (busFilter == null)
            busFilter = new busFilter();

        return busFilter;
    }
	

	
	/*
	 * We create our filter	
	 */

    private static class BusHolder {
        public TextView BusNumView;
        public TextView busINFO;
        public ImageView BusICON;
    }

    private class busFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origBusList;
                results.count = origBusList.size();
            } else {
                // We perform filtering operation
                List<Bus> nbusList = new ArrayList<Bus>();

                for (Bus b : busList) {
                    if (b.getBusNum().startsWith(constraint.toString().toUpperCase()))
                        nbusList.add(b);
                }

                results.values = nbusList;
                results.count = nbusList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                busList = (List<Bus>) results.values;
                notifyDataSetChanged();
            }

        }

    }
}
