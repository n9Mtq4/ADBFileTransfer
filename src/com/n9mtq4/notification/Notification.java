/*
 * NOTE: This is added by intellij IDE. Disregard this message if there is another copyright later in the file.
 * Copyright (C) 2014  Will (n9Mtq4) Bresnahan
 * A library to send notifications to the notification center in OSX
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

package com.n9mtq4.notification;

import java.io.*;

/**
 * Created by Will on 10/1/14.
 */
public class Notification {

	private String title;
	private String text;
	private String subtitle;
	private String soundName;

	/**
	 * Initializes a new Notification with a title
	 * and text content.
	 * These can be overridden later with {@link com.n9mtq4.notification.Notification#setTitle(String)}
	 * and {@link com.n9mtq4.notification.Notification#setText(String)}
	 * @param title The title the notification will have.
	 * @param text The text content the notification will have.
	 * */
	public Notification(String title, String text) {

		this.title = title;
		this.text = text;

	}

	/**
	 * Initializes a new Notification.
	 * */
	public Notification() {
//		dummy
	}

	/**
	 * Displays the notification with the current values.
	 * */
	public boolean display() {

		if (!(System.getProperty("os.name").toLowerCase().contains("mac"))) return false;

		if (sc(text) && sc(title)) {

			if (sc(subtitle) && sc(soundName)) {
				return displayTitleTextSubtitleSoundname(title, text, subtitle, soundName);
			}else if (sc(subtitle)) {
				return displayTitleTextSubtitle(title, text, subtitle);
			}else if (sc(soundName)) {
				return displayTitleTextSoundname(title, text, soundName);
			}else {
				return displayTitleText(title, text);
			}

		}else {
			return false;
		}

	}

	private boolean displayTitleText(String title, String text) {
		sendShellCommand("echo \"display notification \\\"" + text + "\\\" with title \\\"" + title + "\\\"\" | osascript");
		return true;
	}

	private boolean displayTitleTextSubtitle(String title, String text, String subtitle) {
		sendShellCommand("echo \"display notification \\\"" + text + "\\\" with title \\\"" + title + "\\\" subtitle \\\"" + subtitle + "\\\"\" | osascript");
		return true;
	}

	private boolean displayTitleTextSoundname(String title, String text, String soundName) {
		sendShellCommand("echo \"display notification \\\"" + text + "\\\" with title \\\"" + title + "\\\" sound name \\\"" + soundName + "\\\"\" | osascript");
		return true;
	}

	private boolean displayTitleTextSubtitleSoundname(String title, String text, String subtitle, String soundName) {
		sendShellCommand("echo \"display notification \\\"" + text + "\\\" with title \\\"" + title + "\\\" subtitle \\\"" + subtitle + "\\\" sound name \\\"" + soundName + "\\\"\" | osascript");
		return true;
	}

	private String sendShellCommand(String command) {
		String output = "";
		saveFile("./.t.command", command);
		run("chmod +x ./.t.command");
		run("./.t.command");
		deleteFile("./.t.command");
		return output;
	}

	private boolean deleteFile(String path) {
		File f = new File(path);
		return f.delete();
	}

	private void saveFile(String path, String text) {
		String[] tokens_String = text.split("\n");
		PrintStream ps;
		try {
			ps = new PrintStream(path);
			for (String i : tokens_String) {
				ps.println(i);
			}
			ps.close();
		} catch (IOException e) {
		}
	}

	private String run(String command) {
		Runtime cmd_Runtime = Runtime.getRuntime();
		try {
			Process cmdReturn_Process = cmd_Runtime.exec(command);
			InputStream inputStream = cmdReturn_Process.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			String out = "";
			while ((line = bufferedReader.readLine()) != null) {
				out += line + "\n";
			}
			return out;
		} catch (IOException e1) {
			return "error";
		}
	}

	private boolean sc(String text) {
		return text != null && !(text.trim().equalsIgnoreCase(""));
	}

	/**
	 * Gets the notification's currently set title.
	 * @return The notification's title
	 * @see Notification#setTitle(String)
	 * */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the notification's title.
	 * @param title The title to set the notification to
	 * @see Notification#getTitle()
	 * */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the notification's currently set text.
	 * @return The notification's text
	 * @see Notification#setText(String)
	 * */
	public String getText() {
		return text;
	}

	/**
	 * Sets the notification's text content.
	 * @param text The text to set the notification to
	 * @see com.n9mtq4.notification.Notification#getText()
	 * */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the notification's currently set subtitle.
	 * @return The notification's subtitle
	 * @see Notification#setSubtitle(String)
	 * */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * Sets the notification's subtitle.
	 * @param subtitle The subtitle to set the notification to
	 * @see com.n9mtq4.notification.Notification#getSubtitle()
	 * */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * Gets the notification's currently set sound's name.
	 * @return The name of the sound that will play when the notification displays
	 * @see Notification#setSoundName(String)
	 * */
	public String getSoundName() {
		return soundName;
	}

	/**
	 * Sets the notification's sound.
	 * @param soundName The sound's name that will play when the notification displays
	 * @see com.n9mtq4.notification.Notification#getSoundName()
	 * */
	public void setSoundName(String soundName) {
		this.soundName = soundName;
	}

}
