package buchlager.repositories.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import buchlager.repositories.services.AuthorRepositoryService;

public class AuthorDB implements AuthorRepositoryService {

    private static AuthorDB instance = null;
    private final String persistenceUnit ="buchlagerPU";

    private AuthorDB() {
    }

    public static AuthorDB getInstance() {
        if (AuthorDB.instance == null) {
            AuthorDB.instance = new AuthorDB();
        }
        return AuthorDB.instance;
    }

    @Override
    public Author getById(Integer id) {
        Optional<Author> optionalAuthor = null;
        if(!getAll().isEmpty()){
            optionalAuthor = getAll().stream().filter(authtor->authtor.getId() == id).findFirst();
        }

        return (optionalAuthor.isPresent()) ? optionalAuthor.get() : null;//new Author();

       /* EntityManager em = Persistence.createEntityManagerFactory("buchlagerPU").createEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class, id);
        return author;*/

    }

    @Override
    public boolean create(Author author) {
        boolean isCreated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.getTransaction().commit();
        em.close();

        Optional<Author> optionalAuthor = Optional.of(getById(author.getId()));
        if(optionalAuthor.isPresent()) isCreated = true;
        
        return isCreated;
    }

    @Override
    public boolean update(Author newAuthor) {
        boolean isUpdated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        
        Optional<Author> optionalAuthor = Optional.of(em.find(Author.class,newAuthor.getId()));
        em.getTransaction().begin();
        if(optionalAuthor.isPresent()){
            Author authorToUpdate = optionalAuthor.get();
            authorToUpdate.setFirstName(newAuthor.getFirstName());
            authorToUpdate.setLastName(newAuthor.getLastName());
            //authorToUpdate.setBooks(newAuthor.getBooks());
            optionalAuthor = Optional.of(authorToUpdate);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.getTransaction().rollback();
            em.close();
        }
        
        if(optionalAuthor.isPresent()){
            if(optionalAuthor.get().equals(newAuthor)) isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Author author) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        Author current = new Author();
        em.getTransaction().begin();
        if(!em.contains(author)){
            current = em.merge(author);
            em.remove(current);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.remove(author);
            em.getTransaction().commit();
            em.close();
        }
        

        Optional<Author> optionalAuthor = Optional.of(getById(author.getId()));
        if(!optionalAuthor.isPresent()) isDeleted = true;
        
        return isDeleted;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM Author a WHERE a.id = :id";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Author.class).setParameter("id", id);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();

        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

    @Override
    public Collection<Author> getAll() {
        List<Author> authors = new ArrayList<Author>();
        String psqlQuery = "SELECT a FROM Author a";

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Author> selectQuery = em.createQuery(psqlQuery, Author.class);
        authors = selectQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        return (authors.isEmpty() ? Collections.emptyList() : authors);
    }

    @Override
    public Set<Author> getAuthorsByFirstName(String firstName) {
        
        if(getAll().isEmpty()) return null;

        Optional<Set<Author>> optionalAuthors = Optional.of(
            getAll().stream().filter(author->author.getFirstName().equals(firstName))
            .collect(Collectors.toSet())
        );

        return (optionalAuthors.isPresent()) ? optionalAuthors.get() : null;
    }

    @Override
    public Set<Author> getAuthorsByLastName(String lastname) {

        if(getAll().isEmpty()) return null;

        Optional<Set<Author>> optionalAuthors = Optional.of(
            getAll().stream().filter(author->author.getFirstName().equals(lastname))
            .collect(Collectors.toSet())
        );

        return (optionalAuthors.isPresent()) ? optionalAuthors.get() : null;
    }

    @Override
    public Set<Author> getAuthorByFirstAndLastName(String firstName, String lastname) {

        if(getAll().isEmpty()) return null;

        Optional<Set<Author>> optionalAuthors = Optional.of(
            getAll().stream().filter(author->author.getFirstName().equals(firstName) && author.getLastName().equals(lastname))
            .collect(Collectors.toSet())
        );

        return (optionalAuthors.isPresent())? optionalAuthors.get() : null;
    }

    @Override
    public Set<Author> getAuthorsByBook(Book book) {

        if(getAll().isEmpty()) return null;

        Set<Author> authors = getAll().stream().filter(author->author.getBooks().contains(book))
                .collect(Collectors.toSet());
      
        return (authors.isEmpty()) ? null : authors;
    }

}