package com.example.josesantos.transitionsstudy;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etNomeCarta;
    private ImageView ivImagem;
    private TextView tvRaridade;
    private TextView tvMenorPreco;
    private TextView tvNomeEdicao;
    private ImageButton btBusca;
    private String queryValue;
    private TextView tvMedioPreco;
    private TextView tvMaiorPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRaridade = findViewById(R.id.raridade);
        tvNomeEdicao = findViewById(R.id.nome_edicao);
        tvMenorPreco = findViewById(R.id.menor_preco);
        tvMedioPreco = findViewById(R.id.medio_preco);
        tvMaiorPreco = findViewById(R.id.maior_preco);
        etNomeCarta = findViewById(R.id.busca);
        ivImagem = findViewById(R.id.image);
        btBusca = findViewById(R.id.bt_busca);

        configureQuery();

        startQuery("Atog");
    }

    private void configureQuery() {
        btBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuery(etNomeCarta.getText().toString());
            }
        });
    }

    private void startQuery(String query) {
        queryValue = query.replace(" ","%20");

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

    private void getLigamagicPage() {
        try {
            final Document doc1 = Jsoup.connect("http://www.ligamagic.com.br/?view=cards/card&card="+queryValue ).get();

            doc1.getAllElements().filter(new NodeFilter() {
                @Override
                public FilterResult head(Node node, int depth) {

                    Log.d(TAG, "head Node "+node);

                    String attr = node.attr("type").toString();

                    Log.d(TAG, "head from Node "+attr);

                    TextView textView = new TextView(getBaseContext());
                    textView.setText(attr);

                    String fromTv = (String) textView.getText();

                    Log.d(TAG, "head texView: "+fromTv);

                    String replaced = fromTv.replace("\t","").replace("\n","").replace("<","").replace(">","");

                    Log.d(TAG, "head replaced: "+replaced);
                    return null;
                }

                @Override
                public FilterResult tail(Node node, int depth) {
//                    Log.d(TAG, "tail: "+node);
                    return null;
                }
            });

            Elements scriptElements = doc1.getElementsByTag("script");

            for (Element element: scriptElements) {
                for (DataNode node : element.dataNodes()){
                    if (node.getWholeData().contains("VETiRaridade")){
                        MagicInfoParser parser = new MagicInfoParser();
                        parser.parse(node.getWholeData());
                        handleOnMainThread(parser);
                    }
                }
            }

            Elements divElements = doc1.getElementsByTag("div");

            for (Element element: divElements) {
                for (DataNode node : element.dataNodes()){
                    Log.d(TAG, "div "+node.getWholeData());
//                    if (node.getWholeData().contains("VETiRaridade")){
//                        MagicInfoParser parser = new MagicInfoParser();
//                        parser.parse(node.getWholeData());
//                        handleOnMainThread(parser);
//                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Glide.with(this)
                .load(parser.getListImagem().get(0))
                .into(ivImagem);

        tvRaridade.setText("Raridade: "+parser.getListRaridade().get(0));
        tvNomeEdicao.setText("Edição: "+parser.getListNomeOficial().get(0));
        tvMenorPreco.setText(parser.getListMenorPreco().get(0));
        tvMedioPreco.setText(parser.getListMedioPreco().get(0));
        tvMaiorPreco.setText(parser.getListMaiorPreco().get(0));
    }
}
