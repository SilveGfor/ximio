package com.example.ximio.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ximio.MainActivity;
import com.example.ximio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ShopFragment extends Fragment {
    Button btnExit;

    TextView TVMoney;
    TextView TVExp;
    TextView TVRang;

    TextView TVWaterLVL;
    TextView TVGorelkaLVL;

    FloatingActionButton FABAddWaterLvl;
    FloatingActionButton FABAddGorelkaLvl;

    private final Socket socket;
    {
        try{
            socket = IO.socket("http://" + MainActivity.url);
        }catch (URISyntaxException e){
            throw new RuntimeException();
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_shop, container, false);

        btnExit = view.findViewById(R.id.btnExitToMenuFromShop);

        FABAddWaterLvl = view.findViewById(R.id.btnAddWaterLvl);
        FABAddGorelkaLvl = view.findViewById(R.id.btnAddGorelkaLvl);



        TVMoney = view.findViewById(R.id.txtMoney);
        TVExp = view.findViewById(R.id.txtExp);
        TVRang = view.findViewById(R.id.txtRang);

        TVWaterLVL = view.findViewById(R.id.txtWaterLvl);
        TVGorelkaLVL = view.findViewById(R.id.txtGorelkaLvl);

        TVMoney.setText(String.valueOf(MainActivity.money));
        TVExp.setText(String.valueOf(MainActivity.exp));
        TVRang.setText(String.valueOf(MainActivity.rang));

        TVWaterLVL.setText(String.valueOf(6 - MainActivity.waterLVL));
        TVGorelkaLVL.setText(String.valueOf(6 - MainActivity.gorelkaLVL));

        socket.connect();
        ShopFragment.SocketTask socketTask = new ShopFragment.SocketTask();
        socketTask.execute();





        FABAddWaterLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.waterLVL != 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Покупка")
                            .setMessage("Вы точно хотите купить следующий уровень воды?")
                            .setIcon(R.drawable.ic_add)
                            .setCancelable(false)
                            .setNegativeButton("Купить",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            final JSONObject json3 = new JSONObject();
                                            try {
                                                json3.put("nick", MainActivity.NickName);
                                                json3.put("target", "water");
                                                json3.put("lvl", MainActivity.waterLVL - 1);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            socket.emit("objects_boosting", json3);
                                            Log.d("kkk", "Socket_отправка - objects_boosting " + json3.toString());
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Покупка не удалась!")
                            .setMessage("У вас уже максимальный уровень!")
                            .setIcon(R.drawable.ic_error)
                            .setCancelable(false)
                            .setNegativeButton("Ок",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        FABAddGorelkaLvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gorelkaLVL != 1)
                {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Покупка")
                        .setMessage("Вы точно хотите купить следующий уровень горелки?")
                        .setIcon(R.drawable.ic_add)
                        .setCancelable(false)
                        .setNegativeButton("Купить",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        final JSONObject json3 = new JSONObject();
                                        try {
                                            json3.put("nick", MainActivity.NickName);
                                            json3.put("target", "gorelka");
                                            json3.put("lvl", MainActivity.gorelkaLVL - 1);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        socket.emit("objects_boosting", json3);
                                        Log.d("kkk", "Socket_отправка - objects_boosting "+ json3.toString());
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Покупка не удалась!")
                            .setMessage("У вас уже максимальный уровень!")
                            .setIcon(R.drawable.ic_error)
                            .setCancelable(false)
                            .setNegativeButton("Ок",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
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
    class SocketTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("kkk", "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            socket.on("connect", onConnect);
            socket.on("disconnect", onDisconnect);
            socket.on("objects_boosting", onObjectBoosting);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("kkk", "onPostExecute");
        }
    }

    private final Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("kkk", "Connect");
                    /*
                    final JSONObject json2 = new JSONObject();
                    try {
                        json2.put("nick", MainActivity.NickName);
                        json2.put("session_id", MainActivity.Session_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("kkk", "Socket_отправка - connect_to_room - " + json2.toString());
                    socket.emit("connect_to_room", json2);
                     */
                }
            });
        }
    };

    private final Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("kkk", "Disconnect");
                }
            });
        }
    };

    private final Emitter.Listener onObjectBoosting = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String info;
                    String device;
                    Log.d("kkk", "принял - objects_boosting - " + data);
                    try {
                        info = data.getString("info");
                        device = data.getString("device");
                        if (info.equals("OK")) {
                            if (device.equals("water"))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Отчёт")
                                        .setMessage("Новый уровень воды успешно куплен!")
                                        .setIcon(R.drawable.ic_good)
                                        .setCancelable(false)
                                        .setNegativeButton("Ок",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                MainActivity.waterLVL -= 1;
                                MainActivity.money -= 500;
                                TVMoney.setText(String.valueOf(MainActivity.money));
                                TVWaterLVL.setText(String.valueOf(6 - MainActivity.waterLVL));
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Отчёт")
                                        .setMessage("Новый уровень горелки успешно куплен!")
                                        .setIcon(R.drawable.ic_good)
                                        .setCancelable(false)
                                        .setNegativeButton("Ок",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                MainActivity.gorelkaLVL -= 1;
                                MainActivity.money -= 300;
                                TVMoney.setText(String.valueOf(MainActivity.money));
                                TVGorelkaLVL.setText(String.valueOf(6 - MainActivity.gorelkaLVL));
                            }
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Отчёт")
                                    .setMessage(info)
                                    .setIcon(R.drawable.ic_info)
                                    .setCancelable(false)
                                    .setNegativeButton("Вам не хватает денег...",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}