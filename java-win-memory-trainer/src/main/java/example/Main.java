package example;

import java.util.Arrays;

import com.sprogcoder.memory.JTrainer;
import com.sprogcoder.memory.MemoryUtils;
import com.sprogcoder.memory.exception.MemoryException;
import com.sprogcoder.memory.exception.WindowNotFoundException;

public class Main
{

	private static final int MEMORY_ADDRESS = 0x00010ABF;

	public static void main(String[] args) throws WindowNotFoundException, MemoryException
	{
		JTrainer jTrainer = new JTrainer(null, "Minesweeper");
		
		// Old Bytes
		jTrainer.writeProcessMemory(MEMORY_ADDRESS, new int[] { 0x22, 0x22, 0x22, 0x22 });
		byte[] oldBytes = jTrainer.readProcessMemory(MEMORY_ADDRESS, 4);
		System.out.println(Arrays.toString(MemoryUtils.bytesToUnsignedHexs(oldBytes)));
		
		// New Bytes
		jTrainer.writeProcessMemory(MEMORY_ADDRESS, new int[] { 0x90, 0x90, 0x90, 0x90 }); // nop, nop, nop, nop
		byte[] newBytes = jTrainer.readProcessMemory(MEMORY_ADDRESS, 4);
		System.out.println(Arrays.toString(MemoryUtils.bytesToUnsignedHexs(newBytes)));
	}
}
