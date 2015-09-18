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

import butterknife.Bind;
import butterknife.ButterKnife;

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
        @Bind(R.id.pad_id) TextView padId;
        @Bind(R.id.level) TextView level;
        @Bind(R.id.awakenings) TextView awakenings;
        @Bind(R.id.plus_eggs) TextView plusEggs;
        @Bind(R.id.skill_level) TextView skillLevel;
        @Bind(R.id.monster_box) ImageView monsterBox;

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
            view = vi.inflate(R.layout.friend_result_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
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

        return view;
    }
}
