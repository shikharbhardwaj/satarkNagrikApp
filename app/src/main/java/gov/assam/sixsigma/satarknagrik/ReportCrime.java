package gov.assam.sixsigma.satarknagrik;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ReportCrime extends AppCompatActivity{

    String desc,crime;
    TimePicker tp;
    Button btn_rpt,btn_loc;
    DatePicker dp;
    RadioGroup rg;
    EditText et_desc;
    RadioButton rb1,rb2,rb3,rb4;
    int id;
    int hour,min;
    int year,month,date;
    double lat,lon;
    private FirebaseAuth auth;
    Calendar calendar = Calendar.getInstance();
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_crime);
        auth = FirebaseAuth.getInstance();

        btn_rpt = (Button)findViewById(R.id.btn_rpt);
        tp = (TimePicker)findViewById(R.id.tp);
        dp = (DatePicker)findViewById(R.id.dp);
        rg = (RadioGroup)findViewById(R.id.rg);
        et_desc = (EditText)findViewById(R.id.et_desc);
        rb1 = (RadioButton)findViewById(R.id.rb1);
        rb2 = (RadioButton)findViewById(R.id.rb2);
        rb3 = (RadioButton)findViewById(R.id.rb3);
        rb4 = (RadioButton)findViewById(R.id.rb4);
        btn_loc = (Button)findViewById(R.id.btn_loc) ;

        hour = tp.getCurrentHour();
        min = tp.getCurrentMinute();
        year = dp.getYear();
        month = dp.getMonth();
        date = dp.getDayOfMonth();

//        Setting Location Access Listener
        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ReportCrime.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Toast.makeText(ReportCrime.this, "Permission Accessed", Toast.LENGTH_SHORT).show();
            }
        });

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                min = i1;
            }
        });

        // Initializing the datepicker and atttaching date change listener
        dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                date = i2;
            }
        });

        btn_rpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desc = et_desc.getText().toString().trim();
                id = rg.getCheckedRadioButtonId();
                if(id== R.id.rb1){
                    crime = rb1.getText().toString();
                }
                if(id== R.id.rb2){
                    crime = rb2.getText().toString();
                }
                if(id== R.id.rb3){
                    crime = rb3.getText().toString();
                }
                if(id== R.id.rb4){
                    crime = rb4.getText().toString();
                }

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("server/saving-data/fireblog");

                DatabaseReference usersRef = ref.child("crimes");

                FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();
                String UID = use.getUid();

                // DatabaseReference newRef = usersRef.push();
                usersRef.child(UID).setValue(new Crime(crime, Integer.toString(year), Integer.toString(month),
                        Integer.toString(date), Integer.toString(hour), Integer.toString(min),
                        Double.toString(lat), Double.toString(lon), desc), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(getApplicationContext(), "Crime Reported Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(ReportCrime.this, Options.class);
                startActivity(i);
            }
        });

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                if (requestCode == 1) {
                    getLocation();
                }
                else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    Toast.makeText(ReportCrime.this, "Permission not granted!! ", Toast.LENGTH_SHORT).show();
                }
    }


    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e) {
            Toast.makeText(ReportCrime.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        lat = location.getLatitude();
        lon = location.getLongitude();

        Toast.makeText(ReportCrime.this,
                "Current Location: " + location.getLatitude() + ", " + location.getLongitude(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(ReportCrime.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }*/

}