package com.app.newsfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.newsfeed.databinding.ActivityMainBinding
import com.app.newsfeed.network.ServiceApiInterface
import com.app.newsfeed.newbulletin.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModelFactory: NewsBulletinViewModelFactory
    private lateinit var viewModel: NewsBulletinViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory =
            NewsBulletinViewModelFactory(NewsBulletinRepository(ServiceApiInterface.invoke()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsBulletinViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = NewsBulletinAdapter(NewsArticleClickListener { article ->
//            navigateToNewArticle(article)
        })

//        navController = findNavController(R.id.newsNavHost)
//        drawerLayout = binding.drawerLayout

//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navigationView, navController)

        val layoutManager = LinearLayoutManager(this)
        // TODO doesn't match fortnightly style
        val divider = DividerItemDecoration(this, layoutManager.orientation)

        list.adapter = adapter
        list.layoutManager = layoutManager
        list.addItemDecoration(divider)

        if (viewModel.articles.value == null) {
            viewModel.getNewsFeed()
        }

        viewModel.articles.observe(this, {
            it?.let {
                adapter.submitList(it)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        list.addOnScrollListener(listener)
    }

    override fun onPause() {
        super.onPause()
        list.removeOnScrollListener(listener)
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            toolbar.translate(dy)
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//    }


}