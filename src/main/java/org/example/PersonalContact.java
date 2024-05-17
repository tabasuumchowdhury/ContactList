package org.example;

import java.io.Serializable;

/**
 * Child class of Contact.
 * An instance of this class creates a Personal Contact.
 * With Instance Variables: lname, fname, phone Number.
 */
public class PersonalContact extends Contact implements Serializable {
    //Instance variables
    String phoneNumber; //lname and fname inherited from Contact

    //Methods
    //Constructors
    public PersonalContact(String lname, String fname, String phoneNumber) {
        super(lname, fname); //Call to base class All-Args Constructor for
        this.phoneNumber = phoneNumber;

    }

    /**
     * Checks for equality of an object with an instance of PersonalContact class
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
            PersonalContact otherContact = (PersonalContact) object;
            return (fname.equals(otherContact.fname) &&
                    (lname.equals(otherContact.lname)) &&
                    (phoneNumber.equals(otherContact.phoneNumber)));
        }
    }


    /**
     * Formats all the instance variables of the class
     * @return Formatted String
     */
    @Override
    public String toString() {
        return "Personal Contact" +
                "\nName : " + lname + ", " + fname +
                "\nPhone Number: " + phoneNumber;
    }

}
