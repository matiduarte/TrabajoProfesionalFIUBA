package com.p2.sanatorioborattiapp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.p2.sanatorioborattiapp.Entities.Bed;
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
    private Bitmap floorImage;
    int totalFloorNumber;
    int currentFloor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_diagram);

        initializeToolbar();
        initializeNavigationDrawer();
        initializeFloors();

        getFloorInfo(DEFAULT_FLOOR);

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

        for (Bed b : beds) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/10,height/10);

            TextView view = new TextView(this);

            view.setId(b.getId());
            final int id_ = view.getId();
            //view.setX(b.getX() * width / LOGIC_WIDTH - view.getWidth());
           // view.setY(b.getY() * height / LOGIC_HEIGHT);
            view.setBackgroundResource(R.drawable.beds);

            view.setText(b.getPatient().getCompleteName());
            bedFrame.addView(view, params);
            TextView view1 = (TextView) findViewById(id_);
            view1.setX(b.getX() * width / LOGIC_WIDTH - params.width / 2);
            view1.setY(b.getY() * height / LOGIC_HEIGHT - params.height / 2);
            int x = b.getX() * width / LOGIC_WIDTH - params.width / 2;
            int y = b.getY() * height / LOGIC_HEIGHT - params.height / 2;
            int viewWidth = view1.getWidth();
            int viewHeight = view1.getHeight();
            y+=0;
            x+=0;
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
            public void done(boolean success, int total) {
                if (success) {
                    totalFloorNumber = total;
                    displayFloors();
                }
            }
        });
    }

    private void displayFloors() {
        LinearLayout flr = (LinearLayout) findViewById(R.id.floors);

        for (int i = 1; i <= totalFloorNumber; i++) {
            Button btn = new Button(this);
            btn.setText(Integer.toString(i));
            flr.addView(btn);
            final int id = i;
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
