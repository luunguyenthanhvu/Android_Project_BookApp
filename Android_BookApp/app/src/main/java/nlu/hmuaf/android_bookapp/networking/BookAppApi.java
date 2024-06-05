package nlu.hmuaf.android_bookapp.networking;

import nlu.hmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookAppApi {
    @POST("api/v1/user/login")
    Call<TokenResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);
}
