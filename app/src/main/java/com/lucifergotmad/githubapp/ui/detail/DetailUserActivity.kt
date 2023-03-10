package com.lucifergotmad.githubapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lucifergotmad.githubapp.R
import com.lucifergotmad.githubapp.databinding.ActivityDetailUserBinding
import com.lucifergotmad.githubapp.domain.DetailUser
import com.lucifergotmad.githubapp.helper.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = DetailUserActivityArgs.fromBundle(intent.extras!!).username

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: DetailUserViewModel by viewModels {
            factory
        }

        viewModel.getUserByUsername(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is com.lucifergotmad.githubapp.data.Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is com.lucifergotmad.githubapp.data.Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        bind(result.data)
                    }
                    is com.lucifergotmad.githubapp.data.Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this, "Somethings wrong! " + result.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun bind(detailUser: DetailUser) {
        supportActionBar?.title = detailUser.fullName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            tvDetailName.text = detailUser.fullName
            Glide.with(binding.root)
                .load(detailUser.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(ivDetailAvatar)
        }
    }
}