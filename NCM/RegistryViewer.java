package com.jinvoke.demos.regview;

import static com.jinvoke.win32.WinConstants.HKEY_CLASSES_ROOT;
import static com.jinvoke.win32.WinConstants.HKEY_CURRENT_CONFIG;
import static com.jinvoke.win32.WinConstants.HKEY_CURRENT_USER;
import static com.jinvoke.win32.WinConstants.HKEY_DYN_DATA;
import static com.jinvoke.win32.WinConstants.HKEY_LOCAL_MACHINE;
import static com.jinvoke.win32.WinConstants.HKEY_USERS;
import static com.jinvoke.win32.WinConstants.KEY_ENUMERATE_SUB_KEYS;
import static com.jinvoke.win32.WinConstants.KEY_QUERY_VALUE;
import static com.jinvoke.win32.WinConstants.REG_DWORD;
import static com.jinvoke.win32.WinConstants.REG_EXPAND_SZ;
import static com.jinvoke.win32.WinConstants.REG_MULTI_SZ;
import static com.jinvoke.win32.WinConstants.REG_SZ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jinvoke.win32.Advapi32;
import com.jinvoke.win32.structs.FileTime;

public class RegistryViewer extends JPanel {
	
	public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });	
    }
	
	/**
     * Create the GUI and show it.  For thread safety, this method 
     * should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
    	JFrame frame = new JFrame("Windows Registry Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
    	RegistryViewer viewer = new RegistryViewer();
        frame.setContentPane(viewer);

        // Display the window.
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public RegistryViewer() {
        JTable keyValueTable = createKeyValueJTable();

        JScrollPane keyValueView = new JScrollPane(keyValueTable);
        RegistryTree regTree = new RegistryTree(keyValueTable);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(regTree);
        splitPane.setRightComponent(keyValueView);

        // Tweak the UI a bit 
        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(210); 
        splitPane.setPreferredSize(new Dimension(750, 500));
        splitPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        ((BasicSplitPaneUI) splitPane.getUI()).getDivider().setBorder(null);
        
        Dimension minimumSize = new Dimension(200, 100);
        keyValueView.setMinimumSize(minimumSize);
        regTree.setMinimumSize(minimumSize);
        keyValueView.getViewport().setBackground(Color.WHITE);
        
        // Add the split pane to this panel.
    	setLayout(new BorderLayout());
    	add(splitPane);
        setOpaque(true);
    }

	private JTable createKeyValueJTable() {
		KeyValueTableModel kvmodel = new KeyValueTableModel();
        JTable keyValueTable = new JTable(kvmodel);
		keyValueTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		keyValueTable.setShowHorizontalLines(false);
		keyValueTable.setShowVerticalLines(false);
		return keyValueTable;
	}
}


class RegistryTree extends JScrollPane {
	
	JTree tree;
	Map<String, Integer> hKeyMap;
	
	final String unexpanded = "unexpanded";

	public RegistryTree(final JTable table) {
		TreeNode rootNode = createNodes();
		tree = new JTree(rootNode);
		tree.setRowHeight(20);
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse) {
				TreePath treePath = tree.getSelectionPath();
				if (treePath != null) {
					String path = treePath.toString();
					if ((path != null) && !(path.equals("[Computer]"))) {
						System.out.println(tree.getSelectionPath());
						enumerateSection(tree.getSelectionPath(), tree);
						String [][] keyValueArray = enumerateValues(tse.getPath().toString(), tree);
						((KeyValueTableModel) table.getModel()).setData(keyValueArray);
					}
				}
			}});
		
		tree.addTreeWillExpandListener(new TreeWillExpandListener(){
			public void treeWillExpand(TreeExpansionEvent tee) throws ExpandVetoException {
				String path = tee.getPath().toString();
				if (!path.equals("[Computer]")) {
					boolean result = enumerateSection(tee.getPath(), tree);
					if (result == false)
						throw new ExpandVetoException(tee);

				}
			}

			public void treeWillCollapse(TreeExpansionEvent tee) throws ExpandVetoException {
				if ((tee.getPath().toString().equals("[Computer]")))
					throw new ExpandVetoException(tee);
			}});
		
		setViewportView(tree);

		hKeyMap = new HashMap<String, Integer>();
		hKeyMap.put("HKEY_CLASSES_ROOT", HKEY_CLASSES_ROOT);
		hKeyMap.put("HKEY_CURRENT_USER", HKEY_CURRENT_USER);
		hKeyMap.put("HKEY_LOCAL_MACHINE", HKEY_LOCAL_MACHINE);
		hKeyMap.put("HKEY_USERS", HKEY_USERS);
		hKeyMap.put("HKEY_DYN_DATA", HKEY_DYN_DATA);
		hKeyMap.put("HKEY_CURRENT_CONFIG", HKEY_CURRENT_CONFIG);
	}

	private TreeNode createNodes() {
		DefaultMutableTreeNode root;
		DefaultMutableTreeNode parent;
		DefaultMutableTreeNode childUnExpanded;

		root = new DefaultMutableTreeNode("Computer");
		
		parent = new DefaultMutableTreeNode("HKEY_CLASSES_ROOT");
		//root.add(parent);
		
		childUnExpanded = new DefaultMutableTreeNode(unexpanded);
		parent.add(childUnExpanded);
		root.add(parent);
		
		parent = new DefaultMutableTreeNode("HKEY_CURRENT_USER");
		childUnExpanded = new DefaultMutableTreeNode(unexpanded);
		parent.add(childUnExpanded);
		root.add(parent);
		
		parent = new DefaultMutableTreeNode("HKEY_LOCAL_MACHINE");
		childUnExpanded = new DefaultMutableTreeNode(unexpanded);
		parent.add(childUnExpanded);
		root.add(parent);
		
		parent = new DefaultMutableTreeNode("HKEY_USERS");
		childUnExpanded = new DefaultMutableTreeNode(unexpanded);
		parent.add(childUnExpanded);
		root.add(parent);
		
		parent = new DefaultMutableTreeNode("HKEY_CURRENT_CONFIG");
		childUnExpanded = new DefaultMutableTreeNode(unexpanded);
		parent.add(childUnExpanded);
		root.add(parent);

		return root;
	}

	private String getClassKeyFromTreePath(String treePathString) {
		String classKeyString = treePathString.replace("[Computer, ", "");
		int indexOfComma = classKeyString.indexOf(",");
		if (indexOfComma > 0)
			classKeyString = classKeyString.substring(0, indexOfComma);
		else
			classKeyString = classKeyString.replace("]", "");

		return classKeyString;
	}

	private String getSectionKeyFromTreePath(String treePathString, String classKeyString) {
		String sectionKey = treePathString.replace("[Computer, ", "");
		sectionKey = sectionKey.replace(classKeyString, "");
		sectionKey = sectionKey.replace("]", "");

		if (sectionKey.startsWith(",") == true)
			sectionKey = sectionKey.replaceFirst(", ", "");
		if (sectionKey.indexOf(",") > 0)
			sectionKey = sectionKey.replace(", ", "\\");

		return sectionKey;
	}

	private boolean enumerateSection(TreePath treePath, JTree tree) {
		int[] hKey = { 0 };
		int index = 0;

		if ((treePath.getPathCount() - 1) > 0) {
			DefaultMutableTreeNode tn = (DefaultMutableTreeNode) treePath.getLastPathComponent();
			int classKey = 0;
			String sectionKey = "";
			if (tn.getChildCount() > 0) {
				if (tn.getChildAt(0).toString() == unexpanded) { // First Child
					MutableTreeNode mutTreeNode = (MutableTreeNode) tn .getChildAt(0);
					((DefaultTreeModel) tree.getModel()).removeNodeFromParent(mutTreeNode);

					String classKeyString = getClassKeyFromTreePath(treePath.toString());

					classKey = (Integer) hKeyMap.get(classKeyString);

					sectionKey = getSectionKeyFromTreePath(treePath.toString(), classKeyString);

					int result = Advapi32.RegOpenKeyEx(classKey, sectionKey, 0, KEY_ENUMERATE_SUB_KEYS, hKey);
					while (result == 0) {
						StringBuffer valueName = new StringBuffer(256);
						StringBuffer className = new StringBuffer(256);
						FileTime time = new FileTime();
						int[] lpcName = new int[1];
						lpcName[0] = valueName.capacity();
						int[] lpcClass = new int[1];
						lpcClass[0] = className.capacity();
						result = Advapi32.RegEnumKeyEx(hKey[0], index, valueName, lpcName, 0, className, lpcClass, time);
						if (valueName.toString().hashCode() != 0) {
							DefaultMutableTreeNode node = new DefaultMutableTreeNode(valueName);
							tn.add(node);
							node.add(new DefaultMutableTreeNode(unexpanded));
						} else {
							if (index == 0)
								return false;
						}
						index++;
					}
					if (hKey[0] != 0)
						Advapi32.RegCloseKey(hKey[0]);
				}
			}
			return true;
		} else {
			return false;
		}

	}

	private String[][] enumerateValues(String treePathString, JTree tree) {

		int nameSize;
		int index = 0;
		StringBuffer lpValueName;
		ArrayList<String> keyNames = new ArrayList<String>();
		String classKeyString = getClassKeyFromTreePath(treePathString);
		String sectionKey = getSectionKeyFromTreePath(treePathString, classKeyString);
		int classKey = (Integer) hKeyMap.get(classKeyString);
		int[] hKey = { 0 };
		int result = Advapi32.RegOpenKeyEx(classKey, sectionKey, 0, KEY_QUERY_VALUE, hKey);

		if (result == 0) {
			StringBuffer lpClass = new StringBuffer(256);
			FileTime time = new FileTime();
			int[] lpcClass = new int[1];
			lpcClass[0] = lpClass.capacity();
			int[] lpcMaxNameValueLen = { 0 };

			result = Advapi32.RegQueryInfoKey(hKey[0], lpClass, lpcClass, null,
					null, null, null, null, lpcMaxNameValueLen, null, null, time);
			
			while (result == 0) {
				nameSize = lpcMaxNameValueLen[0] + 1;
				lpValueName = new StringBuffer("");
				for (int i = 0; i < nameSize; i++) {
					lpValueName.append('0');
				}
				int[] lpcchValueName = new int[1];
				lpcchValueName[0] = nameSize;
				result = Advapi32.RegEnumValue(hKey[0], index, lpValueName,
						lpcchValueName, null, null, null, null);
				if (result == 0) 
					keyNames.add(lpValueName.toString());
				
				index++;
			}
		}
		
		if (hKey[0] != 0)
			Advapi32.RegCloseKey(hKey[0]);

		String[][] keyValue = new String[keyNames.size()][3];

		for (int i = 0; i < keyNames.size(); i++) {
			String valueKey = keyNames.get(i);
			Advapi32.RegOpenKeyEx(classKey, sectionKey, 0, KEY_QUERY_VALUE, hKey);

			int[] lpType = { 0 };
			byte[] lpData = new byte[1];
			int[] lpcbData = new int[1];
			lpcbData[0] = 1;
			Advapi32.RegQueryValueEx(hKey[0], valueKey, null, lpType, lpData, lpcbData);

			if (valueKey.trim().equals("")) {
				keyValue[i][0] = "(default)";
			} else {
				keyValue[i][0] = valueKey;
			}
			
			StringBuffer dataStr;
			
			switch (lpType[0]) {
				case REG_DWORD:
					int [] data = { 0 };
					Advapi32.RegQueryValueExInt(hKey[0], valueKey, null, lpType, data, lpcbData);
					keyValue[i][1] = "REG_DWORD";
					keyValue[i][2] = Integer.toString(data[0]);
					break;
					
				case REG_SZ:
					dataStr = new StringBuffer(lpcbData[0]);
					Advapi32.RegQueryValueExStr(hKey[0], valueKey, null, lpType, dataStr, lpcbData);
					keyValue[i][1] = "REG_SZ";
					keyValue[i][2] = dataStr.toString();
					break;
					
				case REG_MULTI_SZ:
					dataStr = new StringBuffer(lpcbData[0]);
					Advapi32.RegQueryValueExStr(hKey[0], valueKey, null, lpType, dataStr, lpcbData);
					keyValue[i][1] = "REG_MULTI_SZ";
					keyValue[i][2] = dataStr.toString();
					break;
					
				case REG_EXPAND_SZ:
					dataStr = new StringBuffer(lpcbData[0]);
					Advapi32.RegQueryValueExStr(hKey[0], valueKey, null, lpType, dataStr, lpcbData);
					keyValue[i][1] = "REG_EXPAND_SZ";
					keyValue[i][2] = dataStr.toString();
					break;
					
				default:
					lpData = new byte[lpcbData[0]];
					Advapi32.RegQueryValueExByte(hKey[0], valueKey, null, lpType, lpData, lpcbData);
					StringBuffer byteString = new StringBuffer("");
					for (int k = 0; k < lpcbData[0]; k++)
						byteString.append(String.format("%1$02X ", lpData[k]));
					keyValue[i][1] = "REG_BINARY";
					keyValue[i][2] = byteString.toString();
			}
		}
		return keyValue;
	}
}

class KeyValueTableModel extends AbstractTableModel {
	
	private String[] columnNames = { "Name", "Type", "Data" };
	private Object[][] data = { { "", "", "" } };

	public String getColumnName(int i) {
		return columnNames[i];
	}

	public void setData(Object[][] data) {
		this.data = data;
		fireTableDataChanged();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
}
