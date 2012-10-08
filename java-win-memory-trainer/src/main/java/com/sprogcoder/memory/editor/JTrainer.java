package com.sprogcoder.memory.editor;

import com.sprogcoder.memory.editor.JKernel32.MemoryException;
import com.sprogcoder.memory.editor.JUser32.WindowNotFoundException;
import com.sun.jna.platform.win32.WinDef.HWND;

public class JTrainer
{

	private int pid = -1;
	private String windowClass = "";
	private String windowText = "";
	private boolean firstTime = true;

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

	public void writeProcessMemory(int address, int value[])
	{
		int hProcess = JKernel32.openProcess(pid);
		JKernel32.writeProcessMemory(hProcess, address, value);
		JKernel32.closeHandle(hProcess);
	}

	public byte[] readProcessMemory(int address, int bufferSize) throws MemoryException
	{
		int hProcess = JKernel32.openProcess(pid);
		byte[] result = JKernel32.ReadMyProcessMemory(hProcess, address, bufferSize);
		JKernel32.closeHandle(hProcess);
		return result;
	}

	public boolean isUpdateNeeded() throws WindowNotFoundException
	{
		HWND windowHandle = JUser32.findWindow(windowClass, windowText);
		if (windowHandle != null)
		{
			int newPid = JUser32.getWindowThreadProcessId(windowHandle);
			if (newPid != this.pid)
			{
				this.pid = newPid;
				int hProcess = JKernel32.openProcess(this.pid);
				if (hProcess > 0)
				{
					return true;
				}
			}
			else if (firstTime == true)
			{
				firstTime = false;
				return true;
			}
		}
		return false;
	}

}
