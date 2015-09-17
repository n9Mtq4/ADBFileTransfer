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
 * Created by Will on 7/1/14.
 */
public class Debug {
	
	private static boolean c() {
		
		return Global._DEBUG;
		
	}
	
	public static void print(String msg) {
		
		if (c()) {
			
			System.out.println(msg);
			
		}
		
	}
	
	public static void print(int msg) {
		
		if (c()) {
			
			System.out.println(msg);
			
		}
		
	}
	
	public static void printCMD(String[] msg) {
		
		if (c()) {
			
			String s = "";
			
			for (String d : msg) {
				
				s = s.concat(d + " ");
				
			}
			
			s = s.trim();
			
			System.out.println(s);
			
		}
		
	}
	
}
