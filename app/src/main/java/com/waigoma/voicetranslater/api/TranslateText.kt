package com.waigoma.voicetranslater.api

import java.net.HttpURLConnection
import java.net.URL

class TranslateText {
    companion object {
        private const val API_URL = "https://script.google.com/macros/s/AKfycbw2YKc3eQjIskZCQUnhTLrAFRHvsDUDLGw2rQem1y50jH4Gjf6SRyiQmpBrxrx-rCNE/exec"
        private const val TAG_TEXT = "?text="
        private const val TAG_SOURCE = "&source="
        private const val TAG_TARGET = "&target="

        private const val CONNECTION_TIMEOUT_MS = 10000
        private const val READ_TIMEOUT_MS = 10000

        /**
         * 翻訳を行う。
         *
         * @param text 翻訳するテキスト
         * @param source 翻訳元の言語
         * @param target 翻訳先の言語
         * @return 翻訳後のテキスト
         */
        fun translate(text: String, source: String, target: String): String {
            return run(URL("$API_URL$TAG_TEXT$text$TAG_SOURCE$source$TAG_TARGET$target"))
        }

        private fun run(link: URL): String {
            val connection = link.openConnection() as HttpURLConnection

            try {
                connection.connectTimeout = CONNECTION_TIMEOUT_MS
                connection.readTimeout = READ_TIMEOUT_MS

                val statusCode = connection.responseCode
                if (statusCode != HttpURLConnection.HTTP_OK)
                    return ""

                val stream = connection.inputStream

                return stream.bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                return "${e.message}"
            } finally {
                connection.disconnect()
            }
        }
    }
}