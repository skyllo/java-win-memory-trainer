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

package com.sprogcoder.memory.exception;

public class MemoryException extends Exception
{
	private static final long serialVersionUID = 7713157380441890236L;

	public MemoryException(String functionName, String message)
	{
		super(String.format("Kernel32 Function %s failed:\n\t%s", functionName, message));
	}
}