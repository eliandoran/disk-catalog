package io.disk_indexer.core.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.disk_indexer.core.model.EntryTypes;

@Entity
@Table(name="Entries")
public class Entry extends EntityBase {
	private EntryTypes entryType;
	private String name;
	private long modificationDate;
	private long size;

	@ManyToOne
	private Entry parentEntry;

	@ManyToOne
	private Collection collection;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Metadata> metadata;

	public Entry() {

	}

	public Entry(EntryTypes entryType) {
		this.entryType = entryType;
	}

	public EntryTypes getEntryType() {
		return this.entryType;
	}

	public void setEntryType(EntryTypes entryType) {
		this.entryType = entryType;
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
		return this.collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public List<Metadata> getMetadata() {
		return this.metadata;
	}

	public void setMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
	}

	public void addMetadata(Iterable<Metadata> metadata) {
		if (this.metadata == null) {
			this.metadata = new LinkedList<>();
		}

		for (Metadata data : metadata) {
			this.metadata.add(data);
		}
	}
}
