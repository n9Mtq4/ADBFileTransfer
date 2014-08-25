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

/**
 * Created by Will on 7/4/14.
 */
public class NewFileDialog extends Dialog {
	
	private String fileP;
	
	public NewFileDialog(String filePath) {
		
		super("New File", "Enter a New File Name.", "Create", "Cancel");
		this.fileP = filePath;
		
	}
	
	/**
	 * method is called when confirm button is pressed
	 * <br>
	 * NOTE: it is advised you dispose the JFrame (getFrame().dispose();)
	 *
	 * @param pressedButton button that is pressed
	 */
	@Override
	public void onButtonPress(JButton pressedButton) {
		
		Files.newFile(fileP, getTextField().getText());
		getFrame().dispose();
		
	}
	
}
