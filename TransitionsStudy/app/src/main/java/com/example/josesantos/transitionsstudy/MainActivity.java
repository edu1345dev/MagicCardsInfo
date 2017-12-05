package com.example.josesantos.transitionsstudy;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etNomeCarta;

    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private String queryValue;
    private ProgressBar progressBar;
    private TextView tvNotFound;
    int loadController = 0;
    List<String> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress);
        etNomeCarta = findViewById(R.id.busca);
        recyclerView = findViewById(R.id.lojas_recycler);
        viewPager = findViewById(R.id.view_pager);
        tvNotFound = findViewById(R.id.tv_not_found);

        configureQuery();
        configureRecycler();
        configureViewPager();

        cards.add("Ancient Grudge");
        cards.add("Annul");
        cards.add("Ash Barrens");
        cards.add("Atog");
        cards.add("Augur of Bolas");
        cards.add("Aura Flux");
        cards.add("Beetleback Chief");
        cards.add("Bone to Ash");
        cards.add("Bonesplitter");
        cards.add("Chainer's Edict");
        cards.add("Chromatic Star");
        cards.add("Compulsive Research");
        cards.add("Darksteel Citadel");
        cards.add("Curse of the Bloody Tome");
        cards.add("Daze");
        cards.add("Devour Flesh");
        cards.add("Dismal Backwater");
        cards.add("Disfigure");
        cards.add("Electrostatic Bolt");
        cards.add("Flame Slash");
        cards.add("Frogmite");
        cards.add("Hydroblast");
        cards.add("Izzet Boilerworks");
        cards.add("Izzet Boilerworks");
        cards.add("Gearseeker Serpent");
        cards.add("Ancient Grudge");
        cards.add("Annul");
        cards.add("Ash Barrens");
        cards.add("Atog");
        cards.add("Augur of Bolas");
        cards.add("Aura Flux");
        cards.add("Beetleback Chief");
        cards.add("Bone to Ash");
        cards.add("Bonesplitter");
        cards.add("Chainer's Edict");
        cards.add("Chromatic Star");
        cards.add("Compulsive Research");
        cards.add("Darksteel Citadel");
        cards.add("Curse of the Bloody Tome");
        cards.add("Daze");
        cards.add("Devour Flesh");
        cards.add("Dismal Backwater");
        cards.add("Disfigure");
        cards.add("Electrostatic Bolt");
        cards.add("Flame Slash");
        cards.add("Frogmite");
        cards.add("Hydroblast");
        cards.add("Izzet Boilerworks");
        cards.add("Izzet Boilerworks");
        cards.add("Gearseeker Serpent");
        cards.add("Ancient Grudge");
        cards.add("Annul");
        cards.add("Ash Barrens");
        cards.add("Atog");
        cards.add("Augur of Bolas");
        cards.add("Aura Flux");
        cards.add("Beetleback Chief");
        cards.add("Bone to Ash");
        cards.add("Bonesplitter");
        cards.add("Chainer's Edict");
        cards.add("Chromatic Star");
        cards.add("Compulsive Research");
        cards.add("Darksteel Citadel");
        cards.add("Curse of the Bloody Tome");
        cards.add("Daze");
        cards.add("Devour Flesh");
        cards.add("Dismal Backwater");
        cards.add("Disfigure");
        cards.add("Electrostatic Bolt");
        cards.add("Flame Slash");
        cards.add("Frogmite");
        cards.add("Hydroblast");
        cards.add("Izzet Boilerworks");
        cards.add("Izzet Boilerworks");
        cards.add("Gearseeker Serpent");
        cards.add("Ancient Grudge");
        cards.add("Annul");
        cards.add("Ash Barrens");
        cards.add("Atog");
        cards.add("Augur of Bolas");
        cards.add("Aura Flux");
        cards.add("Beetleback Chief");
        cards.add("Bone to Ash");
        cards.add("Bonesplitter");
        cards.add("Chainer's Edict");
        cards.add("Chromatic Star");
        cards.add("Compulsive Research");
        cards.add("Darksteel Citadel");
        cards.add("Curse of the Bloody Tome");
        cards.add("Daze");
        cards.add("Devour Flesh");
        cards.add("Dismal Backwater");
        cards.add("Disfigure");
        cards.add("Electrostatic Bolt");
        cards.add("Flame Slash");
        cards.add("Frogmite");
        cards.add("Hydroblast");
        cards.add("Izzet Boilerworks");
        cards.add("Izzet Boilerworks");
        cards.add("Gearseeker Serpent");

    }

    private void configureViewPager() {
        viewPager.setAdapter(new CardsPagerAdapter(getSupportFragmentManager()));
    }

    private void configureRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new LojasAdapter(new ArrayList<LojaInfo>()));
    }

    private void configureQuery() {
        etNomeCarta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "START SEARCH");
                    requestCardsFromApi();
                    return false;
                }
                return false;
            }
        });
    }

    private void requestCardsFromApi() {
        List<String> list = new ArrayList<>();
        list.add("Atog");
        list.add("Raio");
        list.add("Capturar Pensamento");

        showPopWindow(list);
    }

    private void showPopWindow(List<String> resultsList) {
        String[] arrayString = resultsList.toArray(new String[resultsList.size()]);

        final PopupWindow popupWindow = new PopupWindow(this);
        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayString ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String query = adapterView.getAdapter().getItem(i).toString();
                startQuery(query);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(listView);
        popupWindow.showAsDropDown(etNomeCarta);


    }

    private void startQuery(String query) {
        queryValue = query;

        showProgress();

        HandlerThread thread = new HandlerThread("thread");
        thread.start();

        Handler handler = new Handler(thread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                getLigamagicPage();
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        tvNotFound.setVisibility(View.GONE);

    }

    private void hideProgress(){
        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    private void noResultFound(){
        tvNotFound.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
    }

    private void getLigamagicPage() {
        try {

//            String card = cards.get(loadController);
//            Log.d(TAG, "INCREMENT CONTROLLER "+card);

            final Document doc1 =
                    Jsoup.connect("http://www.ligamagic.com.br/?view=cards/card&card="+queryValue).get();

            Elements scriptElements = doc1.getElementsByTag("script");

            for (Element element: scriptElements) {
                for (DataNode node : element.dataNodes()){
                    if (node.getWholeData().contains("VETiRaridade")){

                        MagicInfoParser parser = new MagicInfoParser();
                        parser.parse(node.getWholeData());

                        handleOnMainThread(parser);

                        LojasInfoParser lojasInfoParser = new LojasInfoParser();
                        lojasInfoParser.parse(doc1);

//                        loadController++;
//
//                        continueLoadind();

                        handleOnMainThread(lojasInfoParser);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void continueLoadind() {
        if (loadController < cards.size()){
            Log.d(TAG, "INCREMENT CONTROLLER "+loadController);

            getLigamagicPage();
        }else {
            loadController = 0;

            Log.d(TAG, "FINISH SEARCH");

        }
    }

    private void handleNotFoundOnMaindThread() {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                noResultFound();
            }
        });
    }

    private void handleOnMainThread(final MagicInfoParser parser) {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                showContent(parser);
            }
        });
    }

    private void showContent(MagicInfoParser parser) {
        CardsPagerAdapter cardsPagerAdapter = new CardsPagerAdapter(getSupportFragmentManager());
        cardsPagerAdapter.setListCards(parser.getListOfCards());

        hideProgress();

        viewPager.setAdapter(cardsPagerAdapter);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    private void handleOnMainThread(final LojasInfoParser parser) {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                showRecycler(parser);
            }
        });
    }

    private void showRecycler(LojasInfoParser parser) {



        ((LojasAdapter)recyclerView.getAdapter()).setListLojas(parser.getLojasInfo());
        recyclerView.getAdapter().notifyDataSetChanged();

        if (parser.lojasInfo.size() == 0){
            noResultFound();
        }
    }


}
