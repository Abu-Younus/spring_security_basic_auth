package com.younus.springsecuritybasicauth.controller;

import com.younus.springsecuritybasicauth.domain.BookDto;
import com.younus.springsecuritybasicauth.entity.BookEntity;
import com.younus.springsecuritybasicauth.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String getAllBooks(Model model) {
        return findPaginated(1, "title", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDirection") String sortDirection,
            Model model
    ) {
        int pageSize = 5;
        Page<BookEntity> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<BookEntity> bookEntities = page.getContent();

        model.addAttribute("books", bookEntities);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return "books/index";
    }

    @GetMapping("/create")
    public String createBookPage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("book", bookDto);
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult) {
        return bookService.createBook(bookDto, bindingResult);
    }

    @GetMapping("/edit")
    public String editBookPage(Model model, @RequestParam Long id) {
        return bookService.editBookPage(model, id);
    }

    @PostMapping("/edit")
    public String updateBook(Model model,
                             @RequestParam Long id,
                             @Valid @ModelAttribute("book") BookDto bookDto,
                             BindingResult bindingResult) {
        return bookService.updateBook(model, id, bookDto, bindingResult);
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam Long id) {
        return bookService.deleteBook(id);
    }
}
