package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 9/17/15.
 */
public class SupportedLeadsAdapter extends BaseAdapter
{
    private Context context;
    private List<MonsterAttributes> matches;

    public SupportedLeadsAdapter(Context context)
    {
        this.context = context;
        this.matches = MonsterServer.getMonsterServer().getMatches("");
    }

    public void updateWithPrefix(String prefix)
    {
        matches = MonsterServer.getMonsterServer().getMatches(prefix);
        notifyDataSetChanged();
    }

    public int getCount() {
        return matches.size();
    }

    public MonsterAttributes getItem(int position) {
        return matches.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        @Bind(R.id.monster_picture) ImageView monsterPicture;
        @Bind(R.id.monster_name) TextView monsterName;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if (view == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.supported_lead_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        MonsterAttributes monster = matches.get(position);
        Picasso.with(context).load(monster.getImageUrl()).error(R.mipmap.mystery_creature).into(holder.monsterPicture);
        holder.monsterName.setText(monster.getName());

        return view;
    }
}
