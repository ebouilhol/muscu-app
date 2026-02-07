package com.muscuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val programme = ProgrammeRepository(this).loadProgramme()
        val factory = ProgrammeViewModelFactory(programme)
        setContent {
            val viewModel: ProgrammeViewModel = viewModel(factory = factory)
            ProgrammeScreen(viewModel = viewModel)
        }
    }
}
