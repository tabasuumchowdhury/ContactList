package org.example;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * Di
 * @param <T> any class deriving the Contact class
 */
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
         * Contructor,
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
        int count = 0;
        Node position = head;
        while (position != null) {
            count++;
            position = position.link;
        }
        return count;
    }

    /**
     * Checks if the Contact List object is empty
     * @return true if it is empty, else false
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the ContactList object
     * Does so by removing any variable pointing to the first node
     */
    public void clear() {
        head = null;
    }

    /**
     * Adds contact node to the end
     * @param contact the contact that's being added to the last new node
     */
    @Override
    public void addContact(Contact contact) {
        Node newNode = new Node((T) contact);
        if (head == null)
            head = newNode;
        else {
            Node current = head;
            while (current.link != null)
                current = current.link;
            current.link = newNode;
        }
    }

    /**
     * Adds contact node to the beginning
     * @param contact the contact thats being added to the first new node
     */
    public void addToStart(T contact) {
        head = new Node(contact, head);
    }

    /**
     * Removes the first contact node
     */
    public void deleteHeadNode() {
        if (head != null)
            head = head.link;
    }

    /**
     * Removes a specific contact
     * @param lname of the Contact
     * @param fname of the Contact, used to find the Contact
     * @throws ContactNotFoundException thrown if the ContactList does not have the target Contact
     */
    public void deleteContact(String lname, String fname) throws ContactNotFoundException {
        if (head == null) {
            throw new ContactNotFoundException("There are no contacts to delete");
        }

        ContactListIterator it = iterator();
        Node previous = null;
       Node position = head;
       while (it.hasNext()) {
           T data = it.next();

           if (data.getLname().equalsIgnoreCase(lname) && data.getFname().equalsIgnoreCase(fname)) {
               if (previous == null) {
                   deleteHeadNode();
               } else {
                   position.link = it.previous.link;
               }
               return;
           }
           previous = it.position;
       }

        throw new ContactNotFoundException("There is no such contact on the list. \n" +
                "Contact " + lname + ", " + fname + " was not found.");

    }

    /**
     * Prints all the Contacts
     * Starts at the head null and goes through all the nodes to print the contact information
     */
    @Override
    public void displayContact() {
        if (head == null)
            System.out.println("No Contacts found.");
        Node position = head;
        while (position != null) {
            System.out.println(position.data);
            position = position.link;
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
        Node result = searchContactHelper(lname, fname, head);
        if (result == null)
            System.out.println("Contact " + lname + ", " + fname + " was not found");
        else
            System.out.println("Contact was found: " + result.data);

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
        if (position == null)
            return null;
        if (position.data.getLname().equalsIgnoreCase(lname)) {
            if (position.data.getFname().equalsIgnoreCase(fname))
                return position;
        }
        return searchContactHelper(lname, fname, position.link);
    }

    /**
     * Saves all the contacts to a file
     * @param fileName in what file the contacts will be saved
     */
    @Override
    public void saveContact(String fileName) {
        if (head == null)
            System.out.println("There are no Contacts to save.");

        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            Node position = head;
            while (position != null) {
                outputStream.writeObject(position);
                position = position.link;
            }
            outputStream.close();
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
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(fileName));
            head = null;
            Node tail = null;
            while (true) {
                try {
                    Node newNode = (Node) inputStream.readObject();
                    if (head == null) {
                        head = newNode;
                    } else {
                        tail.link = newNode;
                    }
                    tail = newNode;
                } catch (EOFException e) {
                    break;
                }
            }
            Node position = tail;
            while (position != null) {
                System.out.println(position.data);
                position = position.link;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Contact was not found");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
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
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            ContactList<T> otherList = (ContactList<T>) otherObject;
            if (size() != otherList.size())
                return false;
            Node position = head;
            Node otherPosition = otherList.head;
            while (position != null) {
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
     * @param <T> the data that is being sorted, in this case Contacts
     */
    public <T extends Comparable<T>> void selectionSort() {
        if (head == null) {
            return;
        }

        for (Node current = head; current.link != null; current = current.link) {
            Node min = current;
            for (Node next = current.link; next != null; next = next.link) {
                if (next.data.compareTo(min.data) < 0)
                    min = next;
            }
            if (min != current) {
                swap(current, min);
            }
        }
    }

    /**
     * Helper method
     * Swaps the data contained in two nodes
     * @param node1 first node being swapped
     * @param node2 second node being swapped
     */
    private void swap(Node node1, Node node2) {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }
}
