package com.example.shopkart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

val CustomAlignment = Alignment { size, space, layoutDirection ->
    // Calculate x and y offset
    val x = space.width - size.width - 80
    val y = space.height - size.height - 40
    IntOffset(x, y)
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(text = "Shop",
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    ) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )

                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        BottomBadgeIcon(count = 5, icon = {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        })
                    }

                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.size(48.dp)) {
                        BottomBadgeIcon(count = 3, icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.cart3),
                                contentDescription = "cart",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        })
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White
                )

            )
        },

        content = {  paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .padding(paddingValues)
            ) {
                item { BannerSlider() }
                item { CategorySection() }
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "New Products",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium)
                        Text(text = "See all",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF9B9494)
                        )
                    }
                }
                items(productList) { product ->
                    ProductCard(product = product)
                }
            }
        }

    )

}

@Composable
fun BottomBadgeIcon(
    count: Int,
    icon: @Composable () -> Unit,
    badgeColor: Color = Color(0xFFB9F658),
    textColor: Color = Color.Black
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        icon()

        if (count > 0) {
            Box(
                modifier = Modifier
                    .offset(x = 2.dp, y = 2.dp)
                    .background(badgeColor, CircleShape)
                    .size(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(),
                    color = textColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
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
        ) {     page ->
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
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Image(painter = painterResource(id = R.drawable.bannerbackground),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )


        Column(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .align(
                    Alignment.CenterStart
                )) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Special offer for you!",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFFB6FF41), shape = RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "12-16 October",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Icon(painter = painterResource(id = R.drawable.landscape),
            contentDescription = "image",
            tint = Color.White,
            modifier = Modifier
                .padding(30.dp)
                .align(CustomAlignment)
                .size(48.dp)
        )


    }
}


@Composable
fun CategorySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Categories",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium)
            Text(text = "See all",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF9B9494))
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(categoryList) {   category ->
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
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category.name, style = MaterialTheme.typography.labelSmall)

    }
}
data class Category(val name: String, val imageRes: Int)

val categoryList = listOf(
    Category("Cleaners", R.drawable.cleaner),
    Category("Toner", R.drawable.toner),
    Category("Serums", R.drawable.serum),
    Category("Moisturisers", R.drawable.moisturiser),
    Category("Sunscreens", R.drawable.sunscreen)
)

@Composable
fun ProductCard(product: Product) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Box(modifier = Modifier.padding(12.dp)) {

            // Wishlist Icon (Top-left)
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Wishlist",
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )

            // Best Seller Badge (Top-right)
            Text(
                text = "Best seller",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color(0xFF2ECC71), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelSmall
            )

            Column(
                modifier = Modifier
                    .padding(top = 32.dp) // space for top icons
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Product Image (Left Side)
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Right Content
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.brand,
                            color = Color(0xFF27AE60),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Rs. ${product.discountPrice}",
                                color = Color(0xFF2980B9),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Rs. ${product.originalPrice}",
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFF1C40F),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "${product.reviews} reviews",
                                modifier = Modifier.padding(start = 6.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* Add to cart */ },
                    shape = RoundedCornerShape(25),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to cart",
                        tint = Color.White
                    )
                }

            }
        }
    }
}
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
        name = "French Clay Cleanser",
        brand = "Clencera",
        description = "Skin Tightness • Dry & Dehydrated Skin",
        discountPrice = 355,
        originalPrice = 444,
        reviews = 249,
        imageRes = R.drawable.cleanser_image
    ),

    Product(
        name = "Vitamin C Serum",
        brand = "GlowUp",
        description = "Brightens & Evens Skin Tone",
        discountPrice = 355,
        originalPrice = 444,
        reviews = 249,
        imageRes = R.drawable.serum_image
    ),

    Product(
        name = "Charcoal Face Wash",
        brand = "Menx",
        description = "Oil Control & Deep Clean",
        discountPrice = 355,
        originalPrice = 444,
        reviews = 249,
        imageRes = R.drawable.facewash_image
    ),
)