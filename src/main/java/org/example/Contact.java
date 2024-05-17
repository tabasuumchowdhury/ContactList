package org.example;

import java.io.Serializable;

public class Contact implements Comparable<Object>, Serializable {
    //Instance Variables
    String fname;
    String lname;

    //Methods
    //Constructors

    /**
     * No-Args Constructor
     * Not used, just to keep computer happy
     */
    public Contact() {
        fname = null;
        lname = null;
    }

    /**
     * All-Args Constructor
     * @param fname is the first name of the contact
     * @param lname is the last name of the contact
     */
    public Contact(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    //Getters
    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    //Setters
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Checks for equality of an object with an instance of Contact class
     * @param object checks its equality to instance of object
     * @return true if it is the same object, else false.
     */
    public boolean equals(Object object) {
        if (object == null)
            return false;
        else if (!getClass().equals(object.getClass()))
            return false;
        else {
            Contact otherContact = (Contact) object;
            return (fname.equals(otherContact.fname) && (lname.equals(otherContact.lname)));
        }
    }

    /**
     * Formats all the instance variables of the class
     * Will get overridden in child class definitions
     * @return Formatted String
     */
    public String toString() {
        return "Name : " + lname + ", " + fname;
    }

    /**
     * Implementation of Comparable
     * Allows the sorting of Contacts
     * @param obj the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object obj) {
        int result;
        Contact other = (Contact) obj;
        if(lname.equals(other.lname))
            result=fname.compareTo(other.fname);
        else
            result=lname.compareTo(other.lname);
        return result;
    }
}
