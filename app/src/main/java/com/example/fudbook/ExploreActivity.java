package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.fudbook.ui.bookshelf.fragment_bookshelf;
import com.example.fudbook.ui.explore.fragment_basket;
import com.example.fudbook.ui.explore.fragment_basket_item;
import com.example.fudbook.ui.explore.fragment_explore_1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExploreActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "ExploreActivity";

    // buttons
    private FloatingActionButton d_basket_button;

    private Fragment FragmentBasket;
    private FragmentManager fm;

    private boolean isBasketOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        isBasketOpen = false;

        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.exp_container, new fragment_explore_1()).commit();
        // handle backstacking

//        // button set up
//        d_basket_button = findViewById(R.id.basket_btn); // FLOATING BUTTON
//        d_basket_button.setOnClickListener(basket_listener);

    }

//    public void exitBasket(View v) {
//        if (FragmentBasket != null)
//            fm.beginTransaction().remove(FragmentBasket).commit();
//
//        isBasketOpen = false;
//    }


//    // open basket
//    private FloatingActionButton.OnClickListener basket_listener =
//            new ImageButton.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    if (!isBasketOpen) {
//                        FragmentBasket = new fragment_basket();
//                        fm.beginTransaction().add(R.id.exp_container, FragmentBasket).commit();
//                        isBasketOpen = true;
//                    }
//                }
//            };



    /** DEPRECATED
     *  REMOVED NAV BAR FROM EXPLORE PAGE
    private ImageButton.OnClickListener dash_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // go back to main activity and load dashboard
                    Intent exp_intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(exp_intent);
                }
            };

    private ImageButton.OnClickListener bookshelf_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up bookshelf
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.exp_container, new fragment_bookshelf()).commit();
                }
            };

     */
}
