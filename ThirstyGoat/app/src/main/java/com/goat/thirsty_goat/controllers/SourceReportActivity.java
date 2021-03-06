//package com.goat.thirsty_goat.controllers;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.auth0.android.Auth0;
//import com.auth0.android.authentication.AuthenticationAPIClient;
//import com.auth0.android.authentication.AuthenticationException;
//import com.auth0.android.callback.BaseCallback;
//import com.auth0.android.management.ManagementException;
//import com.auth0.android.management.UsersAPIClient;
//import com.auth0.android.result.UserProfile;
//import com.goat.thirsty_goat.R;
//import com.goat.thirsty_goat.application.App;
//import com.goat.thirsty_goat.models.UserManager;
//
//import java.util.HashMap;
//import java.util.Map;

package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Walker on 2/27/17.
 *
 * This activity is in charge of creating a new source report to the user
 */
public class SourceReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

//    private EditText mUserNameField;
//    private EditText mEmailField;
//    private Spinner mAccounTypeSpinner;
//    private Button mSaveButton;
//    private Button mCancelEditButton;
//    private Button mMapButton;
//    private Auth0 mAuth0;
//    private AuthenticationAPIClient mClient;
//    private UserProfile mUserProfile;

    private static final String TAG = SourceReportActivity.class.getSimpleName();

//    private EditText mLatitudeEditText;
//    private EditText mLongitudeEditText;
    private Spinner mWaterConditionSpinner;
    private Spinner mWaterTypeSpinner;

    private GoogleMap mMap;


    // keeps up with instance data to create the report
    private Location mLocation;
//    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_report);

//        mSaveButton = (Button) findViewById(R.id.save_button);
//        mCancelEditButton = (Button) findViewById(R.id.cancel_edit_button);
//        mMapButton = (Button) findViewById(R.id.map_button);

//        mLatitudeEditText = (EditText) findViewById(R.id.latitude_edit_text);
//        mLongitudeEditText = (EditText) findViewById(R.id.longitude_edit_text);
        mWaterConditionSpinner = (Spinner) findViewById(R.id.water_condition_spinner);
        mWaterTypeSpinner = (Spinner) findViewById(R.id.water_type_spinner);

        ArrayAdapter<SourceCondition> waterConditionAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SourceCondition.values());
        waterConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterConditionSpinner.setAdapter(waterConditionAdapter);

        ArrayAdapter<SourceType> waterTypeAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SourceType.values());
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterTypeSpinner.setAdapter(waterTypeAdapter);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            double lat = extras.getDouble(MapsActivity.LATITUDE_MESSAGE);
            double lng = extras.getDouble(MapsActivity.LONGITUDE_MESSAGE);
            mLocation = new Location(lat, lng);

//            mLatitudeEditText.setText(String.valueOf(mLatitude), TextView.BufferType.EDITABLE);
//            mLongitudeEditText.setText(String.valueOf(mLongitude), TextView.BufferType.EDITABLE);
        }

        // Maps things
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    // for some reason, these aren't updating when clicked
    @ Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "something selected");
//        switch (parent.getId()){
//            case R.id.water_type_spinner:
//                mSourceType = (SourceType) parent.getItemAtPosition(position);
//                break;
//            case R.id.water_condition_spinner:
//                mWaterCondition = (SourceCondition) parent.getItemAtPosition(position);
//                break;
//        }
    }

    // for some reason, this isn't doing anything
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "nothing selected");
//        switch (parent.getId()){
//            case R.id.water_type_spinner:
//                mSourceType = SourceType.BOTTLED;
//                break;
//            case R.id.water_condition_spinner:
//                mWaterCondition = SourceCondition.POTABLE;
//                break;
//        }
    }

    public void onSubmitPressed(View view) {
        Log.d(TAG, "pressed submit in water report");
//        mLatitude = Double.parseDouble(mLatitudeEditText.getText().toString());
//        mLongitude = Double.parseDouble(mLongitudeEditText.getText().toString());

        // Maps things
        LatLng latLng = mMap.getCameraPosition().target;
        mLocation = new Location(latLng);

        SourceType sourceType = (SourceType) mWaterTypeSpinner.getSelectedItem();
        SourceCondition sourceCondition = (SourceCondition) mWaterConditionSpinner.getSelectedItem();

        Log.d(TAG, "long = " + mLocation.getLongitude());
        Log.d(TAG, "lat = " + mLocation.getLatitude());
        Log.d(TAG, "type = " + sourceType.toString());
        Log.d(TAG, "condition = " + sourceCondition.toString());

        ModelFacade mFacade = ModelFacade.getInstance();
        mFacade.addSourceReport(sourceType, sourceCondition, mLocation);

        finish();
    }

    public void onCancelPressed(View view) {
        Log.d(TAG, "pressed cancel in water report");
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())));
    }
}
