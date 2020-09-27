package com.example.salesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.adapter.DatabaseInsertAdapter;
import com.b07.database.adapter.DatabaseSelectAdapter;
import com.b07.database.adapter.DatabaseUpdateAdapter;
import com.b07.database.helper.DatabaseInsertHelperAndroid;
import com.b07.database.helper.DatabaseSelectHelperAndroid;
import com.b07.database.helper.DatabaseUpdateHelperAndroid;
import com.b07.exceptions.BuildTypeException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.DatabaseInsertHelperException;
import com.b07.exceptions.DatabaseSelectHelperException;
import com.b07.exceptions.UserSetException;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private int check;
    private ShoppingCartObject cart = com.example.salesapplication.ShoppingCartObject.getInstance();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseInsertAdapter.setInserter(new DatabaseInsertHelperAndroid(getApplicationContext()));
        DatabaseSelectAdapter.setSelector(new DatabaseSelectHelperAndroid(getApplicationContext()));
        DatabaseUpdateAdapter.setUpdater(new DatabaseUpdateHelperAndroid(getApplicationContext()));

        File dbtest = getApplicationContext().getDatabasePath("inventorymgmt.db");
        if (dbtest.exists()) {
            Log.d("Check if exists", "" + dbtest.exists());
            check = 0;
        } else {
            Log.d("Check if exists", "" + dbtest.exists());
            DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
            try {
                DatabaseSelectAdapter.getUsersDetails();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (BuildTypeException e) {
                e.printStackTrace();
            } catch (UserSetException e) {
                e.printStackTrace();
            } catch (DatabaseSelectHelperException e) {
                e.printStackTrace();
            }
            check = -1;
        }

        cart.setTotal_items(0);
        if (check == -1) {

            try {
                DatabaseInsertAdapter.insertRole("ADMIN");
                DatabaseInsertAdapter.insertRole("EMPLOYEE");
                DatabaseInsertAdapter.insertRole("CUSTOMER");

                int item1Id = DatabaseInsertAdapter.insertItem("FISHING_ROD", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item1Id, 100);
                int item2Id = DatabaseInsertAdapter.insertItem("HOCKEY_STICK", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item2Id, 100);
                int item3Id = DatabaseInsertAdapter.insertItem("SKATES", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item3Id, 100);
                int item4Id = DatabaseInsertAdapter.insertItem("RUNNING_SHOES", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item4Id, 100);
                int item5Id = DatabaseInsertAdapter.insertItem("PROTEIN_BAR", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item5Id, 100);

            }  catch (DatabaseInsertException | SQLException | DatabaseInsertHelperException | DatabaseSelectHelperException e) {
                e.printStackTrace();
                finish();
            }

            Intent i = new Intent(this, admin_initiation.class);
            startActivity(i);
        } else {

            /**
            try {

                DatabaseInsertAdapter.insertRole("ADMIN");
                DatabaseInsertAdapter.insertRole("EMPLOYEE");
                DatabaseInsertAdapter.insertRole("CUSTOMER");

                int item1Id = DatabaseInsertAdapter.insertItem("FISHING_ROD", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item1Id, 100);
                int item2Id = DatabaseInsertAdapter.insertItem("HOCKEY_STICK", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item2Id, 100);
                int item3Id = DatabaseInsertAdapter.insertItem("SKATES", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item3Id, 100);
                int item4Id = DatabaseInsertAdapter.insertItem("RUNNING_SHOES", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item4Id, 100);
                int item5Id = DatabaseInsertAdapter.insertItem("PROTEIN_BAR", new BigDecimal("12"));
                DatabaseInsertAdapter.insertInventory(item5Id, 100);

                DatabaseInsertAdapter.insertNewUser("Jenny Customer", 19, "Canada", "a");
                DatabaseInsertAdapter.insertUserRole(1, 3);
                DatabaseInsertAdapter.insertAccount(1, true);
                DatabaseInsertAdapter.insertAccount(1, true);
            } catch (DatabaseInsertException | DatabaseSelectHelperException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DatabaseInsertHelperException e) {
                e.printStackTrace();
            }**/
            Intent i = new Intent(this, LoginScreen.class);
            startActivity(i);
        }
    }
}
