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
    List<TopLeader> topLeaders;

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

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.top_monster_item, null);
            holder = new ViewHolder();
            holder.rank = (TextView) v.findViewById(R.id.rank);
            holder.monsterImg = (ImageView) v.findViewById(R.id.top_monster_img);
            holder.name = (TextView) v.findViewById(R.id.top_leader_name);
            holder.count = (TextView) v.findViewById(R.id.top_leader_count);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        String name = topLeaders.get(position).getName();
        int count = topLeaders.get(position).getLeaderCount();
        Monster monster = MonsterServer.getMonsterServer().getMonsterAttributes(name);
        holder.rank.setText(Integer.toString(++position) + ".");
        Picasso.with(context).load(monster.getImageUrl()).into(holder.monsterImg);
        holder.name.setText(name);
        holder.count.setText(Integer.toString(count));

        return v;
    }
}
