package id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.main.App;



/**
 * Created by Kukuh182 on 08-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class MenuBerandaAdapter extends RecyclerView.Adapter<MenuBerandaAdapter.ViewHolder> {

    List<MenuBerandaMA> rvData;
    MenuBerandaMA model;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MenuBerandaMA item);
    }

    public MenuBerandaAdapter(List<MenuBerandaMA> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new MenuBerandaMA();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_menu_beranda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);
        Glide.with(App.getContext()).load(model.getIcon()).into(holder.iv_icon);
        holder.tv_description.setText(model.getDescription().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_menu_beranda)LinearLayout ll_menu_beranda;
        @BindView(R.id.iv_icon)ImageView iv_icon;
        @BindView(R.id.tv_description)TextView tv_description;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final MenuBerandaMA menuBerandaModel, final OnItemClickListener listener) {
            ll_menu_beranda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(menuBerandaModel);
                }
            });
        }
    }
}
