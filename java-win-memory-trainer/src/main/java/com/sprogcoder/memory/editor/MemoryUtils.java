package com.sprogcoder.memory.editor;

import java.util.Arrays;
import java.util.Collections;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

public class MemoryUtils
{

	public static int convertByteArrayToUnsignedInt(byte[] bytes)
	{
		byte[] byteCopy = Arrays.copyOf(bytes, bytes.length);
		Collections.reverse(Bytes.asList(byteCopy));
		return Ints.fromByteArray(byteCopy);
	}

	public static int[] convertByteArrayToSignedIntArray(byte[] bytes)
	{
		int[] realvalues = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			realvalues[i] = bytes[i] & 0xFF;
		}
		return realvalues;
	}

}
