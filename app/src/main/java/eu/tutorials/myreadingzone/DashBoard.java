package eu.tutorials.myreadingzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {
    TextView uid, email,name,gEmail;
    ProgressBar bar;
    Button btn;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = (TextView) findViewById(R.id.viewEmail);
        uid = (TextView) findViewById(R.id.viewUid);
        name = (TextView) findViewById(R.id.name);
        gEmail = (TextView) findViewById(R.id.gEmail);
        btn = (Button) findViewById(R.id.logOutBtn);
        img = (ImageView) findViewById(R.id.imageView3);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if( account != null){
            name.setVisibility(View.VISIBLE);
            gEmail.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            name.setText(account.getDisplayName());
            gEmail.setText(account.getEmail());
            Glide.with(this).load(account.getPhotoUrl()).into(img);
        }else {
            email.setVisibility(View.VISIBLE);
            uid.setVisibility(View.VISIBLE);
            email.setText("Email :- "+getIntent().getStringExtra("email").toString());
            uid.setText("UID :- "+getIntent().getStringExtra("Uid").toString());

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashBoard.this,MainActivity.class));
                finish();
            }
        });
    }

}