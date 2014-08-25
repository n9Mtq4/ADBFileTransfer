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

package com.n9mtq4.adbfiletransfer.listener;

import com.n9mtq4.adbfiletransfer.Debug;
import com.n9mtq4.adbfiletransfer.Files;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Will on 7/4/14.
 */
public class FileListListener extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getClickCount() == 2) {
			
			Debug.print("Double click");
			
			JTable target = (JTable) e.getSource();
			int row = target.getSelectedRow();
			int column = target.getSelectedColumn();
			int fileNameINT = target.getColumnModel().getColumnIndex("File");
			int typeINT = target.getColumnModel().getColumnIndex("Type");
			
			String fileName = (String) target.getValueAt(row, fileNameINT);
			String typeName = (String) target.getValueAt(row, typeINT);
			
			if (fileName.equalsIgnoreCase("../")) {
				
				String newPath = Files.upOne(Files.getCurrentPath());
				Debug.print(newPath);
				Files.navTo(newPath);
				return;
				
			}
			
			if (typeName.equalsIgnoreCase("folder") || typeName.equalsIgnoreCase("mounted")) {
				
				Files.navTo(Files.getCurrentPath() + fileName);
				
			}
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		
		
		
	}
}
