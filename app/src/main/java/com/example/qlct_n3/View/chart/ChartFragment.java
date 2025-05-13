package com.example.qlct_n3.View.chart;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlct_n3.Model.SpendingInChart;
import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.FragmentChartBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartFragment extends Fragment {
    private static final String TAG = "ChartFragment";
    private ChartViewModel viewModel;
    private FragmentChartBinding binding;
    private ChartAdapter adapter;
    private Calendar calendar;
    private int rv;
    private int spd;
    private PieChart pieChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentChartBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        adapter = new ChartAdapter();
        calendar = Calendar.getInstance();
        rv = 0;
        spd = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadView();
        return binding.getRoot();
    }

    // Hàm load xử lý onClick
    private void loadView() {
        binding.recyclerview.setAdapter(adapter);
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Chi tiêu"));
        binding.tapLayout.addTab(binding.tapLayout.newTab().setText("Thu nhập"));

        // Khởi tạo PieChart
        pieChart = binding.pieChart;
        setupPieChart();

        createDataChart();
        binding.tvMonth.setText("Tháng " + (calendar.get(Calendar.MONTH) + 1));

        binding.tapLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        spendingAdapter();
                        break;
                    default:
                        revenueAdapter();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        spendingAdapter();
                        break;
                    default:
                        revenueAdapter();
                        break;
                }
            }
        });

        binding.imvBackNonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                String selectedDate = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                binding.tvMonth.setText("Tháng " + selectedDate);
                check();
            }
        });

        binding.imvIncreaseMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                String selectedDate = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                binding.tvMonth.setText("Tháng " + selectedDate);
                check();
            }
        });
    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
    }

    // Hàm load recycleview chi tiêu
    private void spendingAdapter() {
        List<PieEntry> entries = new ArrayList<>();
        List<SpendingInChart> spendingInChart = new ArrayList<>();
        viewModel.get_SpendingInChartChi(requireContext(), calendar.get(Calendar.MONTH) + 1);
        viewModel.SpendingInChartChi().observe(getViewLifecycleOwner(),
                new Observer<List<SpendingInChart>>() {
                    @Override
                    public void onChanged(List<SpendingInChart> spendingInCharts) {
                        spd = 0;
                        if (spendingInCharts.isEmpty()) {
                            pieChart.setVisibility(View.GONE);
                            binding.tvNothing.setVisibility(View.VISIBLE);
                        } else {
                            Map<String, List<SpendingInChart>> listMap = spendingInCharts.stream().
                                    collect(Collectors.groupingBy(SpendingInChart::getTenDanhMuc));

                            entries.clear();
                            spendingInChart.clear();

                            listMap.forEach((_tenDanhMuc, list) -> {
                                long sum = list.stream().mapToLong(SpendingInChart::getTien).sum();
                                SpendingInChart s = new SpendingInChart(sum, _tenDanhMuc, list.get(0).getIcon());
                                spendingInChart.add(s);
                                entries.add(new PieEntry(sum, _tenDanhMuc));
                                spd -= sum;
                            });

                            updatePieChart(entries);
                            pieChart.setVisibility(View.VISIBLE);
                            binding.tvNothing.setVisibility(View.GONE);
                        }
                        adapter.setAdapter(spendingInChart);
                        updateTotal();
                    }
                });
    }

    // hàm load recycleview thông kê khoản tiêu
    private void revenueAdapter() {
        List<PieEntry> entries = new ArrayList<>();
        List<SpendingInChart> spendingInChart = new ArrayList<>();
        viewModel.get_SpendingInChartThu(requireContext(), calendar.get(Calendar.MONTH) + 1);
        viewModel.SpendingInChartThu().observe(getViewLifecycleOwner(),
                new Observer<List<SpendingInChart>>() {
                    @Override
                    public void onChanged(List<SpendingInChart> spendingInCharts) {
                        rv = 0;
                        if (spendingInCharts.isEmpty()) {
                            pieChart.setVisibility(View.GONE);
                            binding.tvNothing.setVisibility(View.VISIBLE);
                        } else {
                            Map<String, List<SpendingInChart>> listMap = spendingInCharts.stream().
                                    collect(Collectors.groupingBy(SpendingInChart::getTenDanhMuc));

                            entries.clear();
                            spendingInChart.clear();

                            listMap.forEach((_tenDanhMuc, list) -> {
                                long sum = list.stream().mapToLong(SpendingInChart::getTien).sum();
                                SpendingInChart s = new SpendingInChart(sum, _tenDanhMuc, list.get(0).getIcon());
                                spendingInChart.add(s);
                                entries.add(new PieEntry(sum, _tenDanhMuc));
                                rv += sum;
                            });

                            updatePieChart(entries);
                            pieChart.setVisibility(View.VISIBLE);
                            binding.tvNothing.setVisibility(View.GONE);
                        }
                        adapter.setAdapter(spendingInChart);
                        updateTotal();
                    }
                });
    }

    private void updatePieChart(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    @Override
    public void onResume() {
        check();
        super.onResume();
    }

    private void createDataChart() {
        revenueAdapter();
        spendingAdapter();
    }

    private void check() {
        switch (binding.tapLayout.getSelectedTabPosition()) {
            case 0:
                revenueAdapter();
                spendingAdapter();
                break;
            default:
                spendingAdapter();
                revenueAdapter();
                break;
        }
    }

    private void updateTotal() {
        binding.tvSpending.setText(spd + " đ");
        binding.tvRevenue.setText(rv + " đ");
        binding.tvTotal.setText((spd + rv) + " đ");
    }
}