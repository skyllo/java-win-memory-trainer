package com.sprogcoder.memory;

import com.sprogcoder.memory.JKernel32.MemoryException;
import com.sprogcoder.memory.JUser32.WindowNotFoundException;

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
		this.pid = JUser32.getWindowThreadProcessId(JUser32.findWindow(windowClass, windowText));
	}

	public void writeProcessMemory(int lpBaseAddress, int lpBuffer[]) throws MemoryException
	{
		int hProcess = JKernel32.openProcess(pid);
		JKernel32.writeProcessMemory(hProcess, lpBaseAddress, lpBuffer);
		JKernel32.closeHandle(hProcess);
	}

	public byte[] readProcessMemory(int lpBaseAddress, int nSize) throws MemoryException
	{
		int hProcess = JKernel32.openProcess(pid);
		byte[] result = JKernel32.readProcessMemory(hProcess, lpBaseAddress, nSize);
		JKernel32.closeHandle(hProcess);
		return result;
	}
	
	public String getLastError()
	{
		return JKernel32.getLastError();
	}
	
	public void retryProcess() throws WindowNotFoundException
	{
		if (!isProcessAvailable())
		{
			this.pid = JUser32.getWindowThreadProcessId(JUser32.findWindow(windowClass, windowText));
		}
	}

	public boolean isProcessAvailable()
	{
		try
		{
			JKernel32.openProcess(this.pid);
			return true;
		}
		catch (MemoryException e)
		{
			//
		}
		return false;
	}

}
