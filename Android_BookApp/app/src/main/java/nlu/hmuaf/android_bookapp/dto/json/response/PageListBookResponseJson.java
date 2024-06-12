package nlu.hmuaf.android_bookapp.dto.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageListBookResponseJson {
    @JsonProperty("content")
    private List<ListBookResponseDTO> content;
}
