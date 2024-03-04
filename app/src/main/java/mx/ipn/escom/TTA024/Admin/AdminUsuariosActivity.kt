package mx.ipn.escom.TTA024.Admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.TTA024.Estudiante
import mx.ipn.escom.TTA024.R

class AdminUsuariosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Usuarios()
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun RowScope.TableCellImage(
    image: Int,
    tamano: Float,
    clase: Class<*>
) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = image),
        contentDescription = "usuario",
        modifier = Modifier
            .clickable {
                val navigate =
                    Intent(
                        context,
                        clase
                    )
                context.startActivity(navigate)
            }
            .weight(tamano)
            .height(10.dp)

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Usuarios() {
    // Just a fake data... a Pair of Int and String
    val headers = arrayOf("Id", "Nombre", "Eliminar", "Editar")
    val estudiante1 = Estudiante(1, "adal", "danidc", "halo_chif@hotmail.com", "activo")
    val estudiante2 = Estudiante(2, "adal2", "danidc2", "halo_chif@hotmail.com2", "activo2")
    val estudiante3 = Estudiante(3, "adal3", "danidc3", "halo_chif@hotmail.com3", "activo3")
    val estudianteList = listOf<Estudiante>(estudiante1, estudiante2, estudiante3)
    // Each cell of a column must have the same weight.
    val ancho = 300
    val columsWeight = (ancho / headers.size).toFloat()
    // The LazyColumn will be our table. Notice the use of the weights below

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Usuarios")
            }
        )

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            // Here is the header
            item() {

                Row(Modifier.background(Color.Gray)) {
                    for (header in headers) {
                        TableCell(text = header, weight = columsWeight)
                    }
                }

            }
            // Here are all the lines of your table.
            items(estudianteList) {
                val estudiante = it
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = estudiante.idEstudiante.toString(), weight = columsWeight)
                    TableCell(text = estudiante.nombreUsuario, weight = columsWeight)
                    TableCellImage(
                        image = R.drawable.deleteicon,
                        tamano = columsWeight,
                        clase = AdminDeleteUserActivity::class.java
                    )
                    TableCellImage(
                        image = R.drawable.deleteicon,
                        tamano = columsWeight,
                        clase = AdminDeleteUserActivity::class.java
                    )
                }
            }
        }
    }
}