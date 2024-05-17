package org.example;

import java.io.IOException;

/**
 * Interface which lists all the necessary methods for a ContactList
 * Should be able to add, delete, display and search for Contacts
 * Then, save and load them from a file.
 */
public interface ContactOperations {
    public void addContact(Contact contact);
    public void deleteContact(String lname, String fname) throws ContactNotFoundException;
    public void displayContact();
    public void searchContact(String lname, String fname);
    public void saveContact(String fileName) throws IOException, ClassNotFoundException;
    public void loadContact(String fileName) throws IOException, ClassNotFoundException;
}
