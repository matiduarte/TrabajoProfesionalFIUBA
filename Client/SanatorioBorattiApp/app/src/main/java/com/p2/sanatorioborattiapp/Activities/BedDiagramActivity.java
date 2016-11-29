package com.p2.sanatorioborattiapp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.p2.sanatorioborattiapp.Entities.Bed;
import com.p2.sanatorioborattiapp.Entities.Floor;
import com.p2.sanatorioborattiapp.Interfaces.GetBeds;
import com.p2.sanatorioborattiapp.Interfaces.GetFloors;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.util.List;

/**
 * Created by fran on 12/08/16.
 */
public class BedDiagramActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private int DEFAULT_FLOOR = 1;
    private int LOGIC_WIDTH = 100;
    private int LOGIC_HEIGHT= 100;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private List<Bed> beds;
    private List<Floor> floors;
    private Bitmap floorImage;
    int currentFloor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_diagram);

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloors();

        //getFloorInfo(DEFAULT_FLOOR);
    }

    private void getFloorInfo(int floor) {
        final Service service = new Service(this);
        service.getBeds(floor,new GetBeds() {
            @Override
            public void done(boolean success, List<Bed> bedList, int current, String image) {
                if (success) {
                    currentFloor = current;
                    beds = bedList;
                    floorImage = stringToBitmap(image);
                    display();
                }
            }
        });
    }

    private Bitmap stringToBitmap(String pictureString){
        try {
            byte [] encodeByte = Base64.decode(pictureString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void display() {

        FrameLayout bedFrame = (FrameLayout) findViewById(R.id.container_body);
        int width = bedFrame.getWidth();
        int height = bedFrame.getHeight();
        bedFrame.removeAllViews();
        bedFrame.setBackground(new BitmapDrawable(getResources(),floorImage));
        int lastId = 1;
        for (Bed b : beds) {
            float posX = b.getX() * width / LOGIC_WIDTH;
            float posY = b.getY() * height / LOGIC_HEIGHT;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/10,height/10);

            //TextView view = new TextView(this);
            ImageButton view = new ImageButton(this);
            view.setId(lastId++);
            final int id_ = view.getId();
            view.setBackgroundResource(R.drawable.beds);
            //view.setText(b.getPatient().getCompleteName());
            bedFrame.addView(view, params);
            ImageButton view1 = (ImageButton) findViewById(id_);
            view1.setX(posX);
            view1.setY(posY);
            final String patientName = b.getPatient().getCompleteName();
            if (b.getPatient().getFirstName() != null && !b.getPatient().getFirstName().equals("")) {
                view.setBackgroundResource(R.drawable.beds_blue);
                view.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                            Toast.makeText(view.getContext(),
                                                            patientName, Toast.LENGTH_SHORT)
                                                            .show();
                                        }
                                });
            }
            //LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width,height/10);

            //TextView viewName = new TextView(this);

            //viewName.setId(lastId++);
            //final int idName = viewName.getId();
            //view.setBackgroundResource(R.drawable.beds);
            //viewName.setText(b.getPatient().getLastName());
            //bedFrame.addView(viewName, params2);
            //TextView view2 = (TextView) findViewById(idName);
            //int anchoCama = view1.getWidth();
            //int anchoNombre = view2.getWidth();
            //int dif = (anchoNombre - anchoCama) / 2;

            //view2.setX(posX - dif);
            //view2.setY(posY - 5);
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

    private void initializeFloors() {
        final Service service = new Service(this);
        service.getFloors(new GetFloors() {
            @Override
            public void done(boolean success, List<Floor> floorList) {
                if (success) {
                    floors = floorList;
                    displayFloors();
                }
            }
        });
    }

    private void displayFloors() {
        LinearLayout flr = (LinearLayout) findViewById(R.id.floors);

        for (Floor floor : floors) {
            Button btn = new Button(this);
            btn.setText(floor.getName());
            flr.addView(btn);
            final int id = floor.getId();
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    getFloorInfo(id);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
    }

}
