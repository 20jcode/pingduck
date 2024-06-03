package com.sosal.pingduck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.sosal.pingduck.databinding.ActivityMsgViewBinding

class MsgLookUpActivity : AppCompatActivity() {
    lateinit var cancelBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_look_up)

        cancelBtn = findViewById<Button>(R.id.msgLookUpCancelBtn)

        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }


        Toast.makeText(this,intent.getStringExtra("id").toString(),Toast.LENGTH_LONG).show()
    }


}