package buchlager.repositories.services;

import java.util.List;

import buchlager.models.BookAuthor;

public interface BookAuthorRepositoryService {
    List<Integer> findBooksOfAuthor(int authorId);
    List<Integer> findAuthorsOfBook(int bookId);
    List<BookAuthor> getAllReferences();
    BookAuthor getById(int id);
    boolean createBookAutorReference(int buchId, int autorId);
    boolean updateBookAutorReference(BookAuthor bookAuthor);
    boolean deleteBookAutorReference(int buchId, int autorId);
    boolean deleteBuchReferenz(int buchId);
    boolean deleteAutorReferenz(int autorId);

}