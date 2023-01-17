package com.waigoma.voicetranslater.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.waigoma.voicetranslater.MainActivity
import com.waigoma.voicetranslater.R
import com.waigoma.voicetranslater.api.TranslateText
import com.waigoma.voicetranslater.databinding.FragmentRecognizeBinding
import com.waigoma.voicetranslater.api.VoiceRecognize.Companion as VoiceRecComp

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecognizeFragment : Fragment() {
    private val dataManager = MainActivity.settings
    private var _binding: FragmentRecognizeBinding? = null
    private lateinit var recText: TextView
    private lateinit var transText: TextView

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // 音声認識の結果を受け取るための Activity の起動
    private val recLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != Activity.RESULT_OK)
            return@registerForActivityResult

        val data = it.data
        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val spokenText = results?.get(0)

        recText.text = spokenText
        translate(spokenText!!, transText)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecognizeBinding.inflate(inflater, container, false)

        recText = binding.textviewRec
        transText = binding.textviewTrans

        checkMicPermission()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStartStop.setOnClickListener {
            checkMicPermission()
            speech()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * マイクへのアクセス権限を確認する
     */
    private fun checkMicPermission() {
        val granted = ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.RECORD_AUDIO)
        if (granted != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage(R.string.permission_record_audio_message)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
                    }
            } else {
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
            }
        }
    }

    /**
     * 音声認識を開始する
     */
    private fun speech() {
        val lang = dataManager.getLocaleSystemName(dataManager.data.RecognizeLang)
        val intent = VoiceRecComp.speech(lang, dataManager.data.offlineMode)

        try {
            recLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            recText.text = "エラーが発生しました。\n${e.message}"
        }
    }

    /**
     * 翻訳する
     * @param text 翻訳するテキスト
     * @param transTextView 翻訳結果を表示する TextView
     */
    private fun translate(text: String, transTextView: TextView) {
        val translateText = TranslateText()
        val source = dataManager.getLocaleSystemName(dataManager.data.RecognizeLang)
        val target = dataManager.getLocaleSystemName(dataManager.data.TranslateLang)
        translateText.translate(text, source, target, transTextView)
    }
}