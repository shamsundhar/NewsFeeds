package com.app.newsfeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.app.newsfeed.databinding.ActivityMainBinding
import com.app.newsfeed.utils.FortnightlyToolbar
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var appBar : FortnightlyToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appBar = toolbar
        val navController = Navigation.findNavController(this,R.id.newsNavHost)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.newsBulletinFragment){
                menu.setImageResource(R.drawable.vd_menu)
            }else{
                menu.setImageResource(R.drawable.vd_back)
            }
        }
    }


}