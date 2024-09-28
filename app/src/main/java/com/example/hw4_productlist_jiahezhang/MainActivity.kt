package com.example.hw4_productlist_jiahezhang
import com.example.hw4_productlist_jiahezhang.ui.theme.Hw4_productList_JiaheZhangTheme
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.* // for layout
import androidx.compose.material3.* // for Material design components
import androidx.compose.runtime.* // for managing the composable state
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double
)
val sampleProducts = listOf(
    Product(1, "Laptop", "A high-performance laptop.", 999.99),
    Product(2, "Smartphone", "Latest smartphone with 5G support.", 799.99),
    Product(3, "Headphones", "Noise-cancelling headphones.", 199.99),
    Product(4, "Smartwatch", "Track your fitness and notifications.", 299.99)
)

@Composable
fun ProductList(products: List<Product>, onProductSelected: (Product) -> Unit) {
    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(products) { product ->
            ListItem(
                headlineContent = { Text(product.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onProductSelected(product) },
                tonalElevation = 10.dp,
                shadowElevation = 5.dp
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?) {
    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Product Name: ${product.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description: ${product.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: $${product.price}")
        }
    } else {
        Text(
            text = "Select a product to see the details",
            modifier = Modifier.padding(16.dp)
        )
    }
}





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hw4_productList_JiaheZhangTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // State to hold the selected product
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (isLandscape) {
        // Landscape: Show both list and details side by side
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                ProductList(products = sampleProducts, onProductSelected = { product ->
                    selectedProduct = product
                })
            }
            Box(modifier = Modifier.weight(1f)) {
                ProductDetails(product = selectedProduct)
            }
        }
    } else {
        // Portrait: Show only one pane at a time
        var showingDetails by remember { mutableStateOf(false) }

        if (showingDetails) {
            // Show product details in portrait
            Column(modifier = Modifier
                .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Button(onClick = { showingDetails = false }) {
                    Text("Back to List")
                }
                ProductDetails(product = selectedProduct)

            }

        } else {
            // Show product list in portrait
            ProductList(products = sampleProducts, onProductSelected = { product ->
                selectedProduct = product
                showingDetails = true
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hw4_productList_JiaheZhangTheme {
        Main()
    }
}