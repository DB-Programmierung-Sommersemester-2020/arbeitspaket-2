package buchlager.repositories.services;

import java.util.Set;

import buchlager.models.Address;
import buchlager.models.Book;
import buchlager.models.Publisher;


public interface PublisherRepositoryService extends Repository<Publisher,Integer>{
    Set<Publisher> getPublischerByName(String name);
    Set<Publisher> getPublisherByBook(Book publisher);
    Set<Publisher> getPublisherByAddress(Address address);
}