package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

class MenuController implements View.OnClickListener {

    private Context appContext;

    public MenuController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View v) {
        if (((Activity) this.appContext).findViewById(R.id.menu_view).getVisibility() == RelativeLayout.GONE) {
            RelativeLayout menu = ((Activity) this.appContext).findViewById(R.id.menu_view);
            menu.setVisibility(RelativeLayout.VISIBLE);
        } else {
            ((Activity) this.appContext).findViewById(R.id.menu_view).setVisibility(RelativeLayout.GONE);
        }
    }
}
