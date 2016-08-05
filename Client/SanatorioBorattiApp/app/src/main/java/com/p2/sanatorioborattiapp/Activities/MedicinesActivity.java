package com.p2.sanatorioborattiapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.p2.sanatorioborattiapp.Activities.Adapter.MedicinesListAdapter;
import com.p2.sanatorioborattiapp.Activities.Animation.DividerItemDecoration;
import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.DeleteUserMedicine;
import com.p2.sanatorioborattiapp.Interfaces.GetUserMedicines;
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
    private List<Medicine> medicinesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        getPatientInfo();

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloatingButton();

        getMedicinesInfo();

    }

    private void initializeMedicinesList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        registerForContextMenu(recyclerView);

        mAdapter = new MedicinesListAdapter(medicinesList);
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
                showNewMedicationtDialog();
            }
        });
    }

    private void showNewMedicationtDialog() {
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

    private void getMedicinesInfo() {
        final Service service = new Service(this);
        User u = new User();
        u.setUserId(patientId);

        service.getUserMedicinesInBackground(u, new GetUserMedicines(){
            @Override
            public void done(boolean success, List<Medicine> medicines) {
                if(success) {
                    medicinesList = medicines;
                    initializeMedicinesList();
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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
            case R.id.action_delete_medicine:
                deleteMedicine(medicinesList.get(item.getOrder()));
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
                    getMedicinesInfo();
                }
            }

        });
    }
}

