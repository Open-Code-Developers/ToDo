package crazyputje.todo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonRemoveToDo extends GuiButton {

	private String message;

	public GuiButtonRemoveToDo(int id, int x, int y, String toDo) {
		super(id, x, y, Minecraft.getMinecraft().fontRenderer.getStringWidth(toDo), 12, "");
		message = toDo;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (drawButton) {
			drawString(mc.fontRenderer, "> " + message, xPosition, yPosition, 16777215);
		}
	}

	public String getMessage() {
		return message;
	}
}
