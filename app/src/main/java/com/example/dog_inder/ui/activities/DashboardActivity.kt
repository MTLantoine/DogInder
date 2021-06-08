package com.example.dog_inder.ui.activities

import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.liveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.dog_inder.ui.adapter.CardStackAdapter
import com.example.dog_inder.utils.model.Card
import com.yuyakaido.android.cardstackview.*
import java.util.*
import com.example.dog_inder.R
import com.example.dog_inder.utils.http.Resource
import com.example.dog_inder.utils.http.RetrofitBuilder
import com.example.dog_inder.utils.http.Status
import kotlinx.coroutines.Dispatchers

class DashboardActivity : AppCompatActivity() {

    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private val items: MutableList<Card> = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var cardStackView: CardStackView = findViewById(R.id.card_stack_view)

        manager = CardStackLayoutManager(this, object : CardStackListener {
            override fun onCardDragging(
                direction: Direction,
                ratio: Float
            ) {}

            override fun onCardSwiped(direction: Direction) {
                if (direction == Direction.Right) {
                    Toast.makeText(this@DashboardActivity, "LIKE !", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Top) {
                    Toast.makeText(this@DashboardActivity, "LIKE !", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Left) {
                    Toast.makeText(this@DashboardActivity, "DISLIKE !", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(this@DashboardActivity, "DISLIKE !", Toast.LENGTH_SHORT).show()
                }

                getImage().observe(this@DashboardActivity, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                var img: String? = resource.data?.message
                                img?.let { it1 -> Card(it1) }?.let { it2 -> items.add(it2) }
                            }
                            Status.ERROR -> {
                                Toast.makeText(this@DashboardActivity, it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                Toast.makeText(this@DashboardActivity, "Loading ...", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }

            override fun onCardRewound() {}

            override fun onCardCanceled() {}

            override fun onCardAppeared(view: View, position: Int) {}

            override fun onCardDisappeared(view: View, position: Int) {}
        }).apply {
            setDirections(listOf(Direction.Right, Direction.Left))
        }

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setSwipeableMethod(SwipeableMethod.Manual)
        manager.setOverlayInterpolator(LinearInterpolator())
        adapter = CardStackAdapter(addList())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun addList(): List<Card> {
        getImage().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var img: String? = resource.data?.message
                        img?.let { it1 -> Card(it1) }?.let { it2 -> items.add(it2) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Loading ...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        items.add(Card("https://images.dog.ceo//breeds//bulldog-english//jager-2.jpg"))
        items.add(Card("https://images.dog.ceo//breeds//vizsla//n02100583_854.jpg"))
        items.add(Card("https://images.dog.ceo//breeds//labradoodle//labradoodle-forrest.png"))
        items.add(Card("https://images.dog.ceo//breeds//otterhound//n02091635_1606.jpg"))
        items.add(Card("https://images.dog.ceo//breeds//terrier-american//n02093428_1108.jpg"))
        return items
    }

    private fun getImage() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        var api = RetrofitBuilder.apiService;
        try {
            emit(Resource.success(data = api.getImg()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}