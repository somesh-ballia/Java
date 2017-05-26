package com.jinvoke.demos.taskmgr;

import static com.jinvoke.win32.WinConstants.GWL_EXSTYLE;
import static com.jinvoke.win32.WinConstants.GWL_HINSTANCE;
import static com.jinvoke.win32.WinConstants.GW_OWNER;
import static com.jinvoke.win32.WinConstants.PROCESS_ALL_ACCESS;
import static com.jinvoke.win32.WinConstants.PROCESS_QUERY_INFORMATION;
import static com.jinvoke.win32.WinConstants.PROCESS_VM_READ;
import static com.jinvoke.win32.WinConstants.SEE_MASK_INVOKEIDLIST;
import static com.jinvoke.win32.WinConstants.SHGFI_ICON;
import static com.jinvoke.win32.WinConstants.SHGFI_SMALLICON;
import static com.jinvoke.win32.WinConstants.SMTO_ABORTIFHUNG;
import static com.jinvoke.win32.WinConstants.SMTO_BLOCK;
import static com.jinvoke.win32.WinConstants.SW_MINIMIZE;
import static com.jinvoke.win32.WinConstants.SW_RESTORE;
import static com.jinvoke.win32.WinConstants.SW_SHOW;
import static com.jinvoke.win32.WinConstants.SW_SHOWNORMAL;
import static com.jinvoke.win32.WinConstants.WM_NULL;
import static com.jinvoke.win32.WinConstants.WS_EX_APPWINDOW;
import static com.jinvoke.win32.WinConstants.WS_EX_TOOLWINDOW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.jinvoke.Callback;
import com.jinvoke.Charset;
import com.jinvoke.JInvoke;
import com.jinvoke.NativeImport;
import com.jinvoke.Util;
import com.jinvoke.win32.Kernel32;
import com.jinvoke.win32.Psapi;
import com.jinvoke.win32.Shell32;
import com.jinvoke.win32.User32;
import com.jinvoke.win32.Version;
import com.jinvoke.win32.WinConstants;
import com.jinvoke.win32.structs.ProcessMemoryCounters;
import com.jinvoke.win32.structs.ShFileInfo;
import com.jinvoke.win32.structs.ShellExecuteInfo;
import com.jinvoke.win32.structs.VS_FixedFileInfo;

public class TaskManager extends JPanel {

	static JFrame frame;
	static List<String[]> appInfo = new ArrayList<String[]>();


	public TaskManager() {
		super(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent appPanel = makeAppPanel();
		appPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabbedPane.addTab("Applications", appPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent procPanel = makeProcessPanel();
		procPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tabbedPane.addTab("Processes", procPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		// Add the tabbed pane to this panel.
		add(tabbedPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// JButton endTaskbtn = new JButton("End Task");

		JButton newTaskbtn = new JButton("New Task");
		newTaskbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shell32.SHRunDialog(User32.GetActiveWindow(), 0, 0, null, null,
						true);
			}
		});

		buttonPanel.add(newTaskbtn);
		// buttonPanel.add(endTaskbtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	protected JComponent makeAppPanel() {
		JScrollPane appPane = createAppPane();
		appPane.getViewport().setBackground(Color.WHITE);

		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());
		panel.add(appPane, BorderLayout.CENTER);
		return panel;
	}

