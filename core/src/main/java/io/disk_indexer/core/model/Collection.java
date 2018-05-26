package io.disk_indexer.core.model;

public class Collection extends Model<Integer> {
	private String title;

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
		return this.rootEntry;
	}

	public void setRootEntry(Entry rootEntry) {
		this.rootEntry = rootEntry;
	}
}
