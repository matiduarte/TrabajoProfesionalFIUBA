package com.p2.sanatorioborattiapp.Activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.p2.sanatorioborattiapp.Entities.Bed;
import com.p2.sanatorioborattiapp.Interfaces.GetFloors;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.List;

/**
 * Created by fran on 12/08/16.
 */
public class BedDiagramActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private int DEFAULT_FLOOR = 1;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private List<Bed> beds;
    int totalFloorNumber;
    int currentFloor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_diagram);

        initializeToolbar();
        initializeNavigationDrawer();

        getFloorInfo(DEFAULT_FLOOR);
    }

    private void getFloorInfo(int floor) {
        final Service service = new Service(this);
        service.getFloors(floor,new GetFloors() {
            @Override
            public void done(boolean success, List<Bed> bedList, int total, int current) {
                if (success) {
                    currentFloor = current;
                    beds = bedList;
                    totalFloorNumber = total;
                    display();
                }
            }
        });
    }

    private void display() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        FrameLayout fr = (FrameLayout) findViewById(R.id.container_body);
        fr.removeAllViews();
        for (Bed b : beds) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/10,height/10);

            ImageButton btn = new ImageButton(this);
            btn.setId(b.getId());
            final int id_ = btn.getId();
            btn.setX(b.getX());
            btn.setY(b.getY());
            btn.setBackgroundResource(R.drawable.beds);
            fr.addView(btn, params);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
        LinearLayout flr = (LinearLayout) findViewById(R.id.LinearLayout02);
        flr.removeAllViews();
        for (int i = 1; i <= totalFloorNumber; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/10, LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            //btn.setX((i - 1) * width / 10);
            btn.setText(Integer.toString(i));
            //flr.addView(btn,params);
            flr.addView(btn);
            final int id = i;
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    getFloorInfo(id);
                }
            });
        }

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
