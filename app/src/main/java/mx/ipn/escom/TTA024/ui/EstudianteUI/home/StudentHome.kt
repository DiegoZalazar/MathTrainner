package mx.ipn.escom.TTA024.ui.EstudianteUI.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExerciseNavScreens
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExercisesScreenViewModel
import mx.ipn.escom.TTA024.ui.StudentScreens

@Composable
fun StudentHome(
    studentVM: StudentHomeViewModel = viewModel(),
    exercisesScreenViewModel: ExercisesScreenViewModel,
    navController: NavController
) {
    var studentHomeUIState = studentVM.studentHomeUIState
    var token by remember { mutableStateOf("") }

    var userName by remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            val name = attributes.find { it.key == AuthUserAttributeKey.name() }
            Log.i("AuthDemo", "User attributes = $attributes")
            Log.i("AuthDemo", name?.value?:"no se pudo obtener el nombre")
            userName = name?.value?:"no se pudo obtener el nombre"

            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            token = session.tokensResult.value?.idToken?: "no hay token"
            studentVM.updateToken(token)
            studentVM.getModulos()
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }
    }

    var closeSesionLoading by remember { mutableStateOf(false) }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
            }
            navController.navigate("login"){
                popUpTo("student") { inclusive = true }
            }
            closeSesionLoading = false
        }
    }

    var temas by remember {
        mutableStateOf(
            listOf(
                    "Cálculo Diferencial",
                    "Cálculo Integral",
            )
        )
    }



    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(temas[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { StudentModalDrawer(
                items = temas,
                selected = selectedItem.value,
                onClicItem = {
                    scope.launch { drawerState.close() }
                    selectedItem.value = it
                },
                userName = userName,
                onClicCloseSession = { closeSesionLoading = true }
            )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.09f)
                    .clickable { scope.launch { drawerState.open() } },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                Spacer(Modifier.width(12.dp))
                Text(text = selectedItem.value, style = MaterialTheme.typography.headlineSmall)
            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                when(studentHomeUIState){
                    is StudentHomeUIState.Error -> ErrorScreen {
                        studentVM.getModulos()
                    }
                    is StudentHomeUIState.Loading -> CircularProgressIndicator()
                    is StudentHomeUIState.Success -> {
                        Log.i("StudentHome", studentHomeUIState.modulos.toString())
                        temas = updateTemas(studentHomeUIState.modulos)
                        ListModulos(
                            modulos = filterModulosByTema(studentHomeUIState.modulos, selectedItem.value),
                            scope = scope,
                            navToExercises = {
                                scope.launch {
                                    studentVM.getEjerciciosAndUpdateExercisesVM(it, exercisesScreenViewModel)
                                    navController.navigate(ExerciseNavScreens.Exercises.name)
                                }
                            },
                            navToLeccion = {
                                studentVM.getLeccion(it)
                                navController.navigate(StudentScreens.Leccion.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

fun updateTemas(modulos: List<ModuloUI>): List<String> {
    val temas: MutableSet<String> = mutableSetOf()
    for(modulo in modulos){
        temas.add(modulo.modulo.tema)
    }
    return temas.distinct()
}

fun filterModulosByTema(modulos: List<ModuloUI>, tema: String): List<ModuloUI> {
    val res: MutableList<ModuloUI> = mutableListOf()
    for(modulo in modulos) {
        if(modulo.modulo.tema == tema){
            res.add(modulo)
        }
    }
    return res
}

@Composable
fun ErrorScreen(
    retry: () -> Unit
){
    Column(){
        Text("ocurrio un error, vuelve a intentar o cierra la sesion")
        Spacer(Modifier.height(12.dp))
        Button(onClick = retry) {
            Text("Reintentar")
        }
    }
}

@Composable
fun ListModulos(
    modulos: List<ModuloUI>,
    scope: CoroutineScope,
    navToExercises: (Int) -> Unit,
    navToLeccion: (Int) -> Unit
){
    Column {
        for (modulo in modulos){
            ModuloItem(
                moduloUi = modulo,
                scope = scope,
                navToExercises = navToExercises,
                navToLeccion = navToLeccion
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun ModuloItem (
    moduloUi: ModuloUI,
    scope: CoroutineScope,
    navToExercises: (Int) -> Unit,
    navToLeccion: (Int) -> Unit
) {
    val pos = moduloUi.pos
    val values = arrayOf(0,1,2,1)
    val posWidth = values[((pos%4))] * 100.dp
    val r = painterResource(id = R.drawable.modulodumbell)
    val tooltipState = rememberTooltipState(isPersistent = true)
    Row(
        modifier = Modifier
            .width(300.dp)
            .padding(0.dp)
    ){
        Spacer(modifier = Modifier.width(posWidth))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(){
                        ModuloTooltipContent(
                            title = moduloUi.modulo.nombreModulo, 
                            toLeccion = {
                                scope.launch { tooltipState.dismiss() }
                                navToLeccion(moduloUi.modulo.idModulo)
                            },
                            toExercises = {
                                scope.launch { tooltipState.dismiss() }
                                navToExercises(moduloUi.modulo.idModulo)
                            }
                        )
                    }
                },
                state = tooltipState
            ){
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { scope.launch { tooltipState.show() } },
                    contentAlignment = Alignment.Center,
                ){
                    CircularProgressIndicator(
                        progress = {moduloUi.modulo.avance},
                        modifier = Modifier.fillMaxSize()
                    )
                    Image(
                        painter = r,
                        contentDescription = "modulo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(top = 1.dp)
                            .padding(8.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ModuloTooltipContent(
    title: String,
    toLeccion: () -> Unit,
    toExercises: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(onClick = toLeccion, modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(text = "Ver leccion")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = toExercises, modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(text = "Iniciar ejercicios")
        }

    }
}

@Composable
fun StudentModalDrawer(
    items : List<String>,
    selected: String,
    onClicItem: (String) -> Unit,
    userName: String,
    onClicCloseSession: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            contentAlignment = Alignment.BottomStart
        ){
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                for (item in items) {
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = item == selected,
                        onClick = { onClicItem(item) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(Modifier.height(12.dp))
            }
        }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.Person, "person", modifier = Modifier.size(40.dp))
                    Column(Modifier.padding(12.dp)) {
                        Text(text = "Bienvenido")
                        Text(text = userName, fontWeight = FontWeight.Bold)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(onClick = { /*TODO*/ }) {
                        Text("Configuracion")
                    }
                    Spacer(Modifier.width(12.dp))
                    OutlinedButton(onClick = { onClicCloseSession() }) {
                        Text("Cerrar Sesion")
                    }
                }
            }
        }
    }
}