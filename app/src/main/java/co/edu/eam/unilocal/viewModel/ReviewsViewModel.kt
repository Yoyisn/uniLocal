package co.edu.eam.unilocal.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import co.edu.eam.unilocal.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class ReviewsViewModel: ViewModel () {
    private val _reviews = MutableStateFlow(emptyList<Review>())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    init {
        loadReviews()
    }

    fun getReviewsByPlace(placeID: String): List<Review>{
        return _reviews.value.filter { it.placeID == placeID }
    }

    fun create(review: Review){
        _reviews.value = _reviews.value + review
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadReviews(){

        _reviews.value = listOf(
            Review(
                id = "1",
                userID = "3",
                username = "Pepito",
                placeID = "1",
                rating = 5,
                comment = "Buen lugar",
                date = LocalDateTime.now()
            ),
            Review(
                id = "2",
                userID = "2",
                username = "Carlos",
                placeID = "1",
                rating = 4,
                comment = "Más o menos",
                date = LocalDateTime.now()
            ),
            Review(
                id = "3",
                userID = "2",
                username = "Carlos",
                placeID = "2",
                rating = 5,
                comment = "Buen lugar",
                date = LocalDateTime.now()
            ),
            Review(
                id = "4",
                userID = "2",
                username = "Carlos",
                placeID = "3",
                rating = 5,
                comment = "Buen lugar",
                date = LocalDateTime.now()
            )
        )
    }
}