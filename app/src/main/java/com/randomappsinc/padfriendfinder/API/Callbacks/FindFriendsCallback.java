package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.Activities.FriendResultsActivity;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.Friend;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by alexanderchiou on 2/27/16.
 */
public class FindFriendsCallback extends StandardCallback<List<Friend>> {
    public FindFriendsCallback() {
        super(FriendResultsActivity.LOG_TAG, MyApplication.getAppContext().getString(R.string.fetch_fail));
    }
}
