package mx.ipn.escom.tta047.ui.estudianteUI.exercises

data class ExercisesUIState(
    val exercises: List<ExerciseUIState> = listOf(),
    val currIndex: Int = 0,
    val currExerciseUIState: ExerciseUIState = ExerciseUIState.FinishedExercises,
    val finished: Boolean = false,
    val correctExercises: Int = 0,
    val progress: Float = 0.0f,
    val seconds: Int = 0,
    val running: Boolean = true
)
