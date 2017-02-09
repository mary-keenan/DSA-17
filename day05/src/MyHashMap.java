import java.util.*;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many or too few entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> implements Map<K, V> {

	// average number of entries per map before we grow the map
	private static final double ALPHA = 1.0;
	// average number of entries per map before we shrink the map
	private static final double BETA = .25;

	// resizing factor: (new size) = (old size) * (resize factor)
	private static final double SHRINK_FACTOR = 0.5, GROWTH_FACTOR = 2.0;

	private static final int MIN_MAPS = 16;

	// list of maps
	protected List<MyLinearMap<K,V>> maps;
	private int size = 0; //TODO: how else do I set the size w/o writing a new method? Causes errors because it stays at 0

	public MyHashMap() {
		makeMaps(MIN_MAPS);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Initialize maps
	 */

	protected void makeMaps(int size) {
		maps = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			maps.add(new MyLinearMap<K, V>());
		}
	}

	protected MyLinearMap<K, V> chooseMap(Object key) {
		int hash = 0; //hash code for null objects is just 0
		if (key != null) { //can't get hashCode() of null object
			hash = key.hashCode() % maps.size(); //create hash based on number of maps
		}
		return maps.get(hash);
	}

	@Override
	public boolean containsKey(Object key) { //hint from reading: use chooseMap
		MyLinearMap<K,V> checkMap = chooseMap(key);
		if (checkMap.get(key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) { //hint from reading: don't use chooseMap
		Collection<V> allValues = values();
		if (allValues.contains(value)) {
			return true;
		}
		return false;
	}

	protected void rehash(double growthFactor) {
		Map<K,V> tempMap = new MyLinearMap<>(); //get all entries
		ArrayList<K> allKeys = new ArrayList<>();
		for (int i = 0; i < maps.size(); i++) {
			MyLinearMap<K,V> currMap = maps.get(i);
			Set<K> keySet = currMap.keySet();
			for (K key: keySet) {
				tempMap.put(key, currMap.get(key));
				allKeys.add(key);
			}
		}
		makeMaps((int) ((double) maps.size() * growthFactor));
		int bob = size;
		for (K key: allKeys) {
			put(key, tempMap.get(key));
		}
		size = bob;
	}

	@Override
	public V get(Object key) {
		MyLinearMap<K,V> m = chooseMap(key);
		return m.get(key);
	}

	@Override
	public V put(K key, V value) {
		MyLinearMap<K,V> findMap = chooseMap(key);
		if (findMap.containsKey(key)) {
			V oldValue = findMap.get(key);
			findMap.remove(key);
			findMap.put(key, value);
			return oldValue;
		}
		findMap.put(key, value);
		size++;
		if ((double) size() / (double) maps.size() > ALPHA) { //if there are too MANY entries per map on average
			rehash(GROWTH_FACTOR);
		}
		return null;
	}

	@Override
	public V remove(Object key) {
		MyLinearMap<K,V> findMap = chooseMap(key);
		if (findMap.containsKey(key)) {
			findMap.remove(key);
			size--;
			if (maps.size() > MIN_MAPS && (double) size() / (double) maps.size() < BETA) { //can't shrink if already at MIN_MAPS (if greater than, it can shrink)
				rehash(SHRINK_FACTOR); //if there are too FEW entries per map on average
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public void clear() {
		for (int i=0; i<maps.size(); i++) {
			maps.get(i).clear();
		}
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<>();
		for (MyLinearMap<K,V> map : maps) {
			set.addAll(map.keySet());
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		Collection<V> ll = new LinkedList<>();
		for (MyLinearMap<K,V> map : maps) {
			ll.addAll(map.values());
		}
		return ll;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K,V>> set = new HashSet<>();
		for (MyLinearMap<K,V> map : maps) {
			set.addAll(map.getEntries());
		}
		return set;
	}
}
