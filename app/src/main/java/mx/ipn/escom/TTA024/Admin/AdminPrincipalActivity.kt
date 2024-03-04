package mx.ipn.escom.TTA024.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import mx.ipn.escom.TTA024.R

class AdminPrincipalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Principal()
                Text("Selecciona una opción")
                BotonUsuarios()
            }
        }
    }
}

@Preview
@Composable
fun BotonUsuarios(){
    val context= LocalContext.current
    Box(modifier= Modifier
        .width(400.dp)
        .height(400.dp)
        .background(Color.White)
        .shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
        )
    ) {
        Text(text="Consultar Usuarios", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontSize=22.sp, modifier= Modifier.align(Alignment.TopCenter))
        Image(painter = painterResource(id = R.drawable.usuarioicon),
            contentDescription = "usuario",
            modifier = Modifier
                .clickable {
                    val navigate =
                        Intent(
                            context,
                            AdminUsuariosActivity::class.java
                        )
                    context.startActivity(navigate)
                }
                .align(Alignment.Center))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Principal() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Bienvenido, ")
        }
    )


}
