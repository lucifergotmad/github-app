package com.lucifergotmad.githubapp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucifergotmad.githubapp.R
import com.lucifergotmad.githubapp.adapter.ListUserAdapter
import com.lucifergotmad.githubapp.databinding.FragmentHomeBinding
import com.lucifergotmad.githubapp.helper.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var listUserAdapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun moveToDetailPage(username: String) {
        val toDetailUserActivity =
            HomeFragmentDirections.actionHomeFragmentToDetailUserActivity(username)
        findNavController().navigate(toDetailUserActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.search)?.actionView as SearchView

                searchView.queryHint = resources.getString(R.string.search_placeholder)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query?.isEmpty() == true) return false

                        viewModel.searchUsers(query).observe(viewLifecycleOwner) { result ->
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
                                            context,
                                            "Somethings wrong! " + result.error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }

                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(value: String): Boolean {
                        return false
                    }
                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.favorite_menu -> {
                        findNavController().navigate(R.id.action_homeFragment_to_favoriteUserFragment)
                        true
                    }
                    R.id.setting_menu -> {
                        findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        listUserAdapter = ListUserAdapter()
        listUserAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                moveToDetailPage(username)
            }
        })

        viewModel.getUsers().observe(viewLifecycleOwner) { result ->
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