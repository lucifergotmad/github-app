package com.lucifergotmad.githubapp.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lucifergotmad.githubapp.databinding.FragmentSettingBinding
import com.lucifergotmad.githubapp.helper.ViewModelPreferenceFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelPreferenceFactory(pref)
            )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(
            viewLifecycleOwner
        ) { isDarkModeActive: Boolean ->
            binding.apply {
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}