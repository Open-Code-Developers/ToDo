package crazyputje.todo.api;

import java.util.ArrayList;

public class ToDoApi {

	private static ArrayList<String> toDoList = new ArrayList<String>();

	public static void addToDo(String message) {
		toDoList.add(message);
	}

	public static void removeToDo(String message) {
		toDoList.remove(message);
	}

	public static void remoteToDo(int index) {
		toDoList.remove(index);
	}

	public static ArrayList<String> getToDoList() {
		return toDoList;
	}

	public static void clearList() {
		toDoList.clear();
	}
}
