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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity {
    TextInputLayout t1,t2,t3;
    ProgressBar bar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        t1= (TextInputLayout) findViewById(R.id.regEmail);
        t2= (TextInputLayout) findViewById(R.id.regPassword);
        t3= (TextInputLayout) findViewById(R.id.conformPassword);
        bar= (ProgressBar) findViewById(R.id.progressBar);

    }

    public void signUpHere(View view) {
        bar.setVisibility(View.VISIBLE);
        String email = t1.getEditText().getText().toString().trim();
        String pass = t2.getEditText().getText().toString().trim();
        String cnfmPass = t3.getEditText().getText().toString().trim();

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
        if (pass.isEmpty()) {
            bar.setVisibility(View.INVISIBLE);
            t2.setError("Enter a password");
            t2.requestFocus();
            return;
        }
        if (pass.length() < 8) {
            bar.setVisibility(View.INVISIBLE);
            t2.setError("Password Length Must be 8 Digits");
            t2.requestFocus();
            return;
        }
        if (!cnfmPass.equals(pass)) {
            bar.setVisibility(View.INVISIBLE);
            t3.setError("Password do not match");
            t3.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                            t1.setError("");
                            t2.setError("");
                            t3.setError("");
                            t1.clearFocus();
                            t2.clearFocus();
                            t3.clearFocus();
                            Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            bar.setVisibility(View.INVISIBLE);
                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");
                            t1.setError("");
                            t2.setError("");
                            t3.setError("");
                            t1.clearFocus();
                            t2.clearFocus();
                            t3.clearFocus();
                            Toast.makeText(getApplicationContext(), "Process Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}