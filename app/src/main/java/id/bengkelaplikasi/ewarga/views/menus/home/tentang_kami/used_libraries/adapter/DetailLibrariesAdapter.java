package id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;

/**
 * Created by Kukuh182 on 21-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class DetailLibrariesAdapter extends RecyclerView.Adapter<DetailLibrariesAdapter.ViewHolder> {

    private List<DetailLibrariesModel> rvData;
    private DetailLibrariesModel model;

    public DetailLibrariesAdapter(List<DetailLibrariesModel> inputData){
        this.rvData = inputData;
        this.model = new DetailLibrariesModel();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_libraries, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model = rvData.get(position);
        holder.act_libraries_title.setText(model.getTitle());
        holder.act_libraries_content.setText(model.getContent());

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.act_libraries_title)AppCompatTextView act_libraries_title;
        @BindView(R.id.act_libraries_content)AppCompatTextView act_libraries_content;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
