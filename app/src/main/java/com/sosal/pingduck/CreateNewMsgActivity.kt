package com.sosal.pingduck

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sosal.pingduck.msgDB.DBHelper
import com.sosal.pingduck.msgDB.MsgDTO
import java.text.SimpleDateFormat
import java.util.Date


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


        addChip(msgTarget,"교수님")
        addChip(msgTarget,"선배")
        addChip(msgTarget,"후배")
        addChip(msgPinkTime,"내일")
        addChip(msgPinkTime,"다음생")
        addChip(msgPinkWhy,"아픔")
        addChip(msgPinkWhy,"금붕어산책")
        addChip(msgPinkWhy,"모기 사냥")

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

        //모든 옵션이 없을 경우 예외 처리

        val msg : MsgDTO = MsgDTO(getCheckedChipText(msgTarget),getCheckedChipText(msgPinkTime),
            getTime(),
            getCheckedChipText(msgPinkWhy))

        dbHelper.createMsg(msg)
        Log.d("db msg create", "msg 등록")
    }

    private fun refreshOptions() {

    }

    private fun addChip(chipGroup: ChipGroup,chipName : String){
        chipGroup.addView(Chip(this).apply {
            text = chipName
            isCloseIconVisible = true
            isCheckable = true
        })
    }

    /**
     * Chip 그룹에서 체크된 Chip의 text를 반환한다.
     *
     * @param ChipGroup
     * @return 해당 Chip의 text
     */
    private fun getCheckedChipText(chipGroup: ChipGroup) : String {
        //TODO : 체크된 chip 이 없을 경우 예외 처리
        val checkedChip : Chip = findViewById<Chip>(chipGroup.checkedChipId)

        return checkedChip.text.toString()
    }

    private fun getTime() : String {
        val nowTime : Long = System.currentTimeMillis()
        val nowDate : Date = Date(nowTime)
        val dateTime : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return dateTime.format(nowDate)
    }


}