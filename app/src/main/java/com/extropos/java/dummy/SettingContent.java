package com.extropos.java.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SettingContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		// Add enhanced settings items
		addItem(new DummyItem("1", "Printer Selection"));
		addItem(new DummyItem("2", "User Management"));
		addItem(new DummyItem("3", "License Activation"));
		addItem(new DummyItem("4", "Malaysian E-Invoice Setup"));
		addItem(new DummyItem("5", "Kitchen Printer Setup"));
		addItem(new DummyItem("6", "Store Information"));
		addItem(new DummyItem("7", "Tax Settings"));
		addItem(new DummyItem("8", "Currency Settings"));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
