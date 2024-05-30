package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;

public interface UserService {

  TokenResponseDTO login(LoginRequestDTO requestDTO);

  MessageResponseDTO register(RegisterRequestDTO requestDTO);

  MessageResponseDTO verifyAccount(String email, String verifyCode);

}
