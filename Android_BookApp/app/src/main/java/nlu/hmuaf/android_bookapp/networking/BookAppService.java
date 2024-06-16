package nlu.hmuaf.android_bookapp.networking;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookAppService {
    private static final String BASE_URL = "http://192.168.0.103:8888/";

    public static BookAppApi getClient() {
        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return myRetrofit.create(BookAppApi.class);
    }

}
