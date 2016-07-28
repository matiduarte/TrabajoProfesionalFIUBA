package com.p2.sanatorioborattiapp.Activities.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<Integer, User> expandableListDetail;
    private List<String> listOptions;

    public UserExpandableListAdapter(Context context,  HashMap<Integer, User> expandableListDetail) {
        listOptions = new ArrayList<String>();
        listOptions.add("Tratamiento");
        listOptions.add("Medicacion");
        listOptions.add("Estudios");

        this.context = context;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return listOptions.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_user_sub_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.list_user_item);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return listOptions.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListDetail.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListDetail.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        User user = (User) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_user_row, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.patient_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(user.getCompleteName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}