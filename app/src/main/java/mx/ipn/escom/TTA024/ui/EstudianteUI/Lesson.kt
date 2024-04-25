package mx.ipn.escom.TTA024.ui.EstudianteUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lesson (
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
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
    ){
            it -> LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = it,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
            Image(
                painter = painterResource(id = R.drawable.videopng),
                contentDescription = "video",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }

        item {
            Text(text = stringResource(id = R.string.lesson_content),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp))

        }


    }
    }

}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun LessonPreview(){
    MathTrainerTheme {
        Lesson()
    }
}