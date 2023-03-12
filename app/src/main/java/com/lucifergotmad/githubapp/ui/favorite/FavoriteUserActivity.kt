package com.lucifergotmad.githubapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucifergotmad.githubapp.adapter.ListUserAdapter
import com.lucifergotmad.githubapp.databinding.ActivityFavoriteUserBinding
import com.lucifergotmad.githubapp.domain.User
import com.lucifergotmad.githubapp.helper.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUserAdapter = ListUserAdapter()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FavoriteUserViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUsers().observe(this) { result ->
            if (result != null) {
                val listUser: List<User> = result.map {
                    User(it.username, it.avatarUrl, it.githubUrl)
                }
                listUserAdapter.submitList(listUser)
            }
        }

        binding.rvFavoriteUsers.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }
}