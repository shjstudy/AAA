package com.invengo.xcrf.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.ui.button.ToolBarAction;



/**
 * 组件工厂
 * 
 * @author zxl672
 * 
 */
public class WidgetFactory implements LanguageChangeListener {
	
	
	private static Class<?> PKG = WidgetFactory.class;

	/**
	 * 工厂内全部对象
	 */
	Map<String, JComponent> objectMap = new HashMap<String, JComponent>();

	/**
	 * 按钮
	 */
	Map<AbstractButton, String> abstractButtonList = new HashMap<AbstractButton, String>();

	/**
	 * 显示
	 */
	Map<JLabel, String> jlabelList = new HashMap<JLabel, String>();

	/**
	 * 对话框
	 */
	Map<JDialog, String> jdialoglList = new HashMap<JDialog, String>();

	/**
	 * 按钮
	 */
	Map<JButton, String> jbuttonList = new HashMap<JButton, String>();
	
	/**
	 * 标题线
	 */
	Map<TitledBorder, String> jtitledBorder = new HashMap<TitledBorder, String>();

	/**
	 * 表格
	 */
	Map<JTable, String> jtableList = new HashMap<JTable, String>();

	/**
	 * 选择框
	 */
	Map<JCheckBox, String> jcheckBoxList = new HashMap<JCheckBox, String>();

	/**
	 * 单例模式
	 */
	private static WidgetFactory _instance = new WidgetFactory();
	static {
		BaseMessages.addListener(_instance);
	}

	public static WidgetFactory getInstance() {
		return _instance;
	}

	public Object getBean(String name) {
		return objectMap.get(name);
	}

	/**
	 * 制造选择框 禁止在循环等不确定次数的情况下使用
	 * 
	 * @param name
	 * @param className
	 * @param textName
	 * @return
	 */
	public JCheckBox buildJCheckBox(String name, String className,
			String textName) {
		try {
			JCheckBox jcomponent = (JCheckBox) Class.forName(className)
					.newInstance();
			jcomponent.setText(BaseMessages.getString(textName));
			jcheckBoxList.put(jcomponent, textName);
			objectMap.put(name, jcomponent);
			return jcomponent;
		} catch (Exception e) {
			throw new RuntimeException("buildJCheckBox fail.", e);
		}
	}
	
	/**
	 * 制造选择框 禁止在循环等不确定次数的情况下使用
	 * 
	 * @param name
	 * @param className
	 * @param textName
	 * @return
	 */
	public JCheckBox buildJCheckBox(String textName) {
		try {
			JCheckBox jcomponent = (JCheckBox) Class.forName("javax.swing.JCheckBox")
					.newInstance();
			jcomponent.setText(BaseMessages.getString(textName));
			jcheckBoxList.put(jcomponent, textName);
//			objectMap.put(textName, jcomponent);
			return jcomponent;
		} catch (Exception e) {
			throw new RuntimeException("buildJCheckBox fail.", e);
		}
	}

	/**
	 * 制造按钮 禁止在循环等不确定次数的情况下使用
	 * 
	 * @param name
	 * @param className
	 * @param textName
	 * @return
	 */
	public AbstractButton buildOrdWidget(String name, String className,
			String textName) {
		try {
			AbstractButton jcomponent = (AbstractButton) Class.forName(
					className).newInstance();
			jcomponent.setText(BaseMessages.getString(textName));
			abstractButtonList.put(jcomponent, textName);
			objectMap.put(name, jcomponent);
			return jcomponent;
		} catch (Exception e) {
			throw new RuntimeException("buildOrdWidget fail.", e);
		}
	}
	
	
	/**
	 * 制造按钮 禁止在循环等不确定次数的情况下使用
	 * 
	 * @param name
	 * @param className
	 * @param textName
	 * @return
	 */
	public AbstractButton buildOrdWidget(String textName) {
		try {
			AbstractButton jcomponent = (AbstractButton) Class.forName(
					"javax.swing.JButton").newInstance();
			jcomponent.setText(BaseMessages.getString(textName));
			abstractButtonList.put(jcomponent, textName);
			return jcomponent;
		} catch (Exception e) {
			throw new RuntimeException("buildOrdWidget fail.", e);
		}
	}
	

	/**
	 * 制造显示 禁止在循环等不确定次数的情况下使用
	 * 
	 * @param name
	 * @param className
	 * @param textName
	 * @return
	 */
	private JLabel buildJLabel(String name, String className, String textName , String... param) {
		try {
			JLabel jcomponent = (JLabel) Class.forName(className).newInstance();
			if(param == null || param.length == 0)
			{
				jcomponent.setText(BaseMessages.getString(textName));
			}else
			{
				jcomponent.setText(BaseMessages.getString("",textName,param));
			}
			
			jlabelList.put(jcomponent, textName);
			objectMap.put(name, jcomponent);
			return jcomponent;
		} catch (Exception e) {
			throw new RuntimeException("buildJLabel fail.", e);
		}
	}
	
