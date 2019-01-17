package id.bengkelaplikasi.ewarga.views.menus.home.beranda.event.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.main.App;



/**
 * Created by Kukuh182 on 17-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventMA> rvData;
    private EventMA model;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EventMA item);
    }

    public EventAdapter(List<EventMA> inputData, OnItemClickListener listener){
        this.rvData = inputData;
        this.listener = listener;
        this.model = new EventMA();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.bind(model, listener);
        Glide.with(App.getContext()).load(model.getEvent_photo()).into(holder.riv_event);
        holder.tv_title.setText(model.getEvent_nama());
        holder.tv_location.setText(model.getEvent_lokasi());
        holder.tv_desc.setText(model.getEvent_deskripsi());
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.riv_event)RoundedImageView riv_event;
        @BindView(R.id.tv_title)TextView tv_title;
        @BindView(R.id.tv_location)TextView tv_location;
        @BindView(R.id.tv_desc)TextView tv_desc;
        @BindView(R.id.iv_event_desc)ImageView iv_event_desc;
        @BindView(R.id.ll_event_desc)LinearLayout ll_event_desc;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final EventMA model, final OnItemClickListener listener) {
            iv_event_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showHideDesc();
                }
            });

            riv_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }

        private void showHideDesc(){
            if(ll_event_desc.getVisibility()==View.VISIBLE){
                ll_event_desc.setVisibility(View.GONE);
            }else {
                ll_event_desc.setVisibility(View.VISIBLE);
            }
        }
    }
}
