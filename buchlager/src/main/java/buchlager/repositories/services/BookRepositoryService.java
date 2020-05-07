package buchlager.repositories.services;

import java.util.Set;

import buchlager.models.Book;

public interface BookRepositoryService extends Repository<Book, Integer> {
    Set<Book> getBooksByTitle(String title);
    Set<Book> getBooksByAuthor(String author);
    Set<Book> getBooksByPublischer(String publischer);

}