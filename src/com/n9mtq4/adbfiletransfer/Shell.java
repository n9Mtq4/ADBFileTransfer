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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author adam
 */
//define <output and input> to this abstract class
public class Shell {

	//for internal access
	public Shell() {
	}
	//for external access
	//Send a command to the shell


	public String sendShellCommand(String[] cmd) {

		Debug.printCMD(cmd);
		String AllText = "";
		try {

			String line;
			Process process = new ProcessBuilder(cmd).start();
			BufferedReader STDOUT = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader STDERR = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			try {

				process.waitFor();

			} catch (InterruptedException ex) {

			}

			while ((line = STDERR.readLine()) != null) {

				AllText = AllText + "\n" + line;

			}

			while ((line = STDOUT.readLine()) != null) {

				AllText = AllText + "\n" + line;
				while ((line = STDERR.readLine()) != null) {

					AllText = AllText + "\n" + line;

				}

			}
			
			System.out.println(AllText);
			return AllText;

		} catch (IOException ex) {

			return "CritERROR!!!";

		}

	}


	public String arrayToString(String[] stringarray) {
		String str = " ";
		for (int i = 0; i < stringarray.length; i++) {
			str = str + " " + stringarray[i];
		}
		System.out.println("arrayToString " + stringarray + " expanded to: " + str);
		return str;
	}

	private boolean testForException(Process process) {

		if (process.exitValue() >= 0) {

			return false;
		} else {
			return true;
		}

	}

}
