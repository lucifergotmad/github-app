package com.lucifergotmad.githubapp.ui.detail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucifergotmad.githubapp.adapter.ListUserAdapter
import com.lucifergotmad.githubapp.databinding.FragmentFollowingBinding
import com.lucifergotmad.githubapp.helper.ViewModelFactory
import com.lucifergotmad.githubapp.ui.detail.DetailUserActivity


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUserActivity.EXTRA_GITHUB_USERNAME).toString()

        val listUserAdapter = ListUserAdapter()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FollowingViewModel by viewModels {
            factory
        }

        viewModel.getUserFollowing(username).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is com.lucifergotmad.githubapp.data.Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is com.lucifergotmad.githubapp.data.Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val listUser = result.data
                        listUserAdapter.submitList(listUser)
                    }
                    is com.lucifergotmad.githubapp.data.Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            context, "Somethings wrong! " + result.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.rvUsers.apply {
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