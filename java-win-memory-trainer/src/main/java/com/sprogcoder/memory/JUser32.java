package com.sprogcoder.memory;

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

		int GetWindowThreadProcessId(HWND hWnd, int[] lpdwProcessId);
	}

	public static HWND findWindow(String lpClassName, String lpWindowName) throws WindowNotFoundException
	{
		lpClassName = (lpClassName == null || lpClassName.equals("")) ? null : lpClassName;
		lpWindowName = (lpWindowName == null || lpWindowName.equals("")) ? null : lpWindowName;
		
		HWND hWnd = User32.SYNC_INSTANCE.FindWindowA(lpClassName, lpWindowName);
		if (hWnd == null)
		{
			throw new WindowNotFoundException(lpClassName, lpWindowName);
		}
		return hWnd;
	}

	public static int getWindowThreadProcessId(HWND hWnd)
	{
		int[] lpdwProcessId = new int[1];
		User32.SYNC_INSTANCE.GetWindowThreadProcessId(hWnd, lpdwProcessId);
		return lpdwProcessId[0];
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