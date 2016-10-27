package ru.learn.www.start;

import ru.learn.www.entity.Author;
import ru.learn.www.entity.Book;
import ru.learn.www.helpers.AuthorHelper;
import ru.learn.www.helpers.BookHelper;

public class Start {

    public static void main(String[] args) {
        for (Author author:new AuthorHelper().getAuthorList()) {
            System.out.println(author.getName());
        }

        for (Book book: new BookHelper().getBookList()) {
            System.out.println(book.getName());
        }
    }
}
