package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

data class Product(val name: String, val price: String, val imageRes: Int, val detail: String)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "productList") {
        composable("productList") { ProductListScreen(navController) }
        composable("productDetail/{name}/{price}/{imageRes}") { backStackEntry ->
            ProductDetailScreen(
                name = backStackEntry.arguments?.getString("name") ?: "",
                price = backStackEntry.arguments?.getString("price") ?: "",
                imageRes = backStackEntry.arguments?.getString("imageRes")?.toInt() ?: 0,
                detail = backStackEntry.arguments?.getString("detail") ?: ""
            )
        }
    }
}

@Composable
fun ProductListScreen(navController: NavController) {
    val products = listOf(
        Product("DANVOUY Women's T-Shirt", "$12.99", R.drawable.shirt1, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
        Product("Opna Women's Short Sleeve", "$7.95", R.drawable.shirt2, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
        Product("MBJ Women's Solid Short", "$9.85", R.drawable.shirt3, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
        Product("Rain Jacket Women", "$39.99", R.drawable.shirt4, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "New Arrivals", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        products.forEach { product ->
            ProductItem(product, navController)
        }
    }
}

@Composable
fun ProductItem(product: Product, navController: NavController) {
    Card(
        shape = RoundedCornerShape(10.dp),
        //backgroundColor = Color.White,
        //elevation = 4.dp,
        modifier = Modifier.padding(8.dp).fillMaxWidth().clickable {
            navController.navigate("productDetail/${product.name}/${product.price}/${product.imageRes}/${product.detail}")
        }
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = product.price, color = Color.Gray, fontSize = 14.sp)
            }

            Button(onClick = { /* Handle add to cart */ }) {
                Text("+")
            }
        }
    }
}

@Composable
fun ProductDetailScreen(name: String, price: String, imageRes: Int, detail: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = price, color = Color.Gray, fontSize = 20.sp)
        Text(text = detail, color = Color.Blue, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Handle purchase */ }) {
            Text("Buy Now")
        }
    }
}
