package com.waigoma.voicetranslater.setting

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter

/**
 * 検索可能スピナーのテキスト入力を監視するクラス
 * @param adapter 検索可能スピナーのダイアログの ListView のアダプター
 */
class SettingTextWatcher(
    private val adapter: ArrayAdapter<String>
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        adapter.filter.filter(s)
    }

    override fun afterTextChanged(s: Editable?) {

    }
}
