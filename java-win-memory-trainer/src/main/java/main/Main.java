package main;

import java.util.Arrays;

import com.sprogcoder.memory.editor.JKernel32.MemoryException;
import com.sprogcoder.memory.editor.JTrainer;
import com.sprogcoder.memory.editor.JUser32.WindowNotFoundException;
import com.sprogcoder.memory.editor.MemoryUtils;

public class Main
{

	public static void main(String[] args) throws WindowNotFoundException, MemoryException
	{
		JTrainer jtrainer = new JTrainer("", "Minesweeper");
		byte[] bufferBytes = jtrainer.readProcessMemory(0x005FDE00, 4);
		System.out.println(Arrays.toString(bufferBytes));
		System.out.println(MemoryUtils.convertByteArrayToUnsignedInt(bufferBytes));
		System.out.println(Arrays.toString(MemoryUtils.convertByteArrayToSignedIntArray(bufferBytes)));
	}
}
