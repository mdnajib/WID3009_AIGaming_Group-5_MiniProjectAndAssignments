package pacman.entries.pacman.bt.composite;

import pacman.entries.pacman.bt.Status;
import pacman.entries.pacman.bt.TreeNode;

public class Sequence extends CompositeNode {

    public Sequence(String name) {
        super(name + " Sequence");
    }

    @Override
    public void initialize() {

    }

    @Override
    public Status update() {
        // iterate over every node
        for(TreeNode child : children) {
            Status childStatus = child.tick();

            if(childStatus != Status.SUCCESS) {
                return childStatus;
            }
        }

        return Status.SUCCESS;
    }

    @Override
    public void postUpdate() {

    }
}
