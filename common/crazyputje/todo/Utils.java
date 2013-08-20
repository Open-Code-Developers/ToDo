package crazyputje.todo;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class Utils {

	public static File getSaveFile(String name) {
		File saveFile = new File(Minecraft.getMinecraft().mcDataDir, "/todo/" + name + ".dat");
		saveFile.getParentFile().mkdir();
		return saveFile;
	}

	public static ServerData getServerData() {
		return (ServerData) ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), isObfuscated() ? "field_71422_O" : "currentServerData");
	}

	public static ArrayList<String> wrapList(ArrayList<String> list, int len) {
		final ArrayList<String> wrappedList = new ArrayList<String>();

		for (String s : list) {
			s = "> " + s;
			for (String w : wrapText(s, len)) {
				if (!w.startsWith("> ")) {
					w = "  " + w;
				}
				wrappedList.add(w);
			}
		}
		return wrappedList;

	}

	public static String[] wrapText(String text, int len) {
		if (text == null)
			return new String[] {};

		if (len <= 0)
			return new String[] { text };

		if (text.length() <= len)
			return new String[] { text };

		final char[] chars = text.toCharArray();
		final Vector<String> lines = new Vector<String>();
		final StringBuffer line = new StringBuffer();
		final StringBuffer word = new StringBuffer();

		for (final char c : chars) {
			word.append(c);

			if (c == ' ') {
				if ((line.length() + word.length()) > len) {
					lines.add(line.toString());
					line.delete(0, line.length());
				}

				line.append(word);
				word.delete(0, word.length());
			}
		}

		if (word.length() > 0) {
			if ((line.length() + word.length()) > len) {
				lines.add(line.toString());
				line.delete(0, line.length());
			}
			line.append(word);
		}

		if (line.length() > 0) {
			lines.add(line.toString());
		}

		final String[] ret = new String[lines.size()];
		int c = 0;
		for (final Enumeration<String> e = lines.elements(); e.hasMoreElements(); c++) {
			ret[c] = e.nextElement();
		}

		return ret;
	}

	public static boolean isObfuscated() {
		try {
			Minecraft.class.getDeclaredField("currentServerData");
			return false;
		} catch (Exception e) {
			return true;
		}

	}
}
