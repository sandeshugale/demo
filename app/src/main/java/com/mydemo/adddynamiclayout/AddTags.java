package com.mydemo.adddynamiclayout;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ubuntu on 1/2/17.
 */

public class AddTags {
    private TextView mTvName;
    private ImageView mIvRemove;
    private RelativeLayout mLayoutView;

    public RelativeLayout getmLayoutView() {
        return mLayoutView;
    }

    public void setmLayoutView(RelativeLayout mLayoutView) {
        this.mLayoutView = mLayoutView;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextView getmTvName() {
        return mTvName;
    }

    public void setmTvName(TextView mTvName) {
        this.mTvName = mTvName;
    }

    public ImageView getmIvRemove() {
        return mIvRemove;
    }

    public void setmIvRemove(ImageView mIvRemove) {
        this.mIvRemove = mIvRemove;
    }
}
