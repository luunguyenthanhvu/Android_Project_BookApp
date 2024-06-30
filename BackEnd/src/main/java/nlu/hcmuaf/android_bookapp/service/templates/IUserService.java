package nlu.hcmuaf.android_bookapp.service.templates;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.ForgotPasswordDTO;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;

public interface IUserService extends IDataInitializer {

  TokenResponseDTO login(LoginRequestDTO requestDTO);

  MessageResponseDTO register(RegisterRequestDTO requestDTO);

  MessageResponseDTO verifyAccount(VerifyRequestDTO requestDTO);

  MessageResponseDTO forgotPassword(ForgotPasswordDTO requestDTO);

  List<ListAddressResponseDTO> addNewAddress(long userId, AddressRequestDTO requestDTO);

  List<ListAddressResponseDTO> getListAddress(long userId);

  List<ListAddressResponseDTO> updateAddress(long userId, AddressRequestDTO requestDTO);

  List<ListAddressResponseDTO> deleteAddress(long userId, long addressId);
}
