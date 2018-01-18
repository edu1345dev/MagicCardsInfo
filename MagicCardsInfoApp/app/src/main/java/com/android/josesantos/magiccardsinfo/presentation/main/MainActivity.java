package com.android.josesantos.magiccardsinfo.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.josesantos.magiccardsinfo.R;
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard;
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse;
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojasInfoParser;
import com.android.josesantos.magiccardsinfo.data.ligamagic.MagicInfoParser;
import com.android.josesantos.magiccardsinfo.presentation.CardsPagerAdapter;
import com.android.josesantos.magiccardsinfo.presentation.LojasAdapter;
import com.rd.PageIndicatorView;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int loadController = 0;
    List<String> cards = new ArrayList<>();
    private EditText etNomeCarta;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private String queryValue;
    private ProgressBar progressBar;
    private TextView tvNotFound;
    private PopupWindow popupWindow;
    private Handler queryHandler;
    private Runnable searchRunnable;
    private PageIndicatorView pageIndicatorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        etNomeCarta = findViewById(R.id.busca);
        recyclerView = findViewById(R.id.lojas_recycler);
        viewPager = findViewById(R.id.view_pager);
        tvNotFound = findViewById(R.id.tv_not_found);
        pageIndicatorView = findViewById(R.id.view_pager_indicator);

        configureQuery();
        configurePopWindow();
        configureRecycler();
        configureViewPager();


        checkForUpdates();

    }

    private void configurePopWindow() {
        popupWindow = new PopupWindow(this);
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
        etNomeCarta.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                requestCardsFromApi(etNomeCarta.getText().toString());

                return false;
            }
            return false;
        });

        etNomeCarta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
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

        queryHandler.postDelayed(getSearchRunnable(), 800);

    }

    private Runnable getSearchRunnable() {
        searchRunnable = () -> requestCardsFromApi(etNomeCarta.getText().toString());
        return searchRunnable;
    }

    private void requestCardsFromApi(String query) {
        if (query.equals("")) {
            return;
        }

        showProgress();

//        service.getMagicCardsByName(query)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<MagicApiResponse>() {
//                    @Override
//                    public void onNext(MagicApiResponse value) {
//                        generateListToAdapter(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        handleError(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        hideProgress();
//                    }
//                });
    }

    private void handleError(Throwable e) {
        Log.d(TAG, "onError: " + e.getMessage());
        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        hideProgress();
    }

    private void generateListToAdapter(MagicApiResponse value) {
        showPopWindow(removeRepeateadResults(value));
    }

    private List<String> removeRepeateadResults(MagicApiResponse apiResponse) {
        List<String> cardsResult = new ArrayList<>();

        for (MagicApiCard card : apiResponse.getCards()) {
            if (!cardsResult.contains(card.getName())){
                cardsResult.add(card.getName());
            }
        }

        if (cardsResult.isEmpty()){
            cardsResult.add(getString(R.string.no_results_found));
        }

        return cardsResult;
    }

    private void showPopWindow(List<String> resultsList) {
        String[] arrayString = resultsList.toArray(new String[resultsList.size()]);

        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayString));
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            String query = adapterView.getAdapter().getItem(i).toString();
            startQuery(query);
            popupWindow.dismiss();

            hideKeyboard();
        });

        popupWindow.setHeight(resultsList.size()*150);
        if (popupWindow.getHeight() > 600){
            popupWindow.setHeight(600);
        }

        popupWindow.setContentView(listView);

        popupWindow.showAsDropDown(etNomeCarta);


    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void startQuery(String query) {
        queryValue = query;

        showProgress();

        HandlerThread thread = new HandlerThread("thread");
        thread.start();

        Handler handler = new Handler(thread.getLooper());
        handler.post(this::getLigamagicPage);
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

                        runOnUiThread(() -> {
                            showContent(parser);
                            showRecycler(lojasInfoParser1);
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

        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setVisibility(View.VISIBLE);
    }

    private void showRecycler(LojasInfoParser parser) {
        ((LojasAdapter) recyclerView.getAdapter()).setListLojas(parser.getLojasInfo());
        recyclerView.getAdapter().notifyDataSetChanged();

        if (parser.lojasInfo.size() == 0) {
            noResultFound();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
