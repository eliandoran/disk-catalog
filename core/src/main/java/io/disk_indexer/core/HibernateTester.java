package io.disk_indexer.core;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateTester {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("core");
	}

}
