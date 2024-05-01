package mx.ipn.escom.TTA024.ui.smallcomponents

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
//import com.google.accompanist.web.LoadingState
//import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LaTeXView(
    _latex: String,
    onClick: () -> Unit = {}
) {
    val latex = _latex.replace("\\", "\\\\")
    val color = MaterialTheme.colorScheme.onSecondary.toArgb()

    var webView: WebView? by remember { mutableStateOf(null) }
    /*
    val state = rememberWebViewState("file:///android_asset/latex_render.html")

    if (state.loadingState is LoadingState.Finished) {
        webView?.loadUrl("javascript:addBody('${latex}')")
    }
    com.google.accompanist.web.WebView(
        state = state,
        modifier = Modifier.clickable(onClick = onClick),
        onCreated = {
            it.settings.javaScriptEnabled = true
            webView = it
            it.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
            it.setOnTouchListener { _, event -> true }
            it.setBackgroundColor(0)
        }
    )*/
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun LatexViewPreview(){
    MaterialTheme{
        LaTeXView(_latex = "\\sin(x) \\cdot \\cos(y) \\cdot \\sin(x \\cdot y)")
    }
}