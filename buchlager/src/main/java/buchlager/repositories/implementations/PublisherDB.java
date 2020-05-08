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

import buchlager.models.Address;
import buchlager.models.Book;
import buchlager.models.Publisher;
import buchlager.repositories.services.PublisherRepositoryService;

public class PublisherDB implements PublisherRepositoryService {

    
        private static PublisherDB instance = null;
        private String persistenceUnit = "buchlagerPU";
        private boolean isPublischersEmpty = true;
    
        private PublisherDB() {
        }
    
        public static PublisherDB getInstance() {
            if (PublisherDB.instance == null) {
                PublisherDB.instance = new PublisherDB();
            }
            return PublisherDB.instance;
        }
    
       
    @Override
    public Publisher getById(Integer id) {
        if(isPublischersEmpty) return null;
        Optional<Publisher> publishers = getAll().stream().filter(p->p.getId() == id).findFirst();
        
        return publishers.isPresent() ? publishers.get() : null;
    }

    @Override
    public boolean create(Publisher publisher) {
        boolean isCreated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        em.getTransaction().begin();
        em.persist(publisher);
        em.getTransaction().commit();
        em.close();

        Optional<Publisher> optionalPublisher = Optional.of(getById(publisher.getId()));
        if(optionalPublisher.isPresent()) isCreated = true;
        
        return isCreated;
    }

    @Override
    public boolean update(Publisher newPublisher) {
        boolean isUpdated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        
        Optional<Publisher> optionalPublisher = Optional.of(em.find(Publisher.class,newPublisher.getId()));
        em.getTransaction().begin();
        if(optionalPublisher.isPresent()){
            Publisher publisherToUpdate = optionalPublisher.get();
            publisherToUpdate.setName(newPublisher.getName());
            publisherToUpdate.setAddress(newPublisher.getAddress());

            optionalPublisher = Optional.of(publisherToUpdate);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.getTransaction().rollback();
            em.close();
        }
        
        if(optionalPublisher.isPresent()){
            if(optionalPublisher.get().equals(newPublisher)) isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Publisher publisher) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        Publisher current = new Publisher();
        em.getTransaction().begin();
        if(!em.contains(publisher)){
            current = em.merge(publisher);
            em.remove(current);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.remove(publisher);
            em.getTransaction().commit();
            em.close();
        } 

        Optional<Publisher> optionalPublisher = Optional.of(getById(publisher.getId()));
        if(!optionalPublisher.isPresent()) isDeleted = true;
        
        return isDeleted;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM Publisher p WHERE p.id = :id";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Address.class).setParameter("id", id);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();

        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }

    @Override
    public Collection<Publisher> getAll() {
        List<Publisher> publishers = new ArrayList<Publisher>();
        String psqlQuery = "SELECT p FROM Publischer a";

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Publisher>selectQuery = em.createQuery(psqlQuery, Publisher.class);
        publishers=selectQuery.getResultList();

        if(publishers.isEmpty()){
            isPublischersEmpty = true;
        }
        em.getTransaction().commit();
        em.close();

        return (isPublischersEmpty ? null : publishers);
    }

    @Override
    public Set<Publisher> getPublischerByName(String name) {
        if(isPublischersEmpty) return null;

        Set<Publisher> publishers = getAll().stream().filter(p->p.getName().equals(name)).collect(Collectors.toSet());
        return publishers.isEmpty() ? null : publishers;
    }

    @Override
    public Set<Publisher> getPublisherByBook(Book book){
        if(isPublischersEmpty) return null;
        Set<Publisher> publishers = getAll().stream().filter(p->p.getBooks().contains(book)).collect(Collectors.toSet());
        
        return publishers.isEmpty() ? null : publishers;
    }

    @Override
    public Set<Publisher> getPublisherByAddress(Address address) {
        if(isPublischersEmpty) return null;
        Set<Publisher> publishers = getAll().stream().filter(p->p.getAddress().equals(address)).collect(Collectors.toSet());
        
        return publishers.isEmpty() ? null : publishers;
    }

  
    
}