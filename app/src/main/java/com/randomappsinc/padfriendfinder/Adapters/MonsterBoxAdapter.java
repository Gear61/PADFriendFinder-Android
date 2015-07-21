package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class MonsterBoxAdapter extends BaseAdapter
{
    private Context context;
    private List<MonsterAttributes> monsters;

    public MonsterBoxAdapter(Context context)
    {
        this.context = context;
        this.monsters = new ArrayList<>();
    }

    public void addMonsters(List<MonsterAttributes> monsters)
    {
        this.monsters.addAll(monsters);
    }

    public int getCount() {
        return monsters.size();
    }

    public Object getItem(int position) {
        return monsters.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public ImageView picture;
        public TextView name;
        public TextView level;
        public TextView awakenings;
        public TextView plusEggs;
        public TextView skillLevel;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.monster_ac_item, null);
            holder = new ViewHolder();
            holder.picture = (ImageView) v.findViewById(R.id.picture);
            holder.name = (TextView) v.findViewById(R.id.name);
            holder.level = (TextView) v.findViewById(R.id.level);
            holder.awakenings = (TextView) v.findViewById(R.id.awakenings);
            holder.plusEggs = (TextView) v.findViewById(R.id.plus_eggs);
            holder.skillLevel = (TextView) v.findViewById(R.id.skill_level);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        MonsterAttributes monster = monsters.get(position);
        holder.picture.setImageResource(monster.getDrawableId());
        holder.name.setText(monster.getName());
        holder.level.setText(monster.getLevel());
        holder.awakenings.setText(monster.getAwakenings());
        holder.plusEggs.setText(monster.getPlusEggs());
        holder.skillLevel.setText(monster.getSkillLevel());

        return v;
    }
}
