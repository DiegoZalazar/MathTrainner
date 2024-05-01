package mx.ipn.escom.TTA024.ui

//import android.renderscript.ScriptGroup.Input


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.TTA024.ui.navigation.AppNavigation
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme
import mx.ipn.escom.TTA024.ui.viewmodels.ModulosAdminViewModel

// hollaaa
//import org.json.JSONObject
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathTrainer()
                }
            }
        }
    }
}