package nlu.hcmuaf.android_bookapp.service.templates;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.request.BillRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListOrderResponseDTO;

public interface IBillService {

  void saveUserBills(Long userId, BillRequestDTO billRequestDTO);

  List<ListOrderResponseDTO> getUserOrder(long userId);
}
