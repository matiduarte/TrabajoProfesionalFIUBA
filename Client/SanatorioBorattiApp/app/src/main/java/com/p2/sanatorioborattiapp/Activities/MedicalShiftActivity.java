package com.p2.sanatorioborattiapp.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.Entities.MedicalShift;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Interfaces.GetShifts;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.Calendar;
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

    private int mYear;
    private int mMonth;
    private int mDay;

    private StringBuilder myDate;
    private TextView mDateDisplay;
    private Button mPickDate;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_shift);

        initializeToolbar();
        initializeNavigationDrawer();
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        userId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));
        //getDateShifts("14-3-2016");

        mDateDisplay = (TextView) findViewById(R.id.showMyDate);
        mPickDate = (Button) findViewById(R.id.myDatePickerButton);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        setDate();
        // display the current date
        getDateShifts(myDate.toString());
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

    private void setDate() {
        myDate =  new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("-")
                .append(mMonth + 1).append("-")
                .append(mYear);
    }

    private void display() {
        this.mDateDisplay.setText(myDate);
        String[] shiftText = new String[shifts.size()];
        int i = 0;
        for (MedicalShift shift : shifts) {
            String s = "Hora: " + shift.getTime() + "\nPaciente: " + shift.getPatientName();
            shiftText[i] = s;
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_shift, shiftText);

        ListView listView = (ListView) findViewById(R.id.shifts);
        listView.setAdapter(adapter);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    //display();
                    setDate();
                    getDateShifts(myDate.toString());
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
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
