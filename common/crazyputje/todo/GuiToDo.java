package crazyputje.todo;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazyputje.todo.api.ToDoApi;

@SideOnly(Side.CLIENT)
public class GuiToDo extends Gui {

	private static GuiToDo instance = new GuiToDo();

	public static void renderGui(float partialTicks, ScaledResolution resolution, int mouseX, int mouseY) {
		instance.render(partialTicks, resolution, mouseX, mouseY);
	}

	private void render(float partialTicks, ScaledResolution resolution, int mouseX, int mouseY) {
		ArrayList<String> wrappedList = Utils.wrapList(ToDoApi.getToDoList(), 32);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		int width = resolution.getScaledWidth();

		drawRect(2, 2, width / 3, 16 + (wrappedList.size() * 12), Integer.MIN_VALUE);
		drawCenteredString(fontRenderer, "ToDo list", width / 6, 4, 14737632);

		int height = 6;
		for (String toDo : wrappedList) {
			height += 12;
			drawString(fontRenderer, toDo, 4, height, 14737632);
		}
	}
}
