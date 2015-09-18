package com.randomappsinc.padfriendfinder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padfriendfinder.Activities.OthersBoxActivity;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 7/21/15.
 */
public class FriendResultsAdapter extends BaseAdapter
{
    private Context context;
    private List<Friend> friends;

    public FriendResultsAdapter(Context context)
    {
        this.context = context;
        this.friends = new ArrayList<>();
    }

    public void addFriends(List<Friend> friends)
    {
        this.friends.addAll(friends);
        notifyDataSetChanged();
    }

    public int getCount() {
        return friends.size();
    }

    public Friend getItem(int position) {
        return friends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        public TextView padId;
        public TextView level;
        public TextView awakenings;
        public TextView plusEggs;
        public TextView skillLevel;
        public ImageView monsterBox;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.friend_result_list_item, null);
            holder = new ViewHolder();
            holder.padId = (TextView) v.findViewById(R.id.pad_id);
            holder.level = (TextView) v.findViewById(R.id.level);
            holder.awakenings = (TextView) v.findViewById(R.id.awakenings);
            holder.plusEggs = (TextView) v.findViewById(R.id.plus_eggs);
            holder.skillLevel = (TextView) v.findViewById(R.id.skill_level);
            holder.monsterBox = (ImageView) v.findViewById(R.id.monsterBox);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final Friend friend = friends.get(position);
        holder.padId.setText(friend.getPadId());
        holder.level.setText(String.valueOf(friend.getMonster().getLevel()));
        holder.awakenings.setText(String.valueOf(friend.getMonster().getAwakenings()));
        holder.plusEggs.setText(String.valueOf(friend.getMonster().getPlusEggs()));
        holder.skillLevel.setText(String.valueOf(friend.getMonster().getSkillLevel()));
        holder.monsterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OthersBoxActivity.class);
                intent.putExtra(Constants.OTHERS_ID_KEY, friend.getPadId());
                context.startActivity(intent);
            }
        });

        return v;
    }
}
