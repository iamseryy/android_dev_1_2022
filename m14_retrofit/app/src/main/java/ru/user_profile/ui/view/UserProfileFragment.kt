package ru.user_profile.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.user_profile.databinding.FragmentUserProfileBinding
import ru.user_profile.ui.base.UserProfileViewModelFactory
import ru.user_profile.ui.viewmodel.State
import ru.user_profile.ui.viewmodel.UserProfileViewModel

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserProfileViewModel by viewModels{UserProfileViewModelFactory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null) viewModel.getRandomUser()

        binding.apply {
            updateButton.setOnClickListener {
                viewModel.getRandomUser()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collect {
                    when(it) {
                        State.Loading -> {
                            progressBar.isVisible = true
                            updateButton.isEnabled = false
                        }

                        is State.Error -> {
                            progressBar.isVisible = false
                            updateButton.isEnabled = true
                        }

                        State.Finish -> {
                            progressBar.isVisible = false
                            updateButton.isEnabled = true
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.resultsFlow.collect {
                    it?.results?.first()?.apply {

                        profileDetailsTextView.text = """
                            Login: ${login.username}
                            Name: ${name.title} ${name.first} ${name.last}
                            Age: ${dob.age}
                            Email: $email
                            Phone: $phone
                            Cell: $cell
                            Location: ${location.country}, ${location.state}, ${location.street.name} ${location.street.number} 
                        """.trimIndent()

                        Glide.with(view)
                            .load(picture.large)
                            .into(profileImageView)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.error.collect {
                    Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}