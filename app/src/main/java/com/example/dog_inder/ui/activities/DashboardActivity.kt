package com.example.dog_inder.ui.activities

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import com.example.dog_inder.R
import com.example.dog_inder.ui.adapter.ListAdapter
import com.example.dog_inder.utils.model.Card
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mDislike: ImageButton
    private lateinit var mLike: ImageButton
    private var url = "https://dog.ceo/api/breeds/image/random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        AsyncTaskHandleJson().execute(url)

        mDislike = findViewById(R.id.dislike_btn)
        mLike = findViewById(R.id.like_btn)

        mDislike.setOnClickListener(this)
        mLike.setOnClickListener(this)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {

        val jsonObject = JSONObject(jsonString)
        val list = ArrayList<Card>()

        list.add(Card(jsonObject.getString("message")))

        val adapter = ListAdapter(this, list)
        val cardsList = findViewById<ListView>(R.id.cards_list)
        cardsList.adapter = adapter
    }

    override fun onClick(v: View?) {
        AsyncTaskHandleJson().execute(url)
    }
}