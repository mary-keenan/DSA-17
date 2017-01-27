public class MyArrayList {
	private Cow[] elems;
	private int size;

	public MyArrayList() {
		this(10);
	}

	public MyArrayList(int capacity) {
		elems = new Cow[capacity];
		size = 0;
	}

	public void add(Cow c) {
		if (size==elems.length)
			doubleSize();

		elems[size] = c;
		size++;
	}

	public int size() {
		return size;
	}

	public Cow get(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		return elems[index];
	}

	public Cow remove(int index){
		if (index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds.");

		if (size<elems.length/4 && elems.length > 10)
			halfSize();

		Cow removed = get(index);
		index++;

		// Shift elements to fill empty space from removing element
		while (index < size) {
			elems[index-1] = elems[index];
			index++;
		}

		size--;
		return removed;
	}

	public void add(int index, Cow c){
		if (index > size)
			throw new IndexOutOfBoundsException("Index out of bounds.");

		if (size==elems.length)
			doubleSize();

		// Shift elements to make space for new element
		int mover = index;
		while (mover<size) {
			elems[mover+1]=elems[mover];
			mover++;
		}

		elems[index]=c;
		size++;
	}

	private void doubleSize(){
		Cow[] newElems = new Cow[elems.length*2];
		System.arraycopy(elems, 0, newElems, 0, size);
		elems = newElems;
	}

	private void halfSize(){
		int newCapacity = elems.length / 2;
		Cow[] newElems = new Cow[newCapacity];
		System.arraycopy(elems, 0, newElems, 0, newCapacity);
		elems = newElems;
	}

	public static void main(String[] args) {
		MyArrayList myArray = new MyArrayList();
		Cow c1 = new Cow("David", 21, "Red");
		Cow c2 = new Cow("Katie", 20, "Blue");
		Cow c3 = new Cow("Sidd", 21, "Green");
		Cow c4 = new Cow("Hieu", 21, "Purple");
		myArray.add(c1); myArray.add(c2); myArray.add(c3); myArray.add(c4);
	}
}
