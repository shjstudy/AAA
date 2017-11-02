package com.invengo.xcrf.ui.panel;

import java.util.Date;
import java.util.List;

public class Pick {
	private String pickName;
	private Date markTime;
	private String protocol;
	private int index;

	private List<ConnType> connTypes;

	public String getPickName() {
		return pickName;
	}

	public void setPickName(String pickName) {
		this.pickName = pickName;
	}

	public Date getMarkTime() {
		return markTime;
	}

	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public List<ConnType> getConnTypes() {
		return connTypes;
	}

	public void setConnTypes(List<ConnType> connTypes) {
		this.connTypes = connTypes;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
