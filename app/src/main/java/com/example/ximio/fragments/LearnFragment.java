package com.example.ximio.fragments;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ximio.R;
import com.example.ximio.adapters.RulesAdapter;
import com.example.ximio.models.RuleModel;

import java.util.ArrayList;
import java.util.List;

public class LearnFragment extends Fragment {


    ViewPager viewPager;
    RulesAdapter rules_adapter;
    List<RuleModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    Button Back1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        Back1 = view.findViewById(R.id.btnBack1);

        Back1.setVisibility(View.VISIBLE);

        models = new ArrayList<>();
        models.add(new RuleModel(R.drawable.colbocha, "Описание", "xim.io - игра, нацеленная на изучение части курса химии для 8-9 классов. В процессе игры ты повысишь понимание химических реакций и условий их протекания. Чем лучше будешь угадывать реакции, тем больше денег (тут в скобках значок денег) и опыта (значок опыта) у тебя будет. Чем выше опыт, тем выше твой ранг (значок ранга), а значит тебя будут ждать более интересные реакции и сильные соперники. Но не пугайся, мы уверены, что ты со всем справишься и мир станет для тебя чуточку понятнее. Главное, чтобы у тебя был интернет и желание!\n"));
        models.add(new RuleModel(R.drawable.colba_red, "Обучение", "Если ты совсем новичок в химии или чувствуешь пробелы в знаниях, советуем начать игру с обучения, где мы постарались в доступной форме рассказать об основах химии и химических реакций"));
        models.add(new RuleModel(R.drawable.colba_yellow, "Ход игры\n" +
                "Одиночная игра\n", "В одиночной игре ты можешь почувствовать себя немного химиком, немного продавцом, немножко алхимиком и магом. Тебе предстоит выполнять заказы покупателей, которые расскажут, какое вещество им нужно и для чего. Для этого необходимо будет выбрать несколько веществ из имеющихся и, возможно, воду (картинка воды) или горелку (картинка горелки), но не обязательно. Если ты совсем запутаешься и не будешь знать, что делать, ты сможешь воспользоваться короткой подсказкой (картинка подсказки). Как только ты выберешь вещества и инструменты химика (горелка и вода), ты сможешь начать реакцию. Если она окажется верной, покупатель тебе заплатит, если нет - ты сможешь попробовать еще, пока не вышло время и покупатель не ушел. Поскольку покупатель добрый, то у тебя есть неограниченное количество попыток и использование подсказки ни на что не влияет, кроме твоих знаний\n"));
        models.add(new RuleModel(R.drawable.colba_orange, "Мультиплеер", "В мультиплеере тебе предстоит соревноваться с другими игроками, которые будут подбираться в зависимости от твоего ранга (ранг может быть 0 - от 1000 exp, 1 - от 2000 exp, 2 - от 3000 exp). Если твой ранг 0, то тебе никогда не попадутся сильные соперники со 2 рангом. Процесс игры такой же, как и в одиночной игре, только тебе не будут доступны подсказки. Кроме того, теперь у воды и горелки есть уровень прокачки, что влияет на скорость протекания реакций. Сначала уровни воды и горелки равны 1, что будет увеличивать время реакций на 5 секунд при использовании воды или горелки и на 10 секунд, если ты используешь и то и другое. Уровень воды и горелки можно прокачать в магазине за деньги, заработанные в одиночной игре. За победу в мултиплеере тебе будут начислять опыт"));
        models.add(new RuleModel(R.drawable.colba_blue, "Магазин", "В магазине ты можешь прокачать твои инструменты химика: воду и горелку - и узнать их уровень. Чем выше уровень инструментов, тем больше шансов на победу в мультиплеере. Уровни могут быть от 1 до 5"));

        models.add(new RuleModel(R.drawable.colbocha, "Настройки", "В настройках ты можешь выбрать громкость музыки"));

        models.add(new RuleModel(R.drawable.colba_red, "Химические реакции и условия их протекания", "Химическая реакция - это превращение одних веществ (реагентов) в другие, отличающиеся по химическому составу или строению (продукты реакции). То есть, если у нас есть ряд веществ (реагентов), и мы хотим получить новые вещества, отличные от исходных, должна произойти химическая реакция\n" +
                "\n" +
                "Условия протекания химической реакции, рассматриваемые в игре: температура (мы рассмотрим только повышение. Увеличение температуры приводит вещество в более возбужденное состояние, что позволяет произвести реакцию), наличие растворителя (мы рассмотрим воду. В пункте про вещества и смеси мы рассказали, что без растворителя реакции не будет, а вещества при контакте образуют лишь смесь)\n" +
                "Условие, что для реакции вещества должны соприкасаться, мы считаем очевидным\n"));
        models.add(new RuleModel(R.drawable.colba_yellow, "Примечание", "Если игра работает медленно, то закройте приложение полностью и откройте заново"));
        rules_adapter = new RulesAdapter(models, getActivity());

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(rules_adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.Green),
                getResources().getColor(R.color.Red),
                getResources().getColor(R.color.Yellow),
                getResources().getColor(R.color.Orange),
                getResources().getColor(R.color.Blue),
                getResources().getColor(R.color.Green),
                getResources().getColor(R.color.Red),
                getResources().getColor(R.color.Yellow)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (rules_adapter.getCount() - 1) && position < (colors.length - 1))
                {

                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );

                }
                else
                {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back1.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity, new MenuFragment()).commit();
            }
        });

        return view;
    }
}