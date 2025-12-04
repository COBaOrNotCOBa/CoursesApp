package com.example.presentation.courses

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.domain.model.Course
import com.example.presentation.R
import com.example.presentation.databinding.ItemCourseBinding
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
            // Текстовые данные
            textTitle.text = course.title
            textDescription.text = course.text
            textPrice.text = "${course.price} ₽"
            textPublishDate.text = course.startDate
            textRating.text = course.rate.toString()

            // TODO: сюда позже можно повесить загрузку обложки (Glide / Coil / Picasso)
            // imageCover.setImageResource(...)

            // Избранное (иконка в кружке)
            updateFavoriteIcon(
                imageView = imageBookmark,
                isFavorite = course.hasLike
            )

            // Обработчик нажатия по избранному – по иконке и по контейнеру
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
        R.drawable.ic_favorite_on  // «зажжённое» избранное
    } else {
        R.drawable.ic_favorite     // обычный контур
    }

    val tintColor = if (isFavorite) {
        ContextCompat.getColor(context, R.color.courses_primary_green)
    } else {
        ContextCompat.getColor(context, R.color.courses_white)
    }

    imageView.setImageResource(drawableRes)
    imageView.imageTintList = ColorStateList.valueOf(tintColor)
}
