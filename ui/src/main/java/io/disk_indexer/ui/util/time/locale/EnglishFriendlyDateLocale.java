package io.disk_indexer.ui.util.time.locale;

import io.disk_indexer.ui.util.time.FriendlyDateLocale;

public class EnglishFriendlyDateLocale implements FriendlyDateLocale {

	@Override
	public String getTodayFormat() {
		return "'Today'";
	}

	@Override
	public String getYesterdayFormat() {
		return "'Yesterday'";
	}

	@Override
	public String getSameYearFormat() {
		return "dd MMM";
	}

	@Override
	public String getDefaultFormat() {
		return "dd MMM yyyy";
	}

}
