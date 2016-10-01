package com.p2.sanatorioborattiapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2.sanatorioborattiapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Integer doctorId;
    private ImageView profilePicture;
    private EditText latNameEditText;
    private TextView latNameText;
    private EditText firstNameEditText;
    private TextView firstNameText;

    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        latNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        latNameText = (TextView) findViewById(R.id.lastNameText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        firstNameText = (TextView) findViewById(R.id.firstNameText);
        //getPatientInfo();

        initializeToolbar();
        initializeNavigationDrawer();

    }


    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Perfil");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    private void initializeNavigationDrawer() {
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    public void editLastName(View v){
        latNameEditText.setVisibility(View.VISIBLE);
        latNameText.setVisibility(View.INVISIBLE);
        latNameEditText.requestFocus();
    }

    public void editFirstName(View v){
        firstNameEditText.setVisibility(View.VISIBLE);
        firstNameText.setVisibility(View.INVISIBLE);
        firstNameEditText.requestFocus();
    }

    public void openGallery(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    profilePicture.setImageBitmap(yourSelectedImage);
                }
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

