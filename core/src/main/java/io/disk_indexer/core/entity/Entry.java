package io.disk_indexer.core.entity;

import javax.persistence.Entity;

import io.disk_indexer.core.model.EntryTypes;

@Entity
public class Entry extends EntityBase {
	private EntryTypes entryType;
	private String name;
	private long modificationDate;
	private long size;

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
}
