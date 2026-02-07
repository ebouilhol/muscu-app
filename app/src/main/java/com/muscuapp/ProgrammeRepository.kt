package com.muscuapp

import android.content.Context
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ProgrammeRepository(private val context: Context) {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    fun loadProgramme(assetName: String = "programme.json"): ProgrammeFile {
        val raw = context.assets.open(assetName).bufferedReader().use { it.readText() }
        return json.decodeFromString<ProgrammeFile>(raw)
    }

    fun saveHistorique(historique: List<SeanceHistorique>, fileName: String = "historique.json") {
        val file = context.filesDir.resolve(fileName)
        val payload = json.encodeToString(ListSerializer(SeanceHistorique.serializer()), historique)
        file.writeText(payload)
    }
}
