package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {

  void loadDefaultData();

  Page<ListBookResponseDTO> getNewBookList(Pageable pageable);
}
