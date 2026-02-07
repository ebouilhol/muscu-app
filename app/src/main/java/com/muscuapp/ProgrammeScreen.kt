package com.muscuapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProgrammeScreen(viewModel: ProgrammeViewModel) {
    val programme = viewModel.programmeActif
    val semaineIndex = viewModel.semaineIndex

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = programme?.nom ?: "Programme",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.size(12.dp))
            SemaineSelector(
                semaines = programme?.semaines?.map { it.index } ?: emptyList(),
                selectedIndex = semaineIndex,
                onSelect = viewModel::changerSemaine
            )
            Spacer(modifier = Modifier.size(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val semaine = programme?.semaines?.firstOrNull { it.index == semaineIndex }
                val seances = semaine?.seances ?: emptyList()
                items(seances) { seance ->
                    SeanceCard(seance = seance, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun SemaineSelector(
    semaines: List<Int>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        semaines.forEach { index ->
            val isSelected = index == selectedIndex
            Button(onClick = { onSelect(index) }) {
                Text(text = "Semaine $index")
            }
        }
    }
}

@Composable
private fun SeanceCard(seance: Seance, viewModel: ProgrammeViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${seance.nom} • ${seance.jour}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(8.dp))
            seance.exercices.forEach { exercice ->
                ExerciceItem(exercice = exercice, viewModel = viewModel)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
private fun ExerciceItem(exercice: Exercice, viewModel: ProgrammeViewModel) {
    var seriesText by remember(exercice.id) { mutableStateOf(exercice.series.toString()) }
    var repetitionsText by remember(exercice.id) { mutableStateOf(exercice.repetitions.toString()) }
    var poidsText by remember(exercice.id) { mutableStateOf(exercice.poids.toString()) }

    Column {
        Text(text = exercice.nom, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.size(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = seriesText,
                onValueChange = { seriesText = it },
                label = { Text("Séries") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = repetitionsText,
                onValueChange = { repetitionsText = it },
                label = { Text("Répétitions") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = poidsText,
                onValueChange = { poidsText = it },
                label = { Text("Poids") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "Récup série: ${exercice.recuperationSerieSec}s • Récup exercice: ${exercice.recuperationExerciceSec}s"
        )
        Text(
            text = "Progression: +${exercice.progression.valeur}% ➜ ${viewModel.poidsProgression(exercice)}"
        )
        Spacer(modifier = Modifier.size(6.dp))
        Button(
            onClick = {
                val series = seriesText.toIntOrNull() ?: exercice.series
                val repetitions = repetitionsText.toIntOrNull() ?: exercice.repetitions
                val poids = poidsText.toDoubleOrNull() ?: exercice.poids
                viewModel.selectionnerExercice(exercice)
                viewModel.mettreAJourExercice(series, repetitions, poids)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enregistrer")
        }
    }
}
