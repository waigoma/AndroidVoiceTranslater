package com.waigoma.voicetranslater

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController

import com.waigoma.voicetranslater.databinding.FragmentFirstBinding
import com.waigoma.voicetranslater.VoiceRecognize.Companion as VoiceRecComp

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private lateinit var textView: TextView
    private lateinit var recLang: VoiceRecComp.Lang

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != Activity.RESULT_OK)
            return@registerForActivityResult

        val data = it.data
        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val spokenText = results?.get(0)

        textView.text = spokenText
    }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        textView = binding.textviewFirst

        checkMicPermission()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recLang = VoiceRecComp.Lang.JAPANESE

        binding.buttonStartStop.setOnClickListener {
            checkMicPermission()
            speech(recLang)
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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
        val granted = ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.RECORD_AUDIO)
        if (granted != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage(R.string.permission_record_audio_message)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
                    }
            } else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.RECORD_AUDIO), 1)
            }
        }
    }

    private fun speech(langType: VoiceRecComp.Lang) {
        val intent = VoiceRecComp.speech(langType)

        try {
            resultLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            textView.text = "エラーが発生しました。\n${e.message}"
        }
    }
}