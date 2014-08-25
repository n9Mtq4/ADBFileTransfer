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

import com.n9mtq4.adbfiletransfer.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Will on 8/24/14.
 */
public class Dialog {
	
	private JFrame frame;
	private JButton submit;
	private JButton cancel;
	private JPanel buttonPanel;
	private JTextField textField;
	private JLabel label;
	
	public Dialog(String title, String msg, String okButton, String cancelButton) {
		
		gui(title, msg, okButton, cancelButton);
		
	}
	
	public void gui(String title, String msg, String okButton, String cancelButton) {
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		label = new JLabel(msg);
		textField = new JTextField();
		submit = new JButton(okButton);
		cancel = new JButton(cancelButton);
		buttonPanel = new JPanel(new GridLayout(1, 2));
		
		buttonPanel.add(cancel);
		buttonPanel.add(submit);
		
		frame.add(label, BorderLayout.NORTH);
		frame.add(textField, BorderLayout.CENTER);
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
	
	/**
	 * method is called when confirm button is pressed
	 * <br>
	 * NOTE: it is advised you dispose the JFrame (getFrame().dispose();)
	 * @param pressedButton button that is pressed
	 * */
	public void onButtonPress(JButton pressedButton) {
	}
	
	public void onCancel() {
		
		frame.dispose();
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JButton getSubmit() {
		return submit;
	}

	public void setSubmit(JButton submit) {
		this.submit = submit;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
}
