package com.example.searchanddestroy.sounds

import android.content.Context
import android.media.MediaPlayer
import com.example.searchanddestroy.R

class Player(private val context: Context) {
    private val players: MutableList<MediaPlayer> = mutableListOf()
    private val bombSound = MediaPlayer.create(context,SoundTrack.BOMB_SOUND.toRawWav())

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

    fun playBombSound(){
        bombSound.start()
        bombSound.isLooping = true
    }

    fun stopBombSound(){
        bombSound.isLooping = false
        bombSound.stop()
    }

    private fun SoundTrack.toRawWav(): Int {
        return when (this) {
            SoundTrack.BOMB_PLANTED -> R.raw.bomb_planted
            SoundTrack.BOMB_SOUND -> R.raw.bomb_sound
            SoundTrack.INTENSE_BOMB_SOUND -> R.raw.intense_bomb_sound
            SoundTrack.EXPLOSION -> R.raw.explosion
        }
    }
}