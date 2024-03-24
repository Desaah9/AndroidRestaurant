package fr.isen.desauvage.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.desauvage.androidrestaurant.ui.theme.AndroidRestaurantTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidRestaurantTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Greeting(::onMenuClick)
                }
            }
        }
    }

    private fun onMenuClick(menu: String) {
        Toast.makeText(this, menu, Toast.LENGTH_LONG).show()
        val intent = Intent(this, MenuActivity::class.java).apply {
            putExtra(MenuActivity.CATEGORY_KEY, menu)
        }
        startActivity(intent)
    }
}

@Composable
fun Greeting(onMenuClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#E1D2B8"))) // beige
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.le_desaah),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp)
                .wrapContentSize(align = Alignment.Center)
        )

        Spacer(modifier = Modifier.height(32.dp))
        StyledButton(onClick = { onMenuClick("Entrées") }, buttonText = "Entrées")
        StyledButton(onClick = { onMenuClick("Plats") }, buttonText = "Plats")
        StyledButton(onClick = { onMenuClick("Desserts") }, buttonText = "Desserts")

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Nous contacter : 07 83 63 94 24",
            fontSize = 24.sp,
            color = Color(android.graphics.Color.parseColor("#1F3855")), // bleu
            modifier = Modifier
        )
    }
}

@Composable
fun StyledButton(onClick: () -> Unit, buttonText: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#1F3855"))
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = buttonText,
            color = Color.LightGray
        )
    }
}









/*import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.desauvage.androidrestaurant.ui.theme.AndroidRestaurantTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidRestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    Greeting("Android",::onMenuClick)
                }
            }
        }
    }

    private fun onMenuClick(menu: String)
    {
        Toast.makeText(this,menu,Toast.LENGTH_LONG).show()
        val intent = Intent(this, MenuActivity::class.java).apply{
            putExtra(MenuActivity.CATEGORY_KEY, menu)
        }
        startActivity(intent)
    }
}

@Composable
fun Greeting(name: String, onMenuClick: (String) -> Unit){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#E1D2B8"))) //beige
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.le_desaah),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp)
                .wrapContentSize(align = Alignment.Center)
        )

        Spacer(modifier = Modifier.height(32.dp))
        StyledButton(onClick = { onMenuClick("Entrées") }, buttonText = "Entrées")
        StyledButton(onClick = { onMenuClick("Plats") }, buttonText = "Plats")
        StyledButton(onClick = { onMenuClick("Desserts") }, buttonText = "Desserts")

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Nous contacter : 07 83 63 94 24",
            fontSize = 24.sp,
            color = Color(android.graphics.Color.parseColor("#1F3855")),//bleu
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
        )

    }
}


@Composable
fun StyledButton(onClick: () -> Unit, buttonText: String){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#1F3855"))),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 25.dp,
            pressedElevation = 30.dp
        )
    ) {
        Text(
            text = buttonText,
            color = Color.LightGray
            /*fontWeight = FontWeight.Bold,
            fontSize = 18.sp,*/
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidRestaurantTheme {
        Greeting("android", {})
    }
}
*/
