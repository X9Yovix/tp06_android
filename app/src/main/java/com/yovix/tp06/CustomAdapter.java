package com.yovix.tp06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    List<Pojo> pays;

    public CustomAdapter(List<Pojo> pays, Context context) {
        this.pays = pays;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lay_inf = LayoutInflater.from(parent.getContext());
        View view = lay_inf.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    /*
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //2nd try
        return new CustomAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        //1st try
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        //return new ViewHolder(view);

    }

     */
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        //holder.display(pays.get(position));
        holder.textView.setText(pays.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pays.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortByName(List<Pojo> list) {
        Collections.sort(list, Pojo.listAtoZ);
        notifyDataSetChanged();
    }

    public void reverseByName(List<Pojo> list) {
        sortByName(list);
        Collections.sort(list, Pojo.listZtoA);
        //Collections.reverse(list);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(String trim) {
        Pojo p = new Pojo(trim);
        pays.add(p);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        pays.remove(position);
        notifyDataSetChanged();
    }

    protected void openBrowser(String url) {
        Uri url_intent = Uri.parse("https://en.wikipedia.org/wiki/" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, url_intent);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose option");
            menu.setHeaderIcon(R.drawable.settings);
            menu.add(getAdapterPosition(), R.id.delete, 0, "Delete");
            menu.add(getAdapterPosition(), R.id.wiki, 1, "Wiki");

            //2ND METHOD
            //MenuItem Delete = menu.add(getAdapterPosition(), 1, 1, "Delete");
            //MenuItem Wiki = menu.add(getAdapterPosition(), 2, 2, "Wiki");
            //Delete.setOnMenuItemClickListener(onEditMenu);
            //Wiki.setOnMenuItemClickListener(onEditMenu);
        }

/*
//2ND METHOD
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        removeItem(item.getGroupId());
                        System.out.println("deleted"+item.getGroupId());
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                }
                return true;
            }
        };

 */
    }
}
