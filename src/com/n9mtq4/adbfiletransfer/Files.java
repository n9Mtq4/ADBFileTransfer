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

import java.util.ArrayList;

/**
 * Created by Will on 6/30/14.
 */
public class Files {
	
	public static String currentPath = "/sdcard/";
	public static String copyPath = "";
	
	public static void navTo(String filePath) {
		
		currentPath = filePath;
		Gui.frame.setTitle("ADB File Transfer - " + Files.getCurrentPath());
		refresh();
		
	}
	
	public static void refresh() {
		
		Gui.model.setDataVector(Files.getFilesAt(currentPath), Files.getHeader());
		
	}
	
	public static void rootToggle() {
		
		Global.RUN_AS_ROOT = !Global.RUN_AS_ROOT;
		if (Global.RUN_AS_ROOT) {
			
			Files.navTo("/");
			
		}else {
			
			Files.navTo("/sdcard/");
			
		}
		
	}
	
	public static void hiddenToggle() {
		
		Global.SHOW_HIDDEN_FILES = !Global.SHOW_HIDDEN_FILES;
		refresh();
		
	}
	
	public static void rename(String oldName, String newName) {
		
		ADB.rShell("mv " + Files.getCurrentPath() + oldName + " " + Files.getCurrentPath() + newName);
		
		refresh();
		
	}
	
	public static void setCurrentPath(String path) {
		
		Files.currentPath = path;
		
	}
	
	public static String getCurrentPath() {
		
		return currentPath;
		
	}
	
	public static void newFolder(String filePath, String folderName) {
		
		ADB.rShell("mkdir " + filePath + folderName);
		refresh();
		
	}
	
	public static String upOne(String filePath) {
		
		if (Files.getCurrentPath().equalsIgnoreCase("/")) {
			
			return Files.getCurrentPath();
			
		}
		
		String newPath = filePath.substring(0, filePath.lastIndexOf("/"));
		newPath = newPath.substring(0, newPath.lastIndexOf("/") + 1);
		
		return newPath;
		
	}
	
	public static void deleteFolder(String filePath) {
		
		String out = ADB.rShell("rmdir " + filePath);
		if (out.contains("rmdir failed")) {
			
			new TextAreaWindow("Error", out, Gui.frame);
			
		}
		
		refresh();
		
	}
	
	public static void deleteFile(String filePath, String fileName) {
		
		ADB.rShell("rm " + filePath + fileName);
		refresh();
		
	}
	
	public static void newFile(String filePath, String fileName) {
		
		ADB.rShell("touch " + filePath + fileName);
		refresh();
		
	}
	
	public static void copy(String fromPath, String toPath) {
		
		if (!(copyPath.equalsIgnoreCase(""))) {
			
			ADB.rShell("cp -r " + fromPath + " " + toPath);
			refresh();
			
		}
		
	}
	
	public static String getCopyPath() {
		
		return copyPath;
		
	}
	
	public static void setCopyPath(String newPath) {
		
		copyPath = getAdjustedFilePath(newPath);
		
	}
	
	public static String getSelectedFileName() {
		
		int row = Gui.fileList.getSelectedRow();
		int column = Gui.fileList.getColumnModel().getColumnIndex("File");
		if (row == -1 || column == -1) {
			
			return "";
			
		}
		String file = (String) Gui.fileList.getValueAt(row, column);
		
		return file;
		
	}
	
	public static Object[] getHeader() {
		
		Object[] o = {"File", "Size", "Last Modified", "Permissions", "Type"};
		
		return o;
		
	}
	
	private static String getBefore(String string, String pattern) {
		
		int index = string.indexOf(pattern);
		if (index == -1) {
			return "";
		}
		
		try {
			
			return string.substring(0, index);
			
		}catch (StringIndexOutOfBoundsException e) {
			
			return "";
			
		}
		
	}
	
	private static String getLastStringParts(int startIndex, String before) {
		String cache = before;
		for (int word = 0; word < startIndex; word++) {
			try {
				String sub = cache.trim().split(" ", 2)[1];
				cache = sub;
			}catch (StringIndexOutOfBoundsException e) {
				return null;
			}
		}
		return cache;
	}
	
