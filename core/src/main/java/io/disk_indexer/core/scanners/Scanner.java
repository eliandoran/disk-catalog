package io.disk_indexer.core.scanners;

import io.disk_indexer.core.model.Entry;

public interface Scanner {
	Entry scan(String path);
}