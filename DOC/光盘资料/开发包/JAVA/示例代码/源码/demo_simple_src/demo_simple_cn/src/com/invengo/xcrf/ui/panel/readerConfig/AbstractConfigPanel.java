package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.Reader;

import javax.swing.JPanel;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig;

public abstract class AbstractConfigPanel extends JPanel implements ConfigPanel {
	protected Reader readerInfo;

	protected UserConfig userConfig;

	protected RFIDProtocol protocol;

	public AbstractConfigPanel() {
		super();
	}

	public void fillConfigData() {
		Demo demo = DemoRegistry.getCurrentDemo();
		this.readerInfo = demo.getReader();
		this.userConfig = demo.getConfig();
		this.protocol = demo.getProtocl();
	}

}
