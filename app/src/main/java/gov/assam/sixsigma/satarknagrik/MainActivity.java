package gov.assam.sixsigma.satarknagrik;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, etname;
    private Button btnSignUp;
    int id =1;
    ProgressBar progressBar;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        // Creating variables for views
        btnSignUp = (Button)findViewById(R.id.sign_up_button);
        inputEmail = (EditText)findViewById(R.id.etmail1);
        etname = (EditText)findViewById(R.id.etname1) ;
        inputPassword = (EditText)findViewById(R.id.etpass1);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etname.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //Create new user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this,
                                "createUserWithEmail:onComplete:" + task.isSuccessful(),
                                Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("server/saving-data/fireblog");

                            DatabaseReference usersRef = ref.child("users");

                            FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();
                            String UID = use.getUid();

                            // DatabaseReference newRef = usersRef.push();
                            usersRef.child(UID).setValue(new User(name, email, password));

                            Intent i = new Intent(MainActivity.this, Options.class);
                            // i.putExtra("name",name);
                            startActivity(i);

                        }
                    }
                });



            }
        });



    }
}
