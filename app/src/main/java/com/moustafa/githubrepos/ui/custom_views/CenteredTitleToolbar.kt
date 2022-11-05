package com.moustafa.githubrepos.ui.custom_views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.moustafa.githubrepos.utils.DisplayUtils


class CenteredTitleToolbar : Toolbar {

    private var _titleTextView: AppCompatTextView? = null
    private var _screenWidth: Int = 0
    private var _centerTitle = true
    private var _currentTitle = ""
    private val location = IntArray(2)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        _screenWidth = DisplayUtils.getDisplayPixelWidth(context)
        _titleTextView = AppCompatTextView(context)

        _titleTextView?.setTextColor(context.resources.getColor(android.R.color.black))
        _titleTextView?.maxLines = 1
        _titleTextView?.ellipsize = TextUtils.TruncateAt.END
        addView(_titleTextView)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (_centerTitle) {
            _titleTextView?.getLocationOnScreen(location)
            _titleTextView?.translationX =
                _titleTextView?.translationX?.plus(
                    (-location[0] + _screenWidth / 2 - (_titleTextView?.width?.div(
                        2
                    ) ?: 0))
                ) ?: 0f
            _titleTextView?.text = _currentTitle
        }
    }

    override fun setTitle(title: CharSequence?) {
        _titleTextView?.text = title ?: ""
        _currentTitle = title?.toString() ?: ""
        requestLayout()
    }

    override fun setTitle(titleRes: Int) {
        _titleTextView?.setText(titleRes)
        _currentTitle = title?.toString() ?: ""
        requestLayout()
    }

}
