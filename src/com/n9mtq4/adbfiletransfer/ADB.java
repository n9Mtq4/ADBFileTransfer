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

/**
 * Created by Will on 6/30/14.
 */
public class ADB {
	
	public static void start() {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "start-server"};
		String out = shell.sendShellCommand(cmd);
		
	}
	
	public static void stop() {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "kill-server"};
		String out = shell.sendShellCommand(cmd);
		
	}
	
	public static String install(String localPath) {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "install", localPath};
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String uninstall(String packName) {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "uninstall", packName};
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static boolean connect(String ip) {
		
		boolean success = false;
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "connect", ip};
		String out = shell.sendShellCommand(cmd);
		if (out.contains("unable to connect")) {
			
			success = false;
			
		}else if(out.contains("connected to")) {
			
			success = true;
			
		}
		return success;
		
	}
	
	public static void usb() {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "usb"};
		String out = shell.sendShellCommand(cmd);
		
	}
	
	public static String shell(String command) {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "shell", command};
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String rootShell(String command) {
		
		Shell shell = new Shell();
		String suCommand = "su -c " + command;
		String[] cmd = {"adb", "shell", suCommand};
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String devices() {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "devices"};
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String push(String local, String remote) {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "push", local, remote};
		Debug.printCMD(cmd);
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String pull(String remote, String local) {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "pull", remote, local};
		Debug.printCMD(cmd);
		String out = shell.sendShellCommand(cmd);
		
		return out;
		
	}
	
	public static String rShell(String command) {
		
		if (Global.RUN_AS_ROOT) {
			
			return rootShell(command);
			
		}else {
			
			return shell(command);
			
		}
		
	}
	
}
