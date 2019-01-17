package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;


/**
 * Created by Kukuh182 on 15-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class WhitelistAdapter extends RecyclerView.Adapter<WhitelistAdapter.ViewHolder>  {

    private List<WhitelistMA> rvData;
    private WhitelistMA model;
    private OnItemClickListener listener;

    public WhitelistAdapter(List<WhitelistMA> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new WhitelistMA();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_whitelist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);

        String alphabetic_name = Utilities.alphabeticName(model.getAlfabet());
        String name = model.getName();
        String phone = model.getPhone();

        holder.tv_alfabet.setText(alphabetic_name);
        holder.tv_title.setText(name);
        holder.tv_description.setText(phone);
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_item_note)LinearLayout ll_item_note;
        @BindView(R.id.tv_alfabet)TextView tv_alfabet;
        @BindView(R.id.tv_title)TextView tv_title;
        @BindView(R.id.tv_description)TextView tv_description;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final WhitelistMA model, final OnItemClickListener listener) {
            ll_item_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WhitelistMA item);
    }
}
