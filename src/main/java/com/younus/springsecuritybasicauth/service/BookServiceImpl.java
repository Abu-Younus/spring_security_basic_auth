package com.younus.springsecuritybasicauth.service;

import com.younus.springsecuritybasicauth.domain.BookDto;
import com.younus.springsecuritybasicauth.entity.BookEntity;
import com.younus.springsecuritybasicauth.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String createBook(BookDto bookDto, BindingResult bindingResult) {
        if(bookDto.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("bookDto", "image", "The image is required"));
        }
        if(bindingResult.hasErrors()) {
            return "books/create";
        }

        MultipartFile image = bookDto.getImage();
        Date createdAt = new Date();
        String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();
        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setCategory(bookDto.getCategory());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setPublisher(bookDto.getPublisher());
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setEdition(bookDto.getEdition());
        bookEntity.setNumberOfPages(bookDto.getNumberOfPages());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setDescription(bookDto.getDescription());
        bookEntity.setCountry(bookDto.getCountry());
        bookEntity.setLanguage(bookDto.getLanguage());
        bookEntity.setImage(imageName);
        bookEntity.setCreatedAt(new Date());

        bookRepository.save(bookEntity);

        return "redirect:/?create_success";
    }

    @Override
    public Page<BookEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return bookRepository.findAll(pageable);
    }

    @Override
    public String editBookPage(Model model, Long id) {
        try {
            BookEntity bookEntity = bookRepository.findById(id).get();
            model.addAttribute("bookEntity", bookEntity);

            BookDto bookDto = new BookDto();
            bookDto.setTitle(bookEntity.getTitle());
            bookDto.setCategory(bookEntity.getCategory());
            bookDto.setAuthor(bookEntity.getAuthor());
            bookDto.setPublisher(bookEntity.getPublisher());
            bookDto.setIsbn(bookEntity.getIsbn());
            bookDto.setEdition(bookEntity.getEdition());
            bookDto.setNumberOfPages(bookEntity.getNumberOfPages());
            bookDto.setPrice(bookEntity.getPrice());
            bookDto.setCountry(bookEntity.getCountry());
            bookDto.setLanguage(bookEntity.getLanguage());

            model.addAttribute("book", bookDto);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/";
        }
        return "books/edit";
    }

    @Override
    public String updateBook(Model model, Long id, BookDto bookDto, BindingResult bindingResult) {
        try {
            BookEntity bookEntity = bookRepository.findById(id).get();
            model.addAttribute("bookEntity", bookEntity);

            if(bindingResult.hasErrors()) {
                return "books/edit";
            }

            if(!bookDto.getImage().isEmpty()) {
                //delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + bookEntity.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //save new image
                MultipartFile image = bookDto.getImage();
                Date createdAt = new Date();
                String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                }

                bookEntity.setImage(imageName);
            }

            bookEntity.setTitle(bookDto.getTitle());
            bookEntity.setCategory(bookDto.getCategory());
            bookEntity.setAuthor(bookDto.getAuthor());
            bookEntity.setPublisher(bookDto.getPublisher());
            bookEntity.setIsbn(bookDto.getIsbn());
            bookEntity.setEdition(bookDto.getEdition());
            bookEntity.setNumberOfPages(bookDto.getNumberOfPages());
            bookEntity.setPrice(bookDto.getPrice());
            bookEntity.setDescription(bookDto.getDescription());
            bookEntity.setCountry(bookDto.getCountry());
            bookEntity.setLanguage(bookDto.getLanguage());
            bookEntity.setUpdatedAt(new Date());

            bookRepository.save(bookEntity);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/?update_success";
    }

    @Override
    public String deleteBook(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).get();

        //delete old image
        String uploadDir = "public/images/";
        Path imagePath = Paths.get(uploadDir + bookEntity.getImage());

        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        bookRepository.delete(bookEntity);

        return "redirect:/?delete_success";
    }
}
