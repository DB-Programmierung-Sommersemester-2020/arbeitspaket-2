package buchlager.repositories.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javax.persistence.Query;

import buchlager.models.Address;
import buchlager.models.Publisher;
import buchlager.repositories.services.AddressRepositoryService;

public class AddressDB implements AddressRepositoryService {
    private static AddressDB instance = null;
    private String persistenceUnit = "buchlagerPU";

    private AddressDB() {
    }

    public static AddressDB getInstance() {
        if (AddressDB.instance == null) {
            AddressDB.instance = new AddressDB();
        }
        return AddressDB.instance;
    }

    @Override
    public Address getById(Integer id) {
        if(getAll() == null) return null;
        Optional<Address> address = getAll().stream().filter(adr -> adr.getId() == id).findFirst();

        return address.isPresent() ? address.get() : null;
    }

    @Override
    public boolean create(Address address) {
        boolean isCreated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        em.close();

        Optional<Address> optionalAuthor = Optional.of(getById(address.getId()));
        if(optionalAuthor.isPresent()) isCreated = true;
        
        return isCreated;
    }

    @Override
    public boolean update(Address newAddress) {
        boolean isUpdated = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        
        Optional<Address> optionalAddress = Optional.of(em.find(Address.class,newAddress.getId()));
        em.getTransaction().begin();
        if(optionalAddress.isPresent()){
            Address addressToUpdate = optionalAddress.get();
            addressToUpdate.setPlace(newAddress.getPlace());

            optionalAddress = Optional.of(addressToUpdate);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.getTransaction().rollback();
            em.close();
        }
        
        if(optionalAddress.isPresent()){
            if(optionalAddress.get().equals(newAddress)) isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Address address) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        Address current = new Address();
        em.getTransaction().begin();
        if(!em.contains(address)){
            current = em.merge(address);
            em.remove(current);
            em.getTransaction().commit();
            em.close();
        }
        else{
            em.remove(address);
            em.getTransaction().commit();
            em.close();
        }
        

        Optional<Address> optionalAuthor = Optional.of(getById(address.getId()));
        if(!optionalAuthor.isPresent()) isDeleted = true;
        
        return isDeleted;
    }

    public boolean delete(int id) {
        boolean isDeleted = false;

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
        String jpqlDeleteString = "DELETE FROM Address a WHERE a.id = :id";
        em.getTransaction().begin();
        Query query = em.createQuery(jpqlDeleteString,Address.class).setParameter("id", id);
        
        int countOfDeleted = query.executeUpdate();
        em.getTransaction().commit();
        em.close();

        if(countOfDeleted > 0) isDeleted = true;

        return isDeleted;
    }


    @Override
    public Collection<Address> getAll() {
        List<Address> addresses = new ArrayList<Address>();
        String psqlQuery = "SELECT a FROM Address a";

        EntityManager em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Address> selectQuery = em.createQuery(psqlQuery, Address.class);
        addresses = selectQuery.getResultList();

        em.getTransaction().commit();
        em.close();
        return (addresses.isEmpty() ? null : addresses);
    }

    @Override
    public Set<Address> getAdressesByPlace(String place) {
        if(getAll().isEmpty()) return null;
        Set<Address> addresses = getAll().stream().filter(adr->adr.getPlace().equals(place)).collect(Collectors.toSet());
        return addresses.isEmpty() ? null : addresses;
    }

    @Override
    public Address getAdressByPubliser(Publisher publisher) {
        return publisher.getAddress();
    }
}