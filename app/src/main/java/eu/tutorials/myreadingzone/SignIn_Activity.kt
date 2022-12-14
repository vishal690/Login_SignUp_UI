package eu.tutorials.myreadingzone

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.graphics.Color
import eu.tutorials.myreadingzone.DashBoard
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import eu.tutorials.myreadingzone.R
import eu.tutorials.myreadingzone.SignUp_Activity
import eu.tutorials.myreadingzone.Forgot_Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class SignIn_Activity : AppCompatActivity() {
    var t1: TextInputLayout? = null
    var t2: TextInputLayout? = null
    var textView: TextView? = null
    var textView2: TextView? = null
    var bar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    var gSignIn: ImageView? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) startActivity(Intent(applicationContext, DashBoard::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar!!.hide()
        window.statusBarColor = Color.TRANSPARENT
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView2 = findViewById<View>(R.id.alreadyAccountSignIn) as TextView
        t1 = findViewById<View>(R.id.email) as TextInputLayout
        t2 = findViewById<View>(R.id.password) as TextInputLayout
        bar = findViewById<View>(R.id.progressBar2) as ProgressBar
        textView = findViewById<View>(R.id.forgotPassword) as TextView
        gSignIn = findViewById<View>(R.id.imageView) as ImageView
        mAuth = FirebaseAuth.getInstance()
        gSignIn!!.setOnClickListener { processLogin() }
        googleSignIn()
        textView2!!.setOnClickListener {
            val intent = Intent(this@SignIn_Activity, SignUp_Activity::class.java)
            startActivity(intent)
        }
        textView!!.setOnClickListener {
            val intent = Intent(this@SignIn_Activity, Forgot_Activity::class.java)
            startActivity(intent)
        }
    }

    fun signInHere(view: View?) {
        bar!!.visibility = View.VISIBLE
        val email = t1!!.editText!!.text.toString().trim { it <= ' ' }
        val password = t2!!.editText!!.text.toString().trim { it <= ' ' }
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
        if (password.isEmpty()) {
            bar!!.visibility = View.INVISIBLE
            t2!!.error = "Enter a password"
            t2!!.requestFocus()
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@SignIn_Activity) { task ->
                if (task.isSuccessful) {
                    t1!!.editText!!.setText("")
                    t2!!.editText!!.setText("")
                    bar!!.visibility = View.INVISIBLE
                    t1!!.clearFocus()
                    t2!!.clearFocus()
                    t1!!.error = ""
                    t2!!.error = ""
                    val intent = Intent(this@SignIn_Activity, DashBoard::class.java)
                    intent.putExtra("email", mAuth!!.currentUser!!.email)
                    intent.putExtra("Uid", mAuth!!.currentUser!!.uid)
                    startActivity(intent)
                } else {
                    t1!!.clearFocus()
                    t2!!.clearFocus()
                    t1!!.error = ""
                    t2!!.error = ""
                    bar!!.visibility = View.INVISIBLE
                    t1!!.editText!!.setText("")
                    t2!!.editText!!.setText("")
                    Toast.makeText(applicationContext, "Invalid Email/Password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun processLogin() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, 101)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                Toast.makeText(
                    applicationContext,
                    "Error in getting information",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error in firebase login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}