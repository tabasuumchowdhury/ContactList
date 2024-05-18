package org.example;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * Di
 * @param <T> any class deriving the Contact class
 */
@SuppressWarnings("ALL")
public class ContactList<T extends Contact> implements ContactOperations, Serializable{
    /**
     * Inner Node Class which allows to have a LinkedList of all the Contacts.
     */
    private class Node implements Serializable{ //should I put T extends Contact here and in ContactList
        //Instance Variables
        private T data;
        private Node link;

        //Methods
        //Constructors

        /**
         * Single-Argument Constructor
         * @param data is a Contact, Business or Personal.
         *             link is unknown so set to null
         */
        public Node(T data) {
            this.data = data;
            this.link = null;
        }

        /**
         * All-Args Constructor
         * @param data is a Contact, Business or Personal
         * @param link leads to another Node to create a LinkedList
         */
        public Node(T data, Node link) {
            this.data = data;
            this.link = link;
        }

        //Setter
        public void setData(T data) {
            this.data = data;
        }

        public void setLink(Node link) {
            this.link = link;
        }

        //Getter
        public T getData() {
            return data;
        }

        public Node getLink() {
            return link;
        }
    } //End of Inner Class Node

    /**
     * Allows an easier movement and manipulation of the LinkedList
     */
    public class ContactListIterator {
        //Instance variables
        private Node position;
        private Node previous;

        //Methods

        /**
         * Constructor
         * When an Iterator is first initialized it starts the beginning of the list
         * The position is at head and there is nothing preceding it
         */
        public ContactListIterator() {
            position = head;
            previous = null;
        }

        /**
         * Restarts the Iterator position to the start
         */
        public void restart() {
            position = head;
            previous = null;
        }

        /**
         * Moves the iterator up a Node
         * @return data at current position
         */
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T toReturn = position.data;
            previous = position;
            position = position.link;
            return toReturn;
        }

        /**
         * Checks if the List contains anymore Nodes
         * @return true if there are any Nodes left, else false
         */
        public boolean hasNext() {
            return (position != null);
        }

        /**
         * Allows to add a Data to wherever the iterators position is currently at
         * @param newData the data (contact) that's being added
         */
        public void addHere(T newData) {
            Node temp = new Node(newData, position);
            previous.link = temp;
        }


        /**
         * Changes the data (contact) at the current position to a different one.
         * @param newData the data (contact) that's being added
         */
        public void changeHere(T newData) {
            previous.link = new Node(newData, position.link);

        }

