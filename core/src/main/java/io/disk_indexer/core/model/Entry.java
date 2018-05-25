package io.disk_indexer.core.model;

import java.util.LinkedList;
import java.util.List;

public class Entry {
	private EntryTypes entryType;
	
	private String name;
	
	private List<Entry> childEntries;	
	
	public Entry(EntryTypes entryType) {
		this.entryType = entryType;
		
		if (entryType == EntryTypes.Directory) {
			childEntries = new LinkedList<>();
		}
	}
	
	public Iterable<Entry> getChildEntries() {
		if (childEntries == null) {
			throw new IllegalStateException("Attempt to obtain child entries from a non-container (i.e. not a directory).");
		}
		
		return childEntries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addChildEntry(Entry child) {
		if (childEntries == null) {
			throw new IllegalStateException("Attempt to add a child entry to a non-container (i.e. not a directory).");
		}
		
		this.childEntries.add(child);
	}
}
