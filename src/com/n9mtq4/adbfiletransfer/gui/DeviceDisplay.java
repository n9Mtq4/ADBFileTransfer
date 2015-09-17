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

package com.n9mtq4.adbfiletransfer.gui;

import com.n9mtq4.adbfiletransfer.ADB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Will on 7/2/14.
 */
public class DeviceDisplay {
	
	public static Dimension DEFAULT_SIZE = new Dimension(420, 240);
	
	private static Object[] columnNames = {"Device", "Status", "Connection"};
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton button;
	
	public DeviceDisplay(String commandOutput) {
		
		gui(commandOutput, DeviceDisplay.DEFAULT_SIZE, Gui.frame);
		
	}
	
	public DeviceDisplay(String commandOutput, Dimension size, JFrame centerOn) {
		
		gui(commandOutput, size, centerOn);
		
	}
	
	public void gui(String commandOutput, Dimension size, JFrame centerOn) {
		
		Object[][] parsedDevices = parseDevice(commandOutput);
		
		frame = new JFrame("Connected Devices");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		table = new JTable(parsedDevices, columnNames);
		scrollPane = new JScrollPane(table);
		button = new JButton("OK");
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(button, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(size);
		frame.setLocationRelativeTo(centerOn);
		frame.setVisible(true);
		
		frame.getRootPane().setDefaultButton(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onButtonPress(button);
			}
		});
		
	}
	
	public void onButtonPress(JButton pressedButton) {
		
		frame.dispose();
		
	}
	
	public Object[][] parseDevice(String commandOutput) {
		
		String trimmed = commandOutput.trim();
		String[] dList = trimmed.split("\n");
		
		ArrayList<ArrayList<String>> objectList = new ArrayList<ArrayList<String>>();
		 
		for (String s : dList) {
			
			if (s.contains("\t")) {
				
				String[] t = s.split("\t");
				ArrayList<String> stringList = new ArrayList<String>();
				
				String dOutput = ADB.shell("cat /system/build.prop | grep \"ro.product.model\"");
				String deviceName = dOutput.substring("ro.product.model=".length() + 1);
				stringList.add(deviceName);
				stringList.add(t[1]);
				stringList.add(t[0]);
				
				objectList.add(stringList);
				
			}
			
		}
		
		Object[][] finalArray = new Object[objectList.size()][3];
		
		for (int i = 0; i < objectList.size(); i++) {
			
			ArrayList<String> a = objectList.get(i);
			finalArray[i] = a.toArray();
			
		}
		
		return finalArray;
		
	}
	
}
