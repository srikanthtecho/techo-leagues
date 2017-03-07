package com.techolution.map;

public class CustomMap<K, V> {

	static class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;

		public Entry(K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	// Entry table for storing data
	private Entry<K, V>[] table;

	// Initial Capacity
	private int initialCapacity = 4;

	@SuppressWarnings("unchecked")
	public CustomMap() {
		table = new Entry[initialCapacity];
	}

	@SuppressWarnings("unchecked")
	public CustomMap(int capacity) {
		this.initialCapacity = capacity;
		table = new Entry[initialCapacity];
	}

	// Method for generating hashvalue of key entered.
	private int hash(K key) {
		return Math.abs(key.hashCode()) % initialCapacity;
	}

	// Displaying all the Values in HashMap
	public void displayAll() {
		for (int i = 0; i < initialCapacity; i++) {
			if (table[i] != null) {
				Entry<K, V> entry = table[i];
				while (entry != null) {
					System.out.print("{" + entry.key + ":" + entry.value + "}" + " ");
					entry = entry.next;
				}
			}
		}
	}

	// Getting Single Value based on Key
	public V get(K key) {
		int hashKey = hash(key);
		if (table[hashKey] == null) {
			return null;
		} else {
			Entry<K, V> linkLoc = table[hashKey];
			while (linkLoc != null) {
				if (linkLoc.key.equals(key)) {
					return linkLoc.value;
				}
				linkLoc = linkLoc.next;
			}
			return null;
		}
	}

	public void put(K key, V data) {
		if (key == null) {
			return;
		}
		Entry<K, V> newRecord = new Entry<K, V>(key, data, null);
		int hashVal = hash(key);
		if (table[hashVal] == null) {
			table[hashVal] = newRecord;
		} else {
			Entry<K, V> previousNode = null;
			Entry<K, V> currentNode = table[hashVal];
			while (currentNode != null) {
				if (currentNode.key.equals(key)) {
					if (previousNode == null) {
						newRecord.next = currentNode.next;
						table[hashVal] = newRecord;
						return;
					} else {
						newRecord.next = currentNode.next;
						previousNode.next = newRecord;
						return;
					}
				}
				previousNode = currentNode;
				currentNode = currentNode.next;
			}
			previousNode.next = newRecord;
		}
	}

	public boolean remove(K key) {
		int hashVal = hash(key);
		if (table[hashVal] == null) {
			return false;
		} else {
			Entry<K, V> prev = null;
			Entry<K, V> cur = table[hashVal];
			while (cur != null) {
				if (cur.key.equals(key)) {

					if (prev == null) {
						table[hashVal] = table[hashVal].next;
						return true;
					} else {
						prev.next = cur.next;
						return true;
					}
				}
				if (prev == null) {
					return false;
				} else {
					prev.next = cur.next;
					cur = cur.next;
				}
			}
			return false;
		}
	}

}
