package com.goat.thirsty_goat.models;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * Created by Walker on 3/11/17.
 */

public enum WaterPurityCondition {
    SAFE(App.getResString(R.string.purity_condition_safe)),
    TREATABLE(App.getResString(R.string.purity_condition_treatable)),
    UNSAFE(App.getResString(R.string.purity_condition_unsafe));

    private final String purityCondition;

    WaterPurityCondition(String purityCondition) {
        this.purityCondition = purityCondition;
    }

    @Override
    public String toString() {
        return purityCondition;
    }
}
