package com.invengo.xcrf.ui.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.panel.TestPanel;

public class NavigatorPanel extends JPanel{
	
	private JScrollPane selectedjsPane;
	private NavigatorPanel navigator;
	private JPanel defaultPanel;
	
	private Map<JLabel,JPanel> buttonMapings = new HashMap<JLabel,JPanel>();
	
	private boolean isStartWithShift = false;
	
	boolean init = false;
	
	public NavigatorPanel(boolean isStartWithShift)
	{
		this.isStartWithShift = isStartWithShift;
		navigator = this;
		setLayout(new BorderLayout());
		
		
//		JToolBar vBox = new JToolBar(SwingConstants.VERTICAL);
//		vBox.setFloatable(false);
		setBackground(Color.white);
		JPanel nava = new JPanel();
		nava.setLayout(new BorderLayout());
		nava.setPreferredSize(new Dimension(144,580));
		nava.setMaximumSize(new Dimension(144,580));
		nava.setBorder(new LineBorder(Color.yellow,0));
		
		
		Box navaBox = Box.createVerticalBox();
		
		List<Group> groups = getGroups();
		
		for(Group group : groups)
		{
			try {
				final JLabel jb = new JLabel(group.getText());
				jb.setName(group.getName());
				jb.setPreferredSize(new Dimension(144,20));
				jb.setMinimumSize(new Dimension(144,20));
				jb.setOpaque(true);
				jb.setHorizontalAlignment(SwingConstants.CENTER);
				jb.setBackground(Color.GRAY);
				
				JPanel jbp = new JPanel();
				jbp.setBackground(Color.white);
				jbp.setLayout(new BorderLayout());
				jbp.setPreferredSize(new Dimension(144,20));
				jbp.setMaximumSize(new Dimension(144,20));
				jbp.add(jb,BorderLayout.CENTER);
				
				
				JPanel j = (JPanel)Class.forName(group.getGroupClass()).newInstance();
				j.setVisible(false);
				buttonMapings.put(jb, j);
				
				navaBox.add(jbp);
				
				if(defaultPanel==null){
					defaultPanel=j;
				}
				
				JPanel jt = new JPanel();
				Box jtBox = Box.createVerticalBox();
				
//				jt.setBorder(new LineBorder(Color.white,0));
//				jt.setBackground(Color.white);
				
				for(Item it : group.getItems())
				{
					ImageIcon icon = new ImageIcon(MainFrame.class
							.getResource("image/readUserdata.png"));
					JLabel jl1 = new JLabel(icon);
					
					final JPanel j1 = (JPanel)Class.forName(it.getClazz()).newInstance();
					if(j1 instanceof TestPanel)
						((TestPanel)j1).setLabel(it.getText());
					j1.setVisible(false);
					buttonMapings.put(jl1, j1);
//					jtBox.add(jl1);
					
					JLabel jl11 = new JLabel(it.getText());
					jl11.setHorizontalAlignment(SwingConstants.CENTER);
					
					
					JPanel jp11 = new JPanel();
//					jp11.setBackground(Color.white);
					jp11.setLayout(new BorderLayout());
					jp11.add(jl1,BorderLayout.CENTER);
					jp11.add(jl11,BorderLayout.SOUTH);
					jp11.setName(it.getName());
					
					
					jtBox.add(jp11);
					
				}
				
				jtBox.setName("ReaderConfigForm");
				Demo currentDemo = DemoRegistry.getCurrentDemo();
				if( currentDemo != null )
				{
					String protocol = currentDemo.getProtocl().toString();
					String modelNo = currentDemo.getConfig().getModelNo();
					String readerType = currentDemo.getConfig().getReaderType();
					Common.readerCapabilitiesCheck(jtBox, protocol, modelNo, readerType);
				}
				
				jt.add(jtBox);
				
				
				final JScrollPane jsPane =  new JScrollPane(jt,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//				jsPane.setPreferredSize(new Dimension(144,356));
				jsPane.setBorder(new LineBorder(Color.white,0));
				if(selectedjsPane==null)
				{
					jsPane.setVisible(true);
					selectedjsPane = jsPane;
				}else
				{
					jsPane.setVisible(false);
				}
				navaBox.add(jsPane);
				
				
				jb.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						if(selectedjsPane != jsPane)
						{
							if(selectedjsPane!=null)
								selectedjsPane.setVisible(false);
								jsPane.setVisible(true);
								selectedjsPane = jsPane;
								navigator.updateUI();
						}
					}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
					
				});
			
			} catch (Exception e1) {
				e1.printStackTrace();
				continue;
			} 
		}
		nava.add(navaBox);
		
		add(nava,BorderLayout.CENTER);
		
	}
	
	public JPanel getDefaultPanel(){
		if(this.defaultPanel==null)
		{
			return new JPanel();
		}
		return this.defaultPanel;
	}
	
	private List<Group> getGroups(){
		List<Group> groups = new ArrayList<Group>();
		String fn = "./navigator.xml";
		try {
			Document doc = new SAXBuilder().build(fn);
			List<Element> groupsE = XPath.newInstance("//group").selectNodes(doc);
			for(Element groupE : groupsE)
			{
				try {
					if("Y".equals(groupE.getAttributeValue("isShift")))
					{
						//若不是点击shift启动程序，则不加载此项
						if(!checkStartWithShift())
						{
							continue;
						}
					}
					
					Group group = new Group();
					group.setName(groupE.getAttributeValue("name"));
					group.setText(BaseMessages.getString(groupE.getAttributeValue("text")));
					group.setGroupClass(groupE.getAttributeValue("class"));
					
					List<Element> itemsE = groupE.getChildren();
					Item[] items = new Item[itemsE.size()];
					for(int i=0;i<itemsE.size();i++)
					{
						Item item = new Item();
						item.setName(itemsE.get(i).getAttributeValue("name"));
						item.setText(BaseMessages.getString(itemsE.get(i).getAttributeValue("text")));
						item.setClazz(itemsE.get(i).getAttributeValue("class"));
						items[i]= item;
					}
					group.setItems(items);
					groups.add(group);
				
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	
	private boolean checkStartWithShift()
	{
		return this.isStartWithShift;
	}

	public void setIsStartWithShift(boolean isShift)
	{
		this.isStartWithShift = isShift;
	}


	public Map<JLabel, JPanel> getButtonMapings() {
		return buttonMapings;
	}

	public void setButtonMapings(Map<JLabel, JPanel> buttonMapings) {
		this.buttonMapings = buttonMapings;
	}
	
	
	
	

}
