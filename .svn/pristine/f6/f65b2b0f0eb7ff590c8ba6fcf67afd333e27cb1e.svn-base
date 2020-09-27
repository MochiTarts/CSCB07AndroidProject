package com.example.salesapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.store.ShoppingCartInterface;
import com.b07.store.ShoppingCartQuantityManager;

import java.sql.SQLException;

class SaveCartController implements View.OnClickListener {

    private Context appContext;
    private int id;
    private Integer account;

    public SaveCartController(Context context, int account, int id) {
        this.appContext = context;
        this.id = id;
        this.account = account;
    }

    @Override
    public void onClick(View v) {
        ShoppingCartInterface currentCart = new UserStore().getCart();

        try {
            DatabaseInsertAdapter.insertShoppingCart((ShoppingCartQuantityManager) currentCart,
                    account, id);
            Toast.makeText(this.appContext,
                    (this.appContext).getResources().getString(R.string.save_success),
                    Toast.LENGTH_LONG);
            RelativeLayout menu = ((Activity) this.appContext).findViewById(R.id.menu_view);
            menu.setVisibility(RelativeLayout.GONE);
        } catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException e) {
            e.printStackTrace();
            Toast.makeText(this.appContext, e.getMessage(), Toast.LENGTH_LONG);
            RelativeLayout menu = ((Activity) this.appContext).findViewById(R.id.menu_view);
            menu.setVisibility(RelativeLayout.GONE);
        }
    }
}
