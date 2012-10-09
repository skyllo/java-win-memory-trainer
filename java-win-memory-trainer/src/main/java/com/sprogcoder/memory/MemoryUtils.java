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

import java.util.Arrays;
import java.util.Collections;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

public class MemoryUtils
{

	/*
	 * Byte Array Conversion Functions
	 */
	public static int[] bytesToUnsignedInts(byte[] bytes)
	{
		int[] realvalues = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			realvalues[i] = bytes[i] & 0xFF;
		}

		return realvalues;
	}

	public static String[] bytesToUnsignedHexs(byte[] bytes)
	{
		String[] hexValues = new String[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			hexValues[i] = Integer.toHexString(bytes[i] & 0xFF);
		}

		return hexValues;
	}

	public static int bytesToSignedInt(byte[] bytes)
	{
		byte[] byteCopy = Arrays.copyOf(bytes, bytes.length);
		Collections.reverse(Bytes.asList(byteCopy));

		return Ints.fromByteArray(byteCopy);
	}

	/*
	 * Unsigned Functions
	 */
	public static int unsignedShortToInt(byte[] bytes)
	{
		int low = bytes[0] & 0xff;
		int high = bytes[1] & 0xff;

		return (int) (high << 8 | low);
	}

	public static long unsignedIntToLong(byte[] bytes)
	{
		long b1 = bytes[0] & 0xff;
		long b2 = bytes[1] & 0xff;
		long b3 = bytes[2] & 0xff;
		long b4 = bytes[3] & 0xff;

		return (long) ((b1 << 24) | (b2 << 16) | (b3 << 8) | (b4));
	}

}
