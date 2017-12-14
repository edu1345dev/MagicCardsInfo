package com.example.josesantos.transitionsstudy.presentation;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.josesantos.transitionsstudy.data.ligamagic.LojasInfoParser;
import com.example.josesantos.transitionsstudy.data.ligamagic.MagicInfoParser;
import com.example.josesantos.transitionsstudy.R;
import com.example.josesantos.transitionsstudy.data.api.magicApi.MagicApiService;
import com.example.josesantos.transitionsstudy.data.entity.MagicApiCard;
import com.example.josesantos.transitionsstudy.data.entity.MagicApiResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
    MagicApiService service = new MagicApiService();
    private PopupWindow popupWindow;
    private Handler queryHandler;
    private Runnable searchRunnable;

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
        configurePopWindow();
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

    private void configurePopWindow() {
        popupWindow = new PopupWindow(this);
        popupWindow.setHeight(600);
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.search_background));
        popupWindow.setOutsideTouchable(true);
    }

    private void configureViewPager() {
        viewPager.setAdapter(new CardsPagerAdapter(getSupportFragmentManager()));
    }

    private void configureRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new LojasAdapter(new ArrayList<>()));
    }

    private void configureQuery() {
        etNomeCarta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    requestCardsFromApi(etNomeCarta.getText().toString());

                    return false;
                }
                return false;
            }
        });

        etNomeCarta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                delayQueryStart();
            }
        });
    }

    private void delayQueryStart() {
        if (queryHandler == null) {
            queryHandler = new Handler();
        }

        if (searchRunnable != null) {
            queryHandler.removeCallbacks(searchRunnable);
        }

        queryHandler.postDelayed(getRunnable(), 500);

    }

    private Runnable getRunnable() {

        searchRunnable = () -> requestCardsFromApi(etNomeCarta.getText().toString());

        return searchRunnable;
    }

    private void requestCardsFromApi(String query) {
        if (query.equals("")) {
            return;
        }

        showProgress();

        service.getMagicCardsByName(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<MagicApiResponse>() {
                    @Override
                    public void onNext(MagicApiResponse value) {
                        generateListToAdapter(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });
    }

    private void generateListToAdapter(MagicApiResponse value) {
        if (value.getCards().isEmpty()) {
            popupWindow.dismiss();
            return;
        }



        showPopWindow(removeRepeateadResults(value));
    }

    private List<String> removeRepeateadResults(MagicApiResponse reponse) {
        List<String> cardsResult = new ArrayList<>();

        for (MagicApiCard card :
                reponse.getCards()) {

            if (!cardsResult.contains(card.getName())){
                cardsResult.add(card.getName());
            }
        }

        return cardsResult;
    }

    private void showPopWindow(List<String> resultsList) {
        String[] arrayString = resultsList.toArray(new String[resultsList.size()]);

        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayString));
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

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    private void noResultFound() {
        tvNotFound.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
    }

    private void getLigamagicPage() {
        try {

//            String card = cards.get(loadController);
//            Log.d(TAG, "INCREMENT CONTROLLER "+card);

            String ligamagicUrl = "http://www.ligamagic.com.br/?view=cards/card&card=";
            String scgUrl = "http://sales.starcitygames.com/search.php?substring=";

            final Document doc1 =
                    Jsoup.connect(ligamagicUrl + queryValue).get();

            Log.d(TAG, "doc: " + doc1.html());

            Elements scriptElements = doc1.getElementsByTag("script");

            final LojasInfoParser lojasInfoParser = new LojasInfoParser();
            lojasInfoParser.parse(doc1);

            for (Element element : scriptElements) {
                for (DataNode node : element.dataNodes()) {
                    Log.d(TAG, "node: " + node.getWholeData());
                    if (node.getWholeData().contains("VETiRaridade")) {

                        final MagicInfoParser parser = new MagicInfoParser();
                        parser.parse(node.getWholeData());


                        final LojasInfoParser lojasInfoParser1 = new LojasInfoParser();
                        lojasInfoParser1.parse(doc1);

//                        loadController++;
//
//                        continueLoadind();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showContent(parser);
                                showRecycler(lojasInfoParser1);
                            }
                        });

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void continueLoadind() {
        if (loadController < cards.size()) {
            Log.d(TAG, "INCREMENT CONTROLLER " + loadController);

            getLigamagicPage();
        } else {
            loadController = 0;

            Log.d(TAG, "FINISH SEARCH");

        }
    }

    private void showContent(MagicInfoParser parser) {
        CardsPagerAdapter cardsPagerAdapter = new CardsPagerAdapter(getSupportFragmentManager());
        cardsPagerAdapter.setListCards(parser.getListOfCards());

        hideProgress();

        viewPager.setAdapter(cardsPagerAdapter);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    private void showRecycler(LojasInfoParser parser) {
        ((LojasAdapter) recyclerView.getAdapter()).setListLojas(parser.getLojasInfo());
        recyclerView.getAdapter().notifyDataSetChanged();

        if (parser.lojasInfo.size() == 0) {
            noResultFound();
        }
    }


}
