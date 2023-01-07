package com.waigoma.voicetranslater.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

/**
 * 設定データを管理する
 * @property activity アクティビティ
 */
class SettingManager(
    private val activity: AppCompatActivity
) {
    lateinit var data: SettingData
    private val pref = activity.getSharedPreferences("setting", Context.MODE_PRIVATE)

    fun initialize() {
        val recognizeLang = pref.getString("recognizeLang", "日本語")!!
        val translateLang = pref.getString("translateLang", "英語")!!
        val offlineMode = pref.getBoolean("offlineMode", false)

        data = SettingData(recognizeLang, translateLang, offlineMode)
    }

    fun save() {
        val editor = pref.edit()
        editor.putString("recognizeLang", data.RecognizeLang)
        editor.putString("translateLang", data.TranslateLang)
        editor.putBoolean("offlineMode", data.offlineMode)
        editor.apply()
    }

    fun getLocaleSystemName(langName: String) : String {
        return SettingData.LANG_MAP[langName] ?: "en"
    }
}