package pacman.entries.pacman.bt.leaf;

import pacman.entries.pacman.bt.Blackboard;
import pacman.entries.pacman.bt.Status;
import pacman.entries.pacman.bt.utils.IControllerActions;
import pacman.entries.pacman.bt.utils.Pair;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CollectClosestPillAction extends ActionNode {

    private Random random;

    public CollectClosestPillAction(Blackboard blackboard, IControllerActions controllerActions) {
        super("Collect closest pill action", blackboard, controllerActions);

        this.random = new Random();
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected Status update() {
        Game currentGameState = controllerActions.getGameState();

        int pacmanPosition = currentGameState.getPacmanCurrentNodeIndex();
        int closestPillPosition = currentGameState.getClosestNodeIndexFromNodeIndex(pacmanPosition, currentGameState.getActivePillsIndices(), Constants.DM.PATH);

        Constants.MOVE directionToPill = currentGameState.getNextMoveTowardsTarget(pacmanPosition, closestPillPosition, Constants.DM.PATH);

        controllerActions.setNextMove(directionToPill);

        return Status.SUCCESS;
    }

    @Override
    protected void postUpdate() {

    }
}
