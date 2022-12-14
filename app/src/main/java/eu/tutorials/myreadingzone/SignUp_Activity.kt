package eu.tutorials.myreadingzone

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.util.Patterns
import android.view.View
import eu.tutorials.myreadingzone.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.widget.Toast

class SignUp_Activity : AppCompatActivity() {
    var t1: TextInputLayout? = null
    var t2: TextInputLayout? = null
    var t3: TextInputLayout? = null
    var bar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.hide()
        window.statusBarColor = Color.TRANSPARENT

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        t1 = findViewById<View>(R.id.regEmail) as TextInputLayout
        t2 = findViewById<View>(R.id.regPassword) as TextInputLayout
        t3 = findViewById<View>(R.id.conformPassword) as TextInputLayout
        bar = findViewById<View>(R.id.progressBar) as ProgressBar
    }

    fun signUpHere(view: View?) {
        bar!!.visibility = View.VISIBLE
        val email = t1!!.editText!!.text.toString().trim { it <= ' ' }
        val pass = t2!!.editText!!.text.toString().trim { it <= ' ' }
        val cnfmPass = t3!!.editText!!.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            bar!!.visibility = View.INVISIBLE
            t1!!.error = "Enter an email address"
            t1!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            bar!!.visibility = View.INVISIBLE
            t1!!.error = "Enter a valid email address"
            t1!!.requestFocus()
            return
        }
        if (pass.isEmpty()) {
            bar!!.visibility = View.INVISIBLE
            t2!!.error = "Enter a password"
            t2!!.requestFocus()
            return
        }
        if (pass.length < 8) {
            bar!!.visibility = View.INVISIBLE
            t2!!.error = "Password Length Must be 8 Digits"
            t2!!.requestFocus()
            return
        }
        if (cnfmPass != pass) {
            bar!!.visibility = View.INVISIBLE
            t3!!.error = "Password do not match"
            t3!!.requestFocus()
            return
        }
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this@SignUp_Activity) { task ->
                if (task.isSuccessful) {
                    bar!!.visibility = View.INVISIBLE
                    t1!!.editText!!.setText("")
                    t2!!.editText!!.setText("")
                    t3!!.editText!!.setText("")
                    t1!!.error = ""
                    t2!!.error = ""
                    t3!!.error = ""
                    t1!!.clearFocus()
                    t2!!.clearFocus()
                    t3!!.clearFocus()
                    Toast.makeText(applicationContext, "SignUp Successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    bar!!.visibility = View.INVISIBLE
                    t1!!.editText!!.setText("")
                    t2!!.editText!!.setText("")
                    t3!!.editText!!.setText("")
                    t1!!.error = ""
                    t2!!.error = ""
                    t3!!.error = ""
                    t1!!.clearFocus()
                    t2!!.clearFocus()
                    t3!!.clearFocus()
                    Toast.makeText(applicationContext, "Process Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}