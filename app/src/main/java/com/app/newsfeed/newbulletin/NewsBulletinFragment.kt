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


class NewsBulletinFragment : Fragment() {

    private lateinit var binding: FragmentNewsBulletinBinding
    private lateinit var viewModelFactory: NewsBulletinViewModelFactory
    private lateinit var viewModel: NewsBulletinViewModel

    private lateinit var rcScrollListener: RecyclerView.OnScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_bulletin, container, false)
        viewModelFactory =
            NewsBulletinViewModelFactory(NewsBulletinRepository(ServiceApiInterface.invoke()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsBulletinViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = NewsBulletinAdapter(NewsArticleClickListener { article ->
            navigateToNewArticle(article)
        })
        binding.newsRcv.adapter = adapter

        rcScrollListener = (activity as MainActivity).listener

        binding.newsRcv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


        if (viewModel.articles.value == null) {
            viewModel.getNewsFeed()
        }

        viewModel.articles.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }


    private fun navigateToNewArticle(article: Article) {
        findNavController().navigate(
            NewsBulletinFragmentDirections.actionNewsBulletinFragmentToNewsArticleFragment(article)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.country_overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.india -> updateNewsFeed("in")
            R.id.nz -> updateNewsFeed("nz")
            R.id.au -> updateNewsFeed("au")
            R.id.us -> updateNewsFeed("us")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNewsFeed(countryCode: String) {
        viewModel.getNewsFeed(countryCode)
    }

    override fun onResume() {
        super.onResume()
        binding.newsRcv.addOnScrollListener(rcScrollListener)
    }

    override fun onPause() {
        super.onPause()
        binding.newsRcv.removeOnScrollListener(rcScrollListener)
    }

}