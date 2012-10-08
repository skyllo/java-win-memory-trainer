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
		this.pid = JUser32.getWindowThreadProcessId(findWindow(windowClass, windowText));
	}
	
	public int openProcess(int dwProcessId) throws MemoryException
	{
		int hProcess = JKernel32.openProcess(pid);
		
		if (hProcess <= 0)
		{
			throw new MemoryException("OpenProcess", getLastError());
		}
		return hProcess;
	}
	
	private HWND findWindow(String lpClassName, String lpWindowName) throws WindowNotFoundException
	{
		HWND hWnd = JUser32.findWindow(lpClassName, lpWindowName);
		if (hWnd == null)
		{
			throw new WindowNotFoundException(lpClassName, lpWindowName);
		}
		
		return hWnd;
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

	public String getLastError()
	{
		return JKernel32.getLastError();
	}

	public void retryProcess() throws WindowNotFoundException
	{
		this.pid = JUser32.getWindowThreadProcessId(JUser32.findWindow(windowClass, windowText));
	}
	
	public boolean isProcessAvailable()
	{
		int hProcess = JKernel32.openProcess(pid);
		return (hProcess > 0);
	}
	
}
