package com.sptuts.newmaterial.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.models.CWCountries;

import java.util.List;

/**
 * Created by SHRI on 6/25/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CWHolder> {


    private List<CWCountries> cw;

    public RecyclerAdapter(List<CWCountries> modelData) {
        this.cw = modelData;
    }

    @Override
    public CWHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new CWHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CWHolder cwHolder, int pos) {
        CWCountries cwCountries = cw.get(pos);
        cwHolder.tv1.setText(cwCountries.getCountryName());
        cwHolder.tv2.setText(cwCountries.getCapitalCity());
    }

    @Override
    public int getItemCount() {
        return cw.size();
    }

    public static class CWHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2;

        public CWHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
        }
    }
}
