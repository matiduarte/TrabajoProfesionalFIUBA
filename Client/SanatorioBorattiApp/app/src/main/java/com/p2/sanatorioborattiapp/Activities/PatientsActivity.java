package com.p2.sanatorioborattiapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.p2.sanatorioborattiapp.Activities.Adapter.UserExpandableListAdapter;
import com.p2.sanatorioborattiapp.Activities.Model.ExpandableListDataPump;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetDoctorPatients;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.HashMap;
import java.util.List;

public class PatientsActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ExpandableListView expandableListView;
    private HashMap<Integer, User> expandableListDetail;
    private UserExpandableListAdapter expandableListAdapter;
    private List<User> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        initializeToolbar();
        initializeNavigationDrawer();
        getPatientsInfo();
    }

    private void getPatientsInfo() {
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        int userId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));

        final Service service = new Service(this);
        User u = new User();
        u.setUserId(userId);

        service.getDoctorPatientsInBackground(u, new GetDoctorPatients(){
            @Override
            public void done(boolean success, List<User> patientsList) {
                if(success) {
                    patients = patientsList;
                    initializeExpandableList();
                }
            }

        });
    }

    private void initializeExpandableList() {
        expandableListView = (ExpandableListView) findViewById(R.id.patients);
        expandableListDetail = ExpandableListDataPump.getData(patients);
        expandableListAdapter = new UserExpandableListAdapter(this, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                expandableListDetail.get(groupPosition);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                expandableListDetail.get(groupPosition);

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Class<?> c;
                if(childPosition == 0){
                    //Treatments
                    c = TreatmentsActivity.class;
                }
                else if(childPosition == 1){
                    //Treatments
                    c = MedicinesActivity.class;
                }
                else if(childPosition == 2){
                    //Treatments
                    c = StudiesActivity.class;
                }
                else{
                    return false;
                }
                User patient = patients.get(groupPosition);

                Intent intent = new Intent(getApplicationContext(), c);
                intent.putExtra("patientId",patient.getUserId());
                intent.putExtra("firstName",patient.getFirstName());
                intent.putExtra("lastName",patient.getLastName());
                startActivity(intent);
                return false;
            }
        });
    }

    private void initializeNavigationDrawer() {
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.patients);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
    }

}