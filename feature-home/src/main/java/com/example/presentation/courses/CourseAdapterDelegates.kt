package com.example.presentation.courses

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.domain.model.Course
import com.example.presentation.home.R
import com.example.core.ui.R as CoreUiR
import com.example.presentation.home.databinding.ItemCourseBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

@SuppressLint("SetTextI18n")
fun courseAdapterDelegate(
    onFavoriteButtonClick: (Course) -> Unit
) = adapterDelegateViewBinding<Course, Course, ItemCourseBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemCourseBinding.inflate(layoutInflater, parent, false)
    }
) {
    bind {
        val course = item

        with(binding) {
            textTitle.text = course.title
            textDescription.text = course.text
            textPrice.text =
                root.context.getString(R.string.course_price_template, course.price)
            textPublishDate.text = course.publishDate.toRuReadableDate()
            textRating.text = course.rate.toString()

            // TODO: сюда позже можно повесить загрузку обложки (Glide / Coil)

            updateFavoriteIcon(
                imageView = imageBookmark,
                isFavorite = course.hasLike
            )

            layoutBookmark.setOnClickListener {
                onFavoriteButtonClick(course)
            }
            imageBookmark.setOnClickListener {
                onFavoriteButtonClick(course)
            }
        }
    }
}

private fun updateFavoriteIcon(
    imageView: ImageView,
    isFavorite: Boolean
) {
    val context = imageView.context

    val drawableRes = if (isFavorite) {
        CoreUiR.drawable.ic_favorite_on
    } else {
        CoreUiR.drawable.ic_favorite
    }

    val tintColor = if (isFavorite) {
        ContextCompat.getColor(context, CoreUiR.color.courses_primary_green)
    } else {
        ContextCompat.getColor(context, CoreUiR.color.courses_white)
    }

    imageView.setImageResource(drawableRes)
    imageView.imageTintList = ColorStateList.valueOf(tintColor)
}
