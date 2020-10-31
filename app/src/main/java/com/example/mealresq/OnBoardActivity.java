package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoardActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelText("");
        addFragment(new Step.Builder().setTitle("\n\nDiscover Your Favourite\nRestaurants")
                .setContent("Rescue your food and help people")
                .setBackgroundColor(Color.parseColor("#2EC4B6")) // int background color
                .setDrawable(R.drawable.ic_start1) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("\n\nSave Food, Save Budget, Save Planet")
                .setContent("We help you and you help the\nplanet")
                .setBackgroundColor(Color.parseColor("#2EC4B6")) // int background color
                .setDrawable(R.drawable.ic_start2) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("\n\nJoin our Community")
                .setContent("Find Your Partner, to make the\n World better Together")
                .setBackgroundColor(Color.parseColor("#2EC4B6")) // int background color
                .setDrawable(R.drawable.ic_start3) // int top drawable
                .build());

    }
    @Override
    public void finishTutorial() {
        // Your implementation
        Intent i = new Intent(OnBoardActivity.this, GettingStarted.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    protected void onStart() {
//        loadMainActivity();
        super.onStart();
    }

//    private void loadMainActivity(){
//        Firebase currentUser = mAuth!!.currentUser
//        if (currentUser != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}