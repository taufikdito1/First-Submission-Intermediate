package com.example.storyapp.ui.activity.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.data.adapter.ListAdapter
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.helper.UserPreference
import com.example.storyapp.ui.activity.login.LoginActivity
import com.example.storyapp.ui.activity.story.AddStoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainVIewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListAdapter
    private lateinit var userPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "StoryApp"

        userPref = UserPreference(this)
        adapter = ListAdapter()

        setRecyclerView()
        setupViewModel()
        validate()
        action()
    }

    private fun action() {
        binding.btnAddStory.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
        }
    }

    private fun validate() {
        if (!userPref.getUser().isLogin) {
            val login = userPref.getUser().isLogin
            Log.d("tag", login.toString())
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainVIewModel::class.java]

        viewModel.getAllStories(userPref.getUser().token)
        viewModel.listStory.observe(this) {
            if (it != null) {
                adapter.setStory(it)
            }
        }
        viewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setRecyclerView() {
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout_user_menu -> {
                userPref.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this@MainActivity, "Logout Success", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            R.id.refresh_menu -> {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this@MainActivity, "Refresh page", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
}