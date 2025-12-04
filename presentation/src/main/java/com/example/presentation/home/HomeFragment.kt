package com.example.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.courses.CoursesAdapter
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: error("FragmentHomeBinding is null in HomeFragment")

    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSortButton()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        coursesAdapter = CoursesAdapter { course ->
            sharedViewModel.onFavoriteCourseClicked(course.id)
        }

        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
        }
    }

    private fun setupSortButton() {
        binding.buttonSortByPublishDate.setOnClickListener {
            sharedViewModel.onSortByPublishDateClicked()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collectLatest { state ->
                    binding.progressBarLoading.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    coursesAdapter.items = state.courses

                    if (state.errorMessage != null) {
                        binding.textViewError.visibility = View.VISIBLE
                        binding.textViewError.text = state.errorMessage
                    } else {
                        binding.textViewError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
