package co.edu.eam.unilocal.ui.places

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import co.edu.eam.unilocal.model.Place
import co.edu.eam.unilocal.model.Review
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import java.time.LocalDateTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    onNavigateBackTo: () -> Unit,
    userId: String?,
    placeId: String,
) {
    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val reviewsViewModel = LocalMainViewModel.current.reviewsViewModel

    val place: Place? = placesViewModel.findById(placeId)
    val images = place?.images ?: emptyList()

    val reviews = remember { mutableStateListOf<Review>() }
    reviews.clear()
    reviews.addAll(reviewsViewModel.getReviewsByPlace(placeId))

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showComments by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Place Detail") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBackTo() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showComments = true }) {
                Icon(Icons.Rounded.Menu, contentDescription = null)
            }
        }
    ) { padding ->

        place?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(images) { image ->
                            AsyncImage(
                                model = image,
                                contentDescription = place.title,
                                modifier = Modifier
                                    .width(280.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = place.title, style = MaterialTheme.typography.headlineSmall)
                            Text(
                                text = place.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text = "Abierto",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2E7D32)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Home, contentDescription = "Ciudad")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ciudad: ${place.city.name}")
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = "Dirección")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Dirección: ${place.address}")
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Call, contentDescription = "Teléfono")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Teléfono: ${if (place.phones.isNotEmpty()) place.phones else "No disponible"}")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text("Horarios", style = MaterialTheme.typography.titleMedium)
                            place.schedules.forEach { schedule ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Horario")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("${schedule.day} ${schedule.open} - ${schedule.close}")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            if (showComments) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showComments = false }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Comments")
                        CommentsList(reviews = reviews)
                        CreateCommentForm(
                            placeId = placeId,
                            userId = userId,
                            onCreateReview = {
                                reviews.add(it)
                                reviewsViewModel.create(it)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsList(reviews: List<Review>) {
    LazyColumn {
        items(reviews) {
            ListItem(
                headlineContent = { Text(it.username) },
                supportingContent = { Text(it.comment) },
                leadingContent = {
                    Icon(Icons.Rounded.AccountBox, contentDescription = null)
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateCommentForm(userId: String?, placeId: String, onCreateReview: (Review) -> Unit) {
    var comment by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Write a comment") }
        )

        IconButton(
            onClick = {
                if (comment.isEmpty()) return@IconButton

                val review = Review(
                    id = UUID.randomUUID().toString(),
                    userID = userId ?: "",
                    username = "Carlitos",
                    placeID = placeId,
                    rating = 5,
                    comment = comment,
                    date = LocalDateTime.now()
                )

                onCreateReview(review)
                comment = ""
            }
        ) {
            Icon(Icons.Rounded.Send, contentDescription = null)
        }
    }
}