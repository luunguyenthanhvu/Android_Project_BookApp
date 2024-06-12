package nlu.hmuaf.android_bookapp.networking;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.json.response.PageListBookResponseJson;
import nlu.hmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookAppApi {
    @POST("api/v1/user/login")
    Call<TokenResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("api/v1/user/register")
    Call<MessageResponseDTO> register(@Body RegisterRequestDTO requestDTO);

    @GET("api/books/new-book")
    Call<List<ListBookResponseDTO>> getNewListBooks(@Query("page") int page, @Query("size") int size);

    @GET("api/books/discount-book")
    Call<List<ListBookResponseDTO>> getDiscountListBooks(@Query("page") int page, @Query("size") int size);
}
