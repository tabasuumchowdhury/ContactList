package org.example;

/**
 * Exception thrown if ever the contact list is empty
 */
public class InvalidInputException extends Exception {
    //Class extending Exception only needs Constructors
    public InvalidInputException() {
        super("Invalid input"); //call to the base class (Exception) constructor
    }
    public InvalidInputException(String message) {
        super(message);
    }
}
