package io.disk_indexer.ui;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.disk_indexer.core.entity.Collection;

public class DataBridge {

	private EntityManager entityManager;

	@PostConstruct
	public void init() {
		setupConnection();
	}

	public Iterable<Collection> getCollections() {
		Iterable<Collection> collections = this.entityManager.createQuery("from Collection", Collection.class).getResultList();
		return collections;
	}

	private void setupConnection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("core-readonly");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
}
