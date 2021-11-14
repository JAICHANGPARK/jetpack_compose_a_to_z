package dreamwalker.com.jetpackcomposea2z

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dreamwalker.com.jetpackcomposea2z.ui.theme.JetpackComposeA2ZTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            //Compose 시작점
            val scrollState = rememberScrollState()
            var isFavorite by rememberSaveable {
                mutableStateOf(false)
            }
            val navController = rememberNavController()

            JetpackComposeA2ZTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}


@Composable
fun FirstScreen(){

}


@Composable
fun SecondScreen(){

}


@Composable
fun ThirdScreen(){

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