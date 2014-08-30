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

/*
* this class is heavily based on Adam Outler's Shell.java which can be found here
* https://github.com/adamoutler/CASUAL/
* */

package com.n9mtq4.adbfiletransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class Shell implements Runnable{
	
	private boolean running;
	private boolean settingOutput;
	private String command;
	private ArrayList<String> cmdHistory;
	private String output;
	
	private Runtime runtime;
	private Process commandProcess;
	private PrintStream StdIn;
	private BufferedReader StdOut;
	private BufferedReader StdErr;
	
	public Shell() {
		
//		init();
		
	}
	
	public Shell(String initialCommand) {
		
		this.command = initialCommand;
		
		init();
		
	}
	
	public void execute() {
		
		start();
		
	}
	
	@Override
	public void run() {
		
//		runs the command in separate thread to keep system running
		String line;
		try {
			
			running = true;
			commandProcess = new ProcessBuilder(command).start();
			StdIn = new PrintStream(commandProcess.getOutputStream());
			StdOut = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
			StdErr = new BufferedReader(new InputStreamReader(commandProcess.getErrorStream()));
			
			while (running) {
				
//				reset output
				output = "";
				
				settingOutput = true;
				while ((line = StdErr.readLine()) != null) {
					
					output = output + line + "\n";
					
				}
				
				while ((line = StdOut.readLine()) != null) {
					
					output = output + line + "\n";
					
					while ((line = StdOut.readLine()) != null) {
						
						output = output + line + "\n";
						
					}
					
				}
				settingOutput = false;
				
				try {
					commandProcess.exitValue();
					running = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void pushStdIn(String StdIn) {
		
//		adds command to history
		cmdHistory.add(StdIn);
//		runs the command
//		TODO: run the command
		
		
		
	}
	
	public String waitForOutput() {
		
		while (running) {
//			dummy - holds thread until command has finished executing
		}
		
		return getOutput();
		
	}
	
	private void start() {
		
		Thread t = new Thread((this));
		t.start();
		
	}
	
	private void init() {
		
		this.running = false;
		this.settingOutput = false;
		this.runtime = Runtime.getRuntime();
		
	}
	
	public String getOutput() {
		
		while (settingOutput) {
//			dummy
		}
		return output;
		
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public ArrayList<String> getCmdHistory() {
		return cmdHistory;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	
//	legacy code for use before Shell is rewritten
/*
 * Copyright (c) 2011 Adam Outler
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
	/**
	 *
	 * @author adam
	 */
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
			
			Debug.print(AllText);
			return AllText;
			
		} catch (IOException ex) {
			
			return "CritERROR!!!";
			
		}
		
	}

	public String sendShellCommand(String cmd) {
		
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
			
			Debug.print(AllText);
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
