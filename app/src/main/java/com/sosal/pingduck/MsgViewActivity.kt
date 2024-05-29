package com.sosal.pingduck

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.sosal.pingduck.databinding.ActivityMsgViewBinding

class MsgViewActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var cancelBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMsgViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cancelBtn = findViewById<Button>(R.id.msgViewCancelBtn)


        toggle = ActionBarDrawerToggle(this,binding.drawer, R.string.open_drawer, R.string.close_drawer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        cancelBtn.setOnClickListener {
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
