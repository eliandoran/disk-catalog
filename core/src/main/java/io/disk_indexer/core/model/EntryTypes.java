package io.disk_indexer.core.model;

public enum EntryTypes {
	File(0),
	Directory(1);

	private int numericValue;

	private EntryTypes(int numericValue) {
		this.numericValue = numericValue;
	}

	public int getNumericValue() {
		return this.numericValue;
	}
}
