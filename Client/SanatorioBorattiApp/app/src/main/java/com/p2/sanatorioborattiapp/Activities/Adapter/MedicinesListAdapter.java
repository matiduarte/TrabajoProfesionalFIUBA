package com.p2.sanatorioborattiapp.Activities.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.R;

import java.util.List;

public class MedicinesListAdapter extends RecyclerView.Adapter<MedicinesListAdapter.MyViewHolder> {

    private List<Medicine> medicineList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView name, doctorName, observations;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            observations = (TextView) view.findViewById(R.id.observations);
            doctorName = (TextView) view.findViewById(R.id.doctor_name);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 1, getAdapterPosition(), R.string.action_delete_medicine);
        }
    }


    public MedicinesListAdapter(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_medicine_row, parent, false);
        itemView.setLongClickable(true);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.name.setText(medicine.getName());
        holder.observations.setText(medicine.getObservations());
        holder.doctorName.setText(medicine.getDoctorName());
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }
}

