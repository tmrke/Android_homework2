package ru.ageev.android_homework2.images_screen

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ru.ageev.android_homework2.databinding.ViewImagesCardScreenBinding

class ViewImagesCardScreen @JvmOverloads constructor(
    context: Context,
    attrSet: AttributeSet? = null,
    defAttrsSet: Int = 0
) : ConstraintLayout(context, attrSet, defAttrsSet) {
    private var binding: ViewImagesCardScreenBinding

    init {
        binding = ViewImagesCardScreenBinding.inflate(LayoutInflater.from(context), this, true)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ViewImagesCardScreen::class.java)
        }
    }
}