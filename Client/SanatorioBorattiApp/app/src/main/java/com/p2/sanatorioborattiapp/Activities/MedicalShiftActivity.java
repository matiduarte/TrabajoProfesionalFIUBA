package com.p2.sanatorioborattiapp.Activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.p2.sanatorioborattiapp.Entities.MedicalShift;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Interfaces.GetShifts;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fran on 28/08/16.
 */
public class MedicalShiftActivity  extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    private int userId;
    private List<MedicalShift> shifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_shift);

        initializeToolbar();
        initializeNavigationDrawer();
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        userId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));
        getDateShifts("14-3-2016");

    }

    private void getDateShifts(String date) {
        final Service service = new Service(this);
        service.getShifts(userId,date,new GetShifts() {
            @Override
            public void done(boolean success, List<MedicalShift> medicalShifts) {
                if (success) {
                    shifts = medicalShifts;
                    MedicalShift.orderShifts(shifts);
                    display();
                }
            }
        });
    }

    private void display() {
        String[] shiftText = new String[shifts.size()];
        int i = 0;
        for (MedicalShift shift : shifts) {
            String s = "Time: " + shift.getTime() + "\nPatient: " + shift.getPatientName();
            shiftText[i] = s;
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_shift, shiftText);

        ListView listView = (ListView) findViewById(R.id.shifts);
        listView.setAdapter(adapter);
    }


    private void initializeNavigationDrawer() {
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.bed_diagram);
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
