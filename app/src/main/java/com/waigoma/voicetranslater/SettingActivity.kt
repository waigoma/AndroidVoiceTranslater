package com.waigoma.voicetranslater

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.waigoma.voicetranslater.databinding.ActivitySettingBinding
import com.waigoma.voicetranslater.setting.SettingData
import com.waigoma.voicetranslater.setting.SettingOnClickListener

/**
 * 設定画面
 */
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingData = MainActivity.settings.data
    private lateinit var textViewRec: TextView
    private lateinit var textViewTrans: TextView
    private lateinit var offlineMode: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SettingData.initialize()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        textViewRec = binding.root.findViewById(R.id.sspinner_setting_rec)
        textViewTrans = binding.root.findViewById(R.id.sspinner_setting_trans)
        offlineMode = binding.root.findViewById(R.id.switch_setting_offline)


        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setSpinner()
        textViewRec.text = settingData.RecognizeLang
        textViewTrans.text = settingData.TranslateLang
        offlineMode.isChecked = settingData.offlineMode
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
                settingData.RecognizeLang = textViewRec.text.toString()
                settingData.TranslateLang = textViewTrans.text.toString()
                settingData.offlineMode = offlineMode.isChecked
                MainActivity.settings.save()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 検索可能スピナーの生成
     */
    private fun setSpinner() {
        textViewRec.text = settingData.RecognizeLang
        textViewTrans.text = settingData.TranslateLang

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