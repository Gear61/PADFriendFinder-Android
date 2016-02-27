package com.randomappsinc.padfriendfinder.API.Callbacks;

import com.randomappsinc.padfriendfinder.Fragments.MonsterBoxFragment;
import com.randomappsinc.padfriendfinder.Misc.MyApplication;
import com.randomappsinc.padfriendfinder.Models.MonsterAttributes;
import com.randomappsinc.padfriendfinder.R;

import java.util.List;

/**
 * Created by alexanderchiou on 2/26/16.
 */
public class GetMonsterBoxCallback extends StandardCallback<List<MonsterAttributes>> {
    public GetMonsterBoxCallback() {
        super(MonsterBoxFragment.LOG_TAG, MyApplication.getAppContext().getString(R.string.box_load_fail));
    }
}
