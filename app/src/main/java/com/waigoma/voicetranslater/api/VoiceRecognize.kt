package com.waigoma.voicetranslater.api

import android.content.Intent
import android.speech.RecognizerIntent

class VoiceRecognize {
    companion object {
        /**
         * 音声認識を行う。
         *
         * @return 音声認識の Intent
         */
        fun speech(lang: String, mode: Boolean): Intent {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            if (mode)
                intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang)

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力してください")

            return intent
        }
    }
}