package com.sosal.pingduck

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity(){
    lateinit var CreateMsgBtn: Button;
    lateinit var ViewMsgBtn: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CreateMsgBtn = findViewById<Button>(R.id.CreateBtn)
        ViewMsgBtn = findViewById<Button>(R.id.ViewBtn)

        CreateMsgBtn.setOnClickListener {
            var intent = Intent(this, CreateNewMsgActivity::class.java)
            startActivityForResult(intent, 101)
        }
        ViewMsgBtn.setOnClickListener {
            var intent = Intent(this, MsgViewActivity::class.java)
            startActivityForResult(intent,100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // Do nothing, just return to MainActivity
            }
            if (resultCode == Activity.RESULT_OK) {
                // Handle result from ViewMsgActivity
            }
        } else if (requestCode == 101) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // Do nothing, just return to MainActivity
            }
            if (resultCode == Activity.RESULT_OK) {
                // Handle result from CreateMsgActivity
            }
        }
    }
}
