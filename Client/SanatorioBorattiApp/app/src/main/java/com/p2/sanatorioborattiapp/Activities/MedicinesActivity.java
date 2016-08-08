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

import com.p2.sanatorioborattiapp.Activities.Adapter.MedicinesListAdapter;
import com.p2.sanatorioborattiapp.Activities.Animation.DividerItemDecoration;
import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.DeleteUserMedicine;
import com.p2.sanatorioborattiapp.Interfaces.GetAllMedicines;
import com.p2.sanatorioborattiapp.Interfaces.GetUserMedicines;
import com.p2.sanatorioborattiapp.Interfaces.SaveUserMedicine;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.HashMap;
import java.util.List;

public class MedicinesActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int patientId;
    private Integer doctorId;
    private String patientFirstName;
    private String patientLastName;
    private RecyclerView recyclerView;
    private MedicinesListAdapter mAdapter;
    private List<Medicine> userMedicinesList;
    private List<Medicine> allMedicines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        getPatientInfo();

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloatingButton();

        getAllMedicinesInfo();
        getUserMedicinesInfo();

    }

    private void initializeMedicinesList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        registerForContextMenu(recyclerView);

        mAdapter = new MedicinesListAdapter(userMedicinesList);
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
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MedicinesActivity.this);
        builderSingle.setIcon(R.drawable.icon_plus_big);
        builderSingle.setTitle(R.string.add_medicine_chose);



        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MedicinesActivity.this, android.R.layout.select_dialog_singlechoice);

        for (int i=0; i<allMedicines.size(); i++){
            arrayAdapter.add(allMedicines.get(i).getName());
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
                        final int medicineId = (int)allMedicines.get(which).getId();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                MedicinesActivity.this);
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
                    getUserMedicinesInfo();
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

    private void getUserMedicinesInfo() {
        final Service service = new Service(this);
        User u = new User();
        u.setUserId(patientId);

        service.getUserMedicinesInBackground(u, new GetUserMedicines(){
            @Override
            public void done(boolean success, List<Medicine> medicines) {
                if(success) {
                    userMedicinesList = medicines;
                    initializeMedicinesList();
                }
            }

        });
    }

    private void getAllMedicinesInfo() {
        final Service service = new Service(this);

        service.getAllMedicinesInBackground(new GetAllMedicines(){
            @Override
            public void done(boolean success, List<Medicine> medicines) {
                if(success) {
                    allMedicines = medicines;
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                deleteMedicine(userMedicinesList.get(item.getOrder()));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteMedicine(Medicine medicine) {
        final Service service = new Service(this);

        service.deleteUserMedicineInBackground(medicine, new DeleteUserMedicine(){
            @Override
            public void done(boolean success) {
                if(success) {
                    getUserMedicinesInfo();
                }
            }

        });
    }
}

