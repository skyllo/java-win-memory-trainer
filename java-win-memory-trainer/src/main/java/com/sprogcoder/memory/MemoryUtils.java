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

	public static String[] convertByteArrayToHex(byte[] bytes)
	{
		String[] hexValues = new String[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			hexValues[i] = Integer.toHexString(bytes[i] & 0xFF);
		}
		return hexValues;
	}

}
