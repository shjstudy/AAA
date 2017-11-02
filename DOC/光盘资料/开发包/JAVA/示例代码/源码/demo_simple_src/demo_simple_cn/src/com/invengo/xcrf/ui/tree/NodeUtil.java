package com.invengo.xcrf.ui.tree;

import java.util.Enumeration;


public class NodeUtil {
	
	
	public static void changeParentNodesStatus(CheckNode node)
	{
//	    if(!node.isLeaf())
//	    	//若有子节点，则同步选中状态到所有子节点
//	    {
//	    	NodeUtil.changeChildNodesSelectStatus(node);
//	    }
//	    else{
	    	//若是叶子节点
	    	//case 1: 叶子节点取消选中时，若有父节点，则将其节点也取消选中
	    	if(node.isSelected() == false)
	    	{
	    		if(node.getParent()!= null && ((CheckNode)node.getParent()).isSelected() == true)
	        		((CheckNode)node.getParent()).setParentNodeSelected(false);
	    	}
	    	//case 2: 叶子节点选中时，若有父节点，则判断其余叶子节点是否全部选中，若全部选中，则更新父节点为选中
	    	else if(node.isSelected() == true)
	    	{
	    		if(node.getParent()!= null && ((CheckNode)node.getParent()).isSelected() == false)
	    		{
	    			CheckNode parentNode = (CheckNode)node.getParent();
	    			if(checkNodeSelectStatus(parentNode,true))
	    			{
	    				parentNode.setParentNodeSelected(true);
	    			}
	    		}
	    	}
	    		
//	    }
	}
	
	//同步节点选中状态到所有子节点
	public static void changeChildNodesSelectStatus(CheckNode node)
	{
		boolean parentSelectedStatus = node.isSelected();
		Enumeration<CheckNode> childNodes = node.children(); 
		while(childNodes.hasMoreElements())
		{
			childNodes.nextElement().setSelected(parentSelectedStatus);
		}
	}
	
	//检查所有子节点是否全部为制定状态
	public static boolean checkNodeSelectStatus(CheckNode node , boolean selected)
	{
		Enumeration<CheckNode> childNodes = node.children(); 
		while(childNodes.hasMoreElements())
		{
			if(childNodes.nextElement().isSelected() != selected)
			return false;
		}
		
		return true;
	}
	
	//获取节点全称：节点全称为 当前节点父节点名称+当前节点名称
	public static String getNodeFullName(CheckNode node)
	{
		CheckNode parent = (CheckNode)node.getParent();
		if(parent == null)
			return node.getName();
		else
//		    return "group:"+parent.getName()+"name:"+node.getName();//"group:"+groupName+"name:"+name;
			return node.getName();
	}
	
	
}
