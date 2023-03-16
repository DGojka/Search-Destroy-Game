package com.example.searchanddestroy.sounds

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

class Speaker(
    val context: Context,
    locale: Locale = Locale.ENGLISH,
    pitch: Float = 1f,
    speechSpeed: Float = 0.9f,
) {
    lateinit var textToSpeech: TextToSpeech
    private var isInitializedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val languageResult = textToSpeech.setLanguage(locale)
                if (languageResult == TextToSpeech.LANG_MISSING_DATA || languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "Language not supported")
                } else {
                    val voice = Voice(
                        "en-us-x-iob-local",
                        Locale("en", "US"),
                        1000,
                        0,
                        true,
                        emptySet()
                    )
                    textToSpeech.voice = voice
                    textToSpeech.setPitch(pitch)
                    textToSpeech.setSpeechRate(speechSpeed)
                    GlobalScope.launch {
                        isInitializedFlow.emit(true)
                    }
                }
            }
        }
    }

    suspend fun say(text: String = "", stringId: Int? = null) {
        val tts = if (stringId == null) {
            text
        } else {
            context.getString(stringId)
        }

        isInitializedFlow
            .filter { it }
            .onEach {
                delay(500)
            }
            .first()

        textToSpeech.speak(
            tts,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }


    companion object {
        val TAG = "Speaker"
        val LOCALE_PL = Locale("pl_PL")

        //todo nice voice lines
        //en-us-x-sfg-network
        //en-us-x-iob-local
        //en-us-x-tpc-local
        //en-us-x-tpd-network
        //en-us-x-iom-local
        //en-us-x-iom-network
    }

    //nice
    //en-us-x-iob-local - female
    //en-us-x-tpc-local - calm female
    //en-us-x-iom-local - calm male
}
