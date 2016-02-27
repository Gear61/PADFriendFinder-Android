package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.Activities.TopLeadersActivity;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.TopLeader;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class TopLeadersCallback extends StandardCallback<List<TopLeader>> {
    public TopLeadersCallback() {
        super(TopLeadersActivity.LOG_TAG, MyApplication.getAppContext().getString(R.string.top_leaders_fail));
    }
}
