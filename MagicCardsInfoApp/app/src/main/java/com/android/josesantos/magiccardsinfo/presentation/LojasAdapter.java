package com.android.josesantos.magiccardsinfo.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.josesantos.magiccardsinfo.R;
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojaInfo;

import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class LojasAdapter extends RecyclerView.Adapter<LojasAdapter.Loja> {

    private Context context;
    private List<LojaInfo> listLojas;

    public LojasAdapter(List<LojaInfo> listLojas, Context context) {
        this.listLojas = listLojas;
        this.context = context;
    }

    public void setListLojas(List<LojaInfo> listLojas) {
        this.listLojas = listLojas;
    }

    @Override
    public Loja onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Loja(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_loja, parent, false));
    }

    @Override
    public void onBindViewHolder(Loja holder, int position) {
        LojaInfo lojaInfo = listLojas.get(position);

        if (lojaInfo.getStoreName() != null){
            if (lojaInfo.getCardName() == null){
                holder.nome.setText(lojaInfo.getStoreName());
                holder.itemView.setBackgroundColor(Color.RED);
            }else {
                holder.nome.setText(lojaInfo.getCardName());
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }

            holder.nome.setVisibility(View.VISIBLE);
        }else {
            holder.nome.setVisibility(View.GONE);
        }

        if (lojaInfo.getEdition() != null){
            holder.edicao.setText(lojaInfo.getEdition());
            holder.edicao.setVisibility(View.VISIBLE);
        }else {
            holder.edicao.setVisibility(View.GONE);
        }

        if (lojaInfo.getPrice() != null){
            holder.preco.setText(lojaInfo.getPrice());
            holder.preco.setVisibility(View.VISIBLE);
        }else {
            holder.preco.setVisibility(View.GONE);
        }

        if (lojaInfo.getPromoPrice() != null){
            holder.precoPromo.setText(lojaInfo.getPromoPrice());
            holder.precoPromo.setVisibility(View.VISIBLE);
            holder.preco.setPaintFlags(holder.preco.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.preco.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            holder.precoPromo.setVisibility(View.GONE);
        }

        if (lojaInfo.getQtd() != null){
            holder.qtd.setText(lojaInfo.condition + " "+ lojaInfo.getQtd());
            holder.qtd.setVisibility(View.VISIBLE);
        }else {
            holder.qtd.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            String url = "https://www.ligamagic.com.br/" + lojaInfo.getLojaUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return listLojas.size();
    }

    class Loja extends RecyclerView.ViewHolder{

        TextView nome;
        TextView edicao;
        TextView preco;
        TextView precoPromo;
        TextView qtd;

        Loja(View itemView) {
            super(itemView);

            setViews(itemView);
        }

        private void setViews(View itemView) {
            nome = itemView.findViewById(R.id.storeName);
            edicao = itemView.findViewById(R.id.edicao);
            preco = itemView.findViewById(R.id.preco);
            precoPromo = itemView.findViewById(R.id.preco_promo);
            qtd = itemView.findViewById(R.id.qtd);
        }
    }
}
