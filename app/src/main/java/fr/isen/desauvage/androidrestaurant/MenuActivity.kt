package fr.isen.desauvage.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desauvage.androidrestaurant.model.Items
import fr.isen.desauvage.androidrestaurant.model.Result
import fr.isen.desauvage.androidrestaurant.ui.theme.AndroidRestaurantTheme
import org.json.JSONObject

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryTitle = intent.getStringExtra(CATEGORY_KEY) ?: ""

        setContent {
            AndroidRestaurantTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(android.graphics.Color.parseColor("#E1D2B8"))) {
                    val itemsState = remember { mutableStateListOf<Items>() }

                    MenuContent(category = categoryTitle, itemsState = itemsState) { selectedDish ->
                        val intent = Intent(this@MenuActivity, DetailActivity::class.java)
                        val selectedDishJson = Gson().toJson(selectedDish)
                        intent.putExtra(DetailActivity.DISH_KEY, selectedDishJson)
                        startActivity(intent)
                    }
                    fetchData(categoryTitle, itemsState)
                }
            }
        }

    }

    companion object {
        const val CATEGORY_KEY = "category"
    }

    private fun fetchData(categoryTitle: String, itemsState: SnapshotStateList<Items>) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                Log.d("CategoryActivity", "les donnees en brut : $response")
                val result = Gson().fromJson(response.toString(), Result::class.java)
                val itemFromCategory = result.data.find { it.nameFr == categoryTitle }?.items ?: emptyList()
                itemsState.addAll(itemFromCategory)
            },
            { error ->
                Log.e("CategoryActivity", "Erreur : $error")
            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)

    }
}

@Composable
fun DishListComponent(dishes: List<Items>, goToDetail: (Items) -> Unit) {
    LazyColumn {
        items(dishes) { dish ->
            DishRowWithPrice(item = dish, goToDetail = goToDetail)
        }
    }
}

@Composable
fun ImageComponent(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    width: Dp
) {
    val painter: Painter = rememberAsyncImagePainter(imageUrl)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .width(width = width),
    )
}

@Composable
fun DishRowWithPrice(item: Items, goToDetail: (Items) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(android.graphics.Color.parseColor("#E1D2B8")))
            .clickable { goToDetail(item) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp, 70.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = item.images.lastOrNull() ?: "",
                placeholder = painterResource(id = R.drawable.lost_bis_foreground),
                error = painterResource(id = R.drawable.lost_bis_foreground),
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                item.nameFr?.let { itemName ->
                    Text(
                        text = itemName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color(android.graphics.Color.parseColor("#E1D2B8")),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

                item.prices?.forEach { price ->
                    Text(
                        text = "${price.price} €",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(android.graphics.Color.parseColor("#E1D2B8")),
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MenuContent(category: String, itemsState: SnapshotStateList<Items>, goToDetail: (Items) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#E1D2B8")))
            .padding(16.dp)
    ) {
        Text(
            text = category,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(android.graphics.Color.parseColor("#1F3855")),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        DishListComponent(dishes = itemsState, goToDetail = goToDetail)
    }
}




/*import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desauvage.androidrestaurant.model.Items
import fr.isen.desauvage.androidrestaurant.model.Result
import fr.isen.desauvage.androidrestaurant.ui.theme.AndroidRestaurantTheme
import org.json.JSONObject

data class Plat(val name: String, val description: String)






class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryTitle = intent.getStringExtra(CATEGORY_KEY) ?:""

        setContent {
            AndroidRestaurantTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val itemsState = remember { mutableStateListOf<Items>() }

                    MenuContent(category = categoryTitle.orEmpty(), itemsState = itemsState) { selectedDish ->
                        val intent = Intent(this, DetailActivity::class.java)
                        val selectedDishJson = Gson().toJson(selectedDish)
                        intent.putExtra(DetailActivity.DISH_KEY, selectedDishJson)
                        startActivity(intent)
                    }
                    fetchData(categoryTitle, itemsState)
                }
            }
        }

    }
    companion object
    {
        const val CATEGORY_KEY = "category"
    }

    private fun fetchData(categoryTitle: String, itemsState: SnapshotStateList<Items>)
    {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            {
                Log.d("CategoryActivity", "les donnees en brut : $it")
                val result = Gson().fromJson(it.toString(), Result::class.java)
                val itemFromCategory = result.data.find { it.nameFr == categoryTitle }?.items ?: emptyList()
                itemsState.addAll(itemFromCategory)
            },
            {  // TODO: Handle err
                Log.e("CategoryActivity", "Erreur : $it")
            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)

    }

    private fun goToDetail(dish: Items){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("dish", dish.toString())
        startActivity(intent)
    }
}

@Composable
fun DishListComponent(dishes: SnapshotStateList<Items>, goToDetail: (Items) -> Unit) {
    LazyColumn {
        items(dishes) { dish ->
            DishRowWithPrice(dish, goToDetail)
        }
    }
}

@Composable
fun DishRowWithPrice(item: Items, goToDetail: (Items) -> Unit) {
    val quantityState = remember { mutableStateOf(1)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(android.graphics.Color.parseColor("#E1D2B8")))//background 3e page
            .clickable { goToDetail(item) },
        shape = RoundedCornerShape(16.dp)


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp, 70.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = item.images.lastOrNull() ?: "",
                placeholder = painterResource(id = R.drawable.lost_foreground),
                error = painterResource(id = R.drawable.lost_foreground),
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item.nameFr?.let {
                    Text(
                        text = it,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = (Color(android.graphics.Color.parseColor("#E1D2B8"))), //Couleur Ecriture plat
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Afficher les prix
                item.prices?.forEach { price ->
                    Text(
                        text = "${price.price} €", // Supposons que le prix soit en euros
                        fontSize = 18.sp,
                        color = (Color(android.graphics.Color.parseColor("#E1D2B8"))), //couleur
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp) // Modifier le padding comme vous le souhaitez
                    )
                }
            }
        }
    }
}

@Composable
fun MenuContent(category: String, itemsState: SnapshotStateList<Items>, goToDetail: (Items) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#E1D2B8"))) //couleur de fond 2e page
            .padding(16.dp),
    )
    {
        Text(
            text = category,
            fontSize = 32.sp, // Police plus grande pour le titre
            color = (Color(android.graphics.Color.parseColor("#1F3855"))), //couleur ecriture Top
            fontWeight = FontWeight.Bold, // Texte en gras pour attirer l'attention
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center, // Centrer le texte horizontalement
            modifier = Modifier.padding(8.dp)
        )

        for (item in itemsState) {
            DishRowWithPrice(item, goToDetail)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuContentPreview() {
    AndroidRestaurantTheme {
        // Simuler des données pour la prévisualisation
        val items = listOf(
            Items("Entrée 1"),
            Items("Entrée 2"),
            Items("Entrée 3")
        )

        MenuContent(category = "Entrées", itemsState = items.toMutableStateList()) {}
    }
}*/