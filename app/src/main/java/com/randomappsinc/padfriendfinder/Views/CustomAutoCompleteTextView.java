package com.randomappsinc.padfriendfinder.Views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by alexanderchiou on 7/24/15.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView
{
    /** Standard Constructors */

    public CustomAutoCompleteTextView(Context context)
    {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean enoughToFilter()
    {
        return true;
    }

    @Override
    public int getThreshold()
    {
        return 0;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd)
    {
        // On selection move cursor to end of text
        setSelection(this.length());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getAdapter() != null)
        {
            performFiltering(getText(), 0);
        }
    }
}
