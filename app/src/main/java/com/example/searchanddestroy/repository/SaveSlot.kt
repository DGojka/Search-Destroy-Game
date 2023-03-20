package com.example.searchanddestroy.repository

import com.example.searchanddestroy.ui.planningscreen.data.GameSettings

data class SaveSlot(val name: String,val settings: GameSettings){
    var id: Long = 0
}