package id.bengkelaplikasi.ewarga.views.menus.register.wilayah.adapter;

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

/**
 * Created by Kukuh182 on 22-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class WilayahAdapter extends RecyclerView.Adapter<WilayahAdapter.ViewHolder>{

    private List<WilayahModel> rvData;
    private WilayahModel model;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WilayahModel item);
    }

    public WilayahAdapter(List<WilayahModel> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new WilayahModel();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_wilayah, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);
        holder.tv_nama_wilayah.setText(model.getNama());
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_daftar_wilayah)LinearLayout ll_daftar_wilayah;
        @BindView(R.id.tv_nama_wilayah)TextView tv_nama_wilayah;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final WilayahModel wilayahModel, final WilayahAdapter.OnItemClickListener listener) {
            ll_daftar_wilayah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(wilayahModel);
                }
            });
        }
    }
}
