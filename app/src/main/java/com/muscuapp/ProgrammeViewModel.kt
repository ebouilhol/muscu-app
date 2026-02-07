package com.muscuapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProgrammeViewModel(private val programmeFile: ProgrammeFile) : ViewModel() {
    var programmeActif: Programme? by mutableStateOf(programmeFile.programmes.firstOrNull())
        private set

    var semaineIndex: Int by mutableStateOf(1)
        private set

    var exerciceSelectionne: Exercice? by mutableStateOf(null)
        private set

    fun changerSemaine(index: Int) {
        semaineIndex = index
    }

    fun selectionnerExercice(exercice: Exercice) {
        exerciceSelectionne = exercice
    }

    fun mettreAJourExercice(serie: Int, repetition: Int, poids: Double) {
        val programme = programmeActif ?: return
        val semaine = programme.semaines.find { it.index == semaineIndex } ?: return
        val exercice = exerciceSelectionne ?: return
        val nouvelleListe = semaine.seances.map { seance ->
            seance.copy(
                exercices = seance.exercices.map { item ->
                    if (item.id == exercice.id) {
                        item.copy(series = serie, repetitions = repetition, poids = poids)
                    } else {
                        item
                    }
                }
            )
        }
        val nouvellesSemaines = programme.semaines.map { item ->
            if (item.index == semaine.index) {
                item.copy(seances = nouvelleListe)
            } else {
                item
            }
        }
        programmeActif = programme.copy(semaines = nouvellesSemaines)
        exerciceSelectionne = exerciceSelectionne?.copy(series = serie, repetitions = repetition, poids = poids)
    }

    fun poidsProgression(exercice: Exercice): Double {
        return ProgressionCalculator.appliquerProgression(exercice.poids, exercice.progression.valeur)
    }
}
