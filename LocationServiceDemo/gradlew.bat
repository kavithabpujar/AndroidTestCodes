package com.opteamix.perfometer.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opteamix.perfometer.R;
import com.opteamix.perfometer.models.pojo.PerfometerReporteePojo;

import java.util.ArrayList;
import java.util.List;


public class ReporteeListAdapter extends RecyclerView.Adapter<ReporteeListAdapter.ReporteeListHolder> {

    private List<String> reportees;
    private List<PerfometerReporteePojo> reporteeList = new ArrayList<>();
    private ReporteeListAdapter.OnItemClickListener onItemClickListener;
    private Context context;

    public ReporteeListAdapter(List<String> reportees, List<PerfometerReporteePojo> reporteeList, Context context, ReporteeListAdapter.OnItemClickListener onItemClickListener) {
        this.reportees = reportees;
        this.reporteeList = reporteeList;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @Override
    public ReporteeListAdapter.ReporteeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportee_list_item, parent, false);
        //ReporteeListHolder reporteeListHolder = new ReporteeListHolder(v, onItemClickListener);
        ReporteeListHolder reporteeListHolder = new ReporteeListHolder(v);
        return reporteeListHolder;
    }

    @Override
    public void onBindViewHolder(ReporteeListAdapter.ReporteeListHolder holder, int position) {
        holder.tv_reportee.setText(reportees.get(position));
        holder.tv_role.setText(reporteeList.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        int size = reportees == null ? 0 : reportees.size();
        return size;
    }

    public interface OnItemClickListener {
        void onItemClick(PerfometerReporteePojo perfometerReporteePojo, ReporteeListAdapter.ReporteeListHolder viewHolder);
    }

    public class ReporteeListHolder extends RecyclerView.ViewHolder {

        Text