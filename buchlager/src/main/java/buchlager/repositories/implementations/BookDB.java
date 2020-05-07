package buchlager.repositories.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import buchlager.models.Author;
import buchlager.models.Book;
import buchlager.models.Publisher;
import buchlager.repositories.services.BookRepositoryService;

public class BookDB implements BookRepositoryService {
    private static BookDB instance = null;
    private String persistenceUnit = "buchlagerPU";

    private BookDB() {
    }

    public static BookDB getInstance() {
        if (BookDB.instance == null) {
            BookDB.instance = new BookDB();
        }
        return BookDB.instance;
    }

    @Override
    public Book getById(Integer id) {
        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        em.getTransaction().begin();
        Book author = em.find(Book.class, id);
        em.getTransaction().commit();
        em.close();

        return author;
    }

    @Override
    public boolean create(Book book) {
       boolean isCreated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();

        Optional<Book>optionalAuthor = Optional.of(getById(book.getId()));
        if(optionalAuthor.isPresent()) isCreated = true;
        
        return isCreated;
    }

    @Override
    public boolean update(Book newBook) {
        boolean isUpdated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        Optional<Book> optionalBook = Optional.of(em.find(Book.class,newBook.getId()));
        
        em.getTransaction().begin();
        if(optionalBook.isPresent()){
            Book bookToUpdate = optionalBook.get();
            bookToUpdate.setPublisher(newBook.getPublisher());
            bookToUpdate.setAmount(newBook.getAmount());
            bookToUpdate.setTitle(newBook.getTitle());
            bookToUpdate.setAuthors(newBook.getAuthors());

            optionalBook = Optional.of(bookToUpdate);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.getTransaction().rollback();
            em.close();
        }
        
        if(optionalBook.isPresent()){
            if(optionalBook.get().equals(newBook)) isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Book book) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        Book current = new Book();
        em.getTransaction().begin();
        if(!em.contains(book)){
            current = em.merge(book);
            em.remove(current);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.remove(book);
            em.getTransaction().commit();
            em.close();
        }
        

        Optional<Book> optionalAuthor = Optional.of(getById(book.getId()));
        if(!optionalAuthor.isPresent()) isDeleted = true;
        
        return isDeleted;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM Book b WHERE b.id = :id";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Author.class).setParameter("id", id);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();

        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

    @Override
    public Collection<Book> getAll() {
        List<Book> books = new ArrayList<Book>();
        String psqlQuery = "SELECT b FROM Book b";

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Book> selectQuery = em.createQuery(psqlQuery, Book.class);
        books = selectQuery.getResultList();

        em.getTransaction().commit();
        em.close();
        return (books.isEmpty() ? null : books);
    }

    @Override
    public Set<Book> getBooksByTitle(String title) {
        
        if(this.getAll() == null) return null;

        Set<Book> booksSet = getAll().stream()
            .filter(book->book.getTitle().equals(title)).collect(Collectors.toSet());
        
        return (booksSet.isEmpty() ? null : booksSet);
    }

    @Override
    public Set<Book> getBooksByAuthor(Author author) {
        
        if(this.getAll() == null) return null;

        Set<Book> booksSet = getAll().stream()
            .filter(book->book.getAuthors().contains(author)).collect(Collectors.toSet());
        
        return (booksSet.isEmpty() ? null : booksSet);

    }


    @Override
    public Set<Book> getBooksByPublischer(String publischer) {
        if(getAll().isEmpty()) return null;
        Set<Book> booksSet = getAll().stream()
            .filter(book->book.getPublisher().getName().equals(publischer)).collect(Collectors.toSet());
        return (booksSet.isEmpty() ? null : booksSet);

    }

    public Set<Book> getBooksByPublischer(Publisher publischer) {
        if(getAll().isEmpty()) return null;
        Set<Book> booksSet = getAll().stream().filter(book->book.getPublisher().equals(publischer)).collect(Collectors.toSet());           
       
        return (booksSet.isEmpty() ? null : booksSet);

    }
}