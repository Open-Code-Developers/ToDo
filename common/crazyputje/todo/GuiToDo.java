package crazyputje.todo;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazyputje.todo.api.ToDoApi;

@SideOnly(Side.CLIENT)
public class GuiToDo extends Gui {

	private static GuiToDo instance = new GuiToDo();
	private int updateCounter = 200;
	private boolean fadeIn = false;
	private boolean fadeOut = true;

	public static void renderGui(float partialTicks, ScaledResolution resolution, int mouseX, int mouseY) {
		instance.render(partialTicks, resolution, mouseX, mouseY);
	}

	public static void fade() {
		instance.fadeOut = !instance.fadeOut;
		instance.fadeIn = !instance.fadeIn;
		if (instance.fadeOut) {
			instance.updateCounter = 175;
		}
		if (instance.fadeIn) {
			instance.updateCounter = 200;
		}
	}

	private void render(float partialTicks, ScaledResolution resolution, int mouseX, int mouseY) {
		if (fadeIn) {
			--updateCounter;
		} else if (fadeOut) {
			++updateCounter;
		}

		GL11.glPushMatrix();
		int k1 = updateCounter;

		if (updateCounter > 200) {
			k1 = 200;
		}

		if (updateCounter < 0) {
			k1 = 0;
		}

		int l1;
		float f = 1.0F * 0.9F + 0.1F;

		double d0 = k1 / 200.0D;
		d0 = 1.0D - d0;
		d0 *= 10.0D;

		if (d0 < 0.0D) {
			d0 = 0.0D;
		}

		if (d0 > 1.0D) {
			d0 = 1.0D;
		}

		d0 *= d0;
		l1 = (int) (255.0D * d0);

		l1 = (int) (l1 * f);

		if (l1 > 3) {
			ArrayList<String> wrappedList = Utils.wrapList(ToDoApi.getToDoList(), 40);
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			int width = resolution.getScaledWidth();

			drawRect(2, 2, width / 3, 14 + (wrappedList.size() * 10), l1 / 2 << 24);
			GL11.glScalef(1F, 1F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			drawCenteredString(fontRenderer, "ToDo list", width / 6, 4, 16777215 + (l1 << 24));

			GL11.glScalef(0.8F, 0.8F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			int height = 6;
			for (String toDo : wrappedList) {
				height += 12;
				drawString(fontRenderer, toDo, 4, height, 16777215 + (l1 << 24));
			}
		}
		GL11.glPopMatrix();
	}
}
