package com.example.openinappassignment.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.openinappassignment.R
import com.example.openinappassignment.databinding.ActivityMainBinding
import com.example.openinappassignment.fragments.CampaignsFragment
import com.example.openinappassignment.fragments.CoursesFragment
import com.example.openinappassignment.fragments.LinkFragment
import com.example.openinappassignment.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val linkFragment = LinkFragment()
    private val coursesFragment = CoursesFragment()
    private val campaignsFragment = CampaignsFragment()
    private val profileFragment = ProfileFragment()
    private var activeFragment: Fragment? = linkFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_frame,linkFragment,"Link")
                add(R.id.main_frame,coursesFragment,"Course").hide(coursesFragment)
                add(R.id.main_frame,campaignsFragment,"Campaign").hide(campaignsFragment)
                add(R.id.main_frame,profileFragment,"profile").hide(profileFragment)
            }.commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_links -> {
                    supportFragmentManager.beginTransaction().show(linkFragment).hide(
                        activeFragment!!
                    ).commit()
                    activeFragment = linkFragment
                    true
                }
                R.id.item_courses -> {
                    supportFragmentManager.beginTransaction().show(coursesFragment).hide(
                        activeFragment!!
                    ).commit()
                    activeFragment = coursesFragment
                    true
                }
                R.id.item_campaigns -> {
                    supportFragmentManager.beginTransaction().show(campaignsFragment).hide(
                        activeFragment!!
                    ).commit()
                    activeFragment = campaignsFragment
                    true
                }
                R.id.item_profile -> {
                    supportFragmentManager.beginTransaction().show(profileFragment).hide(
                        activeFragment!!
                    ).commit()
                    activeFragment = profileFragment
                    true
                }
                R.id.item_add ->{
                    startActivity(Intent(this@MainActivity,AddActivity::class.java))
                    false
                }
                else -> false
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener {  }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (activeFragment == linkFragment){ finish() }
                else{
                    val id = binding.bottomNavigationView.menu.getItem(0)
                    id.isChecked = true
                    supportFragmentManager.beginTransaction().hide(activeFragment!!).show(linkFragment).commit()
                    activeFragment = linkFragment
                }
            }
        })
    }
}