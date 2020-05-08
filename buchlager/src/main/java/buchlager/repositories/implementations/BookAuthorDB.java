package buchlager.repositories.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import buchlager.helpers.BookAuthorDBHelper;
import buchlager.models.Author;
import buchlager.models.Book;
import buchlager.models.BookAuthor;
import buchlager.repositories.services.BookAuthorRepositoryService;

public class BookAuthorDB implements BookAuthorRepositoryService {

    private static BookAuthorDB instance = null;
    private String persistenceUnit = "buchlagerPU";

    private BookAuthorDB() {
    }

    public static BookAuthorDB getInstance() {
        if (BookAuthorDB.instance == null) {
            BookAuthorDB.instance = new BookAuthorDB();
        }
        return BookAuthorDB.instance;
    }

    @Override
    public List<BookAuthor> getAllReferences(){
        List<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();
        String psqlQuery = "SELECT ba FROM BookAuthor ba";

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();

        em.getTransaction().begin();

        TypedQuery<BookAuthor> selectQuery = em.createQuery(psqlQuery, BookAuthor.class);
        bookAuthors = selectQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        return (bookAuthors.isEmpty() ? null : bookAuthors);
    }
    @Override
    public BookAuthor getById(int id){
        if(getAllReferences().isEmpty()) return null;
        Optional<BookAuthor> optionalBookAuthor = getAllReferences().stream().filter(ba->ba.getId() == id).findFirst();
        return optionalBookAuthor.isPresent() ? optionalBookAuthor.get() : null;
    }

    @Override
    public List<Integer> findBooksOfAuthor(int authorId) {
        Set<Book> books = BookDB.getInstance().getBooksByAuthor(AuthorDB.getInstance().getById(authorId));

        if (books.isEmpty() || books == null)
            return null;
        else
            return books.stream().map(book -> book.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Integer> findAuthorsOfBook(int bookId) {
        Set<Author> authors = AuthorDB.getInstance().getAuthorsByBook(BookDB.getInstance().getById(bookId));

        if (authors.isEmpty() || authors == null)
            return null;
        else
            return authors.stream().map(author -> author.getId()).collect(Collectors.toList());
    }

    @Override
    public boolean createBookAutorReference(int bookId, int authorId) {
        boolean isCreated = false;

        if (BookAuthorDBHelper.authorIdExistsInAuthors(authorId) && BookAuthorDBHelper.bookIdExistsInBooks(bookId)) {
            EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
            em.getTransaction().begin();
            em.persist(new BookAuthor(bookId, authorId));
            em.getTransaction().commit();
            em.close();

            isCreated = true; //TODO: Check for created

            if (!BookAuthorDBHelper.bookAllreadyBelongsToAuthor(authorId, bookId)) {
               
                Author author = AuthorDB.getInstance().getById(authorId);
                Book book = BookDB.getInstance().getById(bookId);
                author.getBooks().add(book);

                AuthorDB.getInstance().update(author);

            } 
            else if (!BookAuthorDBHelper.authorAllreadyBelongsToBook(bookId, authorId)) {
               
                Author author = AuthorDB.getInstance().getById(authorId);
                Book book = BookDB.getInstance().getById(bookId);
                book.getAuthors().add(author);

                BookDB.getInstance().update(book);
            }
        }

        return isCreated;
    }

    @Override
    public boolean updateBookAutorReference(BookAuthor newBookAuthor) {
        boolean isUpdated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        
        Optional<BookAuthor> optionalBookAuthor = Optional.of(em.find(BookAuthor.class,newBookAuthor.getId()));
        em.getTransaction().begin();
        if(optionalBookAuthor.isPresent()){
            BookAuthor bookAuthorToUpdate = optionalBookAuthor.get();
            bookAuthorToUpdate.setAuthorId(newBookAuthor.getAuthorId());
            bookAuthorToUpdate.setBookId(newBookAuthor.getBookId());
            optionalBookAuthor = Optional.of(newBookAuthor);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.getTransaction().rollback();
            em.close();
        }
        
        if(optionalBookAuthor.isPresent()){
            if(optionalBookAuthor.get().equals(newBookAuthor)) isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteBookAutorReference(int bookId, int authorId) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM BookAuthor ba WHERE ba.bookId = :bookId AND ba.authorId = :authorId";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Author.class).setParameter("bookId", bookId).setParameter("authorId", authorId);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();

        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

    @Override
    public boolean deleteBuchReferenz(int bookId) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM BookAuthor ba WHERE ba.bookId = :bookId";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Author.class).setParameter("bookId", bookId);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        //TODO: Remove book from author (if neccessary)...
        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

    @Override
    public boolean deleteAutorReferenz(int authorId) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM BookAuthor ba WHERE ba.authorId = :authorId";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Author.class).setParameter("authorId", authorId);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        //TODO: Remove aurhor from book (if neccessary)...
        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

}