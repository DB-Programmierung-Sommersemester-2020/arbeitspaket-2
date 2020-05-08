package buchlager.repositories.services;

import java.util.List;

public interface BookAuthorRepositoryService {
    List<Integer> findBooksOfAuthor(int authorId);
    List<Integer> findAuthorsOfBook();

    boolean createBookAutorReference(int buchId, int autorId);
    boolean updateBookAutorReference(int buchId, int autorId);
    boolean deleteBookAutorReference(int buchId, int autorId);
    boolean deleteBuchReferenz(int buchId);
    boolean deleteAutorReferenz(int autorId);

}