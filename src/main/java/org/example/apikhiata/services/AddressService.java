package org.example.apikhiata.services;

import jakarta.transaction.Transactional;
import org.example.apikhiata.models.Address;
import org.example.apikhiata.models.User;
import org.example.apikhiata.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    //find por id
    public Address findAddressById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado com o id " + id));
    }

    //salvar
    @Transactional
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    //deletar
    @Transactional
    public void deleteAddressById(int id) {
        Address address = findAddressById(id);
        addressRepository.delete(address);
    }

    //atualizar endereço por id
    @Transactional
    public Address updateAddressById(int id, Address address) {
        Address addressToUpdate = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado com o id " + id));
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setNumber(address.getNumber());
        addressToUpdate.setComplement(address.getComplement());
        addressToUpdate.setLabel(address.getLabel());
        return addressRepository.save(addressToUpdate);
    }

    private void updatePartialAddressFields(Address existingAddress, Map<String, Object> atualizacoes) {
        atualizacoes.forEach((campo, valor) -> {
            switch (campo) {
                case "recipient":
                    existingAddress.setRecipient((String) valor);
                    break;
                case "street":
                    existingAddress.setStreet((String) valor);
                    break;
                case "number":
                    existingAddress.setNumber((int) valor);
                    break;
                case "complement":
                    existingAddress.setComplement((String) valor);
                    break;
                case "label":
                    existingAddress.setLabel((String) valor);
                    break;
                default:
                    throw new IllegalArgumentException("Campo está errado: " + campo);
            }
        });

    }

    //atualizar parcialmente endereço por id
    @Transactional
    public Address updatePartialAddressWithId(int id, Map<String, Object> atualizacoes) {
        Address existingAddress = findAddressById(id);
        updatePartialAddressFields(existingAddress, atualizacoes);
        return addressRepository.save(existingAddress);
    }

}
