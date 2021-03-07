package com.example.ximio.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ximio.MainActivity;
import com.example.ximio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MenuFragment extends Fragment {
    Button btnSettings;
    Button btnGameAlone;
    Button btnGameMulti;
    Button btnRules;
    Button btnLearn;
    Button btnExit;

    TextView TVNick;
    TextView TVMoney;
    TextView TVExp;
    TextView TVRang;

    ImageButton FABshop;

    int pressedTimes = 0;

    public static final String APP_PREFERENCES = "user";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASSWORD = "password";
    public static final String APP_PREFERENCES_NICKNAME = "nickname";

    private SharedPreferences mSettings;

    MediaPlayer mPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnGameAlone = view.findViewById(R.id.btnGameAlone);
        btnGameMulti = view.findViewById(R.id.btnGameMulti);
        btnRules = view.findViewById(R.id.btnRules);
        btnLearn = view.findViewById(R.id.btnLearn);
        btnExit = view.findViewById(R.id.btnExit);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        FABshop = view.findViewById(R.id.btnShop);

        TVNick = view.findViewById(R.id.txtNick);
        TVMoney = view.findViewById(R.id.txtMoney);
        TVExp = view.findViewById(R.id.txtExp);
        TVRang = view.findViewById(R.id.txtRang);

        TVNick.setText(MainActivity.NickName);
        TVMoney.setText(String.valueOf(MainActivity.money));
        TVExp.setText(String.valueOf(MainActivity.exp));
        TVRang.setText(String.valueOf(MainActivity.rang));


        MainActivity.mPlayer.start();
        MainActivity.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                MainActivity.mPlayer.start();
            }
        });

        FABshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new ShopFragment()).commit();
            }
        });

        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new LearnFragment()).commit();
            }
        });

        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new RulesFragment()).commit();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Важное сообщение!")
                        .setMessage("Закройте окно!")
                        .setIcon(R.drawable.ic_money)
                        .setCancelable(false)
                        .setNegativeButton("ОК, иду на балкон",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

                 */
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new SettingsFragment()).commit();
            }
        });

        btnGameAlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new GameFragment()).commit();
            }
        });

        btnGameMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new MultiGameFragment()).commit();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_EMAIL, String.valueOf(""));
                editor.putString(APP_PREFERENCES_PASSWORD, String.valueOf(""));
                editor.apply();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new StartFragment()).commit();
            }
        });

        return view;
    }
}