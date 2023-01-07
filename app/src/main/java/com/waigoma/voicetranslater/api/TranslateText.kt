package com.waigoma.voicetranslater.api

import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TranslateText : ViewModel() {
    companion object {
        private const val API_URL = "https://script.google.com/macros/s/AKfycbzKGW6XQqZAYj7ic7Fqjf8ETNdmMpXhhlHv0zYfZQ6co0thL2gycl_qqS_k23hvCP9n/exec"
        private const val TAG_TEXT = "?text="
        private const val TAG_SOURCE = "&source="
        private const val TAG_TARGET = "&target="

        private const val CONNECTION_TIMEOUT_MS = 10000
        private const val READ_TIMEOUT_MS = 10000
    }

    /**
     * 翻訳を行う。
     *
     * @param text 翻訳するテキスト
     * @param source 翻訳元の言語
     * @param target 翻訳先の言語
     * @param transTextView 翻訳結果を表示する TextView
     * @return 翻訳後のテキスト
     */
    fun translate(text: String, source: String, target: String, transTextView: TextView) {
        val mainHandler = android.os.Handler(transTextView.context.mainLooper)
        viewModelScope.launch(Dispatchers.IO) {
            val result = run(URL("$API_URL$TAG_TEXT$text$TAG_SOURCE$source$TAG_TARGET$target"))
            mainHandler.post {
                transTextView.text = result
            }
        }
    }

    /**
     * URL からデータを取得する。
     * @param url 私用する URL
     */
    private fun run(url: URL): String {
        (url.openConnection() as HttpURLConnection).run { // HttpURLConnection を開く
            requestMethod = "GET" // GET メソッドでリクエストを送信する
            connectTimeout = CONNECTION_TIMEOUT_MS // 接続のタイムアウト時間を設定する
            readTimeout = READ_TIMEOUT_MS // 読み込みのタイムアウト時間を設定する

            return if (responseCode == HttpURLConnection.HTTP_OK) { // レスポンスコードが 200 であれば
                readStream(inputStream) // データを取得する
            } else { // それ以外の場合
                errorStream.bufferedReader().use(BufferedReader::readText) // エラーメッセージを取得する
            }
        }
    }

    private fun readStream(inputStream: InputStream) : String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val responseBody = bufferedReader.use { it.readText() }
        bufferedReader.close()

        return responseBody
    }
}