	public JLabel buildJLabel(String text , String... param ) {
		return buildJLabel(text,"javax.swing.JLabel",text,param);
	}
	

	/**
	 * 左边栏
	 * 
	 * @param name
	 * @param radioButton5
	 * @return
	 */
	public JToolBar buildLeftJToolBar(String name, JLabel radioButton5) {
		JToolBar toolBar5 = new JToolBar();

		toolBar5.setBackground(new Color(0xFF, 0xf8, 0xdc));
		toolBar5.setFloatable(false);// 设置JToolBar可否浮动.
		toolBar5.add(radioButton5);

		return toolBar5;
	}

	/**
	 * 制造图标按钮
	 * 
	 * @param name
	 * @param toolBar
	 * @param imagepath
	 * @param textName
	 * @return
	 */
	public JButton buildJButton(String name, JToolBar toolBar,
			String imagepath, String textName) {
		try {
			ToolBarAction tba_new = new ToolBarAction(name, new ImageIcon(this
					.getClass().getResource(imagepath)), null);
			JButton jb = toolBar.add(tba_new);// 将ToolBarAction组件加入JToolBar中.
			jbuttonList.put(jb, textName);
			jb.setToolTipText(BaseMessages.getString(textName));
			return jb;
		} catch (Exception e) {
			throw new RuntimeException("buildJButton fail.", e);
		}
	}
	
	/**
	 * 制造title border
	 * 
	 * @param name
	 * @param toolBar
	 * @param imagepath
	 * @param textName
	 * @return
	 */
	public TitledBorder buildTitleBorder(String textName) {
		try {
			TitledBorder titleBoder  = new TitledBorder(null, BaseMessages.getString(textName), TitledBorder.LEADING, TitledBorder.TOP,
	                null, null);
			jtitledBorder.put(titleBoder, textName);
			return titleBoder;
		} catch (Exception e) {
			throw new RuntimeException("buildJButton fail.", e);
		}
	}

	

	/**
	 * 制造表
	 * 
	 * @param name
	 * @param textName
	 * @return
	 */
	public JTable buildJTable(String name, String textName) {
		try {
			String textvalue = BaseMessages.getString(textName);
			String[] headers = textvalue.split("\\|");
			
			String[] realHeaders = new String[headers.length];
			
			TableColumnModel tableColumnModel = new DefaultTableColumnModel();
			
			for(int i=0 ; i < headers.length ; i++ )
			{
				int len = 75;
				String columnHeader = headers[i];
				int index = columnHeader.lastIndexOf("{");
				if(index != -1)
				{
					try{
						len = Integer.parseInt(columnHeader.substring(index+1,columnHeader.length()-1));
						columnHeader = columnHeader.substring(0, index);
					}catch(Exception e){
						//Parse error, no need to do anything
					}
				}
				realHeaders[i] = columnHeader;
				TableColumn column = new TableColumn(i,len);
				if(len == 0)
				{
					column.setMaxWidth(0);
					column.setPreferredWidth(0);
				}
				
				column.setHeaderValue(columnHeader);
				tableColumnModel.addColumn(column);
			}
			
			MyTableModel defaultTableModel = new MyTableModel(new String[][]{},
					realHeaders);
			
						
			JTable jtable = new JTable(defaultTableModel,tableColumnModel) {
				
				public Set<String> getEditIndexs()
				{
					return ((MyTableModel)getModel()).getEditColumnIndexs();
				}
				
				private static final long serialVersionUID = -9213900016778723918L;

				@Override
				public boolean isCellEditable(int arg0, int arg1) {
					for(String index : getEditIndexs())
					{
						if(index.equals(arg0+","+arg1)) return true;
					}
					return false;
				}
			};
			jtableList.put(jtable, textName);
			objectMap.put(name, jtable);
			return jtable;
		} catch (Exception e) {
			throw new RuntimeException("buildJTable fail.", e);
		}
	}

	public void addSetTitle(JDialog jdialog, String textName) {
		jdialoglList.put(jdialog, textName);
	}

