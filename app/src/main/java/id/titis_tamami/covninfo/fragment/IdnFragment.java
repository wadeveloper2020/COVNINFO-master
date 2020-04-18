
package id.titis_tamami.covninfo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import id.titis_tamami.covninfo.R;
import id.titis_tamami.covninfo.activity.IndonesiaProvinceActivity;
import id.titis_tamami.covninfo.model.IndonesiaSummaryModel;
import id.titis_tamami.covninfo.viewmodel.IndonesiaSummaryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdnFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private SwipeRefreshLayout swipe;
    private TextView tvPositive;
    private TextView tvRecovered;
    private TextView tvDeath;
    public IdnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout frameLayout = view.findViewById(R.id.cv_detail_idn);
        swipe = view.findViewById(R.id.swipeRefreshIdn);
        swipe.setOnRefreshListener(this);
        tvPositive = view.findViewById(R.id.tvValuePositifIdn);
        tvRecovered = view.findViewById(R.id.tvValueRecoveredIdn);
        tvDeath = view.findViewById(R.id.tvValueDeathsIdn);
        frameLayout.setOnClickListener(this);
        loadIdnData();
    }

    private void loadIdnData() {
        IndonesiaSummaryViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(IndonesiaSummaryViewModel.class);
        viewModel.setSummaryData();
        refreshData(true);
        viewModel.getSummaryData().observe(this, new Observer<ArrayList<IndonesiaSummaryModel>>() {
            @Override
            public void onChanged(ArrayList<IndonesiaSummaryModel> indonesiaSummaryModels) {
                if (indonesiaSummaryModels.size() > 0) {
                    refreshData(false);
                    tvPositive.setText(indonesiaSummaryModels.get(0).getPositifIdn());
                    tvRecovered.setText(indonesiaSummaryModels.get(0).getSembuhIdn());
                    tvDeath.setText(indonesiaSummaryModels.get(0).getMeninggalIdn());
                }
            }
        });
    }

    private void refreshData(boolean isRefresh) {
        if (isRefresh) {
            swipe.setRefreshing(true);
        } else {
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        loadIdnData();
    }

    @Override
    public void onClick(View view) {
        Intent provinceIntent = new Intent(getContext(), IndonesiaProvinceActivity.class);
        startActivity(provinceIntent);
    }
}
