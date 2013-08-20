package crazyputje.todo;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazyputje.todo.api.ToDoApi;

@SideOnly(Side.CLIENT)
public class GuiControlToDo extends GuiScreen {
	private GuiTextField txtField;
	private GuiButton addBtn;
	private ArrayList<GuiButton> toDoList;

	@Override
	public void updateScreen() {
		txtField.updateCursorCounter();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		buttonList.clear();
		buttonList.add(addBtn = new GuiButton(0, width / 2 - 100, 80, "Add"));

		toDoList = new ArrayList<GuiButton>();
		for (int i = 0; i < ToDoApi.getToDoList().size(); i++) {
			GuiButton b = new GuiButtonRemoveToDo(i + 10, width / 2 - 150, 40 + (i * 12), ToDoApi.getToDoList().get(i));
			toDoList.add(b);
			buttonList.add(b);
		}

		txtField = new GuiTextField(fontRenderer, width / 2 - 150, 60, 300, 20);
		txtField.setMaxStringLength(100);
		txtField.setFocused(true);
		addBtn.enabled = txtField.getText().trim().length() > 0;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton btn) {
		if (btn.enabled) {
			if (btn.id == 0) {
				ToDoApi.addToDo(txtField.getText());
				GuiButton b = new GuiButtonRemoveToDo(toDoList.size() + 10, width / 2 - 150, 40 + (toDoList.size() * 12), txtField.getText());
				toDoList.add(b);
				buttonList.add(b);
				txtField.setText("");
				addBtn.enabled = false;
			} else if (btn instanceof GuiButtonRemoveToDo) {
				ToDoApi.removeToDo(((GuiButtonRemoveToDo) btn).getMessage());
				buttonList.remove(btn);
				toDoList.remove(btn);
			}
			ToDo.instance.Save();
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		txtField.textboxKeyTyped(par1, par2);
		addBtn.enabled = txtField.getText().trim().length() > 0;

		if (par2 != 28 && par2 != 156) {
			if (par2 == 1) {
				mc.displayGuiScreen((GuiScreen) null);
			}
		} else {
			actionPerformed(addBtn);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		txtField.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		drawCenteredString(fontRenderer, "ToDo list", width / 2, 20, 16777215);

		int i = 0;
		for (GuiButton btn : toDoList) {
			i++;
			btn.yPosition = 40 + (i * 12);
		}

		try {
			updateTxtFieldYPos(txtField, 74 + (toDoList.size() > 0 ? toDoList.get(toDoList.size() - 1).yPosition - toDoList.get(0).yPosition : 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		addBtn.yPosition = 96 + (toDoList.size() > 0 ? toDoList.get(toDoList.size() - 1).yPosition - toDoList.get(0).yPosition : 0);
		txtField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}

	public void updateTxtFieldYPos(GuiTextField txt, int newYPos) throws Exception {
		Field f = GuiTextField.class.getDeclaredField("yPos");
		f.setAccessible(true);
		f.set(txt, newYPos);
	}
}