	/**
	 * 更新语言
	 */
	@SuppressWarnings( { "static-access", "unchecked" })
	public void updateResource() {

		UIManager.put("OptionPane.okButtonText", BaseMessages.getInstance()
				.getString("button.ok"));
		UIManager.put("OptionPane.messageDialogTitle", BaseMessages.getString("OptionPane.messageDialogTitle"));

		// 按钮更新语言
		for (Iterator<?> iter = abstractButtonList.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry<AbstractButton, String> element = (Map.Entry<AbstractButton, String>) iter
					.next();
			AbstractButton jcomponent = element.getKey();
			String textName = element.getValue();
			jcomponent.setText(BaseMessages.getString(textName));
		}

		// label更新语言
		for (Iterator iter = jlabelList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<JLabel, String> element = (Map.Entry<JLabel, String>) iter
					.next();
			JLabel jcomponent = element.getKey();
			String textName = element.getValue();

			String oldText = jcomponent.getText();
			String[] oldTexts = oldText.split(":");
			if (oldTexts.length > 1) {
//				if (textName.equals("LeftPanel.porttext") || textName.equals("LeftPanel.conntext")
//						|| textName.equals("LeftPanel.DisConnecttext")
//						|| textName.equals("LeftPanel.connstatus")
//						||textName.equals("menu.out")) {
					jcomponent.setText(BaseMessages.getString(textName)+oldTexts[1]);
//				}else {
//					jcomponent.setText(BaseMessages.getString(textName) + ":"
//							+ oldTexts[1]);
//				}
			} else {
//				if (textName.equals("LeftPanel.porttext") || textName.equals("LeftPanel.conntext")
//						|| textName.equals("LeftPanel.DisConnecttext")
//						|| textName.equals("LeftPanel.connstatus")
//						||textName.equals("menu.out")) {
					jcomponent.setText(BaseMessages.getString(textName));
//				}else {
//					jcomponent.setText(BaseMessages.getString(textName) + ":");
//				}
			}

		}

		// 对话框按钮
		for (Iterator iter = jdialoglList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<JDialog, String> element = (Map.Entry<JDialog, String>) iter
					.next();
			JDialog jcomponent = element.getKey();
			String textName = element.getValue();
			jcomponent.setTitle(BaseMessages.getString(textName));
		}

		// 选择框
		for (Iterator iter = jcheckBoxList.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry<JCheckBox, String> element = (Map.Entry<JCheckBox, String>) iter
					.next();
			JCheckBox jcomponent = element.getKey();
			String textName = element.getValue();
			jcomponent.setText(BaseMessages.getString(textName));
		}

		//标题线
		
		for (Iterator iter = jtitledBorder.entrySet().iterator(); iter
			.hasNext();) {
			Map.Entry<TitledBorder, String> element = (Map.Entry<TitledBorder, String>) iter
					.next();
			TitledBorder jcomponent = element.getKey();
			String textName = element.getValue();
			jcomponent.setTitle(BaseMessages.getString(textName));
		}
		
		for (Iterator iter = jbuttonList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<JButton, String> element = (Map.Entry<JButton, String>) iter
					.next();
			JButton jcomponent = element.getKey();
			String textName = element.getValue();
			jcomponent.setToolTipText(BaseMessages.getString(textName));
		}

		// 表格更新语言
		for (Iterator iter = jtableList.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<JTable, String> element = (Map.Entry<JTable, String>) iter
					.next();
			JTable jcomponent = element.getKey();
			String textName = element.getValue();
			String textvalue = BaseMessages.getString(textName);
			String[] headers = textvalue.split("\\|");
			TableColumnModel columnModel = jcomponent.getColumnModel();
			int count = columnModel.getColumnCount();
			for (int i = 0; i < count; i++) {
				if (i < headers.length) {
					String columnHeader = headers[i];
					int index = columnHeader.lastIndexOf("{");
					if(index != -1)
					{
						try{
							columnHeader = columnHeader.substring(0, index);
						}catch(Exception e){
							//Parse error, no need to do anything
						}
					}
					columnModel.getColumn(i).setHeaderValue(columnHeader);
				}
			}
			jcomponent.updateUI();
		}

	}

	  public class MyTableModel extends DefaultTableModel {
		   private Set<String> editColumnIndexes = new HashSet<String>();
		   
		   public MyTableModel(Object[][] data, Object[] columnNames) {
		        super(data, columnNames);
		    }
		   
		   public Set<String> getEditColumnIndexs()
		   {
			   return editColumnIndexes;
		   }
		   
		   /**
		    * 设置可编辑的单元格
		    * @param indexes 可编辑列的索引的
		    */
		   public void addEditIndex(List<String> indexes)
		   {
			   editColumnIndexes.addAll(indexes);
		   }
		   
		   public void addEditIndex(String index)
		   {
			   editColumnIndexes.add(index);
		   }
		   
		   public void removeEditIndex(String index)
		   {
			   editColumnIndexes.remove(index);
		   }
		   
		   
	        @Override
			public Class getColumnClass(int c) {
	        	if(getRowCount() == 0 || getValueAt(0, c)==null){
	        		return Object.class;
	        	}
	            return getValueAt(0, c).getClass();
	        }
	   }
	  
	  
	  
}
