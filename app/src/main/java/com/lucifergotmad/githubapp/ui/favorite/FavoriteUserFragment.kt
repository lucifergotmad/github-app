package com.lucifergotmad.githubapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucifergotmad.githubapp.adapter.ListUserAdapter
import com.lucifergotmad.githubapp.databinding.FragmentFavoriteBinding
import com.lucifergotmad.githubapp.domain.User
import com.lucifergotmad.githubapp.helper.ViewModelFactory


class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun moveToDetailPage(username: String) {
        val toDetailUserActivity =
            FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailUserActivity(username)
        findNavController().navigate(toDetailUserActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listUserAdapter = ListUserAdapter()
        listUserAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                moveToDetailPage(username)
            }
        })

        viewModel.getFavoriteUsers().observe(viewLifecycleOwner) { result ->
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}