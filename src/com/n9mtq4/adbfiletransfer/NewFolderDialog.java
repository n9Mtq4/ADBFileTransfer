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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Will on 7/4/14.
 */
public class NewFolderDialog {
	
	private JFrame frame;
	private JButton submit;
	private JButton cancel;
	private JPanel buttonPanel;
	private JTextField ipField;
	private JLabel label;
	
	private String fileP;
	
	public NewFolderDialog(String filePath) {
		
		this.fileP = filePath;
		gui();
		
	}

	public void gui() {
		
		frame = new JFrame("New Folder");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		label = new JLabel("Enter a New Folder Name");
		ipField = new JTextField();
		submit = new JButton("Create");
		cancel = new JButton("Cancel");
		buttonPanel = new JPanel(new GridLayout(1, 2));
		
		buttonPanel.add(cancel);
		buttonPanel.add(submit);
		
		frame.add(label, BorderLayout.NORTH);
		frame.add(ipField, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(new Dimension(360, 90));
		frame.setLocationRelativeTo(Gui.frame);
		frame.setVisible(true);
		
		frame.getRootPane().setDefaultButton(submit);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onButtonPress(submit);
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				onCancel();
			}
		});
		
	}
	
	public void onButtonPress(JButton pressedButton) {
		
		Files.newFolder(fileP, ipField.getText());
		frame.dispose();
		
	}
	
	public void onCancel() {
		
		frame.dispose();
		
	}
	
}
