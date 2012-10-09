/***
  Copyright (c) 2012 sprogcoder <sprogcoder@gmail.com>
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
  
    http://www.apache.org/licenses/LICENSE-2.0
    
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.sprogcoder.memory;

import com.sprogcoder.memory.exception.MemoryException;
import com.sprogcoder.memory.exception.WindowNotFoundException;
import com.sun.jna.platform.win32.WinDef.HWND;

public class JTrainer
{

	private int pid = -1;
	private String windowClass = "";
	private String windowText = "";

	public JTrainer(int pid)
	{
		this.pid = pid;
	}

	public JTrainer(String windowClass, String windowText) throws WindowNotFoundException
	{
		this.windowClass = windowClass;
		this.windowText = windowText;
		this.pid = getProcessIdFromWindow(windowClass, windowText);
	}

	/*
	 * Internal Functions
	 */
	private HWND findWindow(String lpClassName, String lpWindowName) throws WindowNotFoundException
	{
		HWND hWnd = JUser32.findWindow(lpClassName, lpWindowName);
		
		if (hWnd == null)
		{
			throw new WindowNotFoundException(lpClassName, lpWindowName);
		}

		return hWnd;
	}

	private int getProcessIdFromWindow(String lpClassName, String lpWindowName) throws WindowNotFoundException
	{
		return getWindowTheadProcessId(findWindow(windowClass, windowText));
	}

	private int getWindowTheadProcessId(HWND hWnd)
	{
		return JUser32.getWindowThreadProcessId(hWnd);
	}
	
	private int openProcess(int dwProcessId) throws MemoryException
	{
		int hProcess = JKernel32.openProcess(pid);

		if (hProcess <= 0)
		{
			throw new MemoryException("OpenProcess", getLastError());
		}
		
		return hProcess;
	}
	
	/*
	 * External Functions
	 */
	public String getLastError()
	{
		return JKernel32.getLastError();
	}

	public boolean isProcessAvailable()
	{
		int hProcess = JKernel32.openProcess(pid);
		return (hProcess > 0);
	}
	
	public void retryProcess() throws WindowNotFoundException
	{
		this.pid = getProcessIdFromWindow(windowClass, windowText);
	}
	
	public byte[] readProcessMemory(int lpBaseAddress, int nSize) throws MemoryException
	{
		int hProcess = openProcess(pid);
		byte[] result = JKernel32.readProcessMemory(hProcess, lpBaseAddress, nSize);

		if (result == null)
		{
			throw new MemoryException("ReadProcessMemory", getLastError());
		}

		JKernel32.closeHandle(hProcess);

		return result;
	}

	public void writeProcessMemory(int lpBaseAddress, int lpBuffer[]) throws MemoryException
	{
		int hProcess = openProcess(pid);
		boolean success = JKernel32.writeProcessMemory(hProcess, lpBaseAddress, lpBuffer);

		if (!success)
		{
			throw new MemoryException("WriteProcessMemory", getLastError());
		}

		JKernel32.closeHandle(hProcess);
	}

}
