package com.sosal.pingduck

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sosal.pingduck.common.MsgGenerator
import com.sosal.pingduck.msgDB.DBHelper
import com.sosal.pingduck.msgDB.DBOptionHelper
import com.sosal.pingduck.msgDB.MsgDTO
import com.sosal.pingduck.msgDB.OptionTag
import java.text.SimpleDateFormat
import java.util.Date


class CreateNewMsgActivity : AppCompatActivity() {
    //생성버튼
    lateinit var createBtn : Button
    //초기화
    lateinit var refrashBtn : Button
    //취소
    lateinit var cancelBtn : Button

    //ChipGroup
    lateinit var msgTarget: ChipGroup
    lateinit var msgPinkTime : ChipGroup
    lateinit var msgPinkWhy : ChipGroup

    //DB 객체 선언
    lateinit var dbHelper : DBHelper
    lateinit var optionHelper: DBOptionHelper

    //msgGenerator
    lateinit var msgGenerator: MsgGenerator

    lateinit var newMsgTargetInput: EditText
    lateinit var addTargetChipBtn: Button
    lateinit var newMsgTimeInput: EditText
    lateinit var addTimeChipBtn: Button
    lateinit var newMsgReasonInput: EditText
    lateinit var addReasonChipBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DBHelper(this)
        optionHelper = DBOptionHelper(this)

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

        //Input fields and buttons
        newMsgTargetInput = findViewById<EditText>(R.id.newMsgTargetInput)
        addTargetChipBtn = findViewById<Button>(R.id.addTargetChipBtn)
        newMsgTimeInput = findViewById<EditText>(R.id.newMsgTimeInput)
        addTimeChipBtn = findViewById<Button>(R.id.addTimeChipBtn)
        newMsgReasonInput = findViewById<EditText>(R.id.newMsgReasonInput)
        addReasonChipBtn = findViewById<Button>(R.id.addReasonChipBtn)

        //msgGenerator
        msgGenerator = MsgGenerator()


        //init
        if(optionHelper.getOption(OptionTag.MSGTARGET).isEmpty()){
            optionHelper.createOption("교수님",OptionTag.MSGTARGET)
            optionHelper.createOption("선배",OptionTag.MSGTARGET)
            optionHelper.createOption("후배",OptionTag.MSGTARGET)
        }
        if(optionHelper.getOption(OptionTag.MSGPINKTIME).isEmpty()){
            optionHelper.createOption("내일",OptionTag.MSGPINKTIME)
            optionHelper.createOption("다음생",OptionTag.MSGPINKTIME)
        }
        if(optionHelper.getOption(OptionTag.MSGPINKWHY).isEmpty()){
            optionHelper.createOption("아픔",OptionTag.MSGPINKWHY)
            optionHelper.createOption("금붕어산책",OptionTag.MSGPINKWHY)

        }

        for(s:String in optionHelper.getOption(OptionTag.MSGTARGET)){
            addChip(msgTarget,s)
        }
        for(s:String in optionHelper.getOption(OptionTag.MSGPINKTIME)){
            addChip(msgPinkTime,s)
        }
        for(s:String in optionHelper.getOption(OptionTag.MSGPINKWHY)){
            addChip(msgPinkWhy,s)
        }

        createBtn.setOnClickListener {
            try {
                //ChipGroup에서 메세지 옵션 선택
                createMessage()

                Toast.makeText(this,"메세지가 생성되었습니다.",Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            } catch (e:IllegalArgumentException){ //메세지 옵션이 선택되지 않은 경우에 대해서
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                refreshOptions()
            } catch (e:IllegalAccessException){ //이미 메세지가 생성되었다면
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                refreshOptions()
            }

        }

        refrashBtn.setOnClickListener {
            refreshOptions()
        }

        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        addTargetChipBtn.setOnClickListener {
            val chipText = newMsgTargetInput.text.toString().trim()
            if (chipText.isNotEmpty()) {
                optionHelper.createOption(chipText,OptionTag.MSGTARGET)
                addChip(msgTarget, chipText)
                newMsgTargetInput.text.clear()
            }
        }
        addTimeChipBtn.setOnClickListener {
            val chipText = newMsgTimeInput.text.toString().trim()
            if (chipText.isNotEmpty()) {
                optionHelper.createOption(chipText,OptionTag.MSGPINKTIME)
                addChip(msgPinkTime, chipText)
                newMsgTimeInput.text.clear()
            }
        }
        addReasonChipBtn.setOnClickListener {
            val chipText = newMsgReasonInput.text.toString().trim()
            if (chipText.isNotEmpty()) {
                optionHelper.createOption(chipText,OptionTag.MSGPINKWHY)
                addChip(msgPinkWhy, chipText)
                newMsgReasonInput.text.clear()
            }
        }
    }

    private fun createMessage() {

        val msg : MsgDTO = MsgDTO(getCheckedChipText(msgTarget),getCheckedChipText(msgPinkTime),
            getTime(),
            getCheckedChipText(msgPinkWhy))

        msg.generateMsg(msgGenerator.generate(msg))

        dbHelper.createMsg(msg)

        Log.d("db msg create", "msg 등록 : ${msg.getGeneratedMsg()}")
    }

    private fun refreshOptions() {
        resetCheckedChip(msgTarget)
        resetCheckedChip(msgPinkTime)
        resetCheckedChip(msgPinkWhy)
    }

    private fun addChip(chipGroup: ChipGroup,chipName : String){
        chipGroup.addView(Chip(this).apply {
            text = chipName
            isCloseIconVisible = true
            isCheckable = true
            setOnCloseIconClickListener {
                chipGroup.removeView(this)
                optionHelper.delOption(this.text.toString())
            }
        })
    }

    /**
     * Chip 그룹에서 체크된 Chip의 text를 반환한다.
     *
     * @param ChipGroup
     * @return 해당 Chip의 text
     */
    private fun getCheckedChipText(chipGroup: ChipGroup) : String {

        val checkedChip : Chip? = findViewById<Chip>(chipGroup.checkedChipId)

        if(checkedChip == null){
            throw IllegalArgumentException("핑계 생성 옵션을 선택해주세요.")
        } else {
            return checkedChip.text.toString()
        }


    }

    /**
     * 현재 시간을 String으로
     */
    private fun getTime() : String {
        val nowTime : Long = System.currentTimeMillis()
        val nowDate : Date = Date(nowTime)
        val dateTime : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return dateTime.format(nowDate)
    }

    private fun resetCheckedChip(chipGroup: ChipGroup) {
        if(chipGroup.checkedChipId != -1){
            val checkedChip : Chip = findViewById<Chip>(chipGroup.checkedChipId)
            checkedChip.isChecked = false
        }
    }


}