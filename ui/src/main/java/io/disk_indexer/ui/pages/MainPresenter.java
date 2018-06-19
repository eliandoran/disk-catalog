package io.disk_indexer.ui.pages;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.EntityBase;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import io.disk_indexer.ui.CellValueFactoryHelper;
import io.disk_indexer.ui.ClassSelectors;
import io.disk_indexer.ui.DataBridge;
import io.disk_indexer.ui.fileicons.FileExtensionMapper;
import io.disk_indexer.ui.fileicons.FileMapper;
import io.disk_indexer.ui.tree.CollectionTreeItem;
import io.disk_indexer.ui.treeobject.EntryImageNameTreeTableCell;
import io.disk_indexer.ui.treeobject.EntryTreeObject;
import io.disk_indexer.ui.treeobject.EpochTimeStringConverter;
import io.disk_indexer.ui.treeobject.FileSizeStringConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MainPresenter implements Initializable {
	@FXML
	private JFXTreeView<EntityBase> mainNavigation;

	@FXML
	private JFXTreeTableView<EntryTreeObject> treeTableView;

	@FXML
	private JFXTreeTableColumn<EntryTreeObject, Entry> entryNameColumn;
	@FXML
	private JFXTreeTableColumn<EntryTreeObject, String> entrySizeColumn;
	@FXML
	private JFXTreeTableColumn<EntryTreeObject, String> entryDateModifiedColumn;

	@Inject
	DataBridge dataBridge;

	private FileMapper fileMapper = new FileExtensionMapper();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTableView();
		setupMainNavigation();
	}

	private void setupMainNavigation() {
		TreeItem<EntityBase> rootNode = new TreeItem<>();

		rootNode.setExpanded(true);
		this.mainNavigation.setRoot(rootNode);
		this.mainNavigation.setShowRoot(false);
		this.mainNavigation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<EntityBase>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<EntityBase>> observable, TreeItem<EntityBase> oldValue,
					TreeItem<EntityBase> newValue) {
				if (newValue != null) {
					Entry rootEntry = null;

					if (newValue.getValue() instanceof Collection) {
						rootEntry = ((Collection)newValue.getValue()).getRootEntry();
					}

					if (newValue.getValue() instanceof Entry) {
						rootEntry = ((Entry)newValue.getValue());
					}

					MainPresenter.this.treeTableView.setRoot(buildEntries(rootEntry));
				}
			}
		});

		for (Collection collection : this.dataBridge.getCollections()) {
			CollectionTreeItem collectionNode = new CollectionTreeItem(collection);
			rootNode.getChildren().add(collectionNode);
		}
	}

	private void setupTableView() {
		this.entryNameColumn.setCellFactory((e) -> new EntryImageNameTreeTableCell());
		CellValueFactoryHelper.setup(this.entryNameColumn, EntryTreeObject::entryProperty);
		CellValueFactoryHelper.setup(this.entrySizeColumn, EntryTreeObject::sizeProperty, new FileSizeStringConverter());
		CellValueFactoryHelper.setup(this.entryDateModifiedColumn, EntryTreeObject::dateProperty, new EpochTimeStringConverter());

		ClassSelectors.COLUMN_DATE_MODIFIED.applyTo(this.entryDateModifiedColumn);

		Collection collection = this.dataBridge.getCollections().iterator().next();
		Entry rootEntry = collection.getRootEntry().getChildEntries().get(0);

		this.treeTableView.setRoot(buildEntries(rootEntry));
		this.treeTableView.setShowRoot(false);
		this.treeTableView.refresh();
		this.treeTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() != 2)
					return;

				TreeItem<EntryTreeObject> item = MainPresenter.this.treeTableView.getSelectionModel().getSelectedItem();

				if (item == null)
					return;

				Entry entry = item.getValue().entryProperty().get();

				if (entry.getEntryType() == EntryTypes.Directory) {
					MainPresenter.this.treeTableView.setRoot(buildEntries(entry));
				}
			}

		});
	}

	private RecursiveTreeItem<EntryTreeObject> buildEntries(Entry rootEntry) {
		ObservableList<EntryTreeObject> entries = FXCollections.observableArrayList();

		for (Entry childEntry : rootEntry.getChildEntries()) {
			Image icon = this.fileMapper.obtainIcon(childEntry);

			EntryTreeObject childObject = new EntryTreeObject(childEntry, icon);
			entries.add(childObject);
		}

		return new RecursiveTreeItem<>(entries, RecursiveTreeObject::getChildren);
	}
}
