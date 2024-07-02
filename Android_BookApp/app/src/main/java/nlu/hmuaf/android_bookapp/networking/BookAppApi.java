package nlu.hmuaf.android_bookapp.networking;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.json.response.PageListBookResponseJson;
import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.BillRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.ForgotPasswordDTO;
import nlu.hmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.APIKeyResponse;
import nlu.hmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListOrderResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.PageBookResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookAppApi {
    @POST("api/v1/auth/login")
    Call<TokenResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("api/v1/auth/register")
    Call<MessageResponseDTO> register(@Body RegisterRequestDTO requestDTO);

    @POST("api/v1/auth/verify-account")
    Call<MessageResponseDTO> verifyAccount(@Body VerifyRequestDTO requestDTO);

    @POST("api/v1/auth/forgot-pass")
    Call<MessageResponseDTO> forgotPassword(@Body ForgotPasswordDTO requestDTO);

    @GET("api/books/new-book")
    Call<List<ListBookResponseDTO>> getNewListBooks(@Query("page") int page, @Query("size") int size);

    @GET("api/books/discount-book")
    Call<List<ListBookResponseDTO>> getDiscountListBooks(@Query("page") int page, @Query("size") int size);

    @GET("api/v1/auth/test")
    Call<MessageResponseDTO> test();

    @GET("api/v1/user/cart/{userId}")
    Call<List<CartItems>> getUserCart(@Path("userId") long userId);

    @POST("api/v1/user/cart/update/{userId}")
    Call<MessageResponseDTO> updateUserCart(@Path("userId") long userId, @Body List<CartItemRequestDTO> cartItems);

    @POST("api/v1/user/cart/delete/{userId}/{bookId}")
    Call<MessageResponseDTO> deleteCartItem(
            @Path("userId") long userId,
            @Path("bookId") long bookId
    );

    @GET("api/books/book-details/{bookId}")
    Call<BookDetailResponseDTO> getBookDetailByBookId(@Path("bookId") long bookId);

    @GET("api/books/get-books")
    Call<PageBookResponseDTO> findBooks(
            @Query("title") String title,
            @Query("bookType") String bookType,
            @Query("coverType") String coverType,
            @Query("publisher") String publisher,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("api/v1/key/get/google-map")
    Call<APIKeyResponse> getGoogleMapAPIKey();

    @POST("api/v1/user/address/add/{userId}")
    Call<List<ListAddressResponseDTO>> addNewAddress(@Path("userId") long userId, @Body AddressRequestDTO addressRequestDTO);

    @PUT("api/v1/user/address/update/{userId}")
    Call<List<ListAddressResponseDTO>> updateUserAddress(@Path("userId") long userId, @Body AddressRequestDTO addressRequestDTO);

    @GET("api/v1/user/address/{userId}")
    Call<List<ListAddressResponseDTO>> getUserAddress(@Path("userId") long userId);

    @POST("api/v1/user/bills/{userId}")
    Call<MessageResponseDTO> addUserBills(@Path("userId") long userId, @Body BillRequestDTO requestDTO);

    @DELETE("api/v1/user/address/delete/{userId}/{addressId}")
    Call<List<ListAddressResponseDTO>> deleteUserAddress(@Path("userId") long userId, @Path("addressId") long addressId);

    @GET("api/v1/user/orders/{userId}")
    Call<List<ListOrderResponseDTO>> getUserListOrder(@Path("userId") long userId);
}
