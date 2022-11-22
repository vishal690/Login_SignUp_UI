package eu.tutorials.myreadingzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Activity extends AppCompatActivity {
    TextInputLayout t1;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextInputLayout) findViewById(R.id.forgotEmail);
        bar = (ProgressBar) findViewById(R.id.progressBar4);
    }

    public void resetPassword(View view) {
        bar.setVisibility(View.VISIBLE);
        String forgotEmail = t1.getEditText().getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(forgotEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Password reset link send on registered Email", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Forgot_Activity.this,SignIn_Activity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}