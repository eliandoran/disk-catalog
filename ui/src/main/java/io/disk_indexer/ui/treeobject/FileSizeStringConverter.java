package io.disk_indexer.ui.treeobject;

import javafx.util.StringConverter;

public class FileSizeStringConverter extends StringConverter<Long> {

	@Override
	public Long fromString(String string) {
		return null;
	}

	@Override
	public String toString(Long object) {
		long bytes = object.longValue();
		int u = 0;

		for (; bytes > 1024*1024; bytes >>= 10) {
			u++;
		}

		if (bytes > 1024) {
			u++;
		}

		return String.format("%.1f %cB", bytes/1024f, " kMGTPE".charAt(u));
	}

}
