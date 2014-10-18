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

import com.n9mtq4.adbfiletransfer.*;
import com.n9mtq4.adbfiletransfer.gui.dialog.*;
import com.n9mtq4.adbfiletransfer.gui.dialog.dialog.*;
import com.n9mtq4.adbfiletransfer.gui.DeviceDisplay;
import com.n9mtq4.adbfiletransfer.gui.Gui;
import com.n9mtq4.adbfiletransfer.gui.Message;
import com.n9mtq4.adbfiletransfer.gui.TextAreaWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Will on 6/30/14.
 */
public class MenuListener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		JMenuItem source = (JMenuItem) actionEvent.getSource();
		String text = source.getText();
		
		if (text.equalsIgnoreCase("start server")) {
			
			Message m = new Message("ADB", "Starting server");
			ADB.start();
			m.close();
			new TextAreaWindow("ADB Server", "Started Server", Gui.frame);
			
		}else if (text.equalsIgnoreCase("kill server")) {
			
			ADB.stop();
			new TextAreaWindow("ADB Server", "Killed Server", Gui.frame);
			
		}else if (text.equalsIgnoreCase("connect to ip")) {
			
			new IpDialog();
			
		}else if (text.equalsIgnoreCase("connect to usb")) {
			
			ADB.usb();
			new TextAreaWindow("Connected to USB", "Connected to usb", Gui.frame);
			Files.refresh();
			
		}else if (text.equalsIgnoreCase("install")) {
			
			new InstallDialog();
			
		}else if (text.equalsIgnoreCase("uninstall")) {
			
			new UninstallDialog();
			
		}else if (text.equalsIgnoreCase("devices")) {
			
			new DeviceDisplay(ADB.devices());
			
		}else if (text.equalsIgnoreCase("refresh")) {
			
			Files.refresh();
			
		}else if (text.equalsIgnoreCase("push")) {
			
			new PushDialog(Files.getCurrentPath());
			
		}else if (text.equalsIgnoreCase("goto")) {
			
			new GotoDialog();
			
		}else if (text.equalsIgnoreCase("pull")) {
			
			new PullDialog(Files.getCurrentPath(), Files.getSelectedFileName());
			
		}else if (text.equalsIgnoreCase("new folder")) {
			
			new NewFolderDialog(Files.getCurrentPath());
			
		}else if (text.equalsIgnoreCase("new file")) {
			
			new NewFileDialog(Files.getCurrentPath());
			
		}else if (text.equalsIgnoreCase("rename")) {
			
			new RenameDialog(Files.getSelectedFileName());
			
		}else if (text.equalsIgnoreCase("toggle root files")) {
			
			Files.rootToggle();
			
		}else if (text.equalsIgnoreCase("toggle hidden files")) {
			
			Files.hiddenToggle();
			
		}else if (text.equalsIgnoreCase("copy")) {
			
			Files.setCopyPath(Files.getCurrentPath() + Files.getSelectedFileName());
			
		}else if (text.equalsIgnoreCase("paste")) {
			
			Files.copy(Files.getCopyPath(), Files.getCurrentPath());
			
		}else if (text.equalsIgnoreCase("go up")) {
			
			Files.navTo(Files.upOne(Files.getCurrentPath()));
			
		}
		
	}
	
}
