package com.muscuapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgrammeFile(
    val metadata: Metadata,
    val programmes: List<Programme>,
    val historique: List<SeanceHistorique>
)

@Serializable
data class Metadata(
    val langue: String,
    val unitePoids: String,
    val version: String
)

@Serializable
data class Programme(
    val id: String,
    val nom: String,
    val semaines: List<Semaine>
)

@Serializable
data class Semaine(
    val index: Int,
    val seances: List<Seance>
)

@Serializable
data class Seance(
    val id: String,
    val nom: String,
    val jour: String,
    val exercices: List<Exercice>
)

@Serializable
data class Exercice(
    val id: String,
    val nom: String,
    val series: Int,
    val repetitions: Int,
    val poids: Double,
    val recuperationSerieSec: Int,
    val recuperationExerciceSec: Int,
    val progression: Progression
)

@Serializable
data class Progression(
    val type: String,
    val valeur: Double
)

@Serializable
data class SeanceHistorique(
    val id: String,
    val seanceId: String,
    val programmeId: String,
    val dateISO: String,
    val exercices: List<ExerciceHistorique>
)

@Serializable
data class ExerciceHistorique(
    val exerciceId: String,
    val series: Int,
    val repetitions: Int,
    val poids: Double,
    @SerialName("progressionAppliquee")
    val progressionAppliquee: ProgressionAppliquee
)

@Serializable
data class ProgressionAppliquee(
    val pourcentage: Double,
    val poidsAvant: Double,
    val poidsApres: Double
)
