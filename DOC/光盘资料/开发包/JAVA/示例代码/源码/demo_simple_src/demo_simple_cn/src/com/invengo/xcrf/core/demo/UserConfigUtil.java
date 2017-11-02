package com.invengo.xcrf.core.demo;

import invengo.javaapi.core.APIPath;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.PowerOff;
import invengo.javaapi.protocol.IRP1.ReadTag;
import invengo.javaapi.protocol.IRP1.SysConfig_500;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class UserConfigUtil {

	final static String fn = APIPath.folderName + "Sysit.xml";

	public static List<UserConfig> intiAllUserConfigs() throws Exception {
		if (!new File(fn).exists()) {
			create();
		}

		List<UserConfig> allConfigs = initRUserConfigs();
		allConfigs.addAll(initSUserConfigs());
		return allConfigs;
	}

	private static void create() {
		try {
			Document doc = new Document();
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			doc.setRootElement(new Element("Readers"));
			xmlOut.output(doc, new FileOutputStream(new File(fn)));
		} catch (Exception e) {
			// Util.logAndTriggerApiErr("Sysit.xml", "FF13", e.getMessage(),
			// LogType.Fatal);
		}
	}

	public static List<UserConfig> initSUserConfigs() throws Exception {
		List<UserConfig> configs = new ArrayList<UserConfig>();
		Document doc = new SAXBuilder().build(fn);
		List<Element> elements = XPath.newInstance("//Server").selectNodes(doc);

		// <Server Port="12800" Protocol="IRP1" Enable="false" ReaderType="800"
		// />

		for (Element serverE : elements) {
			try {
				String protocol = serverE.getAttributeValue("Protocol")
						.toString();
				String port = serverE.getAttributeValue("Port").toString();
				String enable = serverE.getAttributeValue("Enable").toString();
				String readerType = serverE.getAttributeValue("ReaderType")
						.toString();

				if (protocol.equals("IRP1")) {
					UserConfig_IRP1 config = new UserConfig_IRP1();

					config.serverPort = port;
					config.setEnable(Boolean.valueOf(enable));
					config.setProtocol(RFIDProtocol.IRP1);
					config.setReaderType(readerType);

					configs.add(config);
				}
			} catch (Exception e) {
				System.out.println("Init server error");
				continue;
			}
		}
		return configs;
	}

	public static List<UserConfig> initRUserConfigs() throws Exception {
		List<UserConfig> configs = new ArrayList<UserConfig>();

		Document doc = new SAXBuilder().build(fn);
		List<Element> elements = XPath.newInstance("//Reader").selectNodes(doc);

		for (Element readerE : elements) {
			// XML结构为 -Reader {
			// -Port
			// -UserConfig {
			// -ReaderType
			// -ReadTag {
			// -StopType
			// }
			// }
			// }
			try {
				String protocol = readerE.getChild("Port").getAttributeValue(
						"Protocol").toString();
				if (protocol.equals("IRP1")) {
					UserConfig_IRP1 config = new UserConfig_IRP1();

					config.readerName = readerE.getAttributeValue("Name");
					config.modelNo = readerE.getAttributeValue("ModelNumber");
					config.readerGroup = readerE.getAttributeValue("Group");
					config.enable = Boolean.parseBoolean(readerE
							.getAttributeValue("Enable"));

					Element portE = readerE.getChild("Port");
					config.connType = portE.getAttributeValue("Type");
					config.protocol = RFIDProtocol.valueOf(portE
							.getAttributeValue("Protocol"));
					config.connStr = portE.getText();

					Element userConfigE = readerE.getChild("UserConfig");
					if (userConfigE == null) {
						continue;
					}
					Element readerTypeE = userConfigE.getChild("ReaderType");
					config.readerType = readerTypeE.getText();

					// <ReadTag ReadMemoryBank="" Antenna="" IsLoop=""
					// TagNum="">
					Element readTagE = userConfigE.getChild("ReadTag");
					config.rmb = ReadMemoryBank.valueOf(readTagE
							.getAttributeValue("ReadMemoryBank"));
					config.antennaIndex = Integer.parseInt(readTagE
							.getAttributeValue("Antenna"));
					config.isLoop = Boolean.parseBoolean(readTagE
							.getAttributeValue("IsLoop"));
					config.tagNum = Integer.parseInt(readTagE
							.getAttributeValue("TagNum"));
					// <StopType>800</StopType>
					Element stopTypeE = readTagE.getChild("StopType");
					config.stopType = stopTypeE.getText();

					// 初始化stopRoSpec
					ReadTag rt = null;
					if (readTagE.getChild("ReadTime") != null
							&& readTagE.getChild("StopTime") != null
							&& Integer.parseInt(readTagE.getChild("ReadTime")
									.getText()) > 0
							&& Integer.parseInt(readTagE.getChild("StopTime")
									.getText()) > 0) {
						config.readTime = Integer.parseInt(readTagE.getChild(
								"ReadTime").getText());
						config.stopTime = Integer.parseInt(readTagE.getChild(
								"StopTime").getText());
						rt = new ReadTag(config.rmb, config.readTime,
								config.stopTime);
						if (config.stopType.equals("500"))
							config.stopRoSpec = new PowerOff();
						if (config.stopType.equals("800"))
							config.stopRoSpec = new PowerOff();
						config.stopRoSpec.setIsReturn(false);
					} else {
						rt = new ReadTag(config.rmb);
						if (config.stopType.equals("500"))
							config.stopRoSpec = new PowerOff();
						if (config.stopType.equals("800"))
							config.stopRoSpec = new PowerOff();
						config.stopRoSpec.setIsReturn(false);
					}
					if (readTagE.getChild("TidLen") != null) {
						rt.setTidLen((byte) (Integer.parseInt(readTagE
								.getChild("TidLen").getText())));
						config.setTidLen((byte) (Integer.parseInt(readTagE
								.getChild("TidLen").getText())));
					}
					if (readTagE.getChild("UserDataPtr") != null) {
						rt.setUserDataPtr_6C((byte) (Integer.parseInt(readTagE
								.getChild("UserDataPtr").getText())));
						config.setUserDataPtr((byte) (Integer.parseInt(readTagE
								.getChild("UserDataPtr").getText())));
					}
					// if (readTagE.getChild("UserDataLen_6C") != null) {
					// rt.setUserDataLen_6C((byte) (Integer.parseInt(readTagE
					// .getChild("UserDataLen_6C").getText())));
					// config.setUserDataLen((byte) (Integer.parseInt(readTagE
					// .getChild("UserDataLen_6C").getText())));
					// }
					if (readTagE.getChild("UserDataPtr_6C") != null) {
						rt.setUserDataPtr_6C((byte) (Integer.parseInt(readTagE
								.getChild("UserDataPtr_6C").getText())));
						config.setUserDataPtr((byte) (Integer.parseInt(readTagE
								.getChild("UserDataPtr_6C").getText())));
					}
					if (readTagE.getChild("UserDataLen_6C") != null) {
						rt.setUserDataLen_6C((byte) (Integer.parseInt(readTagE
								.getChild("UserDataLen_6C").getText())));
						config.setUserDataLen((byte) (Integer.parseInt(readTagE
								.getChild("UserDataLen_6C").getText())));
					}
					if (readTagE.getChild("UserDataPtr_6B") != null) {
						rt.setUserDataPtr_6B((byte) (Integer.parseInt(readTagE
								.getChild("UserDataPtr_6B").getText())));
						config.setUserdataPtr_6B((byte) (Integer
								.parseInt(readTagE.getChild("UserDataPtr_6B")
										.getText())));
					}
					if (readTagE.getChild("UserDataLen_6B") != null) {
						rt.setUserDataLen_6B((byte) (Integer.parseInt(readTagE
								.getChild("UserDataLen_6B").getText())));
						config.setUserDataLen_6B((byte) (Integer
								.parseInt(readTagE.getChild("UserDataLen_6B")
										.getText())));
					}
					if (readTagE.getChild("ReservedLen") != null) {
						rt.setReservedLen((byte) (Integer.parseInt(readTagE
								.getChild("ReservedLen").getText())));
						config.setReservedLen((byte) (Integer.parseInt(readTagE
								.getChild("ReservedLen").getText())));
					}
					if (readTagE.getChild("AccessPwd") != null) {
						rt.setAccessPwd(Util
								.convertHexStringToByteArray(readTagE.getChild(
										"AccessPwd").getText()));

						config.setAccessPwd(Util
								.convertHexStringToByteArray(readTagE.getChild(
										"AccessPwd").getText()));
					}
					if (readTagE.getChild("ReadTimes_6C") != null) {
						rt.setReadTimes_6C((byte) (Integer.parseInt(readTagE
								.getChild("ReadTimes_6C").getText())));
						config.setReadTimes_6C((byte) (Integer
								.parseInt(readTagE.getChild("ReadTimes_6C")
										.getText())));
					}
					if (readTagE.getChild("ReadTimes_6B") != null) {
						rt.setReadTimes_6B((byte) (Integer.parseInt(readTagE
								.getChild("ReadTimes_6B").getText())));
						config.setReadTimes_6B((byte) (Integer
								.parseInt(readTagE.getChild("ReadTimes_6B")
										.getText())));
					}

					// 初始化activeAntenna
					if (config.readerType.equals("500")) {
						switch (config.antennaIndex) {
						case 0:
							rt.setAntenna((byte) 0x01);
							break;
						case 1:
							rt.setAntenna((byte) 0x02);
							break;
						case 2:
							rt.setAntenna((byte) 0x00);
							break;
						}
						config.activeAntenna = new SysConfig_500((byte) 0x02,
								(byte) 0x01,
								new byte[] { (byte) (config.antennaIndex + 1) });
					} else if (config.readerType.equals("800")) {
						byte[] d = new byte[1];
						config.antennaIndex = Integer.parseInt(readTagE
								.getAttributeValue("Antenna"));
						switch (config.antennaIndex) {
						case 0:
							rt.setAntenna((byte) 0x01);
							d[0] = 0x01;
							break;
						case 1:
							rt.setAntenna((byte) 0x02);
							d[0] = 0x01;
							break;
						case 2:
							rt.setAntenna((byte) 0x03);
							d[0] = 0x01;
							break;
						case 3:
							rt.setAntenna((byte) 0x04);
							d[0] = 0x01;
							break;
						case 4:
							d[0] = 0x02;
							rt.setAntenna((byte) 0x00);
							break;
						case 5:
							d[0] = 0x03;
							rt.setAntenna((byte) 0x00);
							break;
						case 6:
							d[0] = 0x04;
							rt.setAntenna((byte) 0x00);
							break;
						default:
							rt.setAntenna((byte) config.antennaIndex);

						}
						if (config.getAntennaIndex() < 0x80)
							config.activeAntenna = new SysConfig_800(
									(byte) 0x02, d);
					}

					// 设置循环 q值
					rt.setLoop(config.isLoop);
					// config.tagNum =
					// Integer.parseInt(readTagE.getAttributeValue("TagNum"));

					rt.setQ((byte) (Math.log((double) config.tagNum + 1) / Math
							.log(2)));
					// rt.executeFlag = 0;
					config.startRoSpec = rt;

					// config.isLoop = rt.isLoop();

					configs.add(config);
				}
			} catch (Exception e) {
				System.out.println("Init reader error!");
				continue;
			}
		}
		//
		// Collections.sort(configs, new Comparator<UserConfig>() {
		// @Override
		// public int compare(UserConfig o1, UserConfig o2) {
		// String group1 = o1.getReaderGroup();
		// String group2 = o2.getReaderGroup();
		// if (group1.equals(group2)) {
		// String readerName1 = o1.getReaderName();
		// String readerName2 = o2.getReaderName();
		// return readerName1.compareTo(readerName2);
		// } else
		// return group1.compareTo(group2);
		// }
		// });

		return configs;
	}

	public static boolean updateServer(String port, boolean enable) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fn);
			Element element = (Element) XPath.newInstance(
					"//Server[@Port='" + port + "']").selectSingleNode(doc);
			element.setAttribute("Enable", enable + "");
			xmlOut.output(doc, new FileOutputStream(new File(fn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateServer(String port, boolean enable,
			String readerType) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fn);
			Element element = (Element) XPath.newInstance(
					"//Server[@Port='" + port + "']").selectSingleNode(doc);
			element.setAttribute("Enable", enable + "");
			element.setAttribute("ReaderType", readerType);
			xmlOut.output(doc, new FileOutputStream(new File(fn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean removeServer(String port) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fn);
			Element element = (Element) XPath.newInstance(
					"//Server[@Port='" + port + "']").selectSingleNode(doc);
			doc.getRootElement().removeContent(element);
			xmlOut.output(doc, new FileOutputStream(new File(fn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 将IRP1协议的reader设定信息存储到xml文件
	public static boolean saveUserConfigIRP1ToXmlFile(UserConfig_IRP1 config) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fn);
			if (config.connType.equals("TCPIP_Server")) {
				// <Server Port="12800" Protocol="IRP1" Enable="true"
				// ReaderType="800" />
				Element element = (Element) XPath.newInstance(
						"//Server[@Port='" + config.serverPort + "']")
						.selectSingleNode(doc);
				if (element != null) {
					return false;
				}

				Element rootE = doc.getRootElement();
				Element serverE = new Element("Server");
				serverE.setAttribute("Port", config.serverPort);
				serverE.setAttribute("Protocol", config.protocol.toString());
				serverE.setAttribute("Enable", String.valueOf(config.enable));
				serverE.setAttribute("ReaderType", "500");

				rootE.addContent(serverE);// root

				// 写入文件
				xmlOut.output(doc, new FileOutputStream(new File(fn)));

			} else {
				Element rootE = doc.getRootElement();

				Element readerE = new Element("Reader");
				readerE.setAttribute("Name", config.getReaderName());
				readerE.setAttribute("Group", config.getReaderGroup());
				readerE.setAttribute("Enable", String.valueOf(config.enable));

				if (config.getModelNo() != null) {
					readerE.setAttribute("ModelNumber", String
							.valueOf(config.modelNo));
				}

				Element portE = new Element("Port");
				portE.setAttribute("Type", config.connType);
				portE.setAttribute("Protocol", config.protocol.toString());
				portE.setText(config.connStr);
				readerE.addContent(portE);// Reader

				Element userConfigE = new Element("UserConfig");
				userConfigE
						.setAttribute("Protocol", config.protocol.toString());

				Element readerTypeE = new Element("ReaderType");
				readerTypeE.setText(config.readerType);
				userConfigE.addContent(readerTypeE);// UserConfig

				Element readerTagE = new Element("ReadTag");
				readerTagE
						.setAttribute("ReadMemoryBank", config.rmb.toString());
				readerTagE.setAttribute("Antenna", String
						.valueOf(config.antennaIndex));
				readerTagE
						.setAttribute("IsLoop", String.valueOf(config.isLoop));
				readerTagE
						.setAttribute("TagNum", String.valueOf(config.tagNum));

				Element stopTypeE = new Element("StopType");
				stopTypeE.setText(config.stopType);
				readerTagE.addContent(stopTypeE);// ReadTag
				if (config.readTime > 0 && config.stopTime > 0) {
					Element readTimeE = new Element("ReadTime");
					readTimeE.setText(String.valueOf(config.readTime));
					readerTagE.addContent(readTimeE);// ReadTag

					Element stopTimeE = new Element("StopTime");
					stopTimeE.setText(String.valueOf(config.stopTime));
					readerTagE.addContent(stopTimeE);// ReadTag
				}
				if (config.rmb
						.equals(ReadTag.ReadMemoryBank.EPC_TID_UserData_6C_2)) {
					if (config.tidLen > 0 || config.userDataLen > 0) {
						Element tidLenE = new Element("TidLen");
						tidLenE.setText(String.valueOf(config.tidLen));
						readerTagE.addContent(tidLenE);// ReadTag

						Element userDataPtrE = new Element("UserDataPtr");
						userDataPtrE
								.setText(String.valueOf(config.userDataPtr));
						readerTagE.addContent(userDataPtrE);// ReadTag

						Element userDataLenE = new Element("UserDataLen");
						userDataLenE
								.setText(String.valueOf(config.userDataLen));
						readerTagE.addContent(userDataLenE);// ReadTag
					}
				}

				userConfigE.addContent(readerTagE);// UserConfig

				readerE.addContent(userConfigE);// Reader

				rootE.addContent(readerE);// root

				// 写入文件
				xmlOut.output(doc, new FileOutputStream(new File(fn)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 从xml文件中移除reader设定信息
	public static void removeUserConfigIRP1ToXmlFile(UserConfig_IRP1 config) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fn);
			if (config.connType.equals("TCPIP_Server")) {

			} else {
				Element element = (Element) XPath.newInstance(
						"//Reader[@Name='" + config.readerName + "']")
						.selectSingleNode(doc);
				if (element != null) {
					doc.getRootElement().removeContent(element);
					xmlOut.output(doc, new FileOutputStream(fn));
				} else {
					// Util.logAndTriggerApiErr("Sysit.xml", "FF16", "",
					// LogType.Debug);
				}
			}

		} catch (Exception e) {
		}
	}

	public static boolean updateUserConfigIRP1ToXmlFile(UserConfig_IRP1 config) {
		try {
			if (config.connType.equals("TCPIP_Server")) {
				updateServer(config.serverPort, config.enable,
						config.readerType);
			} else {

				XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
				Document doc = new SAXBuilder().build(fn);

				Element element = (Element) XPath.newInstance(
						"//Reader[@Name='" + config.readerName + "']")
						.selectSingleNode(doc);

				element.setAttribute("Group", config.getReaderGroup());
				element.setAttribute("Enable", String.valueOf(config.enable));
				/* xusheng 2012.5.31 */
				if (config.getNewReaderName() != null)
					element.setAttribute("Name", config.getNewReaderName());
				/* xusheng 2012.5.31 */
				if (config.modelNo != null) {
					element.setAttribute("ModelNumber", config.modelNo);
				} else {
					element.removeAttribute("ModelNumber");
				}

				Element portE = element.getChild("Port");
				portE.setAttribute("Type", config.connType);
				portE.setAttribute("Protocol", config.protocol.toString());
				portE.setText(config.connStr);

				Element userConfigE = element.getChild("UserConfig");
				userConfigE
						.setAttribute("Protocol", config.protocol.toString());

				Element readerTypeE = userConfigE.getChild("ReaderType");
				readerTypeE.setText(config.readerType);

				Element readerTagE = userConfigE.getChild("ReadTag");
				readerTagE
						.setAttribute("ReadMemoryBank", config.rmb.toString());
				readerTagE.setAttribute("Antenna", String
						.valueOf(config.antennaIndex));
				readerTagE
						.setAttribute("IsLoop", String.valueOf(config.isLoop));
				readerTagE
						.setAttribute("TagNum", String.valueOf(config.tagNum));

				Element stopTypeE = readerTagE.getChild("StopType");
				stopTypeE.setText(config.stopType);

				/**
				 * xusgebg 2012.7.17
				 */

				// tidlen
				Element tidLenE = readerTagE.getChild("TidLen");
				if (tidLenE == null) {
					tidLenE = new Element("TidLen");
					readerTagE.addContent(tidLenE);
				}
				tidLenE.setText(config.getTidLen() + "");

				// UserDataPtr_6C
				Element userDataPtr_6cE = readerTagE.getChild("UserDataPtr_6C");
				if (userDataPtr_6cE == null) {
					userDataPtr_6cE = new Element("UserDataPtr_6C");
					readerTagE.addContent(userDataPtr_6cE);
				}
				userDataPtr_6cE.setText(config.getUserDataPtr() + "");

				// UserDataLen_6C
				Element userDataLen_6cE = readerTagE.getChild("UserDataLen_6C");
				if (userDataLen_6cE == null) {
					userDataLen_6cE = new Element("UserDataLen_6C");
					readerTagE.addContent(userDataLen_6cE);
				}
				userDataLen_6cE.setText(config.getUserDataLen() + "");

				// UserdataPtr_6B
				Element userDataPtr_6BE = readerTagE.getChild("UserDataPtr_6B");
				if (userDataPtr_6BE == null) {
					userDataPtr_6BE = new Element("UserDataPtr_6B");
					readerTagE.addContent(userDataPtr_6BE);
				}
				userDataPtr_6BE.setText(config.getUserdataPtr_6B() + "");

				// UserDataLen_6B
				Element userDataLen_6BE = readerTagE.getChild("UserDataLen_6B");
				if (userDataLen_6BE == null) {
					userDataLen_6BE = new Element("UserDataLen_6B");
					readerTagE.addContent(userDataLen_6BE);
				}
				userDataLen_6BE.setText((config.getUserDataLen_6B() + ""));

				// ReservedLen
				Element reservedLenE = readerTagE.getChild("ReservedLen");
				if (reservedLenE == null) {
					reservedLenE = new Element("ReservedLen");
					readerTagE.addContent(reservedLenE);
				}
				reservedLenE.setText(config.getReservedLen() + "");

				// AccessPwd
				Element accessPwdE = readerTagE.getChild("AccessPwd");
				if (accessPwdE == null) {
					accessPwdE = new Element("AccessPwd");
					readerTagE.addContent(accessPwdE);
				}
				accessPwdE.setText(Util.convertByteArrayToHexWordString(config
						.getAccessPwd()));

				// ReadTimes_6C
				Element ReadTimes_6C = readerTagE.getChild("ReadTimes_6C");
				if (ReadTimes_6C == null) {
					ReadTimes_6C = new Element("ReadTimes_6C");
					readerTagE.addContent(ReadTimes_6C);
				}
				ReadTimes_6C.setText(config.getReadTimes_6C() + "");

				// ReadTimes_6B
				Element ReadTimes_6B = readerTagE.getChild("ReadTimes_6B");
				if (ReadTimes_6B == null) {
					ReadTimes_6B = new Element("ReadTimes_6B");
					readerTagE.addContent(ReadTimes_6B);
				}
				ReadTimes_6B.setText(config.getReadTimes_6B() + "");

				Element readTimeE = readerTagE.getChild("ReadTime");
				if (readTimeE == null) {
					readTimeE = new Element("ReadTime");
					readerTagE.addContent(readTimeE);
				}
				readTimeE.setText(String.valueOf(config.readTime));

				Element stopTimeE = readerTagE.getChild("StopTime");
				if (stopTimeE == null) {
					stopTimeE = new Element("StopTime");
					readerTagE.addContent(stopTimeE);
				}
				stopTimeE.setText(String.valueOf(config.stopTime));
				// if (config.rmb
				// .equals(ReadTag.ReadMemoryBank.EPC_TID_UserData_6C_2)) {
				// if (config.tidLen > 0 || config.userDataLen > 0) {
				// Element tidLenE = readerTagE.getChild("TidLen");
				// if (tidLenE == null) {
				// tidLenE = new Element("TidLen");
				// readerTagE.addContent(tidLenE);
				// }
				// tidLenE.setText(String.valueOf(config.tidLen));
				//
				// Element userDataPtrE = readerTagE
				// .getChild("UserDataPtr");
				// if (userDataPtrE == null) {
				// userDataPtrE = new Element("UserDataPtr");
				// readerTagE.addContent(userDataPtrE);
				// }
				// userDataPtrE
				// .setText(String.valueOf(config.userDataPtr));
				//
				// Element userDataLenE = readerTagE
				// .getChild("UserDataLen");
				// if (userDataLenE == null) {
				// userDataLenE = new Element("UserDataLen");
				// readerTagE.addContent(userDataLenE);
				// }
				// userDataLenE
				// .setText(String.valueOf(config.userDataLen));
				// }
				// }

				// 写入文件
				xmlOut.output(doc, new FileOutputStream(new File(fn)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
