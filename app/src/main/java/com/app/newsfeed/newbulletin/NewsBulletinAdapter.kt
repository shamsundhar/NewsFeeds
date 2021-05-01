package com.app.newsfeed.newbulletin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.newsfeed.databinding.HeaderNewsFeedItemBinding
import com.app.newsfeed.databinding.NewsFeedItemBinding

class NewsBulletinAdapter(private val listener: NewsArticleClickListener) :
    ListAdapter<Article, RecyclerView.ViewHolder>(NewsFeedsDiffCallback()) {

    private val NEWS_ARTICLE_HEADER = 0
    private val NEWS_ARTICLE_NORMAL_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWS_ARTICLE_HEADER -> HeaderNewsArticle.from(parent)
            NEWS_ARTICLE_NORMAL_ITEM -> NormalNewsArticle.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NormalNewsArticle -> {
                val item = getItem(position)
                holder.bind(item, listener)
            }
            is HeaderNewsArticle -> {
                val item = getItem(position)
                holder.bind(item, listener)
            }
        }
    }


    class NormalNewsArticle private constructor(private val binding: NewsFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, listener: NewsArticleClickListener) {
            binding.article = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NormalNewsArticle {
                val layoutInflater =
                    LayoutInflater.from(parent.context)

                val binding = NewsFeedItemBinding.inflate(layoutInflater, parent, false)
                return NormalNewsArticle(binding)
            }
        }
    }

    class HeaderNewsArticle private constructor(private val binding: HeaderNewsFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, listener: NewsArticleClickListener) {
            binding.article = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderNewsArticle {
                val layoutInflater =
                    LayoutInflater.from(parent.context)

                val binding = HeaderNewsFeedItemBinding.inflate(layoutInflater, parent, false)
                return HeaderNewsArticle(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> NEWS_ARTICLE_HEADER
            1 -> NEWS_ARTICLE_NORMAL_ITEM
            else -> NEWS_ARTICLE_NORMAL_ITEM
        }
    }

}

class NewsFeedsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source.id == newItem.source.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}

class NewsArticleClickListener(val listener: (article: Article) -> Unit) {
    fun onClick(article: Article) = listener(article)
}






