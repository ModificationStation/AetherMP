package net.mine_diver.aethermp.proxy;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import net.minecraft.src.PlayerBaseAether;

public class PrintStreamFilter extends PrintStream {
	
	public PrintStreamFilter() throws FileNotFoundException {
		super(System.out);
	}
	
	@Override
	public void println(String s) {
		if (!(s.equals("Failed to read player data. Making new") && new Exception().getStackTrace()[1].getClassName().equals(PlayerBaseAether.class.getName())))
			super.println(s);
	}
}
