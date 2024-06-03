package nlu.hcmuaf.android_bookapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.json.BooksJson;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IBookService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements IBookService {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public void loadDefaultData() {
    try {
      List<BooksJson> defaultData = loadDataBookDefault();
      Type listType = new TypeToken<List<Books>>() {
      }.getType();

      List<Books> data = modelMapper.map(defaultData, listType);
      data.forEach(e -> System.out.println(e));
    } catch (Exception ex) {
      System.out.println(ex);
      ex.printStackTrace();
    }
  }

  private List<BooksJson> loadDataBookDefault() {
    try {
      ClassPathResource resource = new ClassPathResource("data.json");
      InputStream inputStream = resource.getInputStream();
      return objectMapper.readValue(inputStream, new TypeReference<List<BooksJson>>() {
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
