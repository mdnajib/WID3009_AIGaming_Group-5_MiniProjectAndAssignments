package pacman.entries.pacman.bt.leaf;

import pacman.entries.pacman.bt.Blackboard;
import pacman.entries.pacman.bt.Status;
import pacman.entries.pacman.bt.TreeNode;

import java.util.function.Supplier;

public class SetVariableLeaf extends ActionNode {

    private String variable;
    private Supplier<String> value;

    public SetVariableLeaf(Blackboard blackboard, String variable, Supplier<String> value) {
        super("Set Variable:" + variable, blackboard, null);

        this.variable = variable;
        this.value = value;
    }

    @Override
    protected void initialize() {}

    @Override
    protected Status update() {
        blackboard.set(variable, value.get());
        return Status.SUCCESS;
    }

    @Override
    protected void postUpdate() {}

}
