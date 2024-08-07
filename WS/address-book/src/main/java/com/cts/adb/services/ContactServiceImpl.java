package com.cts.adb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.repos.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	@Override
	public List<Contact> getAll() {
		return contactRepo.findAll();
	}

	@Override
	public Contact getById(long contactId) {
		return contactRepo.findById(contactId).orElse(null);
	}

	@Override
	public Contact add(Contact contact) throws AddressBookException {
		if(contact.getContactId()!=null && contactRepo.existsById(contact.getContactId())){
			throw new AddressBookException("Contact Id already found.");
		}
		
		return contactRepo.save(contact);
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		if(contact.getContactId()==null || !contactRepo.existsById(contact.getContactId())){
			throw new AddressBookException("Contact not found to update.");
		}
		
		return contactRepo.save(contact);
	}

	@Override
	public void delete(long contactId) throws AddressBookException {
		if(!contactRepo.existsById(contactId)){
			throw new AddressBookException("Contact not found to delete.");
		}
		
		contactRepo.deleteById(contactId);
	}

	@Override
	public Contact getByMobile(String mobile) {
		return contactRepo.findByMobile(mobile).orElse(null);
	}

}
