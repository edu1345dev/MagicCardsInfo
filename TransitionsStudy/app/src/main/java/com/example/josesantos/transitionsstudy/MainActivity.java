package com.example.josesantos.transitionsstudy;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    ConstraintSet constraintSet1 = new ConstraintSet();
    ConstraintSet constraintSet2 = new ConstraintSet();

    ConstraintLayout container;
    private boolean isExpanded = true;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        tv = findViewById(R.id.text);

//        tv.setText(Html.fromHtml(Consntants.cardsList));

        HandlerThread thread = new HandlerThread("thread");
        thread.start();

        Handler handler = new Handler(thread.getLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLigamagicPage();
            }
        },100);

//        constraintSet1.clone(container);
//        constraintSet2.clone(getBaseContext(), R.layout.activity_main_expanded);
//
//        container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                applyTransition();
//            }
//        });
    }

    private void getLigamagicPage() {
        try {
            final Document doc1 = Jsoup.connect("http://www.ligamagic.com.br/?view=cards/card&card=Hazoret%20the%20Fervent").get();

            Log.d(TAG, "getLigamagicPage: "+doc1.getAllElements());

            Elements scriptElements = doc1.getElementsByTag("script");

            for (Element element: scriptElements) {
                for (DataNode node : element.dataNodes()){
                    if (node.getWholeData().contains("VETiRaridade")){

                    }
                    Log.d(TAG, "data nodes: "+ node.getWholeData());
                }
            }

            Log.d(TAG, "------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyTransition() {
        if (isExpanded){
            TransitionSet transition = new TransitionSet();
            transition.addTransition(new ChangeBounds());
            transition.addTransition(new Slide());


            TransitionManager.beginDelayedTransition(container, transition);
            constraintSet2.applyTo(container);

            isExpanded = false;
        }else {
            TransitionSet transition = new TransitionSet();
            transition.addTransition(new ChangeBounds());
            transition.addTransition(new Slide());


            TransitionManager.beginDelayedTransition(container, transition);

            constraintSet1.applyTo(container);

            isExpanded = true;
        }
    }
}
