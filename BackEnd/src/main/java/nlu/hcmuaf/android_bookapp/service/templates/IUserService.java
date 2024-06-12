package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;

public interface IUserService extends IDataInitializer{

  TokenResponseDTO login(LoginRequestDTO requestDTO);

  MessageResponseDTO register(RegisterRequestDTO requestDTO);

  MessageResponseDTO verifyAccount(VerifyRequestDTO requestDTO);

}
