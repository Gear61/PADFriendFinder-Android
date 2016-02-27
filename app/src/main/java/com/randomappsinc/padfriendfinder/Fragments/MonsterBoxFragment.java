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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.padfriendfinder.API.ApiConstants;
import com.randomappsinc.padfriendfinder.API.Callbacks.BasicCallback;
import com.randomappsinc.padfriendfinder.API.Callbacks.GetMonsterBoxCallback;
import com.randomappsinc.padfriendfinder.API.Events.BasicResponseEvent;
import com.randomappsinc.padfriendfinder.API.Events.SnackbarEvent;
import com.randomappsinc.padfriendfinder.API.Models.DeleteMonsterInfo;
import com.randomappsinc.padfriendfinder.API.RestClient;
import com.randomappsinc.padfriendfinder.Activities.MainActivity;
import com.randomappsinc.padfriendfinder.Activities.MonsterFormActivity;
import com.randomappsinc.padfriendfinder.Adapters.MonsterBoxAdapter;
import com.randomappsinc.padfriendfinder.Misc.Constants;
import com.randomappsinc.padfriendfinder.Misc.MonsterBoxManager;
import com.randomappsinc.padfriendfinder.Misc.PreferencesManager;
import com.randomappsinc.padfriendfinder.Models.Monster;
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
    public static final String LOG_TAG = "MonsterBox";

    @Bind(R.id.loading_monsters) View loadingMonsters;
    @Bind(R.id.monster_box_instructions) TextView instructions;
    @Bind(R.id.no_monsters) TextView noMonsters;
    @Bind(R.id.monster_list) ListView monsterList;

    private MaterialDialog deletingMonsterDialog;
    private MonsterBoxAdapter boxAdapter;
    private MonsterUpdateReceiver updateReceiver;

    private String monsterToDelete;

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
        getActivity().registerReceiver(updateReceiver, new IntentFilter(Constants.MONSTER_UPDATE_KEY));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        try
        {
            getActivity().unregisterReceiver(updateReceiver);
        }
        catch (IllegalArgumentException ignored) {}
    }

    public void onEvent(List<Monster> monsterBox) {
        if (boxAdapter.getCount() == 0) {
            boxAdapter.addMonsters(monsterBox);
            MonsterBoxManager.getInstance().addMonsters(monsterBox);
            refreshContent();
        }
    }

    public void onEvent(BasicResponseEvent event) {
        if (event.getEventType().equals(Constants.GET_MONSTERS_KEY) &&
                event.getResponseCode() == ApiConstants.STATUS_OK) {
            GetMonsterBoxCallback callback = new GetMonsterBoxCallback();
            RestClient.getInstance().getPffService().getMonsterBox(PreferencesManager.get().getPadId()).enqueue(callback);
        }
        else if (event.getEventType().equals(Constants.DELETE_KEY)) {
            deletingMonsterDialog.dismiss();
            if (event.getResponseCode() == ApiConstants.STATUS_OK) {
                boxAdapter.deleteMonster(monsterToDelete);
                MonsterBoxManager.getInstance().deleteMonster(monsterToDelete);
                refreshContent();
                showSnackbar(getString(R.string.delete_monster_success));
            }
            else {
                showSnackbar(getString(R.string.delete_monster_failure));
            }
        }
    }

    public void onEvent(SnackbarEvent event) {
        if (event.getScreen().equals(LOG_TAG)) {
            loadingMonsters.setVisibility(View.GONE);
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
                Monster monster = intent.getParcelableExtra(Constants.MONSTER_KEY);
                boxAdapter.updateMonster(monster);
                boxAdapter.notifyDataSetChanged();
                MonsterBoxManager.getInstance().updateMonster(monster);
                refreshContent();
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

    private void showSnackbar(String message) {
        MainActivity activity = (MainActivity) getActivity();
        activity.showSnackbar(message);
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
                        monsterToDelete = monsterName;
                        BasicCallback callback = new BasicCallback(Constants.DELETE_KEY);
                        DeleteMonsterInfo info = new DeleteMonsterInfo(monsterName);
                        RestClient.getInstance().getPffService().deleteMonster(info).enqueue(callback);
                    }
                })
                .show();
    }
}
