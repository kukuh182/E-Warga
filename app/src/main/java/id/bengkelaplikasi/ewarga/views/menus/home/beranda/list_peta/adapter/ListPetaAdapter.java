package id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;


/**
 * Created by Kukuh182 on 17-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class ListPetaAdapter extends RecyclerView.Adapter<ListPetaAdapter.ViewHolder> {

    private List<ListPetaMA> rvData;
    private ListPetaMA model;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListPetaMA item);
    }

    public ListPetaAdapter(List<ListPetaMA> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new ListPetaMA();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_peta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);
        holder.act_title_listpeta.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_listpeta)CardView cv_listpeta;
        @BindView(R.id.act_title_listpeta)AppCompatTextView act_title_listpeta;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final ListPetaMA model, final OnItemClickListener listener) {
            cv_listpeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }
    }
}
