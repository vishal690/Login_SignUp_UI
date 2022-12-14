package eu.tutorials.myreadingzone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.tutorials.myreadingzone.R
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Button
import eu.tutorials.myreadingzone.SignIn_Activity

class MainActivity : AppCompatActivity() {
    var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()
        window.statusBarColor = Color.TRANSPARENT
        btn = findViewById<View>(R.id.button) as Button
        btn!!.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SignIn_Activity::class.java
                )
            )
        }
    }
}