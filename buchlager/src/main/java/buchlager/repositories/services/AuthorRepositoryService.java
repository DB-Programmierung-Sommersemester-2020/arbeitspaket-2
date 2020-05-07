package buchlager.repositories.services;

import java.util.Set;

import buchlager.models.Author;
import buchlager.models.Book;


public interface AuthorRepositoryService extends Repository<Author, Integer> {
    Set<Author> getAuthorsByFirstName(String firstName);
    Set<Author> getAuthorsByLastName(String lastname);
    Set<Author> getAuthorByFirstAndLastName(String firstName, String lastname);
    Set<Author> getAuthorsByBook(Book book);
}