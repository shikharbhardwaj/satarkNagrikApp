package gov.assam.sixsigma.satarknagrik;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

class Crime1 {
    //    Variables
    private Calendar dateTime;
    private String crimeType;
    private double latitude;
    private double longitude;
    private String description;

    //    Method to return string that would
    public String getCrimeInfo() {
        return "Date: " + dateTime.get(dateTime.DATE)
                + "-" + dateTime.get(dateTime.MONTH)
                + "-" + dateTime.get(dateTime.YEAR)

                + "\n"

                + "Time: " + dateTime.get(dateTime.HOUR_OF_DAY)
                + ":" + dateTime.get(dateTime.MINUTE)
                + ":" + dateTime.get(dateTime.SECOND)

                + "\n"

                + "Type: " + crimeType;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

/*
    String getCrimeType() { return crimeType; }

    Calendar getDateTime() { return dateTime; }

    String getDescription() { return description; }
*/

    //  Constructor
    Crime1(Calendar dateTime, String crimeType, double latitude, double longitude) {
        this.dateTime = dateTime;
        this.crimeType = crimeType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }
}

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private LatLngBounds ASSAM = new LatLngBounds(new LatLng(24.0,89.0), new LatLng(28.0,96.0));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("crimes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String c_lat = ds.child("c_lat").getValue(String.class);;
                    String c_lon = ds.child("c_lon").getValue(String.class);;
                    Crime name = ds.child("name").getValue(String.class);
                    Log.d("TAG", name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Calendar dateTime = Calendar.getInstance();

//        Make a Crime object array
        Crime1 crimes[] = new Crime1[]{
                new Crime1(dateTime, "Eve Teasing", 26.1433, 91.7898),
                new Crime1(dateTime, "Theft", 26.7041, 91.1025),
                new Crime1(dateTime, "Garbage Burning", 26.1945, 91.0362)
        };

//        Iterate over the crimes
//        Add markers and tags
        for (Crime1 crime : crimes) {
            LatLng place = new LatLng(crime.getLatitude(), crime.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title("View Details"));
            marker.setTag(crime.getCrimeInfo());
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ASSAM.getCenter(), 6.0f));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String crimeInfo = (String)marker.getTag();

        Toast.makeText(this, crimeInfo,
                Toast.LENGTH_LONG).show();
    }
}
