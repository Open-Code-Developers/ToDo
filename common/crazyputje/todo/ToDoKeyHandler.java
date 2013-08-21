package crazyputje.todo;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ToDoKeyHandler extends KeyHandler {

	private static KeyBinding[] bindings = new KeyBinding[] { new KeyBinding("Show ToDo", Keyboard.KEY_P), new KeyBinding("Edit ToDo", Keyboard.KEY_O) };
	private static boolean[] repeatings = new boolean[] { false, false };

	public ToDoKeyHandler() {
		super(bindings, repeatings);
	}

	@Override
	public String getLabel() {
		return "ToDo_KeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (kb.equals(bindings[0]) && tickEnd && Minecraft.getMinecraft().currentScreen == null) {
			GuiToDo.fade();
		} else if (kb.equals(bindings[1]) && tickEnd) {
			if (!(Minecraft.getMinecraft().currentScreen instanceof GuiControlToDo) && Minecraft.getMinecraft().currentScreen == null) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiControlToDo());
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
}
