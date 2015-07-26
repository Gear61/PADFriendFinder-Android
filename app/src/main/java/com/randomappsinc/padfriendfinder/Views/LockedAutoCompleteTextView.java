package com.randomappsinc.padfriendfinder.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by alexanderchiou on 7/24/15.
 */
public class LockedAutoCompleteTextView extends AutoCompleteTextView
{
    /** Standard Constructors */

    public LockedAutoCompleteTextView(Context context)
    {
        super(context);
    }

    public LockedAutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public LockedAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd)
    {
        // On selection move cursor to end of text
        setSelection(this.length());
    }
}
