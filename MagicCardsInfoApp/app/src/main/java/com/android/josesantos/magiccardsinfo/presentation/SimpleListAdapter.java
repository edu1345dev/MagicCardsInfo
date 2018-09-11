package com.android.josesantos.magiccardsinfo.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.josesantos.magiccardsinfo.R;
import com.android.josesantos.magiccardsinfo.presentation.base.RecyclerViewListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by josesantos on 25/11/17.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.Text> {

    private Context context;
    private List<String> cardsList;
    private RecyclerViewListener.OnItemClickListener clickListener;

    public SimpleListAdapter(List<String> cards, Context context) {
        this.cardsList = cards;
        this.context = context;
    }

    public void addCard(String card) {
        this.cardsList.add(card);
    }

    @Override
    public Text onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Text(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Text holder, int position) {
       if (holder != null){
           holder.textView.setText(cardsList.get(position));
           holder.textView.setOnClickListener(view -> {
               cardsList.remove(holder.getAdapterPosition());
               notifyItemRemoved(holder.getAdapterPosition());
               if (clickListener != null){
                   clickListener.OnItemClick(holder.itemView, holder.getAdapterPosition());
               }
           });
       }
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    @NotNull
    public List<String> getList() {
        return cardsList;
    }

    @NotNull
    public void setCardsList(List<String> cards) {
        cardsList.clear();
        cardsList = cards;
        notifyDataSetChanged();
    }

    public void setOnClickListener(@NotNull RecyclerViewListener.OnItemClickListener onItemClickListener) {
        clickListener = onItemClickListener;
    }

    class Text extends RecyclerView.ViewHolder{

        TextView textView;

        Text(View itemView) {
            super(itemView);

            setViews(itemView);
        }

        private void setViews(View itemView) {
            textView = itemView.findViewById(R.id.text);
        }
    }
}
