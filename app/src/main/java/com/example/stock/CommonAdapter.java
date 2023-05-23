package com.example.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sx  2022/2/28 8:53
 * @email 1668626317@qq.com
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private int layoutId;
    private List<T> data;

    public CommonAdapter(int layoutId) {
        this.layoutId = layoutId;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T t = data.get(position);
        bind(holder, t, position);
    }

    public abstract void bind(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }


}
