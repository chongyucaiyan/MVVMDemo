package com.github.cyc.mvvmdemo.weather.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.github.cyc.mvvmdemo.http.RetrofitManager;
import com.github.cyc.mvvmdemo.weather.model.WeatherData;
import com.github.cyc.mvvmdemo.weather.model.WeatherInfo;
import com.github.cyc.mvvmdemo.weather.request.QueryWeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cyc on 18/5/5.
 */

public class QueryWeatherViewModel {

    private static final String TAG = "QueryWeatherViewModel";

    public final ObservableBoolean loading = new ObservableBoolean(false);

    public final ObservableBoolean loadingSuccess = new ObservableBoolean(false);

    public final ObservableBoolean loadingFailure = new ObservableBoolean(false);

    public final ObservableField<String> city = new ObservableField<>();

    public final ObservableField<String> cityId = new ObservableField<>();

    public final ObservableField<String> temp1 = new ObservableField<>();

    public final ObservableField<String> temp2 = new ObservableField<>();

    public final ObservableField<String> weather = new ObservableField<>();

    public final ObservableField<String> time = new ObservableField<>();

    private Call<WeatherData> mCall;

    public QueryWeatherViewModel() {

    }

    public void queryWeather() {
        loading.set(true);
        loadingSuccess.set(false);
        loadingFailure.set(false);

        mCall = RetrofitManager.get()
                .create(QueryWeatherRequest.class)
                .queryWeather();
        mCall.enqueue(new Callback<WeatherData>() {

            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherInfo weatherInfo = response.body().getWeatherinfo();
                city.set(weatherInfo.getCity());
                cityId.set(weatherInfo.getCityid());
                temp1.set(weatherInfo.getTemp1());
                temp2.set(weatherInfo.getTemp2());
                weather.set(weatherInfo.getWeather());
                time.set(weatherInfo.getPtime());

                loading.set(false);
                loadingSuccess.set(true);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.i(TAG, "call is canceled.");
                } else {
                    loading.set(false);
                    loadingFailure.set(true);
                }
            }
        });
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
