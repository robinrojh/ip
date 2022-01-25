public class SortCommand extends Command {
    private TaskList.SortType type;
    public SortCommand(TaskList.SortType type, String[] inputArray) {
        super(inputArray);
        this.type = type;
    }

    @Override
    public void executeCommand(TaskList taskList, Ui ui, Storage storage) {
        taskList.sortTaskList(this.type);
        storage.save(taskList);
    }
}