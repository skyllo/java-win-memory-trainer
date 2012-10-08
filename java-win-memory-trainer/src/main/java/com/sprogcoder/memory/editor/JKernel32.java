package com.sprogcoder.memory.editor;

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

		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
		Kernel32 SYNC_INSTANCE = (Kernel32) Native.synchronizedLibrary(INSTANCE);

		int OpenProcess(int dwDesiredAccess, int bInheritHandle, int dwProcessId);

		boolean CloseHandle(int handle);

		int WriteProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int[] lpNumberOfBytesWritten);

		boolean ReadProcessMemory(int hProcess, int baseAddress, Pointer outputBuffer, int nSize,
				IntByReference outNumberOfBytesRead);

	}

	public static int openProcess(int dwProcessId)
	{
		int hProcess = Kernel32.SYNC_INSTANCE.OpenProcess(Kernel32.PROCESS_VM_OPERATION | Kernel32.PROCESS_VM_WRITE
				| Kernel32.PROCESS_VM_READ, 0, dwProcessId);
		return hProcess;
	}

	public static boolean closeHandle(int handle)
	{
		return Kernel32.SYNC_INSTANCE.CloseHandle(handle);
	}

	public static void writeProcessMemory(int hProcess, int address, int values[])
	{
		for (int i = 0; i < values.length; i++)
		{
			int[] tempValue = new int[] { values[i] };
			int length = Integer.toHexString(tempValue[0]).length();
			Kernel32.SYNC_INSTANCE.WriteProcessMemory(hProcess, address + (i * 0x00000001), tempValue, length, null);
		}
	}

	public static byte[] ReadMyProcessMemory(int hProcess, int address, int bufferSize) throws MemoryException
	{
		IntByReference baseAddress = new IntByReference();
		baseAddress.setValue(address);
		Memory outputBuffer = new Memory(bufferSize);

		boolean success = Kernel32.SYNC_INSTANCE.ReadProcessMemory(hProcess, address, outputBuffer, bufferSize, null);

		if (success)
		{
			return outputBuffer.getByteArray(0, bufferSize);
		}
		else
		{
			throw new MemoryException("ReadMyProcessMemory", "");
		}
	}
	
	@SuppressWarnings("serial")
	public static class MemoryException extends Exception
	{
		public MemoryException(String functionName, String message)
		{
			super(String.format("Kernel32 Function %s failed: %s", functionName, message));
		}
	}

}