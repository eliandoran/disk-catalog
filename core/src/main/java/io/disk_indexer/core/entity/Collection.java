package io.disk_indexer.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Collections")
public class Collection extends EntityBase {
	private String title;

	@ManyToOne(cascade=CascadeType.PERSIST)
	private Entry rootEntry;

	public Collection() {

	}

	public Collection(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Entry getRootEntry() {
		if (this.rootEntry == null) {
			this.rootEntry = new Entry(EntryTypes.Directory);
			this.rootEntry.setName("Root entry");
		}

		return this.rootEntry;
	}

	public void setRootEntry(Entry rootEntry) {
		this.rootEntry = rootEntry;
	}
}
