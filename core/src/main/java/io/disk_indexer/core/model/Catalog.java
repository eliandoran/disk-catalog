package io.disk_indexer.core.model;

import java.util.LinkedList;
import java.util.List;

public class Catalog {
	private List<Collection> collections;
	
	public Catalog() {
		collections = new LinkedList<>();
	}
	
	public Iterable<Collection> getCollections() {
		return collections;
	}
}
