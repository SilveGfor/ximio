package com.example.ximio.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.shapes.OvalShape;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.ximio.MainActivity;
import com.example.ximio.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class SettingsFragment extends Fragment {


    SeekBar volumeControl;

    Button btnExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnExit = view.findViewById(R.id.btnExitToMenuFromSettings);



        volumeControl = view.findViewById(R.id.volumeControl);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                Log.d("kkk", String.valueOf(progress));
                Log.d("kkk", String.valueOf(volume));
                MainActivity.mPlayer.setVolume(volume, volume);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new MenuFragment()).commit();
            }
        });

        return view;
    }
}