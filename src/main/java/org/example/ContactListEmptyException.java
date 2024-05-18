package org.example;

/**
 * Exception thrown if ever the contact list is empty
 */
public class ContactListEmptyException extends Exception{

    //Class extending Exception only needs Constructors
    public ContactListEmptyException() {
        super("The current Contact List is empty"); //call to the base class (Exception) constructor
    }

    public ContactListEmptyException(String message) {
        super(message);
    }
}
