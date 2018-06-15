package io.disk_indexer.ui;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.disk_indexer.core.entity.Collection;

public class DataBridge {

	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void init() {
		setupConnection();
	}

	public Iterable<Collection> getCollections() {
		Iterable<Collection> collections = this.entityManager.createQuery("from Collection", Collection.class).getResultList();
		return collections;
	}

	private void setupConnection() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("core-readonly");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void closeConnection() {
		this.entityManagerFactory.close();
	}
}
