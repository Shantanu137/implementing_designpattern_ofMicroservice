package com.cts.adb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.services.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactsRestController {

	@Autowired
	private ContactService contactService;

	@GetMapping
	public ResponseEntity<List<Contact>> getAllContactsAction() {
		return ResponseEntity.ok(contactService.getAll());
	}
	
	/*
	 * http://localhost:8888/contacts/1
	 * http://localhost:8888/contacts/2
	 */

	@GetMapping("/{id:[1-9][0-9}{3}")
	public ResponseEntity<Contact> getContactByIdAction(@PathVariable("id") long id) {
		Contact c = contactService.getById(id);
		return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
	}

	/*
	 * http://localhost:8888/contacts/9052224753
	 * http://localhost:8888/contacts/1231231232
	 */

	@GetMapping("/{mobile:[1-9][0-9}{9}}")
	public ResponseEntity<Contact> getContactByMobileAction(@PathVariable("mobile") Strring mobile) {
		Contact c = contactService.getByMobile(mobile);
		return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContactByIdAction(@PathVariable("id") long id) throws AddressBookException {
		contactService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity<Contact> addContactAction(@RequestBody @Valid Contact contact, BindingResult results)
			throws AddressBookException {

		checkForValidationErrors(results);

		return new ResponseEntity<>(contactService.add(contact), HttpStatus.CREATED);
	}

	private void checkForValidationErrors(BindingResult results) throws AddressBookException {
		if (results.hasErrors()) {

			String errMsg = results.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce("",
					(m1, m2) -> m1 + "," + m2);

			throw new AddressBookException(errMsg);
		}
	}

	@PutMapping
	public ResponseEntity<Contact> updateContactAction(@RequestBody @Valid Contact contact, BindingResult results)
			throws AddressBookException {

		checkForValidationErrors(results);

		return new ResponseEntity<>(contactService.update(contact), HttpStatus.ACCEPTED);
	}

}
