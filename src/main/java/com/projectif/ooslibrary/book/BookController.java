package com.projectif.ooslibrary.book;

import com.projectif.ooslibrary.book.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    @ResponseBody
    public List<BookDTO> getBooks(@RequestParam(required = false) String category,
                                  @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        if (category != null) {
            return bookService.getBooksByCategory(category, sortOrder);
        } else {
            return bookService.getAllBooks(sortOrder);
        }
    }
    //ebook 페이지
//    @GetMapping("/books/{book_pk}")
//    @ResponseBody
//    public BookTextDTO getBookText(@PathVariable Long book_pk) {
//        return bookService.getBookPath(book_pk);
//    }

    //library 페이지 이동
    @GetMapping("/library")
    public String booksPage(Model model, @RequestParam(required = false) String category,
                            @RequestParam(required = false, defaultValue = "asc") String sortOrder){
        List<BookDTO> LibraryBooks;

        if (category != null) {
            LibraryBooks = bookService.getBooksByCategory(category, sortOrder);
        } else {
            LibraryBooks = bookService.getAllBooks(sortOrder);
        }
        model.addAttribute("bookList", LibraryBooks);

        return "library/bookplus";
    }

    @GetMapping("/eBook")
    public String eBook(Model model, @RequestParam Long book_plus, @RequestParam Long book_pk) {
        BookTextDTO bookTextDTO = bookService.findBookTextDTOByBookPk(book_pk);

        model.addAttribute("bookTextPath", bookTextDTO.getBookTextPath());
        model.addAttribute("coverImagePath", bookTextDTO.getCoverImagePath());
        model.addAttribute("bookPlus", book_plus);

        return "eBook/e_book";
    }

}
