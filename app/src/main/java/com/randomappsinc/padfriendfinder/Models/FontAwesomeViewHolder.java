package com.randomappsinc.padfriendfinder.Models;

import android.view.View;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 9/18/15.
 */
public class FontAwesomeViewHolder {
    @Bind(R.id.item_icon) public TextView itemIcon;
    @Bind(R.id.item_name) public TextView itemName;

    public FontAwesomeViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
