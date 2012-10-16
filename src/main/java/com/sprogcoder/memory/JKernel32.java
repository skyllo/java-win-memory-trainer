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

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public class JKernel32
{

	public interface Kernel32 extends StdCallLibrary
	{
		public static final int ACCESS_FLAGS = 0x0439;
		public static final int PROCESS_QUERY_INFORMATION = 0x0400;
		public static final int PROCESS_VM_READ = 0x0010;
		public static final int PROCESS_VM_WRITE = 0x0020;
		public static final int PROCESS_VM_OPERATION = 0x0008;
		public static final int PROCESS_ALL_ACCESS = 0x001F0FFF;
		public static final int FORMAT_MESSAGE_FROM_SYSTEM = 4096;
		public static final int FORMAT_MESSAGE_IGNORE_INSERTS = 512;

		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
		Kernel32 SYNC_INSTANCE = (Kernel32) Native.synchronizedLibrary(INSTANCE);

		int GetLastError();

		int FormatMessageW(int dwFlags, Pointer lpSource, int dwMessageId, int dwLanguageId, char[] lpBuffer, int nSize, Pointer Arguments);

		int OpenProcess(int dwDesiredAccess, int bInheritHandle, int dwProcessId);

		boolean CloseHandle(int hObject);

		int WriteProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int[] lpNumberOfBytesWritten);

		boolean ReadProcessMemory(int hProcess, int baseAddress, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead);

	}
	
	public static String getLastError()
	{
		int dwMessageId = Kernel32.SYNC_INSTANCE.GetLastError();
		char[] lpBuffer = new char[1024];
		int lenW = Kernel32.SYNC_INSTANCE.FormatMessageW(Kernel32.FORMAT_MESSAGE_FROM_SYSTEM | Kernel32.FORMAT_MESSAGE_IGNORE_INSERTS, null,
				dwMessageId, 0, lpBuffer, lpBuffer.length, null);
		String message = new String(lpBuffer, 0, lenW);
		return message;
	}

	public static int openProcess(int dwProcessId)
	{
		int hProcess = Kernel32.SYNC_INSTANCE.OpenProcess(Kernel32.PROCESS_VM_OPERATION | Kernel32.PROCESS_VM_WRITE | Kernel32.PROCESS_VM_READ, 0,
				dwProcessId);
		return hProcess;
	}

	public static boolean closeHandle(int hObject)
	{
		return Kernel32.SYNC_INSTANCE.CloseHandle(hObject);
	}

	public static boolean writeProcessMemory(int hProcess, int lpBaseAddress, int lpBuffer[])
	{
		boolean success = true;
		for (int i = 0; i < lpBuffer.length; i++)
		{
			int[] singleValue = new int[] { lpBuffer[i] };
			int valueLength = Integer.toHexString(singleValue[0]).length();
			int result = Kernel32.SYNC_INSTANCE.WriteProcessMemory(hProcess, lpBaseAddress + (i * 0x00000001), singleValue, valueLength, null);
			if (result <= 0)
			{
				success = false;
			}
		}
		return success;
	}

	public static byte[] readProcessMemory(int hProcess, int lpBaseAddress, int nSize)
	{
		IntByReference baseAddress = new IntByReference();
		baseAddress.setValue(lpBaseAddress);
		Memory lpBuffer = new Memory(nSize);

		boolean success = Kernel32.SYNC_INSTANCE.ReadProcessMemory(hProcess, lpBaseAddress, lpBuffer, nSize, null);

		if (success)
		{
			return lpBuffer.getByteArray(0, nSize);
		}
		else
		{
			return null;
		}
	}

}