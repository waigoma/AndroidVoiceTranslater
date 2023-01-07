package com.waigoma.voicetranslater

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.waigoma.voicetranslater.databinding.ActivitySettingBinding
import com.waigoma.voicetranslater.setting.SettingData
import com.waigoma.voicetranslater.setting.SettingOnClickListener

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingData.initialize()

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setSpinner()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //アクションバーのオブジェクトを取得
        val actionBar = supportActionBar
        //アクションバーに「戻るボタン」を追加
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setSpinner() {
        val textViewRec = findViewById<TextView>(R.id.sspinner_setting_rec)
        val textViewTrans = findViewById<TextView>(R.id.sspinner_setting_trans)

        textViewRec.setOnClickListener(
            SettingOnClickListener(
                this,
                R.layout.dialog_searchable_spinner_rec,
                R.id.dialog_edit_text_rec,
                R.id.dialog_list_view_rec,
                textViewRec
            )
        )

        textViewTrans.setOnClickListener(
            SettingOnClickListener(
                this,
                R.layout.dialog_searchable_spinner_trans,
                R.id.dialog_edit_text_trans,
                R.id.dialog_list_view_trans,
                textViewTrans
            )
        )
    }
}