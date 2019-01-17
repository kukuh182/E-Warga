package id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;

/**
 * Created by Kukuh182 on 20-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class InformasiAdapter extends RecyclerView.Adapter<InformasiAdapter.ViewHolder>  {

    private List<InformasiModel> rvData;
    private InformasiModel model;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(InformasiModel item);
    }

    public InformasiAdapter(List<InformasiModel> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_informasi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);
        String tanggalInput = model.getTinformasiTglinput().replace(" ","_");
        String tanggal = tanggalInput.split("_")[0];
        String waktu = tanggalInput.split("_")[1].substring(0,5);
        holder.act_content.setText(model.getTinformasiContent());
        holder.act_title.setText(model.getTinformasiJudul());
        holder.act_date.setText(tanggal);
        holder.act_time.setText(waktu);

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.act_title)AppCompatTextView act_title;
        @BindView(R.id.act_content)ExpandableTextView act_content;
        @BindView(R.id.act_date)AppCompatTextView act_date;
        @BindView(R.id.act_time)AppCompatTextView act_time;
        @BindView(R.id.act_readmore)AppCompatTextView act_readmore;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            act_content.setInterpolator(new OvershootInterpolator());
            act_content.setExpandInterpolator(new OvershootInterpolator());
            act_content.setCollapseInterpolator(new OvershootInterpolator());
        }

        public void bind(final InformasiModel InformasiModel, final OnItemClickListener listener) {
            act_readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (act_content.isExpanded()) {
                        act_content.collapse();
                        act_readmore.setText(R.string.more);
                    }else {
                        act_content.expand();
                        act_readmore.setText(R.string.less);
                    }
                }
            });
        }
    }
}
