package io.disk_indexer.core.scanners;

import java.util.Comparator;

public class ScannerListenerPriorityComparator implements Comparator<ScannerListener> {
	@Override
	public int compare(ScannerListener o1, ScannerListener o2) {
		return Integer.compare(o1.getPriority(), o2.getPriority());
	}
}
