package ru.ageev.android_homework2.post

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import ru.ageev.android_homework2.databinding.ViewPostCardBinding

class ViewPostCard @JvmOverloads constructor(
    context: Context, attrSet: AttributeSet? = null, defAttrsSet: Int = 0
) : MaterialCardView(context, attrSet, defAttrsSet) {
    private var binding: ViewPostCardBinding

    init {
        binding = ViewPostCardBinding.inflate(LayoutInflater.from(context), this, true)
    }
}