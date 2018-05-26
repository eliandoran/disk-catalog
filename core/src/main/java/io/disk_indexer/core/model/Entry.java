package io.disk_indexer.core.model;

import java.util.LinkedList;
import java.util.List;

public class Entry extends Model<Integer> {
	private EntryTypes entryType;

	private String name;

	private long modificationDate;

	private long size;

	private List<Entry> childEntries;

	private Entry parentEntry;

	private Collection collection;

	public Entry(EntryTypes entryType) {
		this.entryType = entryType;
	}

	public Iterable<Entry> getChildEntries() {
		if (this.childEntries == null)
			throw new IllegalStateException("Attempt to obtain child entries from a non-container (i.e. not a directory).");

		return this.childEntries;
	}

	public EntryTypes getEntryType() {
		return this.entryType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}

	public long getSize() {
		return this.size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Entry getParentEntry() {
		return this.parentEntry;
	}

	public void setParentEntry(Entry parentEntry) {
		this.parentEntry = parentEntry;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public void addChildEntry(Entry child) {
		if (this.entryType != EntryTypes.Directory)
			throw new IllegalStateException("Attempt to add a child entry to a non-container (i.e. not a directory).");

		if (this.childEntries == null) {
			this.childEntries = new LinkedList<>();
		}

		this.childEntries.add(child);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.entryType);
		builder.append(": ");
		builder.append(this.name);

		return builder.toString();
	}
}
