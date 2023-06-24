/*
 * Name: Brian Hert
 * Date: 6/22/2020
 * ContactApp
 */

import java.lang.Comparable; // needed to implement the Comparable interface
import java.io.File;  // needed for file manipulation
import java.io.FileNotFoundException;  // needed for error handling
import java.util.Scanner; // needed to read text files

class Person implements Comparable<Person>{

	// variable declaration
	String first, last, phone;

	// three parameter constructor
	Person (String first, String last, String phone){
		this.first = first;
		this.last = last;
		this.phone = phone;
	}

	// getter methods
	public String getFirst() {
		return this.first;
	}

	public String getLast() {
		return this.last;
	}

	public String getPhone() {
		return this.phone;
	}

	// to string method formated for contact directory
	public String toString() {
		return String.format("%s, %s: %s", this.last, this.first, this.phone);
	}

	// setter method
	public void setPhone(String p) {
		this.phone = p;
	}

	// equals method
	public boolean equals(Person other) {
		if (other instanceof Person) {
			Person p = (Person) other;
			return this.toString().contentEquals(p.toString());			
		}
		return false;
	}

	// compareTo method
	public int compareTo(Person other) {
		int last = this.last.compareTo(other.last);
		return last == 0 ? this.first.compareTo(other.first) : last; // if last names are equal, compare first names
	}
}

// Node class used in linked list
class Node {

	// public variable declaration
	public Person info;
	public Node next;

	// constructor accepts Person object
	Node(Person p) {
		this.info = p;
	}
}

// from Directory class we will instantiate our linked list object
class Directory {

	// variable pointing to front/ head node of linked list object
	Node front;

	// constructor with one parameter to accept file path to text file for data input
	Directory(String fileName) {

		// open file
		File inputFile = new File(fileName);
		try {
			// read data from file
			Scanner fileReader = new Scanner(inputFile);

			// create nodes from file and prepend to linked list data structure
			while (fileReader.hasNext()) {
				// parse data
				String[] lineData = fileReader.nextLine().split(" ");
				addContact(lineData[0], lineData[1], lineData[2]);
			}
			fileReader.close(); // close Scanner object

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		// call sort method to have contacts saved in order of last name
		//		sort(); // commented out because it is breaking the listContact() method. It's successfully comparing the nodes, but not relinking them correctly after the second one.
	}

	// sort function using one of: bubble/insertion/selection algorithms
	public void sort() {
		// short circuit if no sorting required
		if (front == null || front.next == null) {
			return;
		}

		// sorted and current both start pointing at front node
		Node sorted = front; // the final node of the sorted side
		Node current = front; // the first node of the unsorted side that is being sorted
		Node nextCurrent = null; // the node we will compare on the next loop
		Node pointer = front; // the node that the current node is being compared to
		Node prevPointer = null; // the previous node we compared the current node to
		Node nextPointer = null; // the next node we will compare the current node to

		// loops until entire linked list has been sorted
		while (current != null) {
			nextCurrent = current.next;
			pointer = front; // start at the front of the linked list with each new current node
			prevPointer = null;
			nextPointer = null;

			System.out.println(pointer.info.toString());
			System.out.println(current.info.toString());
			// iterate over nodes from front to sorted, comparing current to pointer
			do {
				nextPointer = pointer.next;
				//				// if comparing the current node to itself, break out of loop and continue with next current node
				if (current.info.compareTo(pointer.info) == 0 ) {
					break;
				}
				// if the current node comes before the pointer, then insert the node, keep sorted the same, and break out of loop
				if (current.info.compareTo(pointer.info) < 0) {
					System.out.println(current.info.last + " comes before " + pointer.info.last);
					if (prevPointer == null) {
						current.next = pointer;
						break;
					} else {
						prevPointer.next = current;
						current.next = pointer;
						break;
					}

				} else {
					System.out.println(current.info.last + " comes after " + pointer.info.last);
					// if current node comes after the pointer, insert after and continue loop. if the pointer is the sorted node, update current node to be the sorted and break;
					if (pointer.info.equals(sorted.info) ) {
						System.out.println("current node is the final sorted node");
						sorted = current;
						pointer.next = current;
					} else {
						current.next = nextPointer;
						pointer.next = current;						
					}
				}
				prevPointer = pointer;
				pointer = nextPointer;
			} while (pointer != sorted);

			current = nextCurrent;
			if (current != null) System.out.println("\nNext sorting: " + current.info.last);
		}
	}

	// adds new Node to the beginning of the linked list
	public void addContact(String first, String last, String phone) {
		Node newFront = new Node(new Person(first, last, phone));
		newFront.next = front;
		front = newFront;
		//		sort(); // sort new contact into proper location
	}

	public void deleteContact(String last) {

		// empty linked list case
		if (front == null) {
			System.out.println("Contact list is empty.");
			return;
		}

		// found in first node case
		if (front.info.last.contentEquals(last)) {
			if (front.next != null) {
				front = front.next;
				System.out.println("Contact deleted");
				return;
			} else {
				front = null; // one node remaining case
				System.out.println("Contact deleted");
				return;
			}
		}

		// needed for traversing linked list nodes
		Node current = front;
		Node prev = null;

		while ( current.next != null) {
			if (current.next.info.last.contentEquals(last)) {
				current.next = current.next.next; // doesn't actually delete from memory, but unlinks it from Directory linked list
				System.out.println("Contact deleted");
				return;
			}
			prev = current;
			current = current.next;
		}

		if (current.info.last.contentEquals(last)) {
			prev.next = null;
			return;
		}
		System.out.println("Contact not found.");
	}

	// search method
	public Node searchContact(String last) {
		Node current = front;
		// loops through linked list until either contact is found, which will cause a return, or end of list is reached
		while (current != null) {
			if (current.info.last.contentEquals(last)) {
				return current; // returns node with found contact
			}
			current = current.next;
		}
		System.out.println("Contact not found.");
		return null;
	}

	// updates phone number. Need last name to find contact
	public void update(String last, String phone) {
		Node user = searchContact(last);
		if (user != null) {
			user.info.phone = phone;
			System.out.println(user.info.first + " " + user.info.last + "'s phone number updated to: " + user.info.phone);			
		} else {
			System.out.println("Contact could not be updated.");
		}
	}

	public String toString() {
		// empty list case
		System.out.println("1");
		if (front == null) {
			return "Contact list is empty.";
		}
		System.out.println("2");
		// one node case
		String results = "Contact list: \n";
		results += front.info.toString() + "\n";
		System.out.println("3");
		// multiple node case
		Node current = front;
		while (current.next != null) {
			System.out.println(current.info.last);
			System.out.println(current.next.info.last);
			current = current.next;
			results += current.info.toString() + "\n";
		}
		System.out.println("4");
		return results;
	}

	public void listContact() {
		System.out.println(this.toString());
	}
}


// driver class
public class ContactApp {
	static void menu() {
		System.out.println("Choose an option: ");
		System.out.println("1. Add contact");
		System.out.println("2. Delete contact");
		System.out.println("3. Search contact");
		System.out.println("4. List contact");
		System.out.println("5. Update contact");
		System.out.println("6. Exit");
		System.out.print("Enter the number of your choice: ");
	}

