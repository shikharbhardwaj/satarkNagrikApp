package gov.assam.sixsigma.satarknagrik;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {
    Button btn_contact, btn_cm, btn_faq, btn_rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        btn_contact = (Button)findViewById(R.id.btn_contact);
        btn_cm = (Button)findViewById(R.id.btn_cm);
        btn_rc = (Button)findViewById(R.id.btn_rc);
        btn_faq = (Button)findViewById(R.id.btn_faq);

        btn_rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Options.this , ReportCrime.class);
                startActivity(i);
            }
        });

        btn_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(Options.this, MapsActivity.class);
                startActivity(j);
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:+919990295235"));
                startActivity(call);


            }
        });
    }
}
