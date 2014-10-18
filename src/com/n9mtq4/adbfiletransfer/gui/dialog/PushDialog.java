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

package com.n9mtq4.adbfiletransfer.gui.dialog;

import com.n9mtq4.adbfiletransfer.ADB;
import com.n9mtq4.adbfiletransfer.gui.Gui;
import com.n9mtq4.adbfiletransfer.gui.Message;
import com.n9mtq4.adbfiletransfer.gui.TextAreaWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Will on 6/30/14.
 */
public class PushDialog {
	
	private JFrame frame;
	private JTextField local;
	private JTextField remote;
	private JButton cancel;
	private JButton ok;
	private JButton browse;
	private JPanel mainPanel;
	private JPanel conPanel;
	private JScrollPane scroll;
	
	public PushDialog(String remotePath) {
		
		gui("Push File", System.getProperty("user.home"), remotePath);
		
	}
	
	public PushDialog(String title, String localPath, String remotePath) {
		
		gui(title, localPath, remotePath);
		
	}
	
	public void gui(String title, String localPath, String remotePath) {
		
		frame = new JFrame(title);
		
		local = new JTextField(localPath);
		remote = new JTextField(remotePath);
		cancel = new JButton("Cancel");
		ok = new JButton("OK");
		browse = new JButton("Browse local files");
		
		mainPanel = new JPanel(new GridLayout(6, 1));
		conPanel = new JPanel(new GridLayout(1 , 2));
		
		conPanel.add(cancel);
		conPanel.add(ok);
		
		mainPanel.add(new JLabel("Local file:"));
		mainPanel.add(local);
		mainPanel.add(browse);
		mainPanel.add(new JLabel("Remote file:"));
		mainPanel.add(remote);
		mainPanel.add(conPanel);
		
		scroll = new JScrollPane(mainPanel);
		
		frame.add(scroll);
		
		frame.pack();
		frame.setSize(new Dimension(320, 200));
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
		String remotePath = getRemote().getText();
		
		Message m = new Message("Push", "Pushing file...");
		String out = ADB.push(localPath, remotePath);
		m.close();
		
		new TextAreaWindow("Push", out, this.frame);
		frame.dispose();
		
	}
	
	public void onCancel() {
		
		frame.dispose();
		
	}
	
	public void onBrowse() {
		
		JFileChooser fileChooser = new JFileChooser();
		int returnInt = fileChooser.showOpenDialog(this.frame);
		
		if (returnInt == JFileChooser.APPROVE_OPTION) {
			
			File file = fileChooser.getSelectedFile();
			
			remote.setText(remote.getText() + file.getName());
			local.setText(file.getPath());
			
		}
		
	}
	
	public JTextField getLocal() {
		
		return local;
		
	}
	
	public JTextField getRemote() {
		
		return remote;
		
	}
}