        /**
         * Deletes Node at current position by removing any link to it.
         */
        public void delete() {
            previous.link = position.link;
            position = position.link;
        }
    } //End of ContactListIterator inner Class

    // Instance variables
    private Node head;

    //Methods

    /**
     * Constructor: Creates a ContactList
     */
    public ContactList() {
        head = null;
    }

    /**
     * Calls the Iterator No-args constructor
     * @return an iterator to access its methods.
     */
    public ContactListIterator iterator() {
        return new ContactListIterator();
    }

    /**
     * Determine the size of the Contact list by starting at the head Node until there are no more nodes
     * @return the size of the ContactList
     */
    public int size() {
        int count = 0; //initializes count to keep computer happy
        Node position = head; //starts position at beginning of the list (head)
        while (position != null) { //iterates through all the nodes
            count++; //adds to count
            position = position.link;
        }
        return count; //returns count of how many nodes there are (size)
    }

    /**
     * Checks if the Contact List object is empty
     * @return true if it is empty, else false
     */
    public boolean isEmpty() {
        return (head == null); //if there is no node at head, it's empty
    }

    /**
     * Clears the ContactList object
     * Does so by removing any variable pointing to the first node
     */
    public void clear() {
        head = null; //head no longer points anywhere
    }

    /**
     * Adds contact node to the end
     * @param contact the contact that's being added to the last new node
     */
    @Override
    public void addContact(Contact contact) {
        //turns contact into Generic type (any class of Contact or extending it)
        Node newNode = new Node((T) contact);
        //if head points to nothing, start list there
        if (isEmpty())
            head = newNode;
        //else iterate through all the nodes and create new node where empty
        else {
            Node current = head;
            while (current.link != null)
                current = current.link;
            current.link = newNode;
        }
    }

    /**
     * Adds contact node to the beginning
     * @param contact the contact that's being added to the first new node
     */
    public void addToStart(T contact) {
        head = new Node(contact, head); //makes new Contact as first and links it to head
    }

    /**
     * Removes the first contact node
     */
    public void deleteHeadNode() {
        try {
            if (isEmpty())
                throw new ContactListEmptyException("There are no Contacts to delete");
            else
                head = head.link; //Sets head as next node to delete current head
        } catch (ContactListEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a specific contact
     * @param lname of the Contact
     * @param fname of the Contact, used to find the Contact
     * @throws ContactNotFoundException thrown if the ContactList does not have the target Contact
     */
    public void deleteContact(String lname, String fname) throws ContactNotFoundException {
        try {
            if (isEmpty()) { //Cannot delete Contacts if there are no contacts
                throw new ContactListEmptyException("There are no contacts to delete");
            }

            //Deletes Contact
            ContactListIterator it = iterator(); //creates an iterator for easier manipulation

            //Start pointers at the beginning
            Node previous = null;
            Node position = head;

            //Iterate through every node
            while (it.hasNext()) {
                T data = it.next();

                //Check if any of the data matches target
                if (data.getLname().equalsIgnoreCase(lname) && data.getFname().equalsIgnoreCase(fname)) {
                    if (previous == null) { //If matches at first node then delete that
                        deleteHeadNode();
                    } else { //Else skip the link to current position
                        position.link = it.previous.link;
                    }
                    return;
                }
                previous = it.position; //Continues to next position
            }
            throw new ContactNotFoundException("There is no such contact on the list. \n" +
                        "Contact " + lname + ", " + fname + " was not found.");
        } catch (ContactListEmptyException | ContactNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints all the Contacts
     * Starts at the head null and goes through all the nodes to print the contact information
     */
    @Override
    public void displayContact() {
        try {
            if (isEmpty()) //Can't display Contacts if there are none
                throw new ContactListEmptyException("No Contacts found.");

            //Start position at beginning (head) and iterate through the whole list
            Node position = head;
            while (position != null) {
                System.out.println(position.data); //Print each of the Contacts
                position = position.link;
            }
        } catch (ContactListEmptyException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Recursively searches contact by name
     * Uses helping method to start at head
     * Prints out the result accordingly
     * @param lname last Name of the target contact
     * @param fname first Name of the target contact
     */
    //recursive method search contact by name, traverse linked list
    @Override
    public void searchContact(String lname, String fname) {
        try {
            if (isEmpty()) { //Can't search for contacts if there are none
                throw new ContactListEmptyException("The Contact List is empty");
            }

            //Call to helper method, starting at the beginning (head)
            Node result = searchContactHelper(lname, fname, head);
            if (result == null) //If no contact was found
                System.out.println("Contact " + lname + ", " + fname + " was not found");
            else
                System.out.println("Contact was found: " + result.data); //Displays target information
        } catch (ContactListEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Recursively searches contact by name (helper)
     * @param lname last name of the target contact
     * @param fname first name of the target contact
     * @param position used to traverse the linked list by going to the link node at each recursion
     *                 until the last node has been reached (base case) or contact has been found
     * @return the Node at the current position if last name and first name match
     */
    private Node searchContactHelper(String lname, String fname, Node position) {
        //Base case
        if (position == null)
            return null; //If whole list was searched and contact not found
        if (position.data.getLname().equalsIgnoreCase(lname)) { //Checks for equality
            if (position.data.getFname().equalsIgnoreCase(fname))
                return position;
        }
        return searchContactHelper(lname, fname, position.link); //returns to searchContact method
    }

    /**
     * Saves all the contacts to a file
     * @param fileName in what file the contacts will be saved
     */
    @Override
    public void saveContact(String fileName) {
        try {
            if (isEmpty()) //Can't save any contacts to File if there are no contacts to save
                throw new ContactListEmptyException("There are no Contacts to save.");

            ObjectOutputStream outputStream = null; //Keep compiler happy
            outputStream = new ObjectOutputStream(new FileOutputStream(fileName));

            //Iterate through the whole list starting at the beginning (head)
            Node position = head;
            while (position != null) {
                outputStream.writeObject(position); //Write each Contact Object to the file
                position = position.link; //Go to next node
            }
            outputStream.close();
        } catch (ContactListEmptyException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found");
        } catch (IOException e) {
            System.out.println("A different exception");
        }
    }

    /**
     * Loads all the contacts from a file back into the LinkedList
     * @param fileName the name of the file where the contacts are being loaded from
     */
    @Override
    public void loadContact(String fileName) {
        ObjectInputStream inputStream = null; //keeps compiler happy
        try {
            inputStream = new ObjectInputStream(new FileInputStream(fileName)); //Which file to get contacts from

            //Find the end of the current list starting at the beginning (head)
            Node tail = head;
            if (tail != null) {
                while (tail.link != null) {
                    tail = tail.link;
                }
            }

            //The nodes from the file will be appended to the end of the lsit
            while (true) {
                try {
                    Node newNode = (Node) inputStream.readObject();
                    if (tail == null) {
                        head = newNode; //if list empty, head will be the new node
                        tail = newNode;
                    } else {
                        tail.link = newNode;
                        tail = newNode; //tail is now the newer last node, to traverse the linked list.
                    }
                } catch (EOFException e) {
                    break; //happens at the end of the file
                }
            }

            Node position = head;
            while (position != null) {
                System.out.println(position.data);
                position = position.link;
            }

            //Prints list to verify added content.
        } catch (ClassNotFoundException e) {
            System.out.println("Contact was not found");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error.");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error.");
                }
            }
        }
    }


    /**
     * Checks for equality of an object with an instance of ContactList class
     * @param otherObject checks its equality to instance of object
     * @return true if it is the same object, else false.
     */
    public boolean equals(Object otherObject) {
        if (otherObject == null) //can't be equal if there's nothing to compare
            return false;
        else if (getClass() != otherObject.getClass()) //can't be equal if they arent the same class
            return false;
        else {
            ContactList<T> otherList = (ContactList<T>) otherObject;
            if (size() != otherList.size()) //Must be of same size to be equal
                return false;

            //Iterates through both lists to check that each object is the same
            Node position = head;
            Node otherPosition = otherList.head;
            while (position != null) { //until end of lists (same size)
                if (!(position.data.equals(otherPosition.data)))
                    return false;
                position = position.link;
                otherPosition = otherPosition.link;
            }
            return true; //a mismatch was not found
        }
    }

    /**
     * Sorts through all the data to place the nodes in order
     * Selection sorting: O(n^2), chosen for its simplicity
     */
    public void selectionSort() {
        try {
            if (isEmpty()) { //Can't sort a list that's empty
                throw new ContactListEmptyException("No contacts in the list to sort");
            }

            //for loop to iterate through entire list as first comparison value
            for (Node current = head; current.link != null; current = current.link) {
                Node min = current;
                //for loop to iterate through entire list as second comparison value
                for (Node next = current.link; next != null; next = next.link) {
                    if (next.data.compareTo(min.data) < 0)
                        min = next; //sets min to the lowest value
                }
                if (min != current) { //if first comparison value isn't min, swaps
                    swap(current, min);
                }
            }
        } catch (ContactListEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Helper method
     * Swaps the data contained in two nodes
     * @param node1 first node being swapped
     * @param node2 second node being swapped
     */
    private void swap(Node node1, Node node2) {
        T temp = node1.data; //Creates temporary variable to store data of first node
        node1.data = node2.data;
        node2.data = temp;
    }
}
