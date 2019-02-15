package com.dscvit.android.myffcs.models;

import com.dscvit.android.myffcs.R;

public enum ModelFragment {
    LOGIN(R.string.login_fragment, R.layout.fragment_sign_in),
    SIGNUP(R.string.signup_fragment, R.layout.fragment_sign_up);

    private int titleResId;
    private int layoutResId;

    ModelFragment(int titleResId, int layoutResId) {
        this.titleResId = titleResId;
        this.layoutResId = layoutResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }
}
