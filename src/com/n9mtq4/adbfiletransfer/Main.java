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

import com.n9mtq4.adbfiletransfer.gui.DeviceDisplay;
import com.n9mtq4.adbfiletransfer.gui.Gui;
import com.n9mtq4.adbfiletransfer.gui.Message;
import com.n9mtq4.adbfiletransfer.gui.TextAreaWindow;

import javax.swing.*;

/**
 * Created by Will on 6/30/14.
 */
public class Main {
	
	public static int version = 5;
	
	public static void main(String[] args) {
		
		
		if (args.length > 0) {
			
			if (checkArg(args, "debug")) {
				
				Global._DEBUG = true;
				
			}
			if (checkArg(args, "default_ui")) {
				
				Global.NIMBUS = false;
				
			}
			if (checkArg(args, "keep_server")) {
				
				Global.RESTART_SERVER = false;
				
			}
			
		}
		
		if (Global.NIMBUS) {
			try {
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception e) {
//				if Nimbus is not available - don't do anything
			}
		}
		
		if (Global.RESTART_SERVER) {
			
			Message m = new Message("ADB", "Restarting ADB server");
			ADB.stop();
			ADB.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ADB.stop();
			ADB.start();
			m.close();
			
		}
		
		if (!Install.hasADB()) {
			
			new Install();
			
		}
		
		Gui.init();
		new DeviceDisplay(ADB.devices());
		
		if (!Update.isCurrent()) {
			
//			available
			new TextAreaWindow("Update", "There is an update available\nhttps://github.com/n9Mtq4/ADBFileTransfer", Gui.frame);
			
		}
		
	}
	
	private static boolean checkArg(String[] args, String arg) {
		
		boolean contains = false;
		
		for (String s : args) {
			
			if (s.equalsIgnoreCase(arg)) {
				
				contains = true;
				
			}
			
		}
		
		return contains;
		
	}
	
}
