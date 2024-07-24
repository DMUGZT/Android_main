    package com.example.test;


    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;
    import java.util.List;

    public class MonthlySummaryAdapter extends RecyclerView.Adapter<MonthlySummaryAdapter.ViewHolder> {

        private List<MonthlySummary> monthlySummaries;

        public MonthlySummaryAdapter(List<MonthlySummary> monthlySummaries) {
            this.monthlySummaries = monthlySummaries;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_monthly_summary, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MonthlySummary summary = monthlySummaries.get(position);
            holder.tvMonth.setText(summary.getMonth());
            holder.tvMonthIncome.setText(String.format("%.2f", summary.getIncome()));
            holder.tvMonthExpense.setText(String.format("%.2f", summary.getExpense()));
            holder.tvMonthEndBalance.setText(String.format("%.2f", summary.getEndBalance()));
        }

        @Override
        public int getItemCount() {
            return monthlySummaries.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvMonth, tvMonthIncome, tvMonthExpense, tvMonthEndBalance;

            public ViewHolder(View itemView) {
                super(itemView);
                tvMonth = itemView.findViewById(R.id.tvMonth);
                tvMonthIncome = itemView.findViewById(R.id.tvMonthIncome);
                tvMonthExpense = itemView.findViewById(R.id.tvMonthExpense);
                tvMonthEndBalance = itemView.findViewById(R.id.tvMonthEndBalance);
            }
        }
    }
