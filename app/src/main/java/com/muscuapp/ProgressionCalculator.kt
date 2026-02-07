package com.muscuapp

import kotlin.math.round

object ProgressionCalculator {
    fun appliquerProgression(poids: Double, pourcentage: Double): Double {
        val resultat = poids * (1 + pourcentage / 100)
        return round(resultat * 100) / 100
    }
}
