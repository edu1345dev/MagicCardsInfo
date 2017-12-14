package com.android.josesantos.magiccardsinfo.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.josesantos.magiccardsinfo.data.ligamagic.LojaInfo;
import com.android.josesantos.magiccardsinfo.R;

import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class LojasAdapter extends RecyclerView.Adapter<LojasAdapter.Loja> {

    private List<LojaInfo> listLojas;

    public LojasAdapter(List<LojaInfo> listLojas) {
        this.listLojas = listLojas;
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

        if (lojaInfo.getNome() != null){
            holder.nome.setText(lojaInfo.getNome());
        }else {
            holder.nome.setVisibility(View.INVISIBLE);
        }

        if (lojaInfo.getEdition() != null){
            holder.edicao.setText(lojaInfo.getEdition());
        }else {
            holder.edicao.setVisibility(View.INVISIBLE);
        }

        if (lojaInfo.getPrice() != null){
            holder.preco.setText(lojaInfo.getPrice());
        }else {
            holder.preco.setVisibility(View.INVISIBLE);
        }

        if (lojaInfo.getPromoPrice() != null){
            holder.precoPromo.setText(lojaInfo.getPromoPrice());
        }else {
            holder.precoPromo.setVisibility(View.INVISIBLE);
        }

        if (lojaInfo.getQtd() != null){
            holder.qtd.setText(lojaInfo.getQtd());
        }else {
            holder.qtd.setVisibility(View.INVISIBLE);
        }
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
            nome = itemView.findViewById(R.id.nome);
            edicao = itemView.findViewById(R.id.edicao);
            preco = itemView.findViewById(R.id.preco);
            precoPromo = itemView.findViewById(R.id.preco_promo);
            qtd = itemView.findViewById(R.id.qtd);
        }
    }
}
