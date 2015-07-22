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
        notifyDataSetChanged();
    }

    public void updateMonster(MonsterAttributes monster)
    {
        // This loop updates the monster if it's already there
        for (int i = 0; i < monsters.size(); i++)
        {
            if (monsters.get(i).getName().equals(monster.getName()))
            {
                monsters.set(i, monster);
                notifyDataSetChanged();
                return;
            }
        }
        // If we made it out of the loop without returning, it's a new monster
        monsters.add(monster);
        notifyDataSetChanged();
    }

    public int getCount() {
        return monsters.size();
    }

    public MonsterAttributes getItem(int position) {
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
            v = vi.inflate(R.layout.monster_box_list_item, null);
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
        holder.level.setText(String.valueOf(monster.getLevel()));
        holder.awakenings.setText(String.valueOf(monster.getAwakenings()));
        holder.plusEggs.setText(String.valueOf(monster.getPlusEggs()));
        holder.skillLevel.setText(String.valueOf(monster.getSkillLevel()));

        return v;
    }
}
