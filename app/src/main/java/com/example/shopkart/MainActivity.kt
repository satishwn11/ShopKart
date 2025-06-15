package com.example.shopkart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(painter = painterResource(R.drawable.menu), contentDescription = "menu")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(painter = painterResource(R.drawable.search), contentDescription = "search")
                    }
                    IconButton(onClick = { }) {
                        Icon(painter = painterResource(R.drawable.favorite), contentDescription = "favorite")
                    }
                    IconButton(onClick = { }) {
                        Icon(painter = painterResource(R.drawable.shopping), contentDescription = "shopping")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                item { BannerSlider() }
                item { CategorySection() }
                items(productList) { product ->
                    ProductCard(product = product)
                }
            }
        }
    )
}

@Composable
fun BannerSlider() {
    val bannerList = listOf("GET 20% OFF", "BUY 1 GET 1", "NEW ARRIVALS")
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            count = bannerList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) { page ->
            OfferBanner(text = bannerList[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            activeColor = Color(0xFFB6FF41),
            inactiveColor = Color.Gray
        )
    }
}

@Composable
fun OfferBanner(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.banner_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(text = text, color = Color.White, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Special offer for you!", color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFFB6FF41), shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "12-16 October", color = Color.Black)
            }
        }
    }
}

@Composable
fun CategorySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Categories", style = MaterialTheme.typography.titleMedium)
            Text("See all", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(categoryList) { category ->
                CategoryItem(category)
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.name,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.DarkGray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(category.name, style = MaterialTheme.typography.labelSmall)
    }
}

data class Category(val name: String, val imageRes: Int)

val categoryList = listOf(
    Category("Cleaners", R.drawable.cleaner),
    Category("Toner", R.drawable.toner),
    Category("Serum", R.drawable.serum),
    Category("Moisturisers", R.drawable.moisturiser),
    Category("Sunscreens", R.drawable.sunscreen)
)

data class Product(
    val name: String,
    val brand: String,
    val description: String,
    val discountPrice: Int,
    val originalPrice: Int,
    val reviews: Int,
    val imageRes: Int
)

val productList = listOf(
    Product(
        "French Clay Cleanser",
        "Clencera",
        "Skin Tightness • Dry & Dehydrated Skin",
        355,
        444,
        249,
        R.drawable.cleanser_image),
    Product(
        "Vitamin C serum",
        "GlowUp",
        "Brightness & Evens Skin Tone",
        250, 299, 520,
        R.drawable.serum_image),
    Product(
        "Charcoal Face Wash",
        "MenX", "Oil Control & Deep Clean",
        250, 299, 112,
        R.drawable.facewash_image)
)

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "wishlist")
                Text(
                    text = "Best seller",
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.Green)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.brand, color = Color.Green)
            Text(text = product.name, style = MaterialTheme.typography.titleSmall)
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Text(text = "₹${product.discountPrice}", color = Color.Blue)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "₹${product.reviews} reviews", modifier = Modifier.padding(start = 6.dp))
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            }
        }
    }
}