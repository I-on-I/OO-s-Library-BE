package com.projectif.ooslibrary.book_calendar;

import com.projectif.ooslibrary.book.domain.Book;
import com.projectif.ooslibrary.book_calendar.domain.BookCalendar;
import com.projectif.ooslibrary.my_library.domain.BookPlus;
import com.projectif.ooslibrary.my_library.domain.MyLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book_calendarRepository extends JpaRepository<BookCalendar, Long> {
    @Query("SELECT new com.projectif.ooslibrary.book_calendar.Book_calendarDTO(b.bookTitle, b.coverImagePath, bc.bookCalendarReadPercent, bc.bookCalendarScheduleDate) " +
            "FROM BookCalendar bc " +
            "JOIN bc.bookPlus bp " +
            "JOIN bp.book b " +
            "WHERE bc.myLibrary.myLibraryPk = :myLibraryPk " +
            "GROUP BY bc.bookCalendarPk, b.bookTitle, b.coverImagePath, bc.bookCalendarReadPercent, bc.bookCalendarScheduleDate")
    List<Book_calendarDTO> findBookCalendarInfoByMyLibrary(Long myLibraryPk);

    // BookPlus와 myLibrary를 사용하여 BookCalendar 삭제
    void deleteByBookPlusAndMyLibrary(BookPlus bookPlus, MyLibrary myLibrary);
}
