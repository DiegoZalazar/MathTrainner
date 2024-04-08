package mx.ipn.escom.TTA024.ui.estudiante

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.data.Module
import mx.ipn.escom.TTA024.ui.data.modules
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun PathScreen(
    modifier: Modifier = Modifier
){
    var pos = 0;
    Scaffold(
        topBar = {
            TopBar()
        },
    ){
            it -> LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = it,
        horizontalAlignment = Alignment.CenterHorizontally
//            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(modules){
            ModuleItem(
                m = it,
                pos = pos++,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
    }
}

@Composable
fun ModuleItem(
    m: Module,
    pos: Int,
    modifier: Modifier = Modifier
){
    val values = arrayOf(0,1,2,1)
    val posWidth = values[((pos%4))]*100.dp
    val r = if (m.enable) painterResource(id = R.drawable.modulo_done) else painterResource(id = R.drawable.modulo_disable)
    Row(
        modifier = Modifier
            .width(300.dp)
            .padding(0.dp)
    ){
        Spacer(modifier = Modifier.width(posWidth))
        Box(
            modifier = Modifier
                .size(100.dp)
        ){
            Image(
                painter = r,
                contentDescription = "modulo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }

    Spacer(modifier = Modifier.height(30.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reglas de derivación",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun PathScreenPreview(){
    MathTrainerTheme(){
        PathScreen()
    }
}