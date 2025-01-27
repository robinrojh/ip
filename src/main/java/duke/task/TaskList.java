package duke.task;

import java.util.ArrayList;
import java.util.Comparator;

import duke.exception.DukeInvalidArgumentException;
import duke.ui.Ui;


/**
 * Stores data in an ArrayList and executes commands.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Ui ui;

    /**
     * Instantiates an empty ArrayList and new Ui object.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.ui = new Ui();
    }

    /**
     * Instantiates an empty ArrayList
     *
     * @param ui Ui object.
     */
    public TaskList(Ui ui) {
        tasks = new ArrayList<>();
        this.ui = ui;
    }

    /**
     * Sets tasks and ui to given argument.
     *
     * @param tasks Task ArrayList with Task objects.
     * @param ui    Ui object.
     */
    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Returns the size of the task arraylist.
     *
     * @return int Size of the arrayList of tasks
     */
    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds all Task objects that contains the target String.
     * Shows the UI by creating a new temporary TaskList and using toString on it.
     *
     * @param target String Target String to be searched for.
     */
    public void findTask(String target) {
        TaskList taskList = new TaskList(new ArrayList<Task>(), ui);
        for (Task task : tasks) {
            if (task.content.contains(target)) {
                taskList.tasks.add(task);
            }
        }
        ui.showUiForTaskList(taskList);
    }

    /**
     * Marks the task at index as complete.
     *
     * @param index Index of the task to mark (starts from 1).
     */
    public void markTask(int index) throws DukeInvalidArgumentException {
        if (tasks.size() == 0) {
            ui.setNextMessage("No tasks are in the list right now!");
        } else if (index < 1 || index > tasks.size()) {
            throw new DukeInvalidArgumentException("index is not in the list");
        }
        ui.setNextMessage(tasks.get(index - 1).mark());
    }

    /**
     * Unmakrs the task at index as incomplete.
     *
     * @param index Index of the task to unmark (starts from 1).
     */
    public void unmarkTask(int index) throws DukeInvalidArgumentException {
        if (tasks.size() == 0) {
            ui.setNextMessage("No tasks are in the list right now!");
        } else if (index < 1 || index > tasks.size()) {
            throw new DukeInvalidArgumentException("index is not in the list");
        }
        ui.setNextMessage(tasks.get(index - 1).unmark());
    }

    /**
     * Adds the specified task into the tasks ArrayList.
     *
     * @param task Task to be added into tasks ArrayList.
     */
    public void addTask(Task task) {
        // divided in case I need to add other features like parsing date
        tasks.add(task);
        ui.showUiForAdd(task, tasks.size());
    }

    /**
     * Deletes the task at index.
     *
     * @param index Index of the task to be deleted (starts from 1)
     */
    public void deleteTask(int index) throws DukeInvalidArgumentException {
        if (tasks.size() == 0) {
            ui.setNextMessage("No tasks are in the list right now!");
        } else if (index < 1 || index > tasks.size()) {
            throw new DukeInvalidArgumentException("index is not in the list");
        } else {
            Task removedTask = tasks.remove(index - 1);
            ui.showUiForDelete(removedTask, tasks.size());
        }
    }

    /**
     * Specifies the sorting order as an enum.
     */
    public enum SortType {
        DATE,
        CONTENT;

        /**
         * Returns enum as a String in lower case.
         *
         * @return String date or content.
         */
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        /**
         * Compares the enum's name to the input.
         *
         * @param input Input String.
         * @return boolean True if the name and input are equal.
         */
        public boolean equals(String input) {
            if (input.equalsIgnoreCase(this.name())) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Sorts the taskList based on the sortType in ascending order.
     *
     * @param sortType Enum to specify what to sort based on.
     */
    public void sortTaskList(SortType sortType) {
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (sortType.equals(SortType.DATE)) {
                    return task1.compareTo(task2);
                } else if (sortType.equals(SortType.CONTENT)) {
                    return task1.content.compareTo(task2.content);
                } else {
                    return -1;
                }
            }
        };
        tasks.sort(comparator);
        ui.showUiForSort(this, sortType);
    }

    /**
     * Compares two taskLists based on the content Tasks.
     *
     * @param taskList Other taskList to be compared with.
     * @return boolean True if two taskLists have same tasks.
     */
    @Override
    public boolean equals(Object taskList) {
        TaskList taskListCast = (TaskList) taskList;
        if (taskListCast.tasks.size() != this.tasks.size()) {
            return false;
        } else {
            for (int k = 0; k < tasks.size(); k++) {
                if (tasks.get(k).compareTo(taskListCast.tasks.get(k)) != 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Returns the String representation of this taskList.
     *
     * @return String String representation of this TaskList object.
     */
    @Override
    public String toString() {
        String result = "";
        ArrayList<Task> resultTasks = this.tasks;
        for (int index = 0; index < resultTasks.size(); index++) {
            int order = (index + 1);
            Task task = resultTasks.get(index);
            if (index == resultTasks.size() - 1) {
                result += order + ": " + task.toString();
            } else {
                result += order + ": " + task.toString() + "\n";
            }
        }
        return result;
    }

}
