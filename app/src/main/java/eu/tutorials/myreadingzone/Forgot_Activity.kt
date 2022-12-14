package eu.tutorials.myreadingzone

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import eu.tutorials.myreadingzone.R
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
import android.content.Intent
import android.graphics.Color
import android.view.View
import eu.tutorials.myreadingzone.SignIn_Activity

class Forgot_Activity : AppCompatActivity() {
    var t1: TextInputLayout? = null
    var bar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        supportActionBar!!.hide()
        window.statusBarColor = Color.TRANSPARENT
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        t1 = findViewById<View>(R.id.forgotEmail) as TextInputLayout
        bar = findViewById<View>(R.id.progressBar4) as ProgressBar
    }

    fun resetPassword(view: View?) {
        bar!!.visibility = View.VISIBLE
        val forgotEmail = t1!!.editText!!.text.toString().trim { it <= ' ' }
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.sendPasswordResetEmail(forgotEmail).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                bar!!.visibility = View.INVISIBLE
                Toast.makeText(
                    applicationContext,
                    "Password reset link send on registered Email",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@Forgot_Activity, SignIn_Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                bar!!.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}