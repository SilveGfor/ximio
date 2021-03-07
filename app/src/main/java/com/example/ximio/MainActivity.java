package com.example.ximio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.ximio.fragments.MenuFragment;
import com.example.ximio.fragments.StartFragment;

public class MainActivity extends AppCompatActivity {

    public static String NickName = "";
    public static String Session_id = "";
    public static int money = 0;
    public static int exp = 0;
    public static int rang = 1;
    public static int waterLVL = 0;
    public static int gorelkaLVL = 0;
    public static String url = "81.163.28.77:5000";
    public static Boolean MusicOn = false;

    public static MediaPlayer mPlayer;

    public static String password = "";
    public static String nick = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new StartFragment()).commit();
    }
}