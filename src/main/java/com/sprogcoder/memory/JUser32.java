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

	public static HWND findWindow(String lpClassName, String lpWindowName)
	{
		lpClassName = (lpClassName == null || lpClassName.equals("")) ? null : lpClassName;
		lpWindowName = (lpWindowName == null || lpWindowName.equals("")) ? null : lpWindowName;

		HWND hWnd = User32.SYNC_INSTANCE.FindWindowA(lpClassName, lpWindowName);
		return hWnd;
	}

	public static int getWindowThreadProcessId(HWND hWnd)
	{
		int[] lpdwProcessId = new int[1];
		User32.SYNC_INSTANCE.GetWindowThreadProcessId(hWnd, lpdwProcessId);
		return lpdwProcessId[0];
	}

}