package com.bmkg.emoreg.lainlain;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Minami on 7/25/2018.
 */

public class Itemspacer extends RecyclerView.ItemDecoration {

    String text;
    Context ct;

    public Itemspacer(Context ct, String text){
        this.ct=ct;
        this.text=text;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildLayoutPosition(view)==0 || parent.getChildLayoutPosition(view)==parent.getAdapter().getItemCount()-1){
            TextView tv=new TextView(ct);
            tv.setText(text);
        }
    }
}
