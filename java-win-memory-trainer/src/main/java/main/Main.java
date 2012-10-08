package main;

import java.util.Arrays;

import com.sprogcoder.memory.JTrainer;
import com.sprogcoder.memory.JKernel32.MemoryException;
import com.sprogcoder.memory.JUser32.WindowNotFoundException;

public class Main
{

	public static void main(String[] args) throws WindowNotFoundException, MemoryException
	{
		JTrainer jtrainer = new JTrainer(null, "Minesweeper");
		jtrainer.writeProcessMemory(0x00343A98, new int[] { 0x22, 0x22, 0x22, 0x22 });
		byte[] oldBytes = jtrainer.readProcessMemory(0x00343A98, 4);
		System.out.println(Arrays.toString(oldBytes));
		jtrainer.writeProcessMemory(0x00343A98, new int[] { 0x90, 0x90, 0x90, 0x90 });
		byte[] newBytes = jtrainer.readProcessMemory(0x00343A98, 4);
		System.out.println(Arrays.toString(newBytes));
	}
}
