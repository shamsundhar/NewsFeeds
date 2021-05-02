package com.app.newsfeed.newsarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.app.newsfeed.R
import com.app.newsfeed.databinding.FragmentNewsArticleBinding

class NewsArticleFragment : Fragment() {

    private lateinit var binding: FragmentNewsArticleBinding
    private lateinit var factory: NewsArticleViewModelFactory
    private lateinit var viewModel: NewsArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news_article,
            container,
            false
        )


        val newsArticleFragmentArgs by navArgs<NewsArticleFragmentArgs>()
        val article = newsArticleFragmentArgs.article

        factory = NewsArticleViewModelFactory(article)
        viewModel = ViewModelProvider(this, factory).get(NewsArticleViewModel::class.java)
        binding.lifecycleOwner = this

        viewModel.article.observe(viewLifecycleOwner, {
            binding.article = it
            binding.executePendingBindings()
        })

        return binding.root
    }


}