package com.example.presentation.courses

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CoursesAdapter(
    onFavoriteButtonClick: (Course) -> Unit
) : AsyncListDifferDelegationAdapter<Course>(
    CourseDiffCallback(),
    courseAdapterDelegate(onFavoriteButtonClick)
)

private class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {

    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}
