package com.liang.tind.sherlockweather.activity;

import android.os.Bundle;
import android.util.Log;

import com.liang.tind.sherlockweather.R;
import com.liang.tind.sherlockweather.http.ApiServices;
import com.liang.tind.sherlockweather.http.RetrofitClient;
import com.liang.tind.sherlockweather.module.entity.KnowWeather;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.liang.tind.sherlockweather.http.RetrofitClient.baseUrl;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ApiServices mApiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.all).setOnClickListener(view -> handle());

        initWeatherService(ApiServices.KNOW_WEATHER_API_HOST);
//       test();
    }

    private void handle(){

    }
    private void initWeatherService(String baseUrl) {

        RetrofitClient.getInstance(this).createBaseApi().getWeather("101010100", new Observer<KnowWeather>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe: "+d.toString() );
            }

            @Override
            public void onNext(@NonNull KnowWeather knowWeather) {
                Log.e(TAG, "onNext: "+knowWeather.toString() );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: "+e.toString() );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });

    }

    public void test() {
//        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
//        if (true) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addNetworkInterceptor(httpLoggingInterceptor);
//        }
//        OkHttpClient client = builder.build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(client)
//                .build();
        OkHttpClient  okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .cookieJar(new NovateCookieManger(context))
//                .cache(cache)
//                .addInterceptor(new BaseInterceptor(headers))
//                .addInterceptor(new CaheInterceptor(context))
//                .addNetworkInterceptor(new CaheInterceptor(context))
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        Retrofit    retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        mApiServices = retrofit.create(ApiServices.class);
        mApiServices.getKnowWeather("101010100")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull KnowWeather knowWeather) {
                        Log.e(TAG, "onNext: "+knowWeather.toString() );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.toString() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
