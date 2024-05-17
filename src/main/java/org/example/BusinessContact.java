package org.example;
import java.io.Serializable;

/**
 * Child class of Contact.
 * An instance of this class creates a Business Contact.
 * With Instance Variables: lname, fname, email.
 */
public class BusinessContact extends Contact implements Serializable {
    //Instance Variable
    String email;

    //Methods

    //Constructors
    public BusinessContact(String lname, String fname, String email) {
        super(lname, fname); //Call to base class All-Args Constructor for
        this.email = email;

    }

    /**
     * Checks for equality of an object with an instance of BusinessContact class
     * @param object checks its equality to instance of object
     * @return true if it is the same object, else false.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        else if (!getClass().equals(object.getClass()))
            return false;
        else {
            BusinessContact otherContact = (BusinessContact) object;
            return (fname.equals(otherContact.fname) &&
                    (lname.equals(otherContact.lname)) &&
                    (email.equals(otherContact.email)));
        }
    }

    /**
     * Formats all the instance variables of the class
     * @return Formatted String
     */
    @Override
    public String toString() {
        return "Business Contact" +
                "\nName : " + lname + ", " + fname +
                "\nEmail: " + email;
    }
}
