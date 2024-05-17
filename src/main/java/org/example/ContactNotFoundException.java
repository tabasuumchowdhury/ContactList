package org.example;

/**
 * Exception thrown if ever a contact is not found
 * Because it does not exist
 */
public class ContactNotFoundException extends Exception{
    //Class extending Exception only needs Constructors
    public ContactNotFoundException() {
        super(); //call to the base class (Exception) constructor
    }
    public ContactNotFoundException(String message) {
        super(message);
    }
}
