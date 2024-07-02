package nlu.hmuaf.android_bookapp.api_google_map;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
    private Toolbar toolbar;
    private PlacesClient placesClient;
    private EditText editText;
    private ImageButton searchButton;
    private String API_KEY = "";
    private String detailAddress = "";
    private Button buttonConfirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_location);
        toolbar = findViewById(R.id.toolbarGoogleMap);
        setSupportActionBar(toolbar);  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        API_KEY = MyUtils.getApiKey(getApplicationContext());
        // Initialize Places API
        Places.initialize(getApplicationContext(), API_KEY);
        placesClient = Places.createClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        editText = findViewById(R.id.editTextText);
        searchButton = findViewById(R.id.imageButton);
        buttonConfirm = findViewById(R.id.buttonConfirm);



        searchButton.setOnClickListener(view -> {
            String location = editText.getText().toString();
            if (!location.isEmpty()) {
                searchLocation(location);
            } else {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            }
        });
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("detailAddress", detailAddress);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        // Set a default location
        LatLng location = new LatLng(10.8713, 106.7918);
        map.addMarker(new MarkerOptions().position(location).title("Trường đại học Nông Lâm tp HCM"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

        // Set a listener for map clicks
        map.setOnMapClickListener(latLng -> {
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng));

            // Fetch address for the selected location
            getAddress(latLng);
        });
    }

    private void getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = address.getAddressLine(0); // Detailed address
                Toast.makeText(GoogleMapActivity.this, addressText, Toast.LENGTH_LONG).show();
                setDetailAddress(addressText);
            } else {
                Toast.makeText(GoogleMapActivity.this, "No address found", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(GoogleMapActivity.this, "Failed to get address", Toast.LENGTH_LONG).show();
        }
    }

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                Toast.makeText(this, address.getAddressLine(0), Toast.LENGTH_LONG).show();
                setDetailAddress(address.getAddressLine(0));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to find location", Toast.LENGTH_LONG).show();
        }
    }
    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
