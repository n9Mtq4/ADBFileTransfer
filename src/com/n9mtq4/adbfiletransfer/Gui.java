/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.n9mtq4.adbfiletransfer;

import com.n9mtq4.adbfiletransfer.listener.FileListListener;
import com.n9mtq4.adbfiletransfer.listener.MenuListener;
import com.n9mtq4.adbfiletransfer.listener.PopupListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by Will on 6/30/14.
 */
public class Gui {
	
	public static JFrame frame;
	public static JMenuBar mbar;
	
	public static String[] menusText = {"ADB", "File", "Edit", "Go", "View"};
	public static JMenu[] menus = new JMenu[menusText.length];
	
	public static String[] adbItemsText = {"Start Server", "Kill Server", "Connect to IP", "Connect to usb", "Install", "Uninstall", "Devices"};
	public static JMenuItem[] adbItems = new JMenuItem[adbItemsText.length];
	public static String[] fileItemsText = {"Refresh", "Copy", "Paste", "Push", "Pull", "New Folder", "New File"};
	public static JMenuItem[] fileItems = new JMenuItem[fileItemsText.length];
	public static String[] editItemsText = {"Rename"};
	public static JMenuItem[] editItems = new JMenuItem[editItemsText.length];
	public static String[] goItemsText = {"Goto", "Go up"};
	public static JMenuItem[] goItems = new JMenuItem[goItemsText.length];
	public static String[] viewItemsText = {"Toggle hidden files", "Toggle root files"};
	public static JMenuItem[] viewItems = new JMenuItem[viewItemsText.length];
	
	public static FileListModel model;
	public static JScrollPane scrollPane;
	public static JTable fileList;
	public static JPopupMenu popupMenu;
	
	public static String[] popupText = {"Rename", "Delete File", "Delete Folder", "Copy", "Paste", "New Folder", "New File", "Pull"};
	public static JMenuItem[] popupItems = new JMenuItem[popupText.length];
	
	public static void init() {
		
		frame = new JFrame("ADB File Transfer - " + Files.getCurrentPath());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mbar = new JMenuBar();
		
		initMenuBar(mbar, menus, menusText);
		
		initMenu(menus[0], adbItems, adbItemsText);
		initMenu(menus[1], fileItems, fileItemsText);
		initMenu(menus[2], editItems, editItemsText);
		initMenu(menus[3], goItems, goItemsText);
		initMenu(menus[4], viewItems, viewItemsText);
		
		frame.setJMenuBar(mbar);
		
		model = new FileListModel(Files.getFilesAt(Files.getCurrentPath()), Files.getHeader());
		fileList = new JTable(model);
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(fileList);
//		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);
		
		frame.pack();
		frame.setSize(new Dimension(480, 320));
		frame.setVisible(true);
		
		popupMenu = new JPopupMenu();
		initPopup(popupMenu, popupItems, popupText);
		fileList.setComponentPopupMenu(popupMenu);
		
		fileList.addMouseListener(new FileListListener());
		fileDrop();
		
		initKeys();
		
	}
	
	private static void initKeys() {
		
		addKey(adbItems, "start server", 'S', true);
		addKey(adbItems, "kill server", 'S', false);
		addKey(adbItems, "install", 'I', true);
		addKey(adbItems, "uninstall", 'I', false);
		addKey(adbItems, "devices", 'D', true);
		addKey(fileItems, "refresh", 'R', true);
		addKey(fileItems, "copy", 'C', true);
		addKey(fileItems, "paste", 'V', true);
		addKey(fileItems, "new folder", 'N', true);
		addKey(fileItems, "new file", 'N', false);
		addKey(editItems, "rename", 'E', true);
		addKey(fileItems, "push", 'O', true);
		addKey(fileItems, "pull", 'S', true);
		addKey(viewItems, "toggle hidden files", '.', true);
		addKey(viewItems, "toggle root files", '.', false);
		addKey(goItems, "goto", 'G', true);
		addKey(goItems, "go up", 'U', true);
		
	}
	
	private static void initMenuBar(JMenuBar mbar, JMenu[] menus, String[] ss) {
		
		for (int i = 0; i < menus.length; i++) {
			
			menus[i] = new JMenu(ss[i]);
			mbar.add(menus[i]);
			
		}
		
	}
	
	private static void initMenu(JMenu menu, JMenuItem[] jmis, String[] ss) {
		
		for (int i = 0; i < jmis.length; i++) {
			
			jmis[i] = new JMenuItem(ss[i]);
			menu.add(jmis[i]);
			jmis[i].addActionListener(new MenuListener());
			
		}
		
	}
	
	private static void initPopup(JPopupMenu popup, JMenuItem[] jmis, String[] ss) {
		
		for (int i = 0; i < jmis.length; i++) {
			
			jmis[i] = new JMenuItem(ss[i]);
			popup.add(jmis[i]);
			jmis[i].addActionListener(new PopupListener());
			
		}
		
	}
	
	private static void fileDrop() {
		
		new FileDrop(scrollPane, new FileDrop.Listener() {
			@Override
			public void filesDropped(File[] files) {
				
				for (File f : files) {
					
					String out = ADB.push(f.getPath(), Files.getCurrentPath());
					String msg = "Output for file:" + f.getName() + "\n" + out;
					
					new TextAreaWindow("Push", msg, Gui.frame);
					
				}
				
				Files.refresh();
				
			}
		});
		
	}
	
	private static void addKey(JMenuItem[] inMenu, String menuText, char key, boolean b) {
		
		int index = indexOf(inMenu, menuText);
		Debug.print(index);
		if (b) {
			inMenu[index].setAccelerator(KeyStroke.getKeyStroke(key, KeyEvent.META_DOWN_MASK));
		}else {
			inMenu[index].setAccelerator(KeyStroke.getKeyStroke(key, KeyEvent.META_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
		}
		
	}
	
	private static int indexOf(JMenuItem[] menus, String string) {
		
		int in = -1;
		
		for (int i = 0; i < menus.length; i++) {
			
			if (menus[i].getText().equalsIgnoreCase(string)) {
				
				in = i;
				
			}
			
		}
		
		return in;
		
	}
	
}
