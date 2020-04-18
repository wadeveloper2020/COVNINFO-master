
package id.titis_tamami.covninfo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import id.titis_tamami.covninfo.R;
import id.titis_tamami.covninfo.adapter.HistoryListAdapter;
import id.titis_tamami.covninfo.model.HistoryModel;
import id.titis_tamami.covninfo.viewmodel.HistoryViewModel;

public class WorldHistory extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private HistoryListAdapter adapter;
    private TextView tvEmptyList;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_history);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.history);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvEmptyList = findViewById(R.id.tvEmptyList);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshList);
        RecyclerView recyclerView = findViewById(R.id.listRecycler);

        adapter = new HistoryListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadListData();

        swipeRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadListData() {
        HistoryViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
        viewModel.setTodayData();
        refreshingData(true);
        viewModel.getTodayListData().observe(this, new Observer<ArrayList<HistoryModel>>() {
            @Override
            public void onChanged(ArrayList<HistoryModel> historyModels) {
                if (historyModels == null) {
                    tvEmptyList.setVisibility(View.VISIBLE);
                    refreshingData(false);
                } else {
                    adapter.setHistoryModels(historyModels);
                    refreshingData(false);
                }
            }
        });
    }
    private void refreshingData(boolean isRefresh) {
        if (isRefresh) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        loadListData();
    }
}
