package com.app.newsfeed.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.newsfeed.newbulletin.Article
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setDuration")
fun TextView.setDuration(item: Article?) {
    item?.let {
        val publishedAtDate = convertToNewFormat(item.publishedAt)
        val diff: Long = Date().time - publishedAtDate!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        println("Publish : " + publishedAtDate + " | Current : " + Date() + " | H : " + hours)


        text = if (days > 0) {
            "$days D ago"
        } else {
            "$hours H ago"
        }

    }
}

@BindingAdapter("setNewsHeading")
fun TextView.setHeadlines(item: Article?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("setNewsContent")
fun TextView.setNewsContent(item: Article?) {
    item?.let {
        text = item.content
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(item: Article?) {
    item?.let {
        println("loadImage called : " + item.urlToImage)
        Glide
            .with(context)
            .load(item.urlToImage)
            .centerCrop()
            .placeholder(android.R.drawable.spinner_background)
            .into(this)
    }

}

fun String.toDate(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone(
        "UTC"
    )
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun convertToNewFormat(dateStr: String?): Date? {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val destFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return convertedDate ?: Date()
}