package com.example.dog_inder.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.dog_inder.R
import com.example.dog_inder.ui.adapter.CardStackAdapter
import com.example.dog_inder.ui.adapter.CardStackCallback
import com.example.dog_inder.utils.databinding.activityViewBinding
import com.example.dog_inder.utils.model.Card
import com.yuyakaido.android.cardstackview.*
import java.util.*

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

//
//    private lateinit var mDislike: ImageButton
//    private lateinit var mLike: ImageButton
//    private var BASE_URL = "https://dog.ceo/api/breeds/image/random"

    private val TAG = "DashboardActivity"
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var cardStackView: CardStackView = findViewById(R.id.card_stack_view)

//        mDislike = findViewById(R.id.dislike_btn)
//        mLike = findViewById(R.id.like_btn)

//        mDislike.setOnClickListener(this)
//        mLike.setOnClickListener(this)

        manager = CardStackLayoutManager(this, object : CardStackListener {
            override fun onCardDragging(
                direction: Direction,
                ratio: Float
            ) {
                Log.d(
                    TAG,
                    "onCardDragging: d=" + direction.name + " ratio=" + ratio
                )
            }

            override fun onCardSwiped(direction: Direction) {
                Log.d(
                    TAG,
                    "onCardSwiped: p=" + manager.topPosition + " d=" + direction
                )
                if (direction == Direction.Right) {
                    Toast.makeText(this@DashboardActivity, "Direction Right", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Top) {
                    Toast.makeText(this@DashboardActivity, "Direction Top", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Left) {
                    Toast.makeText(this@DashboardActivity, "Direction Left", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(this@DashboardActivity, "Direction Bottom", Toast.LENGTH_SHORT).show()
                }

                // Paginating
                if (manager.topPosition == adapter.itemCount - 5) {
                    paginate()
                }
            }

            override fun onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.topPosition)
            }

            override fun onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.topPosition)
            }

            override fun onCardAppeared(view: View, position: Int) {
                val tv: ImageView = view.findViewById(R.id.item_image)
                Log.d(
                    TAG,
                    "onCardAppeared: " + position + ", nama: " + tv.drawable
                )
            }

            override fun onCardDisappeared(view: View, position: Int) {
                val tv: ImageView = view.findViewById(R.id.item_image)
                Log.d(
                    TAG,
                    "onCardAppeared: " + position + ", nama: " + tv.drawable
                )
            }
        })

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

    private fun paginate() {
        val old: List<Card> = adapter.getItems()
        val newCard: List<Card> = addList()
        val callback = CardStackCallback(old, newCard)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setItems(newCard)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addList(): List<Card> {
        val items: MutableList<Card> = ArrayList<Card>()
        items.add(Card("https://i.picsum.photos/id/502/200/300.jpg?hmac=aHrprSZ5m8KmqTIrgi4dr8YmRjrN_BppdP5jCNrXB0E"))
        items.add(Card("https://i.picsum.photos/id/502/200/300.jpg?hmac=aHrprSZ5m8KmqTIrgi4dr8YmRjrN_BppdP5jCNrXB0E"))
        items.add(Card("https://i.picsum.photos/id/502/200/300.jpg?hmac=aHrprSZ5m8KmqTIrgi4dr8YmRjrN_BppdP5jCNrXB0E"))
        items.add(Card("https://i.picsum.photos/id/502/200/300.jpg?hmac=aHrprSZ5m8KmqTIrgi4dr8YmRjrN_BppdP5jCNrXB0E"))
        items.add(Card("https://i.picsum.photos/id/502/200/300.jpg?hmac=aHrprSZ5m8KmqTIrgi4dr8YmRjrN_BppdP5jCNrXB0E"))
        return items
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}