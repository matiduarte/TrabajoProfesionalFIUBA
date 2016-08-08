package com.p2.sanatorioborattiapp.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.p2.sanatorioborattiapp.Activities.Adapter.StudiesListAdapter;
import com.p2.sanatorioborattiapp.Activities.Animation.DividerItemDecoration;
import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.Study;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetAllStudies;
import com.p2.sanatorioborattiapp.Interfaces.GetPatientStudies;
import com.p2.sanatorioborattiapp.Interfaces.SaveUserMedicine;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.HashMap;
import java.util.List;

public class StudiesActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int patientId;
    private Integer doctorId;
    private String patientFirstName;
    private String patientLastName;
    private RecyclerView recyclerView;
    private StudiesListAdapter mAdapter;
    private List<Study> userStudiesList;
    private List<Study> allStudies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studies);

        getPatientInfo();

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloatingButton();

        getStudyTypesInfo();
        getUserStudiesInfo();

    }

    private void initializeMedicinesList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        registerForContextMenu(recyclerView);

        mAdapter = new StudiesListAdapter(userStudiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    private void initializeFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMedicinesDialog();
            }
        });
    }


    private void showMedicinesDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(StudiesActivity.this);
        builderSingle.setIcon(R.drawable.icon_plus_big);
        builderSingle.setTitle(R.string.add_study_chose);



        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudiesActivity.this, android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i< allStudies.size(); i++){
            arrayAdapter.add(allStudies.get(i).getName());
        }

        builderSingle.setNegativeButton(
                "Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        final int medicineId = (int) allStudies.get(which).getId();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                StudiesActivity.this);
                        builderInner.setView(R.layout.dialog_new_medicine_observations);
                        builderInner.setTitle(strName);
                        builderInner.setPositiveButton(
                                "Agregar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        final EditText editText = ((EditText) ((AlertDialog) dialog).findViewById(R.id.meidicine_observations_text));
                                        String observations = editText.getText().toString();
                                        Medicine newUserMedicine = new Medicine();
                                        newUserMedicine.setMedicineId(medicineId);
                                        newUserMedicine.setObservations(observations);
                                        newUserMedicine.setDoctorId(doctorId);
                                        newUserMedicine.setPatientId(patientId);
                                        dialog.dismiss();
                                        callSaveUserMedicine(newUserMedicine);
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }

    private void callSaveUserMedicine(Medicine newUserMedicine) {
        final Service service = new Service(getApplicationContext());
        service.saveUserMedicineInBackground(newUserMedicine, new SaveUserMedicine(){
            @Override
            public void done(boolean success) {
                if(success){
                    getUserStudiesInfo();
                }else{
                    //TODO: mostrar toast de error
                }
            }

        });
    }

    private void getPatientInfo() {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            patientId = extras.getInt("patientId");
            patientFirstName = extras.getString("firstName");
            patientLastName = extras.getString("lastName");
        }

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        doctorId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));
    }

    private void getUserStudiesInfo() {
        final Service service = new Service(this);
        User u = new User();
        u.setUserId(patientId);

        service.getPatientStudiesInBackground(u, new GetPatientStudies(){
            @Override
            public void done(boolean success, List<Study> studies) {
                if(success) {
                    userStudiesList = studies;
                    initializeMedicinesList();
                }
            }

        });
    }

    private void getStudyTypesInfo() {
        final Service service = new Service(this);

        service.getAllStudiesInBackground(new GetAllStudies(){
            @Override
            public void done(boolean success, List<Study> studies) {
                if(success) {
                    allStudies = studies;
                }
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
        mToolbar.setTitle(patientFirstName + " " + patientLastName);
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

