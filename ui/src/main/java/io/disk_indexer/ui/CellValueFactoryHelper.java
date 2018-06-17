package io.disk_indexer.ui;

import java.util.function.Function;

import com.jfoenix.controls.JFXTreeTableColumn;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.StringConverter;

public class CellValueFactoryHelper {
	public static <T, U> void setup(JFXTreeTableColumn<T, U> column, Function<T, ObservableValue<U>> mapper) {
		column.setCellValueFactory((TreeTableColumn.CellDataFeatures<T, U> param) -> {
			if (column.validateValue(param))
				return mapper.apply(param.getValue().getValue());

			return column.getComputedValue(param);
		});
	}

	public static <T, U> void setup(JFXTreeTableColumn<T, String> column, Function<T, ObservableValue<U>> mapper, StringConverter<U> stringConverter) {
		setup(column, cellData -> new ReadOnlyStringWrapper( stringConverter.toString(mapper.apply(cellData).getValue())));
	}
}
