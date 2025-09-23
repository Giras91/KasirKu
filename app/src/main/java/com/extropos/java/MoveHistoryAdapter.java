package com.extropos.java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.extropos.java.entity.MoveHistory;

import java.util.List;

public class MoveHistoryAdapter extends RecyclerView.Adapter<MoveHistoryAdapter.VH> {
    public interface Callback { void onItemClicked(MoveHistory mh); void onItemSwiped(MoveHistory mh); }
    private List<MoveHistory> items;
    private Callback cb;

    public MoveHistoryAdapter(List<MoveHistory> items, Callback cb) { this.items = items; this.cb = cb; }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final MoveHistory mh = items.get(position);
        holder.primary.setText(mh.getMovedOn() + " - Order " + mh.getOrderId());
        holder.secondary.setText(mh.getFromTable() + " -> " + mh.getToTable());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { if (cb != null) cb.onItemClicked(mh); }
        });
    }

    @Override public int getItemCount() { return items.size(); }

    public MoveHistory getItem(int pos) { return items.get(pos); }

    public void remove(int pos) { items.remove(pos); notifyItemRemoved(pos); }

    static class VH extends RecyclerView.ViewHolder {
        TextView primary, secondary;
        VH(View v) { super(v); primary = v.findViewById(R.id.textPrimary); secondary = v.findViewById(R.id.textSecondary); }
    }
}
