package com.p2.sanatorioborattiapp.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.Activities.Holder.TreatmentListViewHolder;
import com.p2.sanatorioborattiapp.Activities.Model.TreatmentListItem;
import com.p2.sanatorioborattiapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by quimey on 01/08/16.
 */

public class TreatmentListAdapter extends ArrayAdapter<TreatmentListItem> {
    private ArrayList<TreatmentListItem> treatmentListItems;
    private Context context;

    public TreatmentListAdapter(Context context, int textViewResourceId,
                                ArrayList<TreatmentListItem> treatmentListItems) {
        super(context, textViewResourceId, treatmentListItems);
        this.treatmentListItems = treatmentListItems;
        this.context = context;
    }

    @Override
    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent) {
        TreatmentListViewHolder holder = null;
        TreatmentListItem treatmentListItem = treatmentListItems.get(position);

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_treatment_rows, null);

            LinearLayout textViewWrap = (LinearLayout) convertView
                    .findViewById(R.id.text_wrap);
            TextView doctor_name = (TextView) convertView.findViewById(R.id.doctor_name);
            TextView dateText = (TextView) convertView.findViewById(R.id.date);
            TextView text = (TextView) convertView.findViewById(R.id.observations);

            Date date = new Date(treatmentListItem.getTreatment().getDate() * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

            dateText.setText(sdf.format(date));
            doctor_name.setText(treatmentListItem.getTreatment().getDoctorName());

            holder = new TreatmentListViewHolder(textViewWrap, text);
        } else {
            holder = (TreatmentListViewHolder) convertView.getTag();
        }

        holder.getTextView().setText(treatmentListItem.getTreatment().getObservations());

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
                treatmentListItem.getCurrentHeight());
        holder.getTextViewWrap().setLayoutParams(layoutParams);

//        holder.getTextView().setCompoundDrawablesWithIntrinsicBounds(
//                treatmentListItem.getDrawable(), 0, 0, 0);

        convertView.setTag(holder);

        treatmentListItem.setHolder(holder);

        return convertView;
    }

}