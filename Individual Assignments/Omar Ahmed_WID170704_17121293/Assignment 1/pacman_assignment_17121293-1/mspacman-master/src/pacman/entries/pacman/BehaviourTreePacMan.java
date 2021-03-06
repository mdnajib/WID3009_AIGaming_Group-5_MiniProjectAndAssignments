package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.entries.pacman.bt.Blackboard;
import pacman.entries.pacman.bt.TreeNode;
import pacman.entries.pacman.bt.composite.Selector;
import pacman.entries.pacman.bt.composite.Sequence;
import pacman.entries.pacman.bt.leaf.CheckVariableLeaf;
import pacman.entries.pacman.bt.leaf.CollectClosestPillAction;
import pacman.entries.pacman.bt.leaf.FleeAction;
import pacman.entries.pacman.bt.leaf.SetVariableLeaf;
import pacman.entries.pacman.bt.utils.IControllerActions;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BehaviourTreePacMan extends Controller<MOVE> implements IControllerActions {
    private MOVE myMove = MOVE.NEUTRAL;

    private TreeNode root;
    private Blackboard blackboard;

    private Game currentGameState;

    public BehaviourTreePacMan() {
        blackboard = new Blackboard();

        // Build the Behavior Tree
        Sequence rootSequence = new Sequence("Root");
        root = rootSequence;

        // 1) update common variables
        SetVariableLeaf setClosestEnemyDistance = new SetVariableLeaf(blackboard, "enemy.distance", () -> ""+getDangerDistance());
        Selector gatherEscapeSelector = new Selector("Gather-Escape");

        rootSequence.addChild(setClosestEnemyDistance);
        rootSequence.addChild(gatherEscapeSelector);

        // 2 a) build gather sequence
        Sequence gatherSequence = new Sequence("Gather");
        CheckVariableLeaf canGatherCheck = new CheckVariableLeaf(blackboard, "enemy.distance", (dist) -> Integer.parseInt(dist) > 11);
        CollectClosestPillAction collectClosestPillAction = new CollectClosestPillAction(blackboard, this);

        gatherSequence.addChild(canGatherCheck);
        gatherSequence.addChild(collectClosestPillAction);

        // 2 b) build escape sequence
        Sequence escapeSequence = new Sequence("Escape");
        FleeAction fleeAction = new FleeAction(blackboard, this);

        escapeSequence.addChild(fleeAction);


        gatherEscapeSelector.addChild(gatherSequence);
        gatherEscapeSelector.addChild(escapeSequence);

    }

    public MOVE getMove(Game game, long timeDue) {
        currentGameState = game;

        root.tick();

        return myMove;
    }



    private int getDangerDistance() {

        int minDistance = Integer.MAX_VALUE;
        int current = currentGameState.getPacmanCurrentNodeIndex();

        for(Constants.GHOST ghost : Constants.GHOST.values()) {
            if(currentGameState.getGhostEdibleTime(ghost) == 0 && currentGameState.getGhostLairTime(ghost) == 0) {
                minDistance = Math.min(minDistance, currentGameState.getShortestPathDistance(current, currentGameState.getGhostCurrentNodeIndex(ghost)));
            }
        }

        return minDistance;
    }

    @Override
    public void setNextMove(MOVE nextMove) {
        this.myMove = nextMove;
    }

    @Override
    public Game getGameState() {
        return currentGameState;
    }
}