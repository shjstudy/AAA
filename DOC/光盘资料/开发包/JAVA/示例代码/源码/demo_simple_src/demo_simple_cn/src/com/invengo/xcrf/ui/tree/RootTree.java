package com.invengo.xcrf.ui.tree;

import invengo.javaapi.core.RFIDProtocol;

import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.invengo.xcrf.core.Const;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig;
import com.invengo.xcrf.core.demo.UserConfigUtil;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;

public class RootTree extends JTree {

    private static final CheckNode rootNode = new CheckNode(Const.XCRF_ROOT_NODE_NAME);

    private static final CheckNode readerRootNode = new CheckNode("client");

    private static final CheckNode serverRootNode = new CheckNode("server");

    private static final RootTree dirTree = new RootTree(rootNode);

	private TreePath rootPath;

    private RootTree(CheckNode rootNode) {
        super(rootNode);
        rootNode.add(readerRootNode);
        rootNode.add(serverRootNode);
        setRootVisible(false);
        
        rootPath = new TreePath(rootNode);
        
        init();
        
        updateUI();
		expandTree(rootPath);
    }

    public static RootTree getTree() {
        return dirTree;
    }

    public static CheckNode getReaderRootNode() {
        return readerRootNode;
    }

    public static CheckNode getServerRootNode() {
        return serverRootNode;
    }

    private void init() {
        try {
            List<UserConfig> configs = UserConfigUtil.initRUserConfigs();
            for (UserConfig config : configs) {
                Demo demo = new Demo(config);
                //注册demo
                DemoRegistry.registryDemo(demo);
                //树上添加节点
                addNode(demo, readerRootNode);
            }

            List<UserConfig> configs1 = UserConfigUtil.initSUserConfigs();
            for (UserConfig config : configs1) {
                if (config instanceof UserConfig_IRP1) {
                    UserConfig_IRP1 irp1 = (UserConfig_IRP1) config;

                    CheckNode pNode = new CheckNode("Port:" + irp1.getServerPort());
                    pNode.setEnable(irp1.isEnable());
                    pNode.setProtocol(irp1.getProtocol());
                    pNode.setReaderType(irp1.getReaderType());
                    addNode(pNode, serverRootNode);
                }
            }

        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("init error");
        }
    }

    private void expandTree(TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() > 0) {
            for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
                CheckNode n = (CheckNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandTree(path);
            }
        }
        expandPath(parent);
    }

    public void addReaderNode(Demo demo) {
        addNode(demo, getReaderRootNode());
    }

    public void addServerNode(String nodeName, boolean enable) {
        CheckNode pNode = new CheckNode(nodeName);
        pNode.setProtocol(RFIDProtocol.IRP1);
        pNode.setEnable(enable);
        addNode(pNode, getServerRootNode());
//        TreePath path = new TreePath(rootNode);
        pNode.setSelected(true);
        expandTree(rootPath);
    }

    public void addNode(Demo demo, CheckNode root) {
        CheckNode node = addNodeByName(demo.getGroupName(), demo.getDemoName(), root);
        demo.setNode(node);
        TreePath path = rootPath.pathByAddingChild(readerRootNode).pathByAddingChild(node.getParent());
        expandTree(path);
    }

    public void addServerNode(Demo demo, CheckNode root) {
        CheckNode node = new CheckNode(demo.getDemoName(), false, true);

        demo.setNode(node);
        DemoRegistry.registryDemo(demo);
        root.add(node);
//        TreePath path = new TreePath(rootNode);
        expandTree(rootPath);
    }

    //添加子节点
    public CheckNode addNodeByName(String pNodeName, String cNodeName, CheckNode root) {
        CheckNode p = new CheckNode(pNodeName);
        CheckNode c = new CheckNode(cNodeName);
        return this.addNode(p, c, root);
    }

    //添加子节点
    public CheckNode addNode(CheckNode pNode, CheckNode cNode, CheckNode root) {
        Enumeration<CheckNode> parents = root.children();
        while (parents.hasMoreElements()) {
            CheckNode parent = parents.nextElement();
            if (parent.equals(pNode)) {
                Enumeration<CheckNode> childs = parent.children();
                while (childs.hasMoreElements()) {
                    CheckNode child = childs.nextElement();
                    if (child.equals(cNode)) {
                        //什么都不做
                        return null;
                    }
                }

                //添加到新节点
                parent.add(cNode);
                return cNode;
            }
        }

        pNode.add(cNode);
        root.add(pNode);
        return cNode;
    }

    //添加子节点
    public CheckNode addNode(CheckNode pNode, CheckNode root) {
        root.add(pNode);
        return pNode;
    }

    public void removeNode(Demo demo) {
        CheckNode node = (CheckNode) demo.getNode().getParent();
        node.remove(demo.getNode());
        if (node.getChildCount() == 0) {
            ((CheckNode) node.getParent()).remove(node);
        }
    }

    public void removeServerNode(CheckNode parent, CheckNode child) {
        parent.remove(child);
//        expandTree(new TreePath(rootNode));
        expandTree(rootPath);
    }

    private CheckNode getNodeFromPanrentByName(CheckNode parentNode, String nodeName) {
        CheckNode node = null;
        Enumeration<CheckNode> nodes = parentNode.children();
        while (nodes.hasMoreElements()) {
            node = nodes.nextElement();
            if (node.getName().equals(nodeName)) {
                return node;
            }
        }

        return null;
    }

}
