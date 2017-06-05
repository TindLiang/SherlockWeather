package com.liang.tind.sherlockweather.http;

import com.liang.tind.sherlockweather.module.entity.KnowWeather;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Sherlock on 2017/6/1.
 */

public interface ApiServices {

    public static final String Base_URL = "http://ip.taobao.com/";

    public static final String MI_WEATHER_API_HOST = "http://weatherapi.market.xiaomi.com/wtr-v2/";
    public static final String KNOW_WEATHER_API_HOST = "http://knowweather.duapp.com/";
    public static final String ENVIRONMENT_CLOUD_WEATHER_API_HOST = "http://service.envicloud.cn:8082/";

    public static final int WEATHER_DATA_SOURCE_TYPE_KNOW = 1;
    public static final int WEATHER_DATA_SOURCE_TYPE_MI = 2;
    public static final int WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD = 3;

    /**
     *普通写法
     */
    @GET("service/getIpInfo.php/")
    Observable<ResponseBody> getData(@Query("ip") String ip);


    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);


    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path("url") String url,
            @FieldMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(
            @Path("url") String url,
            @Body RequestBody jsonStr);

    /**
     * http://knowweather.duapp.com/v1.0/weather/101010100
     *
     * @param cityId 城市ID
     * @return 天气数据
     */
    @GET("v1.0/weather/{cityId}")
    Observable<KnowWeather> getKnowWeather(@Path("cityId") String cityId);

//    @Multipart
//    @POST("{url}")
//    Observable<ResponseBody> upLoadFile(
//            @Path("url") String url,
//            @Part("image\\"; filename=\\"image.jpg") RequestBody avatar);
//
//    @POST("{url}")
//    Call<ResponseBody> uploadFiles(
//            @Url() String url,
//            @Part("filename") String description,
//            @PartMap()  Map<String, RequestBody> maps);
}
