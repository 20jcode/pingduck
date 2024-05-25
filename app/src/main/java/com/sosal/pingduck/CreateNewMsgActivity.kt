package com.sosal.pingduck

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CreateNewMsgActivity : AppCompatActivity() {
    //생성버튼
    lateinit var createBtn : Button;
    //초기화
    lateinit var refrashBtn : Button;
    //취소
    lateinit var cancelBtn : Button;

    //DB 객체 선언
    var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_msg) //view binding

        //button setting
        createBtn = findViewById<Button>(R.id.newMsgViewCreateBtn)
        refrashBtn = findViewById<Button>(R.id.newMsgViewRefrashBtn)
        cancelBtn = findViewById<Button>(R.id.newMsgViewCancelBtn)


    }
}