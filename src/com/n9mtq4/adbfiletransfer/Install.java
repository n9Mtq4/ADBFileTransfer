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
 * Created by Will on 7/5/14.
 */
public class Install {
	
	public Install() {
		
		new TextAreaWindow("Install ADB", "This program requires ADB to be installed and in the run path\n\nPlease install ADB from here\nhttps://developer.android.com/sdk/index.html", null);
		
	}
	
	public static boolean hasADB() {
		
		Shell shell = new Shell();
		String[] cmd = {"adb", "version"};
		String out = shell.sendShellCommand(cmd);
		if (out.contains("Android Debug Bridge")) {
			
			return true;
			
		}else if (out.contains("command not found")) {
			
			return false;
			
		}else {
			
			System.out.println("ADB verification error");
			return true;
			
		}
		
	}
	
}