	protected JComponent makeProcessPanel() {
		JScrollPane procPane = createProcessPane();

		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());
		panel.add(procPane);
		return panel;
	}

	private JScrollPane createAppPane() {
		ProcessTableModel tablemodel = new ProcessTableModel() ;
		
		getAppInfo();
		tablemodel.setData(appInfo);
		

		final JTable table = new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer());
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);


		table.getColumnModel().getColumn(0).setPreferredWidth(800);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		table.addMouseListener(new MouseAdapter() {
			private void showPopup(MouseEvent me) {
				if (me.isPopupTrigger() && table.isEnabled()) {
					Point p = new Point(me.getX(), me.getY());
					int col = table.columnAtPoint(p);
					int row = table.rowAtPoint(p);
					int mcol = table.getColumn(table.getColumnName(col))
							.getModelIndex();
					table.setRowSelectionInterval(row, row);
					table.setColumnSelectionInterval(col, col);
					JPopupMenu popupMenu = createAppPopupMenu(row, mcol, table);
					if (popupMenu != null && popupMenu.getComponentCount() > 0) {
						popupMenu.show(table, p.x, p.y);
					}
				}
			}

			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}

			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}

		});

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		add(scrollPane);
		return scrollPane;
	}

	private void getAppInfo() {
		Callback callback = new Callback(TaskManager.class, "enumWindowsProc");
		User32.EnumWindows(callback, "data");
	}

	public static boolean enumWindowsProc(int hwnd, String lParam) {
		StringBuffer sb = new StringBuffer(128);
		if (User32.IsWindowVisible(hwnd) == true) {
			if (User32.GetParent(hwnd) == 0) {
				boolean noOwner = (User32.GetWindow(hwnd, GW_OWNER) == 0);
				int exStyle = User32.GetWindowLong(hwnd, GWL_EXSTYLE);
				
				int[] result = {0};
				int retVal = User32.SendMessageTimeout(hwnd, WM_NULL, 0, 0, SMTO_ABORTIFHUNG & SMTO_BLOCK, 1000, result);
				String procStatus;
				if (retVal ==0)
					procStatus = "Not Responding";
				else
					procStatus = "Running";
				
				if ((((exStyle & WS_EX_TOOLWINDOW) == 0) && noOwner)
						|| (((exStyle & WS_EX_APPWINDOW) != 0) && !(noOwner))) {
					if (User32.GetWindowText(hwnd, sb, sb.capacity()) != 0) {
						appInfo.add(new String[] { sb.toString(), procStatus, Integer.toString(hwnd) });
					}
				}
			}
		}
		return true;
	}

	private JScrollPane createProcessPane() {
		DefaultTableModel tablemodel = new DefaultTableModel();
		tablemodel.addColumn("Process Name");
		tablemodel.addColumn("PID");
		tablemodel.addColumn("Description");
		tablemodel.addColumn("Memory");
		tablemodel.addColumn("Process file path");

		StringBuffer moduleName = new StringBuffer(260);
		StringBuffer baseName = new StringBuffer(260);

		int numprocesses = 1024; // guess
		int[] processIDs = new int[numprocesses];
		int[] bytesret = { 0 };
		boolean b = Psapi.EnumProcesses(processIDs, numprocesses * 4, bytesret);
		if (b) {
			for (int i = 0; i < numprocesses; i++) {
				int hProcess = Kernel32.OpenProcess(PROCESS_QUERY_INFORMATION
						+ PROCESS_VM_READ, false, processIDs[i]);

				if (hProcess != 0) {
					int[] modules = new int[200];
					int[] cbNeeded2 = { 0 };
					boolean ok = Psapi.EnumProcessModules(hProcess, modules,
							199, cbNeeded2);
					if (ok) {
						int nSize = 500;
						Psapi.GetModuleFileNameEx(hProcess, modules[0],
								moduleName, nSize);
						// System.out.println(moduleName);
						Psapi.GetModuleBaseName(hProcess, modules[0], baseName, nSize);
						// System.out.println(baseName);
						ProcessMemoryCounters tPMC = new ProcessMemoryCounters();
						int workingSetSize;

						Psapi.GetProcessMemoryInfo(hProcess, tPMC, Util
								.getStructSize(tPMC));
						workingSetSize = tPMC.WorkingSetSize;
						DecimalFormat myFormatter = new DecimalFormat("#,###,###");
						String memInfo = myFormatter
								.format(workingSetSize / 1024);

						tablemodel.addRow(new String[] { baseName.toString(),
								Integer.toString(processIDs[i]),
								getProgramDescription(moduleName),
								memInfo + " K", moduleName.toString() });
					}
				}
				Kernel32.CloseHandle(hProcess);
			}
		}

		final JTable table = new JTable(tablemodel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		// table.setFillsViewportHeight(true);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);

		// Disable auto resizing
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(45);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);

		table.addMouseListener(new MouseAdapter() {
			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && table.isEnabled()) {
					Point p = new Point(e.getX(), e.getY());
					int col = table.columnAtPoint(p);
					int row = table.rowAtPoint(p);
					int mcol = table.getColumn(table.getColumnName(col)).getModelIndex();
					table.setRowSelectionInterval(row, row);
					table.setColumnSelectionInterval(col, col);
					JPopupMenu popupMenu = createProcPopupMenu(row, mcol, table);
					if (popupMenu != null && popupMenu.getComponentCount() > 0) {
						popupMenu.show(table, p.x, p.y);
					}
				}
			}

			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

		});
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane);
		return scrollPane;
	}

	private JPopupMenu createAppPopupMenu(final int row, final int col,
			final JTable table) {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem switchToMenuItem = new JMenuItem("Switch To");
		switchToMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object value = table.getValueAt(row, 2);
				System.out.println(value.toString());
				switchToApp(value.toString());
			}
		});
		popupMenu.add(switchToMenuItem);

		popupMenu.addSeparator();

		JMenuItem maximizeMenuItem = new JMenuItem("Maximize");
		maximizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object hwnd = table.getValueAt(row, 2);
				User32.ShowWindow(Integer.parseInt((String) hwnd),
						WinConstants.SW_MAXIMIZE);
			}
		});
		popupMenu.add(maximizeMenuItem);

		JMenuItem minimizeMenuItem = new JMenuItem("Minimize");
		minimizeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object hwnd = table.getValueAt(row, 2);
				User32.ShowWindow(Integer.parseInt((String) hwnd),
						WinConstants.SW_MINIMIZE);
			}
		});
		popupMenu.add(minimizeMenuItem);

		popupMenu.addSeparator();

		JMenuItem endTaskMenuItem = new JMenuItem("End Task");
		endTaskMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object hwnd = table.getValueAt(row, 2);
				User32.SendMessage(Integer.parseInt((String) hwnd),
						WinConstants.WM_CLOSE, 0, 0);

				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.removeRow(row);
			}
		});
		popupMenu.add(endTaskMenuItem);

		return popupMenu;
	}

	private JPopupMenu createProcPopupMenu(final int row, final int col,
			final JTable table) {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem openFileMenuItem = new JMenuItem("Open File Location");
		openFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object pid = table.getValueAt(row, 1);
				Object procfilePath = table.getValueAt(row, 4);
				System.out.println(pid.toString() + " " + procfilePath);
				openFileLocation(procfilePath.toString());
			}
		});
		popupMenu.add(openFileMenuItem);

		popupMenu.addSeparator();

		JMenuItem endProcMenuItem = new JMenuItem("End Process");
		endProcMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object pid = table.getValueAt(row, 1);
				System.out.println(pid.toString());
				boolean result = endProcess(pid.toString());
				if (result == true) {
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					dtm.removeRow(row);
				}

			}
		});
		popupMenu.add(endProcMenuItem);

		popupMenu.addSeparator();

		JMenuItem propertiesMenuItem = new JMenuItem("Properties");
		propertiesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object fileLocation = table.getValueAt(row, 4);
				System.out.println(fileLocation);
				openPropertiesWindow(fileLocation.toString());
			}
		});
		popupMenu.add(propertiesMenuItem);

		return popupMenu;
	}

	private String getProgramDescription(StringBuffer procPath) {
		int bufferLength = 0;
		byte[] buffer = new byte[1024];
		int[] handle = { 0 };

		bufferLength = Version.GetFileVersionInfoSize(procPath.toString(),
				handle);
		boolean ret = Version.GetFileVersionInfo(procPath.toString(),
				handle[0], bufferLength, buffer);

		int[] ptr = new int[1];
		int[] asize = { 0 };

		VS_FixedFileInfo fixedFileInfo = new VS_FixedFileInfo();
		boolean result = Version.VerQueryValue(buffer, "\\", ptr, asize);

		String programDesc = "";

		if (result == true) {
			Util.ptrToStruct(ptr[0], fixedFileInfo);
			String subBlock = "\\VarFileInfo\\Translation";

			Version.VerQueryValue(buffer, subBlock, ptr, asize);

			int intVal = Util.readInt(ptr[0]);
			int lo = loword(intVal);
			int hi = hiword(intVal);
			String language = String.format("%04X", lo);
			String codepage = String.format("%04X", hi);

			subBlock = "\\StringFileInfo\\" + language + codepage + "\\FileDescription";
			result = Version.VerQueryValue(buffer, subBlock, ptr, asize);
			programDesc = Util.ptrToString(ptr[0]);
		}
		if (programDesc == "") {
			File f = new File(procPath.toString());
			programDesc = f.getName();
		}
		return programDesc;
	}

	private int loword(int i) {
		return i & 0x0000ffff;
	}

	private int hiword(int i) {
		return (i & 0xffff0000) >> 16;
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("J/Invoke Task Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TaskManager taskManager = new TaskManager();
		taskManager.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Add content to the window.
		frame.add(taskManager, BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setBounds(300, 200, 750, 600);
		frame.setVisible(true);
	}

	private void switchToApp(String winHandle) {
		int hwnd = Integer.parseInt(winHandle);
		System.out.println("HWND = " + hwnd);
		if (hwnd != User32.GetForegroundWindow()) {
			int[] a = { 0 };
			int foreThreadID = User32.GetWindowThreadProcessId(User32.GetForegroundWindow(), a);
			int thisthreadID = User32.GetWindowThreadProcessId(hwnd, a);

			System.out.println(foreThreadID + "  " + thisthreadID);

			if (foreThreadID != thisthreadID) {
				User32.ShowWindow(hwnd, SW_MINIMIZE);
				User32.ShowWindow(hwnd, SW_RESTORE);
			} else {
				boolean retVal = User32.SetForegroundWindow(hwnd);
				System.out.println(retVal);
			}

			if (User32.IsIconic(hwnd)) {
				User32.ShowWindow(hwnd, SW_RESTORE);
			} else {
				User32.ShowWindow(hwnd, SW_SHOW);
			}
		}

	}

	private void openFileLocation(String fileLocation) {
		File f = new File(fileLocation);
		String folderName = f.getParent();
		System.out.println(Util.getWindowHandle(frame));
		System.out.println(folderName);
		Shell32.ShellExecute(Util.getWindowHandle(frame), "explore",
				folderName, null, null, SW_SHOWNORMAL);

	}

	private void openPropertiesWindow(String fileLocation) {
		ShellExecuteInfo shInfo = new ShellExecuteInfo();
		shInfo.cbSize = Util.getStructSize(shInfo);
		shInfo.hwnd = Util.getWindowHandle(frame);
		shInfo.lpFile = fileLocation;
		shInfo.nShow = SW_SHOW;
		shInfo.fMask = SEE_MASK_INVOKEIDLIST;
		shInfo.lpVerb = "properties";

		Shell32.ShellExecuteEx(shInfo);
	}

	private boolean endProcess(String ProcID) {
		int processID = Integer.parseInt(ProcID);
		int hProcess = Kernel32.OpenProcess(PROCESS_ALL_ACCESS, false, processID);

		boolean result = Kernel32.TerminateProcess(hProcess, 0);
		return result;
	}


	
	public static void main(String[] args) {

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}
				createAndShowGUI();
			}
		});
	}

}

