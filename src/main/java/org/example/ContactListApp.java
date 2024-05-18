package org.example;
// -------------------------------------------------------
// Final Project
// Written by: Tabasuum Chowdhury - 6220550
// For “Data Structures and OOP” Section 00001 – Winter 2024
// --------------------------------------------------------

import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactListApp {
    //Static variables
    static ContactList<Contact> contactList = new ContactList<Contact>();
    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        double choice = 0.0; //Initiates choice variable to keep computer happy

        //Initial Menu
        System.out.println("****************************************************");
        System.out.println("\t Welcome to your Contact Management System");
        System.out.println("****************************************************");

        //Repeats option Menu until user chooses to exit
        while(true) {
            try { //Tries in case wrong formatting
                System.out.println("\nChoose an option:");
                System.out.println("====================================================");
                System.out.println("1. Add Contact");
                System.out.println("\t1.1. Add Personal Contact");
                System.out.println("\t1.2. Add Business Contact");
                System.out.println("2. Delete Contact");
                System.out.println("3. Display Contacts");
                System.out.println("4. Search Contact");
                System.out.println("5. Save Contacts");
                System.out.println("6. Load Contacts");
                System.out.println("7. Size of Contact List");
                System.out.println("8. Sort Contact List");
                System.out.println("9. Clear Current Contact List");
                System.out.println("10. Exit");
                System.out.println("====================================================");
                System.out.println("Enter your choice: 1.1, 1.2, 2, 3, 4, 5, 6, 7, 8, 9, 10");

                choice = keyboard.nextDouble();
                String temp = keyboard.nextLine(); //Deletes extra line after number

                if (choice == 1.1) {
                    //Add Personal contact
                    addPersonalContact();
                    if (!exitOption())
                        break;
                } else if (choice == 1.2) {
                    //Add Business contact
                    addBusinessContact();
                    if (!exitOption())
                        break;
                } else if (choice == 2) {
                    //Delete contact
                    deleteContact();
                    if (!exitOption())
                        break;
                } else if (choice == 3) {
                    //Display contacts
                    displayContacts();
                    if (!exitOption())
                        break;
                } else if (choice == 4) {
                    //Search contact
                    searchContact();
                    if (!exitOption())
                        break;
                } else if (choice == 5) {
                    //Save contacts
                    saveContacts();
                } else if (choice == 6) {
                    //Load contacts
                    loadContacts();
                    if (!exitOption())
                        break;
                } else if (choice == 7) {
                    //Says size of list
                    contactListSize();
                    if (!exitOption())
                        break;
                } else if (choice == 8) {
                    //Sorts the Contact List
                    sortList();
                    if (!exitOption())
                        break;
                } else if (choice == 9) {
                    //clears current contactList
                    restartList();
                } else if (choice == 10) {
                    //Exit
                    System.out.println("Thanks for using Contact List see you next time");
                    keyboard.close();
                    break;
                } else {
                    //In case user does not enter one of the allowed choices
                    System.out.println("Not a valid choice. Try again");
                }
            } catch (InputMismatchException e) { //Catches input that does not match of type double
                System.out.println("You did not enter a number. Try again"); //Notifies user of problem
                String temp = keyboard.nextLine(); //deletes old line
            }
        }
    }

    //Methods
    //They take methods from ContactList and add more user interactions
    /**
     * Takes in variables to use as argument for ContactList's addContact method
     * Creates a specific contact type as PersonalContact
     * Confirms with user that contact was successfully added
     */
    public static void addPersonalContact() {
        boolean error = false;
        do {
            try {
                //Asking user for variables
                System.out.println("Enter the contact's last name you would like to add: ");
                String lname = keyboard.nextLine();
                System.out.println("Enter the contact's first name you would like to add: ");
                String fname = keyboard.nextLine();
                System.out.println("Enter the personal contact's phone number you would like to add: ");
                String phoneNumber = keyboard.nextLine();

                //Creating new object
                PersonalContact contact = new PersonalContact(fname, lname, phoneNumber);
                error = true;
                contactList.addContact(contact);
                System.out.println("Contact successfully added");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!error);
    }

    /**
     * Takes in variables to use as argument for ContactList's addContact method
     * Creates a specific contact type as BusinessContact
     * Confirms with user that contact was successfully added
     */
    public static void addBusinessContact() {
        boolean error = false;
        do {
            try {
                //Asking user for variables
                System.out.println("Enter the contact's last name you would like to add: ");
                String lname = keyboard.nextLine();
                System.out.println("Enter the contact's first name you would like to add: ");
                String fname = keyboard.nextLine();
                System.out.println("Enter the business contact's email you would like to add: ");
                String email = keyboard.nextLine();

                //Creating new objects
                BusinessContact contact = new BusinessContact(fname, lname, email);
                contactList.addContact(contact);
                System.out.println("Contact successfully added");
                error = true;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!error);
    }

    /**
     * Takes in variables to use as argument for ContactList's deleteContact method
     * Confirms with user that contact was successfully deleted
     */
    public static void deleteContact() {
        System.out.println("Enter the contact's last name you would like to delete: ");
        String lname = keyboard.nextLine();
        System.out.println("Enter the contact's first name you would like to delete: ");
        String fname = keyboard.nextLine();

        //Attempts to delete the contact, catches if List is empty or ContactNotFoundException
        try {
            contactList.deleteContact(lname,fname);
            System.out.println("Contact successfully removed");
        } catch (ContactNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Calls ContactList's displayContacts method
     * Confirms with user that contact was successfully displayed
     */
    public static void displayContacts() {
        contactList.displayContact();
        System.out.println("Successfully displayed all contacts");
    }

    /**
     * Takes in variables to use as argument for ContactList's searchContacts method
     * Confirms with user that contact was successfully found
     */
    public static void searchContact() {
        System.out.println("Enter the contact's last name you would like to search: ");
        String lname = keyboard.nextLine();
        System.out.println("Enter the contact's first name you would like to search: ");
        String fname = keyboard.nextLine();
        contactList.searchContact(lname,fname);
    }

    /**
     * Takes in variables to use as argument for ContactList's saveContact method
     * Such as name of the file
     * Confirms with user that contacts are successfully saved
     */
    public static void saveContacts() {
        System.out.println("In what File would you like to save the contacts in?");
        String fileName = keyboard.nextLine();
        contactList.saveContact(fileName);
        System.out.println("Successfully saved all contacts");
        System.out.println("Would you like to clear all the Contact in the List" +
                " as they have all been saved?");
        String answer = keyboard.nextLine();
        answer = answer.toLowerCase();
        if(answer.startsWith("y")) {
            contactList.clear();
            System.out.println("Current contact List has been cleared and all Contacts have been saved");
        } else {
            System.out.println("Current contactList has not been cleared and all Contacts have been saved");
        }
    }

    /**
     * Takes in variables to use as argument for ContactList's loadContacts method
     * Such as name of the file
     * Confirms with user that contacts are successfully loaded
     */
    public static void loadContacts() {
        System.out.println("In what File would you like to load contacts from?");
        String fileName = keyboard.nextLine();
        contactList.loadContact(fileName);
        System.out.println("Successfully loaded all contacts");
    }

    /**
     * Prints the size of the contactList
     */
    public static void contactListSize() {
        int sizeOfList = contactList.size();
        System.out.println("The size of the Contact List is of " + sizeOfList);
    }

    /**
     * Sorts the ContactList alphabetically
     */
    public static void sortList() {
        System.out.println("Here is the list before sorting\n");
        displayContacts();
        contactList.selectionSort();
        System.out.println("\n\nThe list has been successfully sorted\n");
        displayContacts();
    }

    /**
     * Checks if user wants to continue using the program
     * @return true to continue, false if they want ot end
     */
    public static boolean exitOption() {
        System.out.println("Would you like to continue?");
        String answer = keyboard.nextLine();
        answer = answer.toLowerCase();
        if (answer.startsWith("y")) {
            return true;
        }
        System.out.println("Thanks for using Contact List see you next time");
        return false;
    }

    /**
     * Clears the current contact list
     */
    public static void restartList() {
        contactList.clear();
        System.out.println("Successfully cleared the contactList");
        displayContacts();
    }
}
