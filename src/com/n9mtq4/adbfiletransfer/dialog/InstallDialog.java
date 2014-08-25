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

package com.n9mtq4.adbfiletransfer.dialog;

import com.n9mtq4.adbfiletransfer.ADB;
import com.n9mtq4.adbfiletransfer.Gui;
import com.n9mtq4.adbfiletransfer.Message;
import com.n9mtq4.adbfiletransfer.TextAreaWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Will on 7/5/14.
 */
public class InstallDialog {

	private JFrame frame;
	private JTextField local;
	private JButton cancel;
	private JButton ok;
	private JButton browse;
	private JPanel mainPanel;
	private JPanel conPanel;
	private JScrollPane scroll;
	
	public InstallDialog() {
		
		gui(System.getProperty("user.home"));
		
	}
	
	public void gui(String localPath) {
		
		frame = new JFrame("Install apk");
		
		local = new JTextField(localPath);
		cancel = new JButton("Cancel");
		ok = new JButton("Install");
		browse = new JButton("Browse apk files");
		
		mainPanel = new JPanel(new GridLayout(4, 1));
		conPanel = new JPanel(new GridLayout(1 , 2));
		
		conPanel.add(cancel);
		conPanel.add(ok);
		
		mainPanel.add(new JLabel("Apk file:"));
		mainPanel.add(local);
		mainPanel.add(browse);
		mainPanel.add(conPanel);
		
		scroll = new JScrollPane(mainPanel);
		
		frame.add(scroll);
		
		frame.pack();
		frame.setSize(new Dimension(320, 140));
		frame.setLocationRelativeTo(Gui.frame);
		frame.setVisible(true);
		
		frame.getRootPane().setDefaultButton(ok);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onCancel();
			}
		});
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onOk();
			}
		});
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onBrowse();
			}
		});
		
	}
	
	public void onOk() {
		
		String localPath = getLocal().getText();
		
		Message m = new Message("Install", "Installing package...");
		String out = ADB.install(localPath);
		m.close();
		
		new TextAreaWindow("Install", out, this.frame);
		frame.dispose();
		
	}
	
	public void onCancel() {
		
		frame.dispose();
		
	}
	
	public void onBrowse() {
		
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Android Package Files - .apk", "apk", "android package");
		fileChooser.setFileFilter(filter);
		int returnInt = fileChooser.showOpenDialog(this.frame);
		
		if (returnInt == JFileChooser.APPROVE_OPTION) {
			
			File file = fileChooser.getSelectedFile();
			
			local.setText(file.getPath());
			
		}
		
	}
	
	public JTextField getLocal() {
		
		return local;
		
	}
	
}
