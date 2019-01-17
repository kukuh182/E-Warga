package id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter;

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
 * Created by Kukuh182 on 17-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class DaftarWargaAdapter extends RecyclerView.Adapter<DaftarWargaAdapter.ViewHolder> {

    private List<DaftarWargaModel> rvData;
    private DaftarWargaModel model;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DaftarWargaModel item);
    }

    public DaftarWargaAdapter(List<DaftarWargaModel> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new DaftarWargaModel();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_daftar_warga, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);

        String alphabetic_name = Utilities.alphabeticName(model.getRktpNama());
        String name = model.getRktpNama();
        String description = "RT. "+model.getRktpRt()+" RW. "+model.getRktpRw();

        holder.tv_alphabetic_name.setText(alphabetic_name);
        holder.tv_name_warga.setText(name);
        holder.tv_description_warga.setText(description);
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_daftar_warga)LinearLayout ll_daftar_warga;
        @BindView(R.id.tv_alphabetic_name)TextView tv_alphabetic_name;
        @BindView(R.id.tv_name_warga)TextView tv_name_warga;
        @BindView(R.id.tv_description_warga)TextView tv_description_warga;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final DaftarWargaModel daftarWargaModel, final OnItemClickListener listener) {
            ll_daftar_warga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(daftarWargaModel);
                }
            });
        }
    }
}
