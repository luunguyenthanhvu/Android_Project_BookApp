package nlu.hmuaf.android_bookapp.call_back;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;

public interface BookDataCallback {
    void onDataLoaded(List<ListBookResponseDTO> bookList);
    void onDataLoadFailed(Throwable throwable);
}
