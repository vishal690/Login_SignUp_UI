package eu.tutorials.myreadingzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn_Activity extends AppCompatActivity {

    TextInputLayout t1,t2;
    TextView textView, textView2;
    ProgressBar bar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView2 = (TextView) findViewById(R.id.alreadyAccountSignIn);
        t1 = (TextInputLayout) findViewById(R.id.email);
        t2 = (TextInputLayout) findViewById(R.id.password);
        bar = (ProgressBar) findViewById(R.id.progressBar2);
        textView = (TextView) findViewById(R.id.forgotPassword);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this,Forgot_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void signInHere(View view) {
        bar.setVisibility(View.VISIBLE);
        String email = t1.getEditText().getText().toString().trim();
        String password = t2.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            bar.setVisibility(View.INVISIBLE);
            t1.setError("Enter an email address");
            t1.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bar.setVisibility(View.INVISIBLE);
            t1.setError("Enter a valid email address");
            t1.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            bar.setVisibility(View.INVISIBLE);
            t2.setError("Enter a password");
            t2.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignIn_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            bar.setVisibility(View.INVISIBLE);
                            t1.clearFocus();
                            t2.clearFocus();
                            t1.setError("");
                            t2.setError("");
                            Intent intent = new Intent(SignIn_Activity.this,DashBoard.class);
                            intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                            intent.putExtra("Uid",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }
                        else
                        {   t1.clearFocus();
                            t2.clearFocus();
                            t1.setError("");
                            t2.setError("");
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}