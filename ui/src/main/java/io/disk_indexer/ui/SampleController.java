package io.disk_indexer.ui;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.dao.FileSystemConnectionManager;
import io.disk_indexer.core.dao.impl.SqliteConnectionManager;
import io.disk_indexer.core.exceptions.ConnectionFailedException;
import io.disk_indexer.core.exceptions.InitializationFailedException;
import io.disk_indexer.core.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Collection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;

public class SampleController {
	@FXML
	private JFXTreeTableView<Person> treeTableView;

	@FXML
	private JFXTreeTableColumn<Person, String> entryNameColumn;
	@FXML
	private JFXTreeTableColumn<Person, String> entrySizeColumn;
	@FXML
	private JFXTreeTableColumn<Person, Integer> entryDateModifiedColumn;

	private FileSystemConnectionManager connectionManager;

	@PostConstruct
	public void init() {
		try {
			this.connectionManager = new SqliteConnectionManager();
			this.connectionManager.connect("/home/elian/db.sqlite");

			Iterable<Collection> collections = this.connectionManager.getCollectionDao().readAll();

			for (Collection collection : collections) {
				System.out.println(collection.getTitle());
			}
		} catch (ConnectionFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InitializationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setupCellValueFactory(this.entryNameColumn, Person::firstNameProperty);
		setupCellValueFactory(this.entrySizeColumn, Person::lastNameProperty);
		setupCellValueFactory(this.entryDateModifiedColumn, p -> p.age.asObject());

		ObservableList<Person> persons = FXCollections.observableArrayList();
		persons.add(new Person("Elian", "Doran", 21));
		persons.add(new Person("Elian", "Doran", 21));
		persons.add(new Person("Elian", "Doran", 21));
		persons.add(new Person("Elian", "Doran", 21));
		persons.add(new Person("Elian", "Doran", 21));

		this.treeTableView.setRoot(new RecursiveTreeItem<>(persons, RecursiveTreeObject::getChildren));
		this.treeTableView.setShowRoot(false);
		this.treeTableView.refresh();
	}

	private <T> void setupCellValueFactory(JFXTreeTableColumn<Person, T> column, Function<Person, ObservableValue<T>> mapper) {
		column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Person, T> param) -> {
			if (column.validateValue(param))
				return mapper.apply(param.getValue().getValue());
			else
				return column.getComputedValue(param);
		});
	}

	/*
	 * data class
	 */
	static final class Person extends RecursiveTreeObject<Person> {
		final StringProperty firstName;
		final StringProperty lastName;
		final SimpleIntegerProperty age;

		Person(String firstName, String lastName, int age) {
			this.firstName = new SimpleStringProperty(firstName);
			this.lastName = new SimpleStringProperty(lastName);
			this.age = new SimpleIntegerProperty(age);
		}

		StringProperty firstNameProperty() {
			return this.firstName;
		}

		StringProperty lastNameProperty() {
			return this.lastName;
		}
	}
}
