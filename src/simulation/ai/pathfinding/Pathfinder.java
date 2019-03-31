package simulation.ai.pathfinding;

import com.sun.istack.internal.NotNull;
import simulation.ai.entities.SimulationCharacter;

public class Pathfinder {
    private SimulationCharacter simulationCharacter;
    private PathMap currentMap;
    private int tilePixelSize;

    public Pathfinder(@NotNull SimulationCharacter simulationCharacter, int tilePixelSize){
        this.simulationCharacter = simulationCharacter;
        this.tilePixelSize = tilePixelSize;
    }

    public void update() {
        if(this.currentMap != null){
            PathNode pathNode = this.currentMap.getNodeWithinCoordinates(this.simulationCharacter.getPosition(), this.tilePixelSize);
            if(pathNode != null){
                this.simulationCharacter.setDirectionAngle(pathNode.getRotation());

                if(pathNode.getDistance() == 0){
                    this.simulationCharacter.setMovementSpeed(0);
                    this.simulationCharacter.getAnimationManager().getAnimationStateMachine().setAnimationState(this.simulationCharacter.getAnimationManager().getAnimationStateMachine().getDefaultAnimationState());
                }
                else {
                    this.simulationCharacter.setMovementSpeed(this.simulationCharacter.getMaxMovementSpeed());
                }
            }
        }
    }

    public PathMap getCurrentMap() {
        return this.currentMap;
    }

    public void setCurrentMap(PathMap currentMap) {
        this.currentMap = currentMap;
    }
}
