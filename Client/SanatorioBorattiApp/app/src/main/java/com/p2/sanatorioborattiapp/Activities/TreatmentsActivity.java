package com.p2.sanatorioborattiapp.Activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.p2.sanatorioborattiapp.Activities.Adapter.ListAdapter;
import com.p2.sanatorioborattiapp.Activities.Animation.ResizeAnimation;
import com.p2.sanatorioborattiapp.Activities.Model.ListItem;
import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.Treatment;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetUserTreatments;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreatmentsActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ListView listView;
    private ArrayList<ListItem> listItems;
    private ListAdapter adapter;
    private List<Treatment> treatments;
    private int patientId;
    private String patientFirstName;
    private String patientLastName;

    //Expand Configuration
    private final int COLLAPSED_HEIGHT_1 = 150, COLLAPSED_HEIGHT_2 = 200,

            COLLAPSED_HEIGHT_3 = 250;
    private final int EXPANDED_HEIGHT_1 = 250, EXPANDED_HEIGHT_2 = 300,

            EXPANDED_HEIGHT_3 = 350, EXPANDED_HEIGHT_4 = 400;
    private boolean accordion = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        initializeToolbar();
        initializeNavigationDrawer();
        getPatientInfo();
        getTreatmentsInfo();
    }

    private void getPatientInfo() {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            patientId = extras.getInt("patientId");
            patientFirstName = extras.getString("firstName");
            patientLastName = extras.getString("lastName");
        }
    }

    private void getTreatmentsInfo() {
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

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

        listItems = new ArrayList<ListItem>();
        for (int i=0; i<treatments.size(); i++){
            listItems
                    .add(new ListItem(
                            treatments.get(i).getObservations(),
                            COLLAPSED_HEIGHT_1, COLLAPSED_HEIGHT_1,
                            EXPANDED_HEIGHT_1));
        }

        adapter = new ListAdapter(this, R.layout.list_treatments_rows, listItems);


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
        ListItem listItem = listItems.get(position);
        listItem.getHolder().setTextViewWrap((LinearLayout) view);

        int fromHeight = 0;
        int toHeight = 0;

        if (listItem.isOpen()) {
            fromHeight = listItem.getExpandedHeight();
            toHeight = listItem.getCollapsedHeight();
        } else {
            fromHeight = listItem.getCollapsedHeight();
            toHeight = listItem.getExpandedHeight();

            // This closes all item before the selected one opens
            if (accordion) {
                closeAll();
            }
        }

        toggleAnimation(listItem, position, fromHeight, toHeight, true);
    }

    private void closeAll() {
        int i = 0;
        for (ListItem listItem : listItems) {
            if (listItem.isOpen()) {
                toggleAnimation(listItem, i, listItem.getExpandedHeight(),
                        listItem.getCollapsedHeight(), false);
            }
            i++;
        }
    }

    private void toggleAnimation(final ListItem listItem, final int position,
                                 final int fromHeight, final int toHeight, final boolean goToItem) {

        ResizeAnimation resizeAnimation = new ResizeAnimation(adapter,
                listItem, 0, fromHeight, 0, toHeight);
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listItem.setOpen(!listItem.isOpen());
                listItem.setDrawable(listItem.isOpen() ? R.drawable.up
                        : R.drawable.down);
                listItem.setCurrentHeight(toHeight);
                adapter.notifyDataSetChanged();

                if (goToItem)
                    goToItem(position);
            }
        });

        listItem.getHolder().getTextViewWrap().startAnimation(resizeAnimation);
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
        mToolbar.setTitle(R.string.treatments);
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