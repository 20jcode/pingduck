package com.sosal.pingduck

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sosal.pingduck.msgDB.DBHelper
import com.sosal.pingduck.msgDB.MsgDTO

class CreateNewMsgActivity : AppCompatActivity() {
    //생성버튼
    lateinit var createBtn : Button
    //초기화
    lateinit var refrashBtn : Button
    //취소
    lateinit var cancelBtn : Button

    lateinit var msgTarget: ChipGroup

    lateinit var msgPinkTime : ChipGroup

    lateinit var msgPinkWhy : ChipGroup

    //DB 객체 선언
    lateinit var database : SQLiteDatabase
    lateinit var dbHelper : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DBHelper(this,"msgtest.db",null,1)

        //view binding
        setContentView(R.layout.activity_new_msg)

        //button setting
        createBtn = findViewById<Button>(R.id.newMsgViewCreateBtn)
        refrashBtn = findViewById<Button>(R.id.newMsgViewRefrashBtn)
        cancelBtn = findViewById<Button>(R.id.newMsgViewCancelBtn)

        //ChipGroup setting
        //동적으로 선택지를 추가해준다.
        msgTarget = findViewById<ChipGroup>(R.id.newMsgViewFocusTargetChipGroup)
        msgPinkTime = findViewById<ChipGroup>(R.id.newMsgViewTimeTextChipGroup)
        msgPinkWhy = findViewById<ChipGroup>(R.id.newMsgViewPinkWhyChipGroup)

        //setChipBackgroundColorResource(R.color.teal_200)
        msgTarget.addView(Chip(this).apply{
            text = "교수님"
            isCloseIconVisible = true

        })
        msgTarget.addView(Chip(this).apply{
            text = "선배"
            isCloseIconVisible = true
        })
        msgTarget.addView(Chip(this).apply{
            text = "후배"
            isCloseIconVisible = true
        })
        msgPinkTime.addView(Chip(this).apply{
            text = "오늘"
            isCloseIconVisible = true

        })
        msgPinkTime.addView(Chip(this).apply{
            text = "다음생에 만남"
            isCloseIconVisible = true

        })
        msgPinkWhy.addView(Chip(this).apply{
            text = "그냥"
            isCloseIconVisible = true

        })
        msgPinkWhy.addView(Chip(this).apply{
            text = "금붕어 산책"
            isCloseIconVisible = true

        })
        msgPinkWhy.addView(Chip(this).apply{
            text = "곤충 채집"
            isCloseIconVisible = true

        })

        createBtn.setOnClickListener {
            createMessage()
        }

        refrashBtn.setOnClickListener {
            refreshOptions()
        }

        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun createMessage() {

    }

    private fun refreshOptions() {

    }


}