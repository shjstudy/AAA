package com.invengo.xcrf.core.demo;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.RFIDProtocol;

import javax.swing.JDialog;

public abstract class UserConfig {

    protected IMessage startRoSpec;
    protected IMessage stopRoSpec;
    protected IMessage activeAntenna;
    protected RFIDProtocol protocol;
    protected String modelNo;

    protected String readerName;
    protected String newReaderName;
    protected String readerGroup;
    protected boolean enable;
    protected String connType;
    protected String connStr;
    protected String readerType;

    public IMessage getStartRoSpec() {
        return startRoSpec;
    }

    public void setStartRoSpec(IMessage startRoSpec) {
        this.startRoSpec = startRoSpec;
    }

    public IMessage getStopRoSpec() {
        return stopRoSpec;
    }

    public void setStopRoSpec(IMessage stopRoSpec) {
        this.stopRoSpec = stopRoSpec;
    }

    public IMessage getActiveAntenna() {
        return activeAntenna;
    }

    public void setActiveAntenna(IMessage activeAntenna) {
        this.activeAntenna = activeAntenna;
    }

    public RFIDProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(RFIDProtocol protocol) {
        this.protocol = protocol;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderGroup() {
        return readerGroup;
    }

    public void setReaderGroup(String readerGroup) {
        this.readerGroup = readerGroup;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getConnType() {
        return connType;
    }

    public void setConnType(String connType) {
        this.connType = connType;
    }

    public String getConnStr() {
        return connStr;
    }

    public void setConnStr(String connStr) {
        this.connStr = connStr;
    }

    public abstract void config(JDialog dialog);

    public abstract boolean saveConfig();

    public abstract boolean updateConfig();

    public abstract void removeConfig();

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	public abstract String getReaderType();
	public abstract int getAntennaIndex() ;

	public String getNewReaderName() {
		return newReaderName;
	}

	public void setNewReaderName(String newReaderName) {
		this.newReaderName = newReaderName;
	}
}
