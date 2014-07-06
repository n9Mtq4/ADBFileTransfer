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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Will on 7/4/14.
 */
public class PopupListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		Source source = new Source(actionEvent.getSource());
		String text = source.getText();
		
		int row = Gui.fileList.getSelectedRow();
		int column = Gui.fileList.getSelectedColumn();
		Debug.print("(" + row + ", " + column + ")");
		
		if (row == -1 || column == -1) {
			
//			if nothing is selected
			if (text.equalsIgnoreCase("new folder")) {
				
				new NewFolderDialog(Files.getCurrentPath());
				
			}else if (text.equalsIgnoreCase("new file")) {
				
				new NewFileDialog(Files.getCurrentPath());
				
			}else if (text.equalsIgnoreCase("paste")) {
				
				Files.copy(Files.getCopyPath(), Files.getCurrentPath());
				
			}
			
		}else {
			
//			if a file/folder is selected
			if (text.equalsIgnoreCase("rename")) {
				
				new RenameDialog(Files.getSelectedFileName());
				
			}else if (text.equalsIgnoreCase("new folder")) {
				
				new NewFolderDialog(Files.getCurrentPath());
				
			}else if (text.equalsIgnoreCase("new file")) {
				
				new NewFileDialog(Files.getCurrentPath());
				
			}else if (text.equalsIgnoreCase("pull")) {
				
				new PullDialog(Files.getCurrentPath(), Files.getSelectedFileName());
				
			}else if (text.equalsIgnoreCase("delete file")) {
				
				Files.deleteFile(Files.getCurrentPath(), Files.getSelectedFileName());
				
			}else if (text.equalsIgnoreCase("delete folder")) {
				
				Files.deleteFolder(Files.getCurrentPath() + Files.getSelectedFileName());
				
			}else if (text.equalsIgnoreCase("copy")) {
				
				Files.setCopyPath(Files.getCurrentPath() + Files.getSelectedFileName());
				
			}else if (text.equalsIgnoreCase("paste")) {
				
				Files.copy(Files.getCopyPath(), Files.getCurrentPath());
				
			}
			
		}
		
	}
	
}
