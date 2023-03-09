package com.lucifergotmad.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lucifergotmad.githubapp.R
import com.lucifergotmad.githubapp.databinding.ItemRowUserBinding
import com.lucifergotmad.githubapp.domain.User

class ListUserAdapter : ListAdapter<User, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvItemUsername.text = user.username
            binding.tvGithubUrl.text = user.githubUrl
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.imgItemAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
                    return oldUser.username == newUser.username
                }

                override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
                    return oldUser == newUser
                }
            }
    }
}