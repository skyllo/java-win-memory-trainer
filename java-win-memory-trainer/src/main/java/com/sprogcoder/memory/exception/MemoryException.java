package com.sprogcoder.memory.exception;

public class MemoryException extends Exception
{
	private static final long serialVersionUID = 7713157380441890236L;

	public MemoryException(String functionName, String message)
	{
		super(String.format("Kernel32 Function %s failed:\n\t%s", functionName, message));
	}
}