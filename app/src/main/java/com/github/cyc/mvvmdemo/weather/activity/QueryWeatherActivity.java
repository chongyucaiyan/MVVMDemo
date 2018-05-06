package com.github.cyc.mvvmdemo.weather.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.cyc.mvvmdemo.R;
import com.github.cyc.mvvmdemo.databinding.ActivityQueryWeatherBinding;
import com.github.cyc.mvvmdemo.weather.viewmodel.QueryWeatherViewModel;

public class QueryWeatherActivity extends AppCompatActivity {

    // ViewModel
    private QueryWeatherViewModel mViewModel;

    // DataBinding
    private ActivityQueryWeatherBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_query_weather);
        // 创建ViewModel
        mViewModel = new QueryWeatherViewModel();
        // 绑定View和ViewModel
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消请求
        mViewModel.cancelRequest();
    }
}
