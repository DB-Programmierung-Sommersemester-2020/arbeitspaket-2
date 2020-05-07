package buchlager.repositories.services;

import java.util.Set;

import buchlager.models.Address;
import buchlager.models.Publisher;


public interface PublisherRepositoryService {
    Set<Publisher> getPublischerByName(String name);
    Set<Publisher> getPublisherByBook(Publisher publisher);
    Set<Publisher> getPublisherByAddress(Address address);
}