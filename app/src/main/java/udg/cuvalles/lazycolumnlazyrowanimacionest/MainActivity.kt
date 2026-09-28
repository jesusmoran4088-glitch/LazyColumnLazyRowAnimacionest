package udg.cuvalles.lazycolumnlazyrowanimacionest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import udg.cuvalles.lazycolumnlazyrowanimacionest.ui.theme.LazyColumnLazyRowAnimacionestTheme
import udg.cuvalles.lazycolumnlazyrowanimacionest.ui.theme.SelectedColor
import udg.cuvalles.lazycolumnlazyrowanimacionest.ui.theme.UnselectedColor
import udg.cuvalles.lazycolumnlazyrowanimacionest.ui.theme.CardTextColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnLazyRowAnimacionestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    val items = remember { List(20) { "Elemento #${it + 1}" } }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Actividad 5: Animaciones",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- LazyRow Horizontal ---
        Text("LazyRow (Animación de color al seleccionar)", fontWeight = FontWeight.Medium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(items.take(5)) { item ->
                val isSelected = selectedItem == item
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) SelectedColor else UnselectedColor,
                    animationSpec = tween(durationMillis = 500),
                    label = "colorAnimation"
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .clickable { selectedItem = item }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = item,
                        color = if (isSelected) Color.White else CardTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- LazyColumn Vertical ---
        Text("LazyColumn (Botón Mostrar/Ocultar)", fontWeight = FontWeight.Medium)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                CardItem(
                    text = item,
                    isSelected = selectedItem == item,
                    onClick = { selectedItem = item }
                )
            }
        }
    }
}

@Composable
fun CardItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val cardColor by animateColorAsState(
        targetValue = if (isSelected) SelectedColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 500),
        label = "cardColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) SelectedColor else Color.Black
                )

                // --- BOTÓN SIN ICONOS ---
                // Usamos un TextButton con símbolos de texto para evitar la librería de iconos
                TextButton(onClick = { isExpanded = !isExpanded }) {
                    Text(
                        text = if (isExpanded) "▼" else "▶",
                        fontSize = 20.sp,
                        color = if (isSelected) SelectedColor else Color.Gray
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Text(
                    text = "Este es el texto detallado del elemento. Aquí puedes poner más información sobre $text.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}