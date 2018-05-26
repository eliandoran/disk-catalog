package io.disk_indexer.core.model;

public abstract class Model<TKey> {
	protected TKey id;

	public TKey getId() {
		return this.id;
	}

	public void setId(TKey id) {
		this.id = id;
	}
}
