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

import javax.swing.*;
import java.awt.*;

/**
 * Created by Will on 6/30/14.
 */
public class Message {
	
	public static Dimension DEFAULT_SIZE = new Dimension(400, 90);
	public static Dimension URL_SIZE = new Dimension(600, 90);
	
	private JFrame window;
	
	public Message(String title, String message) {
		
		window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		window.add(new JLabel(message), BorderLayout.CENTER);
		
		window.pack();
		window.setSize(DEFAULT_SIZE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
	}
	
	public Message(String title, String message, Dimension size, JFrame frame) {
		
		window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		window.add(new JLabel(message), BorderLayout.CENTER);
		
		window.pack();
		window.setSize(size);
		window.setLocationRelativeTo(frame);
		window.setResizable(false);
		window.setVisible(true);
		
	}

	public void close() {
		
		this.window.dispose();
		
	}
	
}
