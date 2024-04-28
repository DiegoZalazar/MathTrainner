package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MathTrainerSplashScreen(
    navToHome: () -> Unit,
    navToLogin: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        Amplify.Auth.fetchAuthSession(
            {
                Log.i("Amplify", "Auth session = $it")
                if(it.isSignedIn){
                    GlobalScope.launch{
                        withContext(Dispatchers.Main) {
                            // Código que deseas ejecutar en el hilo principal
                            navToHome()
                        }
                    }
                }else{
                    GlobalScope.launch{
                        withContext(Dispatchers.Main) {
                            // Código que deseas ejecutar en el hilo principal
                            navToLogin()
                        }
                    }
                }
            },
            {
                error -> Log.e("Amplify", "Failed to fetch auth session", error)
                GlobalScope.launch{
                    withContext(Dispatchers.Main) {
                        // Código que deseas ejecutar en el hilo principal
                        navToLogin()
                    }
                }
            }
        )
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MathTrainer",
            style = TextStyle(
                fontSize = 32.sp,
                color = Color(0xFFD62839),
                fontWeight = FontWeight.Bold
            )
        )
        LinearProgressIndicator()

    }
}