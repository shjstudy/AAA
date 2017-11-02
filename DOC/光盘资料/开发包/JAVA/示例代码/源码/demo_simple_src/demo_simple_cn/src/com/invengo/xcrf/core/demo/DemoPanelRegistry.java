package com.invengo.xcrf.core.demo;

import java.util.ArrayList;
import java.util.List;

import com.invengo.xcrf.core.event.IDemoModeChangeEvent;

public class DemoPanelRegistry {

	private static List<IDemoModeChangeEvent> demoPanelLst = new ArrayList<IDemoModeChangeEvent>();

	public static void registryDemoModePanel(IDemoModeChangeEvent demoPanel) {
		demoPanelLst.add(demoPanel);
	}

	public static void changePanelMode() {
		for (IDemoModeChangeEvent demopanel : demoPanelLst) {
			demopanel.changeViewMode();
		}
	}

}
