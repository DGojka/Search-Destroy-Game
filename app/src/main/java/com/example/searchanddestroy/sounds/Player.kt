package com.example.searchanddestroy.sounds

import android.content.Context
import android.media.MediaPlayer
import com.example.searchanddestroy.R

class Player(private val context: Context) {
    private val players: MutableList<MediaPlayer> = mutableListOf()


    fun playSound(soundTrack: SoundTrack) {
        val mp = MediaPlayer.create(context, soundTrack.toRawWav()).apply {
            setOnCompletionListener { mp ->
                mp.reset()
                mp.release()
                players.remove(mp)
            }
        }
        players.add(mp)
        mp.start()
    }

    private fun SoundTrack.toRawWav(): Int {
        return when (this) {
            SoundTrack.BOMB_PLANTED -> R.raw.bomb_planted
        }
    }
}