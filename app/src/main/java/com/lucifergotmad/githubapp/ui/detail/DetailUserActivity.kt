package com.lucifergotmad.githubapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.lucifergotmad.githubapp.R
import com.lucifergotmad.githubapp.adapter.SectionPageAdapter
import com.lucifergotmad.githubapp.data.local.entity.UserEntity
import com.lucifergotmad.githubapp.databinding.ActivityDetailUserBinding
import com.lucifergotmad.githubapp.domain.DetailUser
import com.lucifergotmad.githubapp.helper.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = DetailUserActivityArgs.fromBundle(intent.extras!!).username

        viewModel.getUserByUsername(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is com.lucifergotmad.githubapp.data.Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.root.visibility = View.GONE
                    }
                    is com.lucifergotmad.githubapp.data.Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.root.visibility = View.VISIBLE
                        bind(result.data)
                    }
                    is com.lucifergotmad.githubapp.data.Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.root.visibility = View.VISIBLE
                        Toast.makeText(
                            this, "Somethings wrong! " + result.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        val bundle = Bundle().apply { putString(EXTRA_GITHUB_USERNAME, username) }
        val sectionPagerAdapter = SectionPageAdapter(this, bundle)
        binding.apply {
            viewPager.apply {
                adapter = sectionPagerAdapter
                TabLayoutMediator(tabs, this) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }

    private fun bind(detailUser: DetailUser) {
        supportActionBar?.title = detailUser.fullName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.isUserFavorite(detailUser.username).observe(this) { result ->
            binding.apply {
                if (result) {
                    addToFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            addToFavorite.context,
                            R.drawable.ic_favorite_filled
                        )
                    )
                    addToFavorite.setOnClickListener {
                        viewModel.removeFromFavorite(detailUser.username)
                        addToFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                addToFavorite.context,
                                R.drawable.ic_favorite_outlined
                            )
                        )

                    }
                } else {
                    addToFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            addToFavorite.context,
                            R.drawable.ic_favorite_outlined
                        )
                    )
                    addToFavorite.setOnClickListener {
                        val userEntity = UserEntity(
                            username = detailUser.username,
                            avatarUrl = detailUser.avatarUrl,
                            githubUrl = detailUser.githubUrl
                        )
                        viewModel.addToFavorite(userEntity)
                        addToFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                addToFavorite.context,
                                R.drawable.ic_favorite_filled
                            )
                        )
                    }
                }
            }
        }

        binding.apply {
            tvDetailName.text = detailUser.fullName
            tvDetailUsername.text = detailUser.username
            tvDetailStatus.text = detailUser.bio ?: "-"
            tvDetailCompany.text = detailUser.company ?: "-"
            tvDetailLocation.text = detailUser.location ?: "-"
            tvDetailWebsite.text = detailUser.blog
            tvDetailFollowers.text = getString(R.string.followers, detailUser.followers.toString())
            tvDetailFollowing.text = getString(R.string.following, detailUser.following.toString())
            Glide.with(binding.root)
                .load(detailUser.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(ivDetailAvatar)
        }
    }

    companion object {
        const val EXTRA_GITHUB_USERNAME = "extra_github_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}