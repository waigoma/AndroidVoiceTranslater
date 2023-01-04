package com.waigoma.voicetranslater

import android.content.Intent
import android.speech.RecognizerIntent
import java.util.*

class VoiceRecognize {
    companion object {
        enum class Lang {
            JAPANESE,
            ENGLISH,
            OFFLINE,
            OTHER
        }

        fun speech(langType: Lang): Intent {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            when (langType) {
                Lang.JAPANESE -> intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE.toString())

                Lang.ENGLISH -> intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toString())

                Lang.OFFLINE -> intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)

                Lang.OTHER -> intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            }

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力してください")

            return intent
        }
    }
}