package com.waigoma.voicetranslater.setting

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * 設定画面の検索可能スピナーをクリックしたときの処理
 * @param activity 設定画面のアクティビティ
 * @param dialogLayout 検索可能スピナーのダイアログのレイアウト
 * @param editTextId 検索可能スピナーのダイアログの EditText の ID
 * @param listViewId 検索可能スピナーのダイアログの ListView の ID
 * @param textView 検索可能スピナーのダイアログの TextView
 */
class SettingOnClickListener(
    private val activity: AppCompatActivity,
    private val dialogLayout: Int,
    private val editTextId: Int,
    private val listViewId: Int,
    private val textView: TextView
) : View.OnClickListener {
    override fun onClick(p0: View?) {
        val dialog = Dialog(activity)
        dialog.setContentView(dialogLayout)
//        dialog.window?.setLayout(650, 800)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val editText = dialog.findViewById<TextView>(editTextId)
        val listView = dialog.findViewById<ListView>(listViewId)

        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, SettingData.LANG_MAP.keys.toTypedArray())
        listView.adapter = adapter

        editText.addTextChangedListener(SettingTextWatcher(adapter))
        listView.setOnItemClickListener { _, _, i, _ ->
            println(adapter.getItem(i))
            textView.text = adapter.getItem(i)
            dialog.dismiss()
        }
    }
}