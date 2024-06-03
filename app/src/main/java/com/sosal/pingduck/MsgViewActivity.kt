package com.sosal.pingduck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.sosal.pingduck.databinding.ActivityMsgViewBinding
import com.sosal.pingduck.msgDB.DBHelper
import androidx.recyclerview.widget.LinearLayoutManager

class MsgViewActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var dbHelper: DBHelper
    lateinit var msgAdapter: MsgAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMsgViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DBHelper 초기화
        dbHelper = DBHelper(this)

        // RecyclerView 설정
        val recyclerView = binding.msgRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        msgAdapter = MsgAdapter(dbHelper.getMsgList())
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
