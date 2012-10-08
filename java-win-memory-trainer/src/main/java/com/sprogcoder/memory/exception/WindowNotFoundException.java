package com.sprogcoder.memory.exception;

public class WindowNotFoundException extends Exception
{
	private static final long serialVersionUID = -7065188795268185722L;

	public WindowNotFoundException(String className, String windowName)
	{
		super(String.format("Window Handle null for className: %s; windowName: %s", className, windowName));
	}
}