	private static int getType(String lsline, String seperator) {
		
		String[] list = lsline.split(seperator);
		
		if (list.length > 5) {
			
			if (list[3].contains("-")) {
				
				if (lsline.contains("->")) {
					
//					shortcut
					return 2;
					
				}else {
					
//					folder
					return 0;
					
				}
				
			}
			
			try {
				
				Integer.parseInt(list[3]);
//				file
				return 1;
				
			}catch (NumberFormatException e) {
				
//				unknown
				return -1;
				
			}
			
		}
		
//		unknown
		return -1;
		
	}
	
	private static String getAdjustedFilePath(String filePath) {
		
		try {
			
			String[] list = filePath.split(" ");
			String out = "";
			for (String s : list) {
				
				out += s + "\\ ";
				
			}
			
			out = out.substring(0, out.length() - 2);
			Debug.print(out);
			return out;
			
		}catch (StringIndexOutOfBoundsException e) {
			
			return filePath;
			
		}
		
	}
	
	public static Object[][] getFilesAt(String filePath1) {
		
		String out = "";
		String filePath = getAdjustedFilePath(filePath1);
		
		if (Global.SHOW_HIDDEN_FILES) {
			
			out = ADB.rShell("ls -al " + filePath);
			
		}else {
			
			out = ADB.rShell("ls -l " + filePath);
			
		}
		
		if (out.contains("No such file or directory")) {
			
			Files.navTo(upOne(currentPath));
			new TextAreaWindow("Error", "No such file or directory", Gui.frame);
			return Files.getFilesAt(currentPath);
			
		}
		
		ArrayList<ArrayList<String>> objectList = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> ar = new ArrayList<String>();
		if (!(Files.getCurrentPath().equalsIgnoreCase("/"))) {
			
			ar.add("../");
			ar.add("Go up");
			ar.add("");
			ar.add("");
			ar.add("folder");
			objectList.add(ar);
			
		}
		
		String[] lines = out.split("\n");
		for (String line : lines) {
			
			ArrayList<String> ar1 = new ArrayList<String>();
			
			String l = line.replaceAll("\\s+", "\t");
			String[] list = l.split("\t");
			
			if (getType(l, "\t") == 0) {
				
//				folder
				String perm = list[0];
				String name = getLastStringParts(5, line);
				String date = list[3];
				String time = list[4];
				String dateTime = date + " " + time;
				String extension = "folder";
				
				ar1.add(name + "/");
				ar1.add("");
				ar1.add(dateTime);
				ar1.add(perm);
				ar1.add(extension);
				
				objectList.add(ar1);
				
			}else if (getType(l, "\t") == 1) {
				
//				file
				String perm = list[0];
				String size = list[3];
				String name = getLastStringParts(6, line);
				String date = list[4];
				String time = list[5];
				String dateTime = date + " " + time;
				String extension = "";
				int i = name.lastIndexOf('.');
				if (i > 0) {
					extension = name.substring(i + 1);
				}
				
				ar1.add(name);
				ar1.add(size);
				ar1.add(dateTime);
				ar1.add(perm);
				ar1.add(extension);
				
				objectList.add(ar1);
				
			}else if (getType(l, "\t") == 2) {
				
//				shortcut or mounted disc
				String perm = list[0];
				String name = getBefore(getLastStringParts(5, line), " ->");
				String date = list[3];
				String time = list[4];
				String dateTime = date + " " + time;
				String extension = "mounted";
				
				ar1.add(name + "/");
				ar1.add("");
				ar1.add(dateTime);
				ar1.add(perm);
				ar1.add(extension);
				
				objectList.add(ar1);
				
			}
			
		}
		
		Object[][] finalArray = new Object[objectList.size()][5];
		
		for (int i = 0; i < objectList.size(); i++) {
			
			ArrayList<String> a = objectList.get(i);
			finalArray[i] = a.toArray();
			
		}
		
		return finalArray;
		
	}
	
}
