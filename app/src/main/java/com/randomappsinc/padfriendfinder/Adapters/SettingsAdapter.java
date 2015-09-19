package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 9/18/15.
 */
public class SettingsAdapter extends ArrayAdapter<String>
{
    private List<String> optionNames;
    private Context context;

    @SuppressWarnings("unchecked")
    public SettingsAdapter(Context context, int viewResourceId, List<String> optionNames)
    {
        super(context, viewResourceId, optionNames);
        this.context = context;
        this.optionNames = optionNames;
    }

    public static class ViewHolder
    {
        @Bind(R.id.option_icon) FontAwesomeText optionIcon;
        @Bind(R.id.option_name) TextView optionName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.settings_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String tabName = optionNames.get(position);
        holder.optionName.setText(tabName);

        if (tabName.equals(context.getString(R.string.change_pad_id)))
        {
            holder.optionIcon.setIcon(context.getString(R.string.change_pad_id_icon));
        }
        else if (tabName.equals(context.getString(R.string.choose_avatar)))
        {
            holder.optionIcon.setIcon(context.getString(R.string.choose_avatar_icon));
        }
        else if (tabName.equals(context.getString(R.string.rate_us)))
        {
            holder.optionIcon.setIcon(context.getString(R.string.rate_us_icon));
        }
        return view;
    }
}
