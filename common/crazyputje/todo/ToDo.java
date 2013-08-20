package crazyputje.todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import crazyputje.todo.api.ToDoApi;

@Mod(modid = ToDo.MOD_ID, name = ToDo.MOD_NAME, version = ToDo.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ToDo {

	public final static String MOD_ID = "todo", MOD_NAME = "ToDO", VERSION = "v 1.0";
	public boolean guiToDo_open = false;
	public String worldLoaded;

	@Instance
	public static ToDo instance;

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		if (event.getSide().isServer())
			return;
		KeyBindingRegistry.registerKeyBinding(new ToDoKeyHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void OnRender(RenderGameOverlayEvent.Post event) {
		ServerData data = null;
		try {
			data = Utils.getServerData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers.length > 0 && !MinecraftServer.getServer().func_130014_f_().getWorldInfo().getWorldName().equals(worldLoaded)) {
			worldLoaded = MinecraftServer.getServer().func_130014_f_().getWorldInfo().getWorldName();
			Load();
		} else if (data != null && data.serverIP != null) {
			worldLoaded = data.serverIP;
			Load();
		}

		System.out.println(worldLoaded);

		if (event.type.equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
			if (guiToDo_open) {
				GuiToDo.renderGui(event.partialTicks, event.resolution, event.mouseX, event.mouseY);
			}
		}
	}

	public void Save() {
		try {
			Writer output = new BufferedWriter(new FileWriter(Utils.getSaveFile(worldLoaded)));
			try {
				for (String s : ToDoApi.getToDoList()) {
					output.write(s);
					output.write(System.getProperty("line.separator"));
				}

			} finally {
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Load() {
		ToDoApi.clearList();
		if (Utils.getSaveFile(worldLoaded).exists()) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(Utils.getSaveFile(worldLoaded)));
				try {
					String line = null;
					while ((line = input.readLine()) != null && !line.isEmpty()) {
						ToDoApi.addToDo(line);
					}
				} finally {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
