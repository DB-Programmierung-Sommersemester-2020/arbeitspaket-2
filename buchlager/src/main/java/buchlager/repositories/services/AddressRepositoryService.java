package buchlager.repositories.services;

import java.util.Set;

import buchlager.models.Address;
import buchlager.models.Publisher;

public interface AddressRepositoryService extends Repository<Address, Integer>{
    Set<Address> getAdressesByPlace(String place);
    Address getAdressByPubliser(Publisher publisher);
}