	// method for delaying menu after action has just been completed
	static void menuDelay() throws InterruptedException {
		int delayTime = 500;
		Thread.sleep(delayTime);
		System.out.print(" . ");
		Thread.sleep(delayTime);
		System.out.print(" . ");
		Thread.sleep(delayTime);
		System.out.print(" . \n");
		Thread.sleep(delayTime);
	}

	static void run() {
		// instantiate contact list object
		Directory contactapp = new Directory("list.txt");

		// create scanner to read in user input
		Scanner input = new Scanner(System.in);
		int userChoice = 0;
		while (userChoice != 6) {
			menu();
			String newContactFirst, newContactLast, newContactPhone;
			try {
				userChoice = input.nextInt();
				switch (userChoice) {
				case 1:
					System.out.print("First name: ");
					newContactFirst = input.next();
					System.out.print("Last name: ");
					newContactLast = input.next();
					System.out.print("Phone number: ");
					newContactPhone = input.next();
					contactapp.addContact(newContactFirst, newContactLast, newContactPhone);
					System.out.println("Contact added");
					menuDelay();
					break;
				case 2:
					System.out.print("Enter the last name of the person you want deleted: ");
					String lastName = input.next();
					contactapp.deleteContact(lastName);
					menuDelay();
					break;
				case 3:
					System.out.print("Enter the last name of the person you want to search for: ");
					String SearchLastName = input.next();
					Node userFound = contactapp.searchContact(SearchLastName);
					if (userFound != null) {
						System.out.println(userFound.info.toString());								
					}
					menuDelay();
					break;
				case 4:
					contactapp.listContact();
					menuDelay();
					break;
				case 5:
					System.out.print("Enter the last name of the person you want updated: ");
					String UpdateLastName = input.next();
					System.out.print("Enter the new phone number: ");
					String newPhone = input.next();
					contactapp.update(UpdateLastName, newPhone);
					menuDelay();
					break;
				case 6:
					System.out.println("Exiting program...");
					break;
				default: 
					System.out.println("Invalid choice.");
					menuDelay();
				}


			} catch (java.util.InputMismatchException e) {
				System.out.println("Error, please enter an integer.");
				try {
					menuDelay();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				input.next();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		input.close();
	}

	// begin main method
	public static void main(String[] args) {
		run();
	} // end main method
}
