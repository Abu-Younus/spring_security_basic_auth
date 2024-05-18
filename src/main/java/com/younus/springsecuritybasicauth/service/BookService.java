package com.younus.springsecuritybasicauth.service;

import com.younus.springsecuritybasicauth.domain.BookDto;
import com.younus.springsecuritybasicauth.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface BookService {
    String createBook(BookDto bookDto, BindingResult bindingResult);

    Page<BookEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    String editBookPage(Model model, Long id);

    String updateBook(Model model, Long id, BookDto bookDto, BindingResult bindingResult);

    String deleteBook(Long id);
}
