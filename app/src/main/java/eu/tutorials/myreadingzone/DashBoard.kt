package eu.tutorials.myreadingzone

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ProgressBar
import android.os.Bundle
import eu.tutorials.myreadingzone.R
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import eu.tutorials.myreadingzone.MainActivity

class DashBoard : AppCompatActivity() {
    var uid: TextView? = null
    var email: TextView? = null
    var name: TextView? = null
    var gEmail: TextView? = null
    var bar: ProgressBar? = null
    var btn: Button? = null
    var img: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()
        window.statusBarColor = Color.TRANSPARENT
        email = findViewById<View>(R.id.viewEmail) as TextView
        uid = findViewById<View>(R.id.viewUid) as TextView
        name = findViewById<View>(R.id.name) as TextView
        gEmail = findViewById<View>(R.id.gEmail) as TextView
        btn = findViewById<View>(R.id.logOutBtn) as Button
        img = findViewById<View>(R.id.imageView3) as ImageView
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            name!!.visibility = View.VISIBLE
            gEmail!!.visibility = View.VISIBLE
            img!!.visibility = View.VISIBLE
            name!!.text = account.displayName
            gEmail!!.text = account.email
            Glide.with(this).load(account.photoUrl).into(img!!)
        } else {
            email!!.visibility = View.VISIBLE
            uid!!.visibility = View.VISIBLE
            email!!.text = "Email :- " + intent.getStringExtra("email").toString()
            uid!!.text = "UID :- " + intent.getStringExtra("Uid").toString()
        }
        btn!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@DashBoard, MainActivity::class.java))
            finish()
        }
    }
}