package com.app.newsfeed.newbulletin

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.app.newsfeed.MainActivity
import com.app.newsfeed.R
import com.app.newsfeed.databinding.FragmentNewsBulletinBinding
import com.app.newsfeed.network.ServiceApiInterface
import com.app.newsfeed.utils.FortnightlyToolbar

class NewsBulletinFragment : Fragment() {

    private lateinit var binding: FragmentNewsBulletinBinding
    private lateinit var viewModelFactory: NewsBulletinViewModelFactory
    private lateinit var viewModel: NewsBulletinViewModel
    private lateinit var appBar: FortnightlyToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news_bulletin,
            container,
            false
        )

        viewModelFactory = NewsBulletinViewModelFactory(
            NewsBulletinRepository(
                ServiceApiInterface()
            )
        )

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsBulletinViewModel::class.java).also {
                binding.viewModel = it
                binding.lifecycleOwner = this
            }


        val adapter = NewsBulletinAdapter(NewsArticleClickListener { article ->
            navigateToNewArticle(article)
        })

        binding.newsRcv.also {
            it.adapter = adapter
            it.addItemDecoration( DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ))
        }

        appBar = (activity as MainActivity).appBar

        viewModel.articlesData.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        if (viewModel.articlesData.value == null) {
            viewModel.getNewsFeed()
        }
        return binding.root
    }

    /**
     * Loads the detailed new article screen
     *
     * @param article: The actual NewsArticle object with the data
     *
     */
    private fun navigateToNewArticle(article: Article) {
        findNavController().navigate(
            NewsBulletinFragmentDirections.actionNewsBulletinFragmentToNewsArticleFragment(article)
        )
    }

    override fun onResume() {
        super.onResume()
        binding.newsRcv.addOnScrollListener(listener)
    }

    override fun onPause() {
        super.onPause()
        binding.newsRcv.removeOnScrollListener(listener)
    }

    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            appBar.translate(dy)
        }
    }
}

enum class Country(val countryCode: String) {
    INDIA("in"),
    NEW_ZEALAND("nz"),
    AUSTRALIA("au"),
    UNITED_STATES("us")
}