class MyRenderer extends DefaultTableCellRenderer {

	static {
		JInvoke.initialize();
	}	
	
	@NativeImport(library = "user32")
	public native static int GetWindowThreadProcessId (int hWnd, int[] processId);

	  /*
	   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
	   */
	  public Component getTableCellRendererComponent(JTable table, Object value,
	                                                 boolean isSelected, boolean hasFocus, 
	                                                 int row, int column) {
		  
		int winHandle = Integer.parseInt(table.getValueAt(row, 2).toString());
		int hicon = extractFileIcon(winHandle);
		Icon icon = Util.getIcon(hicon, 16);	
		User32.DestroyIcon(hicon);
		if (isSelected) {
	    	setBackground(table.getSelectionBackground());
	    	setForeground(table.getSelectionForeground());
	    } else {
	    	setBackground(table.getBackground());
    		setForeground(table.getForeground());
	    }
		setText((String)value);
	    setIcon(icon);
	    
	    return this;
	  }
		
	  private int extractFileIcon(int winHandle) {
			int[] modules = new int[200];
			int[] cbNeeded2 = { 0 };
			StringBuffer moduleName = new StringBuffer(256);
			int[] processID ={0};
			int hProcess = User32.GetWindowLong(winHandle, GWL_HINSTANCE);
			int dwThreadId = GetWindowThreadProcessId(winHandle, processID);
			hProcess = Kernel32.OpenProcess(PROCESS_ALL_ACCESS, false, processID[0]);
			
			int nSize = 500;
			Psapi.GetModuleFileNameEx(hProcess, modules[0], moduleName, nSize);
			ShFileInfo shInfo = new ShFileInfo();

			int basicShFlags = SHGFI_SMALLICON | SHGFI_ICON;
			int hIcon = Shell32.SHGetFileInfoA(moduleName.toString(), 
					0,  shInfo, Util.getStructSize(shInfo), basicShFlags);
			return shInfo.hIcon;
		}	
	}

class ProcessTableModel extends AbstractTableModel {
	
	private String[] columnNames = { "Task", "Status", "Window Handle" };
	private List<String[]> data;

	public String getColumnName(int i) {
		return columnNames[i];
	}

	public void setData(List<String[]> data) {
		this.data = data;
		fireTableDataChanged();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}
}