
    package com.example.test;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.List;

    public class YearlySummaryAdapter extends RecyclerView.Adapter<YearlySummaryAdapter.ViewHolder> {

        private List<YearlySummary> yearlySummaries;

        public YearlySummaryAdapter(List<YearlySummary> yearlySummaries) {
            this.yearlySummaries = yearlySummaries;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yearly_summary, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            YearlySummary summary = yearlySummaries.get(position);
            holder.tvYear.setText(summary.getYear());
            holder.tvYearIncome.setText(String.format("%.2f", summary.getIncome()));
            holder.tvYearExpense.setText(String.format("%.2f", summary.getExpense()));
            holder.tvYearEndBalance.setText(String.format("%.2f", summary.getEndBalance()));
        }

        @Override
        public int getItemCount() {
            return yearlySummaries.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvYear, tvYearIncome, tvYearExpense, tvYearEndBalance;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvYear = itemView.findViewById(R.id.tvYear);
                tvYearIncome = itemView.findViewById(R.id.tvYearIncome);
                tvYearExpense = itemView.findViewById(R.id.tvYearExpense);
                tvYearEndBalance = itemView.findViewById(R.id.tvYearEndBalance);
            }
        }
    }
