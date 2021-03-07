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

public class RulesFragment extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        Back1 = view.findViewById(R.id.btnBack1);

        Back1.setVisibility(View.VISIBLE);

        models = new ArrayList<>();
        models.add(new RuleModel(R.drawable.colbocha, "Введение", "В химии существуют понятия веществ и элементов. Вещества - это что-то, что мы можем увидеть в реальном мире и потрогать это. Веществами будут различные металлы, соли, щелочи, газы и т.д., и с веществами вам предстоит взаимодействовать в процессе игры. Многие путают вещества с элементами. Элемент - это совокупность атомов с одинаковым зарядом атомных ядер, из элементов состоят простые вещества, элементы представлены в таблице Менделеева\n"));
        models.add(new RuleModel(R.drawable.colba_red, "Простые и сложные вещества, смеси", "Простые вещества - вещества, состоящие из атомов одного элемента. Например, Cl2, H2, Cu, Fe, Au\n" +
                "Сложные вещества состоят из атомов минимум двух элементов. Например, H2O, H2SO4, HCl, NaNO3 и т.д.\n" +
                "\n" +
                "Сложные вещества не стоит путать со смесями. Смесь - это продукт смешения двух нереагирующих веществ. Например, если смешать KNO3 и Na2SO4, то они не прореагируют, так как для этого нужна как минимум вода. Получится просто смесь из двух солей. И как ее бы ни трясли и ни вертели, она не перестанет быть смесью. Но если эти две соли растворить в воде, то произойдет реакция и получатся новые вещества\n"));
        models.add(new RuleModel(R.drawable.colba_yellow, "Оксиды", "Оксиды - сложные вещества с кислородом со степенью окисления -2. Например, Na2O, K2O, CuO, Al2O3"));
        models.add(new RuleModel(R.drawable.colba_orange, "Гидроксиды", "Гидроксиды - сложные вещества с OH группой, которая имеет степень окисления -1. Например, NaOH, KOH, Cu(OH)2, Al(OH)3 "));
        models.add(new RuleModel(R.drawable.colba_blue, "Кислоты и соли", "Кислоты - сложные вещества, состоящие из нескольких атомов водорода и кислотного остатка. Благодаря водороду, который всегда в формуле записывается на первом месте, кислоты легко находить\n" +
                "\n" +
                "Соли - это сложные вещества, состоящие из катионов металлов и анионов кислотных остатков. На данный момент мы будем рассматривать только средние соли.\n"));
        models.add(new RuleModel(R.drawable.colbocha, "Кислоты и соли", "Основные названия кислот, их формулы, кислотные остатки и их названия:\n" +
                "Фтороводородная (плавиковая) - HF - F- - Фторид\n" +
                "Хлороводородная (соляная) - HCl - Cl- - Хлорид\n" +
                "Бромоводородная - HBr - Br- - Бромид\n" +
                "Иодоводородная - HI - I- - Иодид\n" +
                "Сероводородная - H2S - S2- - Сульфид\n" +
                "Серная - H2SO4 - SO42- - Сульфат\n" +
                "Сернистая - H2SO3 - SO32- - Сульфит\n" +
                "Азотная - HNO3 - NO3- - Нитрат\n" +
                "Азотистая - HNO2 - NO2- - Нитрит\n" +
                "Угольная - H2CO3 - CO32- - Карбонат\n" +
                "Ортофосфорная - H3PO4 - PO43- - Ортофосфат\n" +
                "Кремниевая - H2SiO3 - SiO32- Силикат\n" +
                "\n" +
                "Примеры солей:\n" +
                "Na2SO4 - соль серной кислоты, сульфат натрия\n" +
                "KBr - соль бромоводородной кислоты, бромид калия\n" +
                "CuNO3 - соль азотной кислоты, нитрат меди II\n" +
                "BaSiO3 - соль угольной кислоты, силикат бария\n"));

        models.add(new RuleModel(R.drawable.colba_red, "Химические реакции и условия их протекания", "Химическая реакция - это превращение одних веществ (реагентов) в другие, отличающиеся по химическому составу или строению (продукты реакции). То есть, если у нас есть ряд веществ (реагентов), и мы хотим получить новые вещества, отличные от исходных, должна произойти химическая реакция\n" +
                "\n" +
                "Условия протекания химической реакции, рассматриваемые в игре: температура (мы рассмотрим только повышение. Увеличение температуры приводит вещество в более возбужденное состояние, что позволяет произвести реакцию), наличие растворителя (мы рассмотрим воду. В пункте про вещества и смеси мы рассказали, что без растворителя реакции не будет, а вещества при контакте образуют лишь смесь)\n" +
                "Условие, что для реакции вещества должны соприкасаться, мы считаем очевидным\n"));
        models.add(new RuleModel(R.drawable.colba_yellow, "Химические реакции и условия их протекания", "Условия протекания химической реакции, не рассматриваемые в игре: давление, наличие катализа, ультрафиолетовое излучение, свет, электрический ток\n" +
                "\n" +
                "Реакция соединения - это реакция, когда из двух веществ получается одно. Например, CaO + H2O -> Ca(OH)2\n" +
                "\n" +
                "Реакция разложения - это реакция, обратная реакции соединения, то есть из одного вещества получается два. Например, CaCO3 -> CaO + CO2\n" +
                "\n" +
                "Реакция замещения - это реакция, когда в результате взаимодействия простого и сложного вещества простое замещает часть сложного вещества и образуются новые сложное и простое вещества. Например, CuO + H2 -> Cu + H2O"));

        models.add(new RuleModel(R.drawable.colba_orange, "Химические реакции и условия их протекания", "Реакция обмена - это реакция, когда сложные вещества обмениваются своими частями. Например, NaOH + HCl -> NaCl + Н2О\n" +
                "\n" +
                "Окислительно-восстановительная реакция - это реакция, когда реагирующие вещества меняют свою степень окисления, то есть некоторые берут электроны (окислители), а некоторые отдают (восстановители). При этом степень окисления восстановителей повышается, окислителей понижается\n" +
                "\n" +
                "Степень окисления - условный заряд атома. Его можно узнать в таблице растворимости, в интернете (но лучше запомнить для самых частых веществ) или в сложном веществе по правилу, что сумма степеней окисления вещества всегда равна 0\n"));

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
                getResources().getColor(R.color.Yellow),
                getResources().getColor(R.color.Orange)
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