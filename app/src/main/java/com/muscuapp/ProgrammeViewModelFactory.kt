package com.muscuapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProgrammeViewModelFactory(private val programmeFile: ProgrammeFile) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgrammeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgrammeViewModel(programmeFile) as T
        }
        throw IllegalArgumentException("ViewModel inconnu")
    }
}
