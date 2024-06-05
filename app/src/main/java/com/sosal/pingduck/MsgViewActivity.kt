package com.sosal.pingduck

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.sosal.pingduck.databinding.ActivityMsgViewBinding
import com.sosal.pingduck.msgDB.DBHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.sosal.pingduck.common.MsgAdapter
import com.sosal.pingduck.msgDB.MsgDTO

class MsgViewActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var dbHelper: DBHelper
    lateinit var msgAdapter: MsgAdapter
    lateinit var msgList: MutableList<MsgDTO>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMsgViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DBHelper 초기화
        dbHelper = DBHelper(this)

        // 초기 메세지 리스트 가져오기
        msgList = dbHelper.getMsgList()

        // RecyclerView 설정
        val recyclerView = binding.msgRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        msgAdapter = MsgAdapter(msgList){
            msg ->
            var intent = Intent(this,MsgLookUpActivity::class.java)
            intent.putExtra("data",msg.getGeneratedMsg())

            startActivityForResult(intent,100)
        }
        recyclerView.adapter = msgAdapter


        // Toggle 설정
        toggle = ActionBarDrawerToggle(this,binding.drawer, R.string.open_drawer, R.string.close_drawer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 취소 버튼 설정
        binding.msgViewCancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        // 날짜순 버튼 설정
        val sortByDateBtn = findViewById<Button>(R.id.sortByDateBtn)
        sortByDateBtn.setOnClickListener {
            sortMessagesByDate()
        }

        // 대상순 버튼 설정
        val sortByTargetBtn = findViewById<Button>(R.id.sortByTargetBtn)
        sortByTargetBtn.setOnClickListener {
            sortMessagesByTarget()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortMessagesByDate() {
        msgList.sortBy {it.getMsgCreateTime()}
        msgAdapter.notifyDataSetChanged()
    }

    private fun sortMessagesByTarget() {
        msgList.sortBy {it.getMsgTarget()}
        msgAdapter.notifyDataSetChanged()
    }
}
