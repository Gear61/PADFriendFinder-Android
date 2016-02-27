package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Misc.MonsterServer;
import com.randomappsinc.padfriendfinder.Models.Monster;
import com.randomappsinc.padfriendfinder.Models.TopLeader;
import com.randomappsinc.padfriendfinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jman0_000 on 10/3/2015.
 */
public class TopMonsterAdapter extends BaseAdapter {
    private Context context;
    private List<TopLeader> topLeaders;

    public TopMonsterAdapter(Context context) {
        this.context = context;
        topLeaders = new ArrayList<>();
    }

    public void setTopMonsters(List<TopLeader> topLeaders) {
        this.topLeaders = topLeaders;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView rank;
        public ImageView monsterImg;
        public TextView name;
        public TextView count;
    }

    public int getCount() {
        return topLeaders.size();
    }

    public TopLeader getItem(int position) {
        return topLeaders.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.top_monster_item, parent, false);
            holder = new ViewHolder();
            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.monsterImg = (ImageView) view.findViewById(R.id.top_monster_img);
            holder.name = (TextView) view.findViewById(R.id.top_leader_name);
            holder.count = (TextView) view.findViewById(R.id.top_leader_count);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        String name = topLeaders.get(position).getName();
        int count = topLeaders.get(position).getLeaderCount();
        Monster monster = MonsterServer.getMonsterServer().getMonsterAttributes(name);
        holder.rank.setText(String.valueOf(position + 1) + ".");
        Picasso.with(context).load(monster.getImageUrl()).into(holder.monsterImg);
        holder.name.setText(name);
        holder.count.setText(String.valueOf(count));

        return view;
    }
}
