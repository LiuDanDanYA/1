package com.example.android.connectedweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder> {
    private ArrayList<ForecastDataItem> forecastDataItems;
    private OnForecastItemClickListener onForecastItemClickListener;

    public interface OnForecastItemClickListener {
        void onForecastItemClick(ForecastDataItem forecastDataItem);
    }

    public ForecastAdapter(OnForecastItemClickListener onForecastItemClickListener) {
        this.forecastDataItems = new ArrayList<>();
        this.onForecastItemClickListener = onForecastItemClickListener;
    }

    @NonNull
    @Override
    public ForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastItemViewHolder holder, int position) {
        holder.bind(this.forecastDataItems.get(position));
    }

    public void updateForecastData(ArrayList<ForecastDataItem> forecastDataItems) {
        this.forecastDataItems = forecastDataItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.forecastDataItems.size();
    }

    class ForecastItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView monthTV;
        final private TextView dayTV;
        final private TextView highTempTV;
        final private TextView lowTempTV;
        final private TextView shortDescriptionTV;
        final private TextView popTV;

        public ForecastItemViewHolder(@NonNull View itemView) {
            super(itemView);
            monthTV = itemView.findViewById(R.id.tv_month);
            dayTV = itemView.findViewById(R.id.tv_day);
            highTempTV = itemView.findViewById(R.id.tv_high_temp);
            lowTempTV = itemView.findViewById(R.id.tv_low_temp);
            popTV = itemView.findViewById(R.id.tv_pop);
            shortDescriptionTV = itemView.findViewById(R.id.tv_short_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onForecastItemClickListener.onForecastItemClick(forecastDataItems.get(getAdapterPosition()));
                }
            });
        }

        public void bind(ForecastDataItem forecastDataItem) {
            monthTV.setText(forecastDataItem.getMonthString());
            dayTV.setText(forecastDataItem.getDayString());
            highTempTV.setText(forecastDataItem.getHighTemp() + "°F");
            lowTempTV.setText(forecastDataItem.getLowTemp() + "°F");
            popTV.setText((int)(forecastDataItem.getPop() * 100.0) + "% precip.");
            shortDescriptionTV.setText(forecastDataItem.getShortDescription());
        }

    }
}
