package mx.ipn.escom.tta047.ui

//import android.renderscript.ScriptGroup.Input


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import mx.ipn.escom.tta047.ui.estudianteUI.estudianteconfig.EstudianteConfigVM
import mx.ipn.escom.tta047.ui.estudianteUI.home.StudentHomeViewModel
import mx.ipn.escom.tta047.ui.theme.MathTrainerTheme
import mx.ipn.escom.tta047.ui.viewmodels.AdminEjerciciosViewModel
import mx.ipn.escom.tta047.ui.viewmodels.AdminLeccionesViewModel
import mx.ipn.escom.tta047.ui.viewmodels.AdminUsuariosViewModel
import mx.ipn.escom.tta047.ui.viewmodels.ModulosAdminViewModel

// hollaaa
//import org.json.JSONObject
class MainActivity : ComponentActivity() {
    private val adminModuloViewModel: ModulosAdminViewModel by viewModels()
    private val adminLeccionesViewModel: AdminLeccionesViewModel by viewModels()
    private val adminEjerciciosViewModel: AdminEjerciciosViewModel by viewModels()
    private val adminUsuariosViewModel: AdminUsuariosViewModel by viewModels()
    private val studentHomeViewModel: StudentHomeViewModel by viewModels()
    private val estudianteConfigVM: EstudianteConfigVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathTrainer(
                        modulosAdminViewModel = adminModuloViewModel,
                        adminLeccionesViewModel = adminLeccionesViewModel,
                        adminEjerciciosViewModel = adminEjerciciosViewModel,
                        studentHomeViewModel = studentHomeViewModel,
                        adminUsuariosViewModel = adminUsuariosViewModel,
                        estudianteConfigVM = estudianteConfigVM
                    )
                }
            }
        }
    }
}
