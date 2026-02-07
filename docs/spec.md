# Spécification JSON (programme musculation)

Ce document décrit le schéma JSON utilisé par l'application.

## Principes
- **Progression par exercice** et **uniquement sur le poids**.
- **Historique des séances** conservé localement dans le JSON.
- **Planning par semaine** avec plusieurs séances.

## Champs principaux
- `metadata`: informations globales (langue, unité de poids, version).
- `programmes`: liste de programmes (plusieurs semaines, plusieurs séances).
- `historique`: journal des séances réalisées et de la progression appliquée.

## Règles métier recommandées
- Lors de l'affichage, l'utilisateur peut modifier `poids`, `series`, `repetitions`.
- La progression appliquée au poids se calcule par semaine :
  - `poidsApres = poidsAvant * (1 + pourcentage / 100)`.

## Fichiers fournis
- Schéma JSON : `docs/schema.json`
- Exemple complet : `examples/programme-exemple.json`
