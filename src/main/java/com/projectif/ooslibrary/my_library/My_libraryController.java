package com.projectif.ooslibrary.my_library;

import com.projectif.ooslibrary.book.domain.Book;
import com.projectif.ooslibrary.member.repository.MemberRepository;
import com.projectif.ooslibrary.my_library.domain.MyLibrary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class My_libraryController {
    
    private final My_libraryService myLibraryService;

    // 내 서재 페이지 이동
    @GetMapping("/myLibrary/{myLibraryPk}")
    public String myLibrary(@PathVariable("myLibraryPk") Long myLibraryPk, Model model) {
        // 내 서재에 등록된 책 리스트 가져오기
        List<MyLibraryBookDTO> myLibraryBooks = myLibraryService.getMyLibraryBooks(myLibraryPk);
        model.addAttribute("bookList", myLibraryBooks);
        System.out.println("myLibraryBooks = " + myLibraryBooks);
        return "members/myLibrary";
    }


    @PutMapping("/bookPlus")
    @ResponseBody
    public ResponseEntity<String> saveBookToLibrary(@RequestParam Long bookPk,
                                                    @RequestParam Long myLibraryPk) {
        myLibraryService.saveBookPlus(bookPk, myLibraryPk);
        return ResponseEntity.ok("내서재에 책이 추가 되었습니다.");
    }

    @DeleteMapping("/bookPlus") //내서재 책 삭제
    @ResponseBody
    public ResponseEntity<String> DeleteBookToLibrary(@RequestParam Long bookPk,
                                                          @RequestParam Long myLibraryPk) {
        myLibraryService.DeleteBookToLibrary(bookPk, myLibraryPk);
        return ResponseEntity.ok("내서재에 책이 삭제 되었습니다.");
    }


}
