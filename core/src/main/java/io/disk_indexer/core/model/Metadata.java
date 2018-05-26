package io.disk_indexer.core.model;

public class Metadata extends Model<Integer> {
	private Entry parentEntry;

	private String name;

	private String value;

	public Metadata() {

	}

	public Metadata(String name, String value) {
		this(null, name, value);
	}

	public Metadata(Entry parentEntry, String name, String value) {
		this.parentEntry = parentEntry;
		this.name = name;
		this.value = value;
	}

	public Entry getParentEntry() {
		return this.parentEntry;
	}

	public void setParentEntry(Entry parentEntry) {
		this.parentEntry = parentEntry;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
