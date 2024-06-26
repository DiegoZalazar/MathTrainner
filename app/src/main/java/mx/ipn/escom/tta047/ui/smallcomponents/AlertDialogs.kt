package mx.ipn.escom.tta047.ui.smallcomponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import mx.ipn.escom.tta047.ui.theme.MathTrainerTheme

data class SignInAlertState(
    val title: String = "",
    val msg: String = "",
    val error: Boolean = false,
    val show: Boolean = false
)

@Composable
fun SignInAlert(
    state: SignInAlertState,
    dismiss: () -> Unit
){
    Dialog(
        onDismissRequest = { dismiss() }
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                val contentColor = if(state.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                Text(text = state.title, style = MaterialTheme.typography.headlineSmall, color = contentColor)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = state.msg)
            }
        }
    }
    Log.i("SignInScreen", "alert messagge{ title:${state.title}, msg:${state.msg} }")
}

@Composable
fun ConfirmAlert(
    confirmQuestion: String = "Estas eguro??",
    confirmAlertMsg: String = "Se borrara todo",
    onDimiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
){
    Dialog(
        onDismissRequest = onDimiss
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = confirmQuestion, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = confirmAlertMsg)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onConfirm
                ) {
                    Text("Si, confimar")
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(onClick = onDimiss) {
                    Text("No, cancelar")
                }

            }
        }
    }
}

@Preview(device = "id:pixel_5")
@Composable
fun SignInAlertPreview(){
    val signInAlertState = SignInAlertState(title = "Titulo", msg = "Este es el mensaje", error = false, show = true)
    MathTrainerTheme {
        SignInAlert(state = signInAlertState) {}
    }
}