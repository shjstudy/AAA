package com.invengo.xcrf.ui.panel;

public class ConnType {
	private String value;
	private int openPower;
	private int closePower;
	private int antenna1;
	private int antenna2;
	private int antenna3;
	private int antenna4;
	private int beep;
	private int led;
	private int gpio;
	private int rate;
	private int power;

	public int getOpenPower() {
		return openPower;
	}

	public int getClosePower() {
		return closePower;
	}

	public int getAntenna1() {
		return antenna1;
	}

	public int getAntenna2() {
		return antenna2;
	}

	public int getAntenna3() {
		return antenna3;
	}

	public int getAntenna4() {
		return antenna4;
	}

	public int getBeep() {
		return beep;
	}

	public int getLed() {
		return led;
	}

	public int getGpio() {
		return gpio;
	}

	public int getRate() {
		return rate;
	}

	public int getPower() {
		return power;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOpenPowerStr() {
		return print(openPower);
	}

	public void setOpenPower(int openPower) {
		this.openPower = openPower;
	}

	public String getClosePowerStr() {
		return print(closePower);
	}

	public void setClosePower(int closePower) {
		this.closePower = closePower;
	}

	public String getAntenna1Str() {
		return print(antenna1);
	}

	public void setAntenna1(int antenna1) {
		this.antenna1 = antenna1;
	}

	public String getAntenna2Str() {
		return print(antenna2);
	}

	public void setAntenna2(int antenna2) {
		this.antenna2 = antenna2;
	}

	public String getAntenna3Str() {
		return print(antenna3);
	}

	public void setAntenna3(int antenna3) {
		this.antenna3 = antenna3;
	}

	public String getAntenna4Str() {
		return print(antenna4);
	}

	public void setAntenna4(int antenna4) {
		this.antenna4 = antenna4;
	}

	public String getBeepStr() {
		return print(beep);
	}

	public void setBeep(int beep) {
		this.beep = beep;
	}

	public String getLedStr() {
		return print(led);
	}

	public void setLed(int led) {
		this.led = led;
	}

	public String getGpioStr() {
		return print(gpio);
	}

	public void setGpio(int gpio) {
		this.gpio = gpio;
	}

	public String getRateStr() {
		return print(rate);
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getPowerStr() {
		return print(power);
	}

	public void setPower(int power) {
		this.power = power;
	}

	private String print(int value) {
		String result;
		if (value == 1) {
			result = "¡Ì";
		} else if (value == -1) {
			result = "¡Á";
		} else {
			result = "£­";
		}
		return result;
	}

}
