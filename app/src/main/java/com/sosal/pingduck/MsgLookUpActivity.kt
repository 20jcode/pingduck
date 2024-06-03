package com.sosal.pingduck

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.sosal.pingduck.databinding.ActivityMsgViewBinding
import com.sosal.pingduck.msgDB.DBHelper
import com.sosal.pingduck.msgDB.MsgDTO

class MsgLookUpActivity : AppCompatActivity() {
    lateinit var sendBtn : Button
    lateinit var copyBtn: Button
    lateinit var cancelBtn: Button
    lateinit var dbHelper: DBHelper
    lateinit var msg : MsgDTO
    lateinit var msgText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_look_up)
        dbHelper = DBHelper(this)

        sendBtn = findViewById(R.id.msgLookUpSendBtn)
        copyBtn = findViewById(R.id.msgLookUpCopyBtn)
        cancelBtn = findViewById<Button>(R.id.msgLookUpCancelBtn)
        msgText = findViewById<TextView>(R.id.msgLookUpTextView)

        //intent 데이터 전달 받음
        val msg = intent.getStringExtra("data")
        msgText.text = msg

        //클립보드 복사
        val clipboard : ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",msg)

        copyBtn.setOnClickListener{
            clipboard.setPrimaryClip(clip)
            Log.d("checking","클립보드 복사")
            Toast.makeText(this,"복사되었습니다",Toast.LENGTH_SHORT).show()
        }

        //다른 앱으로 공유
        sendBtn.setOnClickListener {
            val sendIntent : Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,msg)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent,null)
            Log.d("checking","다른 앱 공유")
            startActivity(shareIntent)
        }


        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }



    }


}