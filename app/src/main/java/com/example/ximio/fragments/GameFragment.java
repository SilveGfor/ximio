package com.example.ximio.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ximio.MainActivity;
import com.example.ximio.R;
import com.example.ximio.models.RequestModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameFragment extends Fragment {

    TextView TV_question;

    Button btnExit;
    Button btnContinue;

    int index;
    int room_num = -1;

    String element;
    String hint;

    Button FABstart;
    FloatingActionButton FABinfo;

    TextView TV1_1;
    TextView TV1_2;
    TextView TV1_3;
    TextView TV1_4;
    TextView TV1_5;
    TextView TV2_1;
    TextView TV2_2;
    TextView TV2_3;
    TextView TV2_4;
    TextView TV2_5;

    TextView TV_timer;

    ImageView water;
    ImageView gorelka;

    MediaPlayer mPlayer;

    RequestModel requestModel = new RequestModel();

    private final Socket socket;
    {
        try{
            socket = IO.socket("http://" + MainActivity.url);
        }catch (URISyntaxException e){
            throw new RuntimeException();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Настройка кнопок

        View view =  inflater.inflate(R.layout.fragment_game, container, false);

        FABstart = view.findViewById(R.id.btnStartReaction);
        FABinfo = view.findViewById(R.id.btnHint);

        TV_question = view.findViewById(R.id.question);

        final Boolean[] water_selected = {false};
        final Boolean[] gorelka_selected = {false};

        water = view.findViewById(R.id.water);
        gorelka = view.findViewById(R.id.gorelka);

        TV_timer = view.findViewById(R.id.timer);

        btnExit = view.findViewById(R.id.btnExitToMenu);
        btnContinue = view.findViewById(R.id.btnContinue);

        TV1_1 = view.findViewById(R.id.el1_1);
        TV1_2 = view.findViewById(R.id.el1_2);
        TV1_3 = view.findViewById(R.id.el1_3);
        TV1_4 = view.findViewById(R.id.el1_4);
        TV1_5 = view.findViewById(R.id.el1_5);
        TV2_1 = view.findViewById(R.id.el2_1);
        TV2_2 = view.findViewById(R.id.el2_2);
        TV2_3 = view.findViewById(R.id.el2_3);
        TV2_4 = view.findViewById(R.id.el2_4);
        TV2_5 = view.findViewById(R.id.el2_5);

        //подключение к сокетам
        socket.connect();
        SocketTask socketTask = new SocketTask();
        socketTask.execute();

        final JSONObject json3 = new JSONObject();
        try {
            json3.put("nick", MainActivity.NickName);
            json3.put("session_id", MainActivity.Session_id);
            json3.put("rang", MainActivity.rang);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("play_alone", json3);
        Log.d("kkk", "Socket_отправка - play_alone "+ json3.toString());


        TV1_1.setText("-");
        TV1_2.setText("-");
        TV1_3.setText("-");
        TV1_4.setText("-");
        TV1_5.setText("-");
        TV2_1.setText("-");
        TV2_2.setText("-");
        TV2_3.setText("-");
        TV2_4.setText("-");
        TV2_5.setText("-");
        btnContinue.setVisibility(View.GONE);

        //обработки нажатия кнопок
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new MenuFragment()).commit();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject json3 = new JSONObject();
                try {
                    json3.put("nick", MainActivity.NickName);
                    json3.put("session_id", MainActivity.Session_id);
                    json3.put("rang", MainActivity.rang);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("play_alone", json3);
                Log.d("kkk", "Socket_отправка - play_alone"+ json3.toString());
                ClearView();
                room_num = -1;
                btnContinue.setVisibility(View.GONE);
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!water_selected[0])
                {
                    water_selected[0] = true;

                    mPlayer= MediaPlayer.create(getContext(), R.raw.water_sound);
                    mPlayer.start();

                    water.setBackgroundResource(R.drawable.active_water);
                    requestModel.devices.add("water");
                }
                else
                {
                    water_selected[0] = false;
                    requestModel.devices.remove("water");
                    water.setBackgroundResource(R.drawable.water);
                }
                Log.d("a", "Devices - " + String.valueOf(requestModel.devices));
            }
        });
        gorelka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gorelka_selected[0])
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.spichka_sound);
                    mPlayer.start();
                    gorelka_selected[0] = true;
                    gorelka.setBackgroundResource(R.drawable.active_gorelka);
                    requestModel.devices.add("gorelka");
                }
                else
                {
                    gorelka_selected[0] = false;
                    requestModel.devices.remove("gorelka");
                    gorelka.setBackgroundResource(R.drawable.gorelka);
                }
                Log.d("a", "Devices - " + String.valueOf(requestModel.devices));
            }
        });

        TV1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV1_1.getText());
                if (TV1_1.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV1_1.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV1_1.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV1_2.getText());
                if (TV1_2.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV1_2.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV1_2.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV1_3.getText());
                if (TV1_3.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV1_3.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV1_3.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV1_4.getText());
                if (TV1_4.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV1_4.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV1_4.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV1_5.getText());
                if (TV1_5.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV1_5.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV1_5.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });

        TV2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV2_1.getText());
                if (TV2_1.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV2_1.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV2_1.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV2_2.getText());
                if (TV2_2.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV2_2.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV2_2.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV2_3.getText());
                if (TV2_3.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV2_3.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV2_3.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV2_4.getText());
                if (TV2_4.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV2_4.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV2_4.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });
        TV2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element = String.valueOf(TV2_5.getText());
                if (TV2_5.getBackground() == null)
                {
                    mPlayer= MediaPlayer.create(getContext(), R.raw.push_button);
                    mPlayer.start();
                    TV2_5.setBackgroundResource(R.color.Green);
                    requestModel.elements.add(element);
                }
                else
                {
                    requestModel.elements.remove(element);
                    TV2_5.setBackground(null);
                }
                Log.d("a", "Elements - " + String.valueOf(requestModel.elements));
            }
        });



        FABstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject json = new JSONObject();
                try {
                    json.put("nick", MainActivity.NickName);
                    json.put("list_of_substances", requestModel.elements);
                    json.put("list_of_objects", requestModel.devices);
                    json.put("index", index);
                    json.put("room_num", room_num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("play_alone_answer", json);
                Log.d("kkk", "Socket_отправка - play_alone_answer"+ json.toString());
            }
        });

        //кнопка начала реакции
        FABinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Подсказка")
                        .setMessage(hint)
                        .setIcon(R.drawable.ic_info)
                        .setCancelable(false)
                        .setNegativeButton("А, спасибо",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        /*
        GridView gridView = view.findViewById(R.id.fisrtTBL);
        gridView.setAdapter(new FirstTBLAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("a", "position");
            }
        });
        */
        return view;
    }

    //Асинхронный слушатель событий сокетов
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
            socket.on("play_alone", onPlayAlone);
            socket.on("play_alone_answer", onPlayAloneAnswer);
            socket.on("info", onInfo);
            socket.on("timer", onTimer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("kkk", "onPostExecute");
        }
    }

    //ДАЛЬШЕ разные события сокетов
    private final Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("kkk", "Connect");
                        final JSONObject json2 = new JSONObject();
                        try {
                            json2.put("nick", MainActivity.NickName);
                            json2.put("session_id", MainActivity.Session_id);
                            json2.put("room_num", room_num);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("kkk", "Socket_отправка - connect_to_room - " + json2.toString());
                        socket.emit("connect_to_room", json2);
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

    private final Emitter.Listener onInfo = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("kkk", "STOP!");
                }
            });
        }
    };

    //Таймер
    private final Emitter.Listener onTimer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String time;
                    //Log.d("kkk", "принял - timer - " + data);
                    try {
                        time = data.getString("timer");
                        TV_timer.setText(time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    //ответ
    private final Emitter.Listener onPlayAloneAnswer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int money;
                    String reaction;
                    String substance;
                    Log.d("kkk", "принял - play_alone_answer - " + data);
                    try {
                        reaction = data.getString("reaction");
                        Log.d("kkk", "LOG - " + reaction + " - " + (reaction.equals("incorrect")));
                        if (reaction.equals("incorrect"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Упс!")
                                    .setMessage("Похоже вы ошиблись")
                                    .setIcon(R.drawable.ic_bad)
                                    .setCancelable(false)
                                    .setNegativeButton("Ок",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                            ClearView();
                        }
                        else
                        {
                            substance = data.getString("substance");
                            money = data.getInt("money");
                            MainActivity.money += money;

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Ура!")
                                    .setMessage("Вы ответили правильно и получили " + reaction + "\n" + substance + "\nВы заработали " + money + " монет")
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
                            btnContinue.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private final Emitter.Listener onPlayAlone = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray names;
                    String question;

                    Log.d("kkk", "принял - play_alone - " + data);
                    try {
                        names = data.getJSONArray("substances");
                        index = data.getInt("index");
                        question = data.getString("question");
                        hint = data.getString("hint");
                        room_num = data.getInt("room_num");
                        TV_question.setText(question);

                        TV1_1.setText(String.valueOf(names.get(0)));
                        TV1_2.setText(String.valueOf(names.get(1)));
                        TV1_3.setText(String.valueOf(names.get(2)));
                        TV1_4.setText(String.valueOf(names.get(3)));
                        TV1_5.setText(String.valueOf(names.get(4)));
                        TV2_1.setText(String.valueOf(names.get(5)));
                        TV2_2.setText(String.valueOf(names.get(6)));
                        TV2_3.setText(String.valueOf(names.get(7)));
                        TV2_4.setText(String.valueOf(names.get(8)));
                        TV2_5.setText(String.valueOf(names.get(9)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };

    //очистка объектов
    public void ClearView() {
        water.setBackgroundResource(R.drawable.water);
        gorelka.setBackgroundResource(R.drawable.gorelka);
        for (int i=requestModel.elements.size()-1; i>=0; i--)
        {
            requestModel.elements.remove(i);
        }
        for (int i=requestModel.devices.size()-1; i>=0; i--)
        {
            requestModel.devices.remove(i);
        }
        TV2_1.setBackground(null);
        TV2_2.setBackground(null);
        TV2_3.setBackground(null);
        TV2_4.setBackground(null);
        TV2_5.setBackground(null);

        TV1_1.setBackground(null);
        TV1_2.setBackground(null);
        TV1_3.setBackground(null);
        TV1_4.setBackground(null);
        TV1_5.setBackground(null);
    }
}
