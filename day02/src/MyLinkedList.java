public class MyLinkedList {

	private Node head;
	private Node tail;
	private int size;

	private class Node {
		Chicken val;
		Node prev;
		Node next;

		private Node(Chicken d, Node prev, Node next) {
			this.val = d;
			this.prev = prev;
			this.next = next;
		}
	}

	public MyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void add(Chicken c) {
		addLast(c);
	}

	public Chicken pop() {
		return removeLast();
	}

	public void addLast(Chicken c) {
		if (size == 0) { //or head == null
			head = new Node(c, null, null); //make first node
		} else { //but if the list isn't empty
			Node node = head; //declare it here so we can access it outside of the loop
			for ( ; node.next != null; node = node.next) {} //run through linked list to get to the last node
			node.next = new Node(c, null, null); //then add the new node to the end
			Node newNode = node.next;
			newNode.prev = node; //make old end-of-the-list node the previous node
		}
		size++; //list just got one node longer
	}

//	public void addFirst(Chicken c) {
//		head = new Node(c, null, head); //makes new node with previous first node as next -- works even if size is 0 (cause head = null then)
//		if (head.next != null) {
//			Node node = head;
//			head = head.next;
//			head.prev = node;
//		}
//		size++; //list just got one node longer
//	}

	public void addFirst(Chicken c) {
		if (size == 0) { //or head == null
			head = new Node(c, null, null); //makes new node with previous first node as next
		} else {
			Node node = new Node(c, null, head); //makes new node with previous first node as next
			head = head.next;
			head.prev = node;
		}
		size++; //list just got one node longer
	}

	public Chicken get(int index) {
		if (index < size) { //make sure index is within bounds
			int counter = 0; //need this to know when we we're at the right node
			Node node = head; //declare it here so we can access it outside of the loop
			for ( ; node.next != null; node = node.next) { //run through linked list until
				if (counter == index) { //counter = index, then
					return node.val; //return the value of the node with the specified index
				}
				counter++; //increase counter to keep track of indices
			}
		}
		return null; //index is out of bounds
	}

	public Chicken remove(int index) {
		if (size > 1) {
			Node node = head; //make temporary node
			int counter = 0;
			for ( ; node.next != null; node = node.next){
				if (counter == index-1) { //stop at node before specified node
					Node prevNode = node;
					node = node.next;
					Node nextNode = node;
					prevNode.next = nextNode; //cut out specified node
					nextNode.prev = prevNode;
					node.next = null;
					node.prev = null;
					size--;
					return node.val; //return chicken to Hieu
				}
				counter++;
			}
		} else if (size == 1){ //if only one chicken
			Node tempNode = head; //make temporary node
			head = null; //delete node
			size--;
			return tempNode.val; //return chicken to Hieu
		}
		return null; //size is 0 -- no chickens to "deal with"
	}

	public Chicken removeFirst() {
		if (size > 1) {
			Node tempNode = head; //make temporary node
			head = head.next; //move head forward
			tempNode.next = null; //delete link of temporary node
			head.prev = null;
			size--; //one less chicken
			return tempNode.val; //return chicken to Hieu
		} else if (size == 1){ //if only one chicken
			Node tempNode = head; //make temporary node
			head = null; //delete node
			size--;
			return tempNode.val; //return chicken to Hieu
		}
		return null; //size is 0 -- no chickens to "deal with"
	}

	public Chicken removeLast() {
		if (size > 1) {
			Node node = head; //declare it here so we can access it outside of the loop
			for (; node.next.next != null; node = node.next) {} //run through linked list to get to the second-to-last node
			Node tempNode = node.next; //store last node as temporary node
			node.next = null; //sever the connection to the last node
			tempNode.prev = null;
			size --;
			return tempNode.val; //return the last node's chicken
		} else if (size == 1){ //if only one chicken
			Node tempNode = head; //make temporary node
			head = null; //delete node
			size--;
			return tempNode.val; //return chicken to Hieu
		}
		return null; //size is 0 -- no chickens to "deal with"
	}
}