package com.cts.adb.services;

import java.util.List;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;

public interface ContactService {
	List<Contact> getAll();
	Contact getById(long contactId);
	Contact getByMobile(String mobile);
	Contact add(Contact contact) throws AddressBookException;
	Contact update(Contact contact) throws AddressBookException;
	void delete(long contactId) throws AddressBookException;
}
