package com.sprogcoder.memory.editor;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public class JUser32
{

	public interface User32 extends StdCallLibrary
	{
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
		User32 SYNC_INSTANCE = (User32) Native.synchronizedLibrary(INSTANCE);

		HWND FindWindowA(String lpClassName, String lpWindowName);

		int GetWindowThreadProcessId(HWND windowHandle, int[] lpdwProcessId);
	}

	public static HWND findWindow(String className, String windowName) throws WindowNotFoundException
	{
		if (className.equals(""))
		{
			className = null;
		}
		if (windowName.equals(""))
		{
			windowName = null;
		}
		HWND hwnd = User32.SYNC_INSTANCE.FindWindowA(className, windowName);
		if (hwnd == null)
		{
			throw new WindowNotFoundException(className, windowName);
		}
		return hwnd;
	}

	public static int getWindowThreadProcessId(HWND windowHandle)
	{
		int[] dwProcessId = new int[1];
		User32.SYNC_INSTANCE.GetWindowThreadProcessId(windowHandle, dwProcessId);
		return dwProcessId[0];
	}

	@SuppressWarnings("serial")
	public static class WindowNotFoundException extends Exception
	{
		public WindowNotFoundException(String className, String windowName)
		{
			super(String.format("Window Handle null for className: %s; windowName: %s", className, windowName));
		}
	}

}