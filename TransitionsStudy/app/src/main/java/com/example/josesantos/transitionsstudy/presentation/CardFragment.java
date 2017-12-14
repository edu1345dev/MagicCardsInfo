package com.example.josesantos.transitionsstudy.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.josesantos.transitionsstudy.MagicCardInfo;
import com.example.josesantos.transitionsstudy.R;

/**
 * Created by josesantos on 25/11/17.
 */

public class CardFragment extends Fragment {

    private MagicCardInfo cardInfo;
    private static String CARD = "CARD";

    private ImageView ivImagem;
    private TextView tvRaridade;
    private TextView tvMenorPreco;
    private TextView tvNomeEdicao;
    private TextView tvMedioPreco;
    private TextView tvMaiorPreco;

    public static CardFragment newInstance(MagicCardInfo m) {

        Bundle args = new Bundle();
        args.putSerializable(CARD,m);
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewholder_card_info, null);
        if (getArguments().containsKey(CARD)){
            cardInfo = (MagicCardInfo) getArguments().getSerializable(CARD);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setViews();
        showContent(cardInfo);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setViews() {
        tvRaridade = getView().findViewById(R.id.raridade);
        tvNomeEdicao = getView().findViewById(R.id.nome_edicao);
        tvMenorPreco = getView().findViewById(R.id.menor_preco);
        tvMedioPreco = getView().findViewById(R.id.medio_preco);
        tvMaiorPreco = getView().findViewById(R.id.maior_preco);
        ivImagem = getView().findViewById(R.id.image);
    }

    private void showContent(MagicCardInfo cardInfo) {
        Glide.with(this)
                .load(cardInfo.getImagem())
                .into(ivImagem);

        tvRaridade.setText("Raridade: "+cardInfo.getRaridade());
        tvNomeEdicao.setText("Edição: "+cardInfo.getNomeEdicaoOficial());
        tvMenorPreco.setText(cardInfo.getMenorPreco());
        tvMedioPreco.setText(cardInfo.getMedioPreco());
        tvMaiorPreco.setText(cardInfo.getMaiorPreco());
    }
}
