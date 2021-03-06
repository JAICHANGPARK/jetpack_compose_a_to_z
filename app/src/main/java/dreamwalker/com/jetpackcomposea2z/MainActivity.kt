package dreamwalker.com.jetpackcomposea2z

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dreamwalker.com.jetpackcomposea2z.ui.theme.JetpackComposeA2ZTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    //    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//Compose 시작점
        setContent {
            val viewModel by viewModels<MainViewModel>()
            val scrollState = rememberScrollState()
            var isFavorite by rememberSaveable {
                mutableStateOf(false)
            }

            JetpackComposeA2ZTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center

                    ) {
                        Text(viewModel.data.value, fontSize = 30.sp)
                        Button(onClick = {
                            viewModel.changeValue("world")

                        }) {
                            Text("변경")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationTest() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "first",
    ) {
        composable("first") {
            FirstScreen(navController)
        }
        composable("second") {
            SecondScreen(navController)
        }
        composable("third/{value}") { backStackEntry ->
            ThirdScreen(
                value = backStackEntry.arguments?.getString("value") ?: "",
                navController = navController
            )
        }
    }
}

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Hello")
    val data: State<String> = _data
    fun changeValue(value: String){
        _data.value = value
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    val (value, setValue) = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("첫번째 화면")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController.navigate("second")
        }) {
            Text("두번째")
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextField(value = value, onValueChange = setValue)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            if (value.isNotEmpty()) {
                navController.navigate("third/$value")
            }
        }) {
            Text("세번째")
        }
    }
}


@Composable
fun SecondScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("두번째 화면")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /*TODO*/

            navController.popBackStack()
        }) {
            Text("뒤로가기")
        }

    }
}


@Composable
fun ThirdScreen(value: String, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("세번째 화면")
        Spacer(modifier = Modifier.height(24.dp))
        Text("")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("뒤로가기")
        }

    }
}


@ExperimentalComposeUiApi
@Composable
fun TextFieldTest() {
//            val textValue : MutableState= remember {
//                mutableStateOf("")
//            }
    val (text, setValue) = remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()
    //스낵바를 실행기위해서는 코루틴에서 실행해야함.
    val scope = rememberCoroutineScope()
    //키보드 활용 현재 실험적 기능
    val keyboardController = LocalSoftwareKeyboardController.current
    // 스낵바를 이용하려면 scaffold 활용
    Scaffold(
        scaffoldState = scaffoldState

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TextField(value = text, onValueChange = setValue)
            Button(onClick = {
                //suspense는 코루틴에서 실행
                keyboardController?.hide()
                scope.launch {

                    scaffoldState.snackbarHostState.showSnackbar("Hello World : $text")
                }


            }) {
                Text("클릭")
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onTabFavorite: (Boolean) -> Unit,
) {

    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            Image(
                painter = rememberImagePainter("https://cdn.pixabay.com/photo/2021/09/15/15/48/seals-6627197__340.jpg"),
                contentDescription = "Sample Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onTabFavorite(!isFavorite)
//                    isFavorite = !isFavorite
                }) {
                    Icon(
                        imageVector =
                        if (isFavorite)
                            Icons.Default.Favorite else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Fav",
                        tint = Color.White
                    )
                }
            }
        }

    }
}

@Composable
fun LazyColumnTest() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        item {
            Text("Header")
        }
        items(50) { index ->
            Text("HELLO $index")
        }
        item {
            Text("Footer")
        }
    }
}

@Composable
fun ColumnWithScroll() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.Green)
            .fillMaxWidth()
//            .verticalScroll(state = scrollState)
    ) {
        Text("Hello")
        Text("Hello 1")
        Text("Hello 2")
        Text("Hello 3")
        for (i in 1..50) {
            Text("Hello $i")
        }
    }
}

@Composable
fun BoxCompose() {
    Box(
        modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.TopEnd

    ) {
        Text("Hello")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text("World---")
        }
    }
}

@Composable
@Preview
fun ColumnTest() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Hello", color = Color.White)
        Spacer(Modifier.height(16.dp))
        Text(text = "wow")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeA2ZTheme {
        Greeting("Android")
    }
}