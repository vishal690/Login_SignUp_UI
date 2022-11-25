//package eu.tutorials.myreadingzone;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.firebase.auth.FirebaseAuth;
//
//public class Dashboard2 extends AppCompatActivity {
//    TextView name, email;
//    ImageView img;
//    Button btn;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard2);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//        img = (ImageView) findViewById(R.id.imageView2);
//        email = (TextView) findViewById(R.id.newEmail);
//        name = (TextView) findViewById(R.id.name);
//        btn = (Button) findViewById(R.id.logOutBtn2);
//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        name.setText(account.getDisplayName());
//        email.setText(account.getEmail());
//        Glide.with(this).load(account.getPhotoUrl()).into(img);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(Dashboard2.this,MainActivity.class));
//                finish();
//            }
//        });
//    }
//}