package io.disk_indexer.core.model;

import java.util.LinkedList;
import java.util.List;

public class Entry {
	private EntryTypes entryType;
	
	private String name;
	
	private long modificationDate;
	
	private long size;
	
	private List<Entry> childEntries;
	
	public Entry(EntryTypes entryType) {
		this.entryType = entryType;			
	}
	
	public Iterable<Entry> getChildEntries() {
		if (childEntries == null) {
			throw new IllegalStateException("Attempt to obtain child entries from a non-container (i.e. not a directory).");
		}
		
		return childEntries;
	}
	
	public EntryTypes getEntryType() {
		return entryType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void addChildEntry(Entry child) {
		if (entryType != EntryTypes.Directory) {		
			throw new IllegalStateException("Attempt to add a child entry to a non-container (i.e. not a directory).");
		}
		
		if (childEntries == null) {
			childEntries = new LinkedList<>();
		}
		
		this.childEntries.add(child);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(entryType);
		builder.append(": ");
		builder.append(name);
		
		return builder.toString();
	}
}
