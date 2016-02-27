package com.randomappsinc.padfriendfinder.Fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.DeleteMonster;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.GetMonsterBox;
import com.randomappsinc.padfriendfinder.API.JSONParser;
import com.randomappsinc.padfriendfinder.Activities.MonsterFormActivity;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.Models.RestCallResponse;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by alexanderchiou on 9/15/15.
 */
public class MonsterBoxFragment extends Fragment {
    @Bind(R.id.loading_monsters) View loadingMonsters;
    @Bind(R.id.monster_box_instructions) TextView instructions;
    @Bind(R.id.no_monsters) TextView noMonsters;
    @Bind(R.id.monster_list) ListView monsterList;

    private MaterialDialog deletingMonsterDialog;
    private MonsterBoxAdapter boxAdapter;
    private MonsterBoxReceiver boxReceiver;
    private MonsterUpdateReceiver updateReceiver;
    private MonsterDeleteReceiver deleteReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.monster_box, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        deletingMonsterDialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.deleting_monster)
                .progress(true, 0)
                .build();
        boxAdapter = new MonsterBoxAdapter(getActivity());
        monsterList.setAdapter(boxAdapter);

        updateReceiver = new MonsterUpdateReceiver();
        boxReceiver = new MonsterBoxReceiver();
        deleteReceiver = new MonsterDeleteReceiver();
        getActivity().registerReceiver(updateReceiver, new IntentFilter(Constants.MONSTER_UPDATE_KEY));
        getActivity().registerReceiver(boxReceiver, new IntentFilter(Constants.MONSTER_BOX_KEY));
        getActivity().registerReceiver(deleteReceiver, new IntentFilter(Constants.DELETE_KEY));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        try
        {
            getActivity().unregisterReceiver(boxReceiver);
            getActivity().unregisterReceiver(updateReceiver);
            getActivity().unregisterReceiver(deleteReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }


    public void onEvent(BasicResponseEvent event) {
        if (event.getEventType().equals(Constants.GET_MONSTERS_KEY) &&
                event.getResponseCode() == ApiConstants.STATUS_OK) {
            new GetMonsterBox(getActivity(), PreferencesManager.get().getPadId(), false).execute();
        }
    }

    private class MonsterDeleteReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            deletingMonsterDialog.dismiss();
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                String monsterName = intent.getStringExtra(Constants.NAME_KEY);
                boxAdapter.deleteMonster(monsterName);
                MonsterBoxManager.getInstance().deleteMonster(monsterName);
                refreshContent();
                Toast.makeText(context, Constants.MONSTER_DELETE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, Constants.MONSTER_DELETE_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class MonsterUpdateReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                MonsterAttributes monster = intent.getParcelableExtra(Constants.MONSTER_KEY);
                boxAdapter.updateMonster(monster);
                boxAdapter.notifyDataSetChanged();
                MonsterBoxManager.getInstance().updateMonster(monster);
                refreshContent();
            }
        }
    }

    private class MonsterBoxReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            RestCallResponse response = intent.getParcelableExtra(Constants.REST_CALL_RESPONSE_KEY);
            if (response.getStatusCode() == 200)
            {
                List<MonsterAttributes> monsterBox = JSONParser.parseMonsterBoxResponse(response.getResponse());
                boxAdapter.addMonsters(monsterBox);
                MonsterBoxManager.getInstance().addMonsters(monsterBox);
                refreshContent();
            }
            else
            {
                refreshContent();
                Toast.makeText(context, Constants.BOX_FETCH_FAILED_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }
    }

    // Refresh the page after an API call is made (or after initial loading)
    public void refreshContent() {
        loadingMonsters.setVisibility(View.GONE);
        instructions.setVisibility(View.VISIBLE);
        if (boxAdapter.getCount() == 0) {
            monsterList.setVisibility(View.GONE);
            noMonsters.setVisibility(View.VISIBLE);
        }
        else {
            noMonsters.setVisibility(View.GONE);
            monsterList.setVisibility(View.VISIBLE);
        }
    }

    @OnItemClick(R.id.monster_list)
    public void onItemClick(final int position) {
        final Context context = getActivity();
        final String monsterName = boxAdapter.getItem(position).getName();

        String[] choices = new String[3];
        choices[0] = getString(R.string.find_other) + "\"" + monsterName + "\"";
        choices[1] = getString(R.string.update) + "\"" + monsterName + "\"";
        choices[2] = getString(R.string.delete) + "\"" + monsterName + "\"";

        new MaterialDialog.Builder(getActivity())
                .items(choices)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                Intent searchIntent = new Intent(context, MonsterFormActivity.class);
                                searchIntent.putExtra(Constants.NAME_KEY, monsterName);
                                searchIntent.putExtra(Constants.MODE_KEY, Constants.SEARCH_MODE);
                                getActivity().startActivity(searchIntent);
                                break;
                            case 1:
                                Intent updateIntent = new Intent(context, MonsterFormActivity.class);
                                updateIntent.putExtra(Constants.MONSTER_KEY, boxAdapter.getItem(position));
                                updateIntent.putExtra(Constants.MODE_KEY, Constants.UPDATE_MODE);
                                getActivity().startActivity(updateIntent);
                                break;
                            case 2:
                                showMonsterDeleteDialog(monsterName);
                        }
                    }
                })
                .show();
    }

    private void showMonsterDeleteDialog(final String monsterName) {
        final Context context = getActivity();
        String message = getString(R.string.confirm_delete) + monsterName + getString(R.string.from_your_box);
        new MaterialDialog.Builder(getActivity())
                .title(R.string.delete_monster_confirmation)
                .content(message)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deletingMonsterDialog.show();
                        new DeleteMonster(context, monsterName).execute();
                    }
                })
                .show();
    }
}
