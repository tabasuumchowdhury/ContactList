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
    public PersonalContact(String lname, String fname, String phoneNumber) throws InvalidInputException {
        super(lname, fname); //Call to base class All-Args Constructor for
        if (!isValid(phoneNumber))
            throw new InvalidInputException("The phone number you entered is invalid");
        if (phoneNumber.length() != 10)
            throw new InvalidInputException("The phone number is not 10 digits long");
        this.phoneNumber = formatNum(phoneNumber);

    }


    /**
     * Formats the phone number for better printing
     * @param phoneNumber user input
     * @return formatted phone number
     */
    public String formatNum(String phoneNumber) {
        return ( "(" + phoneNumber.substring(0,3) + ") " + phoneNumber.substring(3,6) +
                "-" + phoneNumber.substring(6));
    }
    /**
     * Checks validity of phone number
     * @param phoneNum the phone number of the client, must contain only numbers
     * @return true, if input valid, else false
     */
    @Override
    public boolean isValid(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            char ch = phoneNum.charAt(i);
            if ( !(ch >= '0' && ch <='9')) {
                return false;
            }
        }
        return true;
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
