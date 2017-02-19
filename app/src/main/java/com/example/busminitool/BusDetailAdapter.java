package com.example.busminitool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BusDetailAdapter extends ArrayAdapter<Bus> implements Filterable {

    private List<Bus> busList;
    private Context context;
    private List<Bus> origBusList;
    private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
    private int selectItem = -1;

    public BusDetailAdapter(List<Bus> busList, Context ctx) {
        super(ctx, R.layout.bus_detail_list, busList);
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
            v = inflater.inflate(R.layout.bus_detail_list, null);
            // Now we can fill the layout with the right values
            TextView txtStationNo = (TextView) v.findViewById(R.id.txtStationNo);
            TextView txtStationName = (TextView) v.findViewById(R.id.txtStationName);
            TextView txtFare = (TextView) v.findViewById(R.id.txtFare);
            TextView txtHalfFare = (TextView) v.findViewById(R.id.txtHalfFare);
            ImageView imgBusIcon = (ImageView) v.findViewById(R.id.imgBusIcon);


            holder.StationNo = txtStationNo;
            holder.StationName = txtStationName;
            holder.Fare = txtFare;
            holder.HalfFare = txtHalfFare;
            holder.BusICON = imgBusIcon;

            v.setTag(holder);

        } else
            holder = (BusHolder) v.getTag();

        Bus b = busList.get(position);
        holder.StationNo.setText(b.getStationNo());
        holder.StationName.setText(b.getStationName());
        holder.Fare.setText(b.getFare());
        holder.HalfFare.setText(b.getHalfFare());
        holder.BusICON.setImageResource(b.getBusICON());
        v.setBackgroundColor(context.getResources().getColor(android.R.color.background_light)); //default color

        if (mSelection.get(position) != null) {
            v.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));// this is a selected position so make it red
        }
        return v;
    }


    public void resetData() {
        busList = origBusList;
    }

	
	/* *********************************
     * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    private static class BusHolder {
        public TextView StationNo;
        public TextView StationName;
        public TextView Fare;
        public ImageView BusICON;
        public TextView HalfFare;
    }
	
	/*
	 * We create our filter	
	 */
	
	
	/*public Filter getFilter() {
		if (busFilter == null)
			busFilter = new busFilter();
		
		return busFilter;
	}*/


	/*private class busFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = origBusList;
				results.count = origBusList.size();
			}
			else {
				// We perform filtering operation
				List<Bus> nbusList = new ArrayList<Bus>();
				
				for (Bus b : busList) {
					if (b.getBusNum().startsWith(constraint.toString()))
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
		
	}*/
}
