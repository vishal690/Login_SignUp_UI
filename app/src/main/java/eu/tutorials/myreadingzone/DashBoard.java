package eu.tutorials.myreadingzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {
    TextView uid, email;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = (TextView) findViewById(R.id.viewEmail);
        uid = (TextView) findViewById(R.id.viewUid);

        email.setText("Email :- "+getIntent().getStringExtra("email").toString());
        uid.setText("UID :- "+getIntent().getStringExtra("Uid").toString());
    }

    public void logOutHere(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DashBoard.this,MainActivity.class));
    }
}