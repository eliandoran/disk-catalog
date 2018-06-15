package io.disk_indexer.ui.pages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.inject.Inject;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.ui.DataBridge;
import io.disk_indexer.ui.tree.CollectionTreeItem;
import io.disk_indexer.ui.treeobject.EntryTreeObject;
import io.disk_indexer.ui.treeobject.FileSizeStringConverter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class MainPresenter implements Initializable {
	@FXML
	private JFXTreeView<String> mainNavigation;

	@FXML
	private JFXTreeTableView<EntryTreeObject> treeTableView;

	@FXML
	private JFXTreeTableColumn<EntryTreeObject, String> entryNameColumn;
	@FXML
	private JFXTreeTableColumn<EntryTreeObject, String> entrySizeColumn;
	@FXML
	private JFXTreeTableColumn<EntryTreeObject, String> entryDateModifiedColumn;

	@Inject
	DataBridge dataBridge;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTableView();
		setupMainNavigation();
	}

	private void setupMainNavigation() {
		TreeItem<String> rootNode = new TreeItem<>("Root");
		rootNode.setExpanded(true);
		this.mainNavigation.setRoot(rootNode);
		this.mainNavigation.setShowRoot(false);

		for (Collection collection : this.dataBridge.getCollections()) {
			CollectionTreeItem collectionNode = new CollectionTreeItem(collection);
			rootNode.getChildren().add(collectionNode);
		}
	}

	private void setupTableView() {
		setupCellValueFactory(this.entryNameColumn, EntryTreeObject::nameProperty);
		setupCellValueFactory(this.entrySizeColumn, cellData -> new ReadOnlyStringWrapper(new FileSizeStringConverter().toString(cellData.sizeProperty().asObject().get())));
		setupCellValueFactory(this.entryDateModifiedColumn, EntryTreeObject::dateProperty);

		ObservableList<EntryTreeObject> entries = FXCollections.observableArrayList();

		Collection collection = this.dataBridge.getCollections().iterator().next();
		Entry rootEntry = collection.getRootEntry().getChildEntries().get(0);

		for (Entry child : rootEntry.getChildEntries()) {
			entries.add(new EntryTreeObject(child));
		}

		this.treeTableView.setRoot(new RecursiveTreeItem<>(entries, RecursiveTreeObject::getChildren));
		this.treeTableView.setShowRoot(false);
		this.treeTableView.refresh();
	}

	private <T> void setupCellValueFactory(JFXTreeTableColumn<EntryTreeObject, T> column, Function<EntryTreeObject, ObservableValue<T>> mapper) {
		column.setCellValueFactory((TreeTableColumn.CellDataFeatures<EntryTreeObject, T> param) -> {
			if (column.validateValue(param))
				return mapper.apply(param.getValue().getValue());
			else
				return column.getComputedValue(param);
		});
	}
}
