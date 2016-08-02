package com.p2.sanatorioborattiapp.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.p2.sanatorioborattiapp.Activities.Adapter.TreatmentListAdapter;
import com.p2.sanatorioborattiapp.Activities.Animation.ResizeAnimation;
import com.p2.sanatorioborattiapp.Activities.Model.TreatmentListItem;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.Treatment;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetUserTreatments;
import com.p2.sanatorioborattiapp.Interfaces.SaveTreatment;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreatmentsActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ListView listView;
    private ArrayList<TreatmentListItem> treatmentListItems;
    private TreatmentListAdapter adapter;
    private List<Treatment> treatments;
    private int patientId;
    private Integer doctorId;
    private String patientFirstName;
    private String patientLastName;

    //Expand Configuration
    private final int COLLAPSED_HEIGHT_1 = 250, COLLAPSED_HEIGHT_2 = 200,

            COLLAPSED_HEIGHT_3 = 250;
    private final int EXPANDED_HEIGHT_1 = 300, EXPANDED_HEIGHT_2 = 300,

            EXPANDED_HEIGHT_3 = 350, EXPANDED_HEIGHT_4 = 400;
    private boolean accordion = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        getPatientInfo();

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloatingButton();

        getTreatmentsInfo();
    }

    private void initializeFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewTreatmentDialog();
            }
        });
    }

    private void showNewTreatmentDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(TreatmentsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_new_treatment, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TreatmentsActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.treatment_text);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newTreatmentText = editText.getText().toString();
                        if (!newTreatmentText.equals("")) {
                            final Service service = new Service(getApplicationContext());
                            Treatment treatment = new Treatment();
                            treatment.setPatientId(patientId);
                            treatment.setDoctorId(doctorId);
                            treatment.setObservations(newTreatmentText);
                            service.saveTreatmentInBackground(treatment, new SaveTreatment(){
                                @Override
                                public void done(boolean success) {
                                    if(success){
                                        getTreatmentsInfo();
                                    }else{
                                        //TODO: mostrar toast de error
                                    }
                                }

                            });
                        }
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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

    private void getTreatmentsInfo() {
        final Service service = new Service(this);
        User u = new User();
        u.setUserId(patientId);

        service.getUserTreatmentsInBackground(u, new GetUserTreatments(){
            @Override
            public void done(boolean success, List<Treatment> treatmentsList) {
                if(success) {
                    treatments = treatmentsList;
                    initializeTreatments();
                }
            }

        });
    }

    private void initializeTreatments() {
        listView = (ListView) findViewById(R.id.list_treatments);

        treatmentListItems = new ArrayList<TreatmentListItem>();
        for (int i=0; i<treatments.size(); i++){
            //TODO: calcular el height segun la cantidad de letras
            treatmentListItems
                    .add(new TreatmentListItem(
                            treatments.get(i),
                            COLLAPSED_HEIGHT_1, COLLAPSED_HEIGHT_1,
                            EXPANDED_HEIGHT_1));
        }

        adapter = new TreatmentListAdapter(this, R.layout.list_treatment_rows, treatmentListItems);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                toggle(view, position);
            }
        });
    }

    private void toggle(View view, final int position) {
        TreatmentListItem treatmentListItem = treatmentListItems.get(position);
        treatmentListItem.getHolder().setTextViewWrap((LinearLayout) view);

        int fromHeight = 0;
        int toHeight = 0;

        if (treatmentListItem.isOpen()) {
            fromHeight = treatmentListItem.getExpandedHeight();
            toHeight = treatmentListItem.getCollapsedHeight();
        } else {
            fromHeight = treatmentListItem.getCollapsedHeight();
            toHeight = treatmentListItem.getExpandedHeight();

            // This closes all item before the selected one opens
            if (accordion) {
                closeAll();
            }
        }

        toggleAnimation(treatmentListItem, position, fromHeight, toHeight, true);
    }

    private void closeAll() {
        int i = 0;
        for (TreatmentListItem treatmentListItem : treatmentListItems) {
            if (treatmentListItem.isOpen()) {
                toggleAnimation(treatmentListItem, i, treatmentListItem.getExpandedHeight(),
                        treatmentListItem.getCollapsedHeight(), false);
            }
            i++;
        }
    }

    private void toggleAnimation(final TreatmentListItem treatmentListItem, final int position,
                                 final int fromHeight, final int toHeight, final boolean goToItem) {

        ResizeAnimation resizeAnimation = new ResizeAnimation(adapter,
                treatmentListItem, 0, fromHeight, 0, toHeight);
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                treatmentListItem.setOpen(!treatmentListItem.isOpen());
                treatmentListItem.setDrawable(treatmentListItem.isOpen() ? R.drawable.up
                        : R.drawable.down);
                treatmentListItem.setCurrentHeight(toHeight);
                adapter.notifyDataSetChanged();

                if (goToItem)
                    goToItem(position);
            }
        });

        treatmentListItem.getHolder().getTextViewWrap().startAnimation(resizeAnimation);
    }

    private void goToItem(final int position) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    listView.smoothScrollToPosition(position);
                } catch (Exception e) {
                    listView.setSelection(position);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
    }

}