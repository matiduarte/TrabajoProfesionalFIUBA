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
import android.widget.Toast;

import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetUserProfile;
import com.p2.sanatorioborattiapp.Interfaces.SaveUserProfile;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ImageView profilePicture;
    private EditText lastNameEditText;
    private TextView latNameText;
    private EditText firstNameEditText;
    private TextView firstNameText;

    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        latNameText = (TextView) findViewById(R.id.lastNameText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        firstNameText = (TextView) findViewById(R.id.firstNameText);

        getUserInfo();

        initializeToolbar();
        initializeNavigationDrawer();

    }

    private void getUserInfo() {
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        int userId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));

        final Service service = new Service(this);
        User u = new User();
        u.setUserId(userId);

        service.getUserProfileInBackground(u, new GetUserProfile(){
            @Override
            public void done(boolean success, User user) {
                if(success) {
                    lastNameEditText.setText(user.getLastName());
                    latNameText.setText(user.getLastName());
                    firstNameEditText.setText(user.getFirstName());
                    firstNameText.setText(user.getFirstName());
                }
            }

        });
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
        lastNameEditText.setVisibility(View.VISIBLE);
        latNameText.setVisibility(View.INVISIBLE);
        lastNameEditText.requestFocus();
    }

    public void editFirstName(View v){
        firstNameEditText.setVisibility(View.VISIBLE);
        firstNameText.setVisibility(View.INVISIBLE);
        firstNameEditText.requestFocus();
    }

    public void saveProfile(View v){
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        int userId =  Integer.valueOf(userDetails.get(SessionManager.KEY_ID));

        final Service service = new Service(getApplicationContext());
        User u = new User();
        u.setUserId(userId);
        u.setFirstName(firstNameEditText.getText().toString());
        u.setLastName(lastNameEditText.getText().toString());
        service.saveUserProfileInBackground(u, new SaveUserProfile(){
            @Override
            public void done(boolean success) {
                if(success){
                    Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                    firstNameEditText.setVisibility(View.INVISIBLE);
                    firstNameText.setVisibility(View.VISIBLE);
                    lastNameEditText.setVisibility(View.INVISIBLE);
                    latNameText.setVisibility(View.VISIBLE);

                    latNameText.setText(lastNameEditText.getText().toString());
                    firstNameText.setText(firstNameEditText.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Ocurrió un error al actulizar el perfil", Toast.LENGTH_SHORT).show();

                }
            }

        });
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

