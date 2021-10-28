package com.example.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed({

//             startActivity(Intent(this,MainActivity::class.java))
//             finish()
             Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, ringtoneType)
             }
        },2000)
    }
}
//EXTRA CODE
if(packageManager.resolveActivity(i,0)!=null)
        {
            startActivity(i)
        }
        else{
            Toast.makeText(this,"Please Install Whatsapp",Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
