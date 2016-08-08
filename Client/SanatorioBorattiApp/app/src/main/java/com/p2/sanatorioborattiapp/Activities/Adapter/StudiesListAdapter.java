package com.p2.sanatorioborattiapp.Activities.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.Entities.Study;
import com.p2.sanatorioborattiapp.R;

import java.util.List;

public class StudiesListAdapter extends RecyclerView.Adapter<StudiesListAdapter.MyViewHolder> {

    private List<Study> studiesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, doctorName, priority;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            priority = (TextView) view.findViewById(R.id.priority);
            doctorName = (TextView) view.findViewById(R.id.doctor_name);
        }
    }


    public StudiesListAdapter(List<Study> studiesList) {
        this.studiesList = studiesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_study_row, parent, false);
        itemView.setLongClickable(true);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Study study = studiesList.get(position);
        holder.name.setText(study.getType());
        holder.priority.setText(String.valueOf(study.getPriority()));
        holder.doctorName.setText(study.getDoctorName());
    }

    @Override
    public int getItemCount() {
        return studiesList.size();
    }
}

