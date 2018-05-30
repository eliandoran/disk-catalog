package io.disk_indexer.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Metadata extends EntityBase {
	private String name;

	private String value;

	public Metadata() {

	}

	public Metadata(String name, String value) {
		this.name = name;
		this.value = value;
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
