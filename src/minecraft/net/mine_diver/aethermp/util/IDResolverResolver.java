package net.mine_diver.aethermp.util;

import java.lang.reflect.Method;

import net.minecraft.src.IDResolver;

public class IDResolverResolver {
	
	public static final boolean IDResolverInstalled;
	public static final Method GetlongNameMethod;
	public static final Method RemoveEntryMethod;
	public static final Method StorePropertiesMethod;
	public static final Method TrimMCPMethod;
	static {
		boolean bool = true;
		try {
			Class.forName("IDResolver");
		} catch (Exception e) {
		    bool = false;
		}
		IDResolverInstalled = bool;
		Method method = null;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("GetlongName", Object.class, int.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		GetlongNameMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("RemoveEntry", String.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		RemoveEntryMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("StoreProperties");
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		StorePropertiesMethod = method;
		if (IDResolverInstalled)
			try {
				method = IDResolver.class.getDeclaredMethod("TrimMCP", String.class);
				method.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		TrimMCPMethod = method;
	}
}
