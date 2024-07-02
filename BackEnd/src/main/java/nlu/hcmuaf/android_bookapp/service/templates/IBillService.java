package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.dto.request.BillRequestDTO;

public interface IBillService {

  void saveUserBills(Long userId, BillRequestDTO billRequestDTO);
}
