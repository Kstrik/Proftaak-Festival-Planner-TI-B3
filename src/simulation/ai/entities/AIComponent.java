package simulation.ai.entities;

import com.sun.istack.internal.NotNull;
import model.entity.Agenda;
import model.entity.Classroom;
import model.entity.Item;
import simulation.ai.pathfinding.MapGenerator;
import simulation.ai.pathfinding.PathMap;
import simulation.ai.pathfinding.Pathfinder;
import simulation.map.world.Room;
import simulation.map.world.Seat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AIComponent {
    private SimulationCharacter simulationCharacter;
    private Pathfinder pathfinder;

    private HashMap<String, PathMap> pathMaps;
    private HashMap<String, Room> rooms;
    private Agenda agenda;
    private ArrayList<Item> scheduleItems;
    private int scheduleIndex;
    private Room targetRoom;
    private Seat targetSeat;
    private double timer;
    private boolean isTeacher;
    boolean isFindingSeat;
    boolean isFinished;

    public AIComponent(@NotNull SimulationCharacter simulationCharacter, @NotNull Pathfinder pathfinder, HashMap<String, PathMap> pathMaps, HashMap<String, Room> rooms,
                       Agenda agenda, ArrayList<Item> scheduleItems, boolean isTeacher){
        this.simulationCharacter = simulationCharacter;
        this.pathfinder = pathfinder;

        this.pathMaps = pathMaps;
        this.rooms = rooms;
        this.agenda = agenda;
        this.scheduleItems = scheduleItems;
        Collections.sort(this.scheduleItems, Item.dateComparator());
        this.scheduleIndex = 0;
        this.targetRoom = null;
        this.targetSeat = null;
        this.timer = 0.0;
        this.isTeacher = isTeacher;
        this.isFindingSeat = false;
        this.isFinished = false;
    }

    public void update(double deltaTime){
        this.timer += deltaTime;

        if(this.scheduleItems.size() != 0)
        {
            int startSeconds = getSecondsFromLocalDateTime(this.scheduleItems.get(this.scheduleIndex).getStart());
            int endSeconds = getSecondsFromLocalDateTime(this.scheduleItems.get(this.scheduleIndex).getEnd());
//            System.out.println("Start: " + startSeconds + " : End: " + endSeconds);
//            System.out.println("Current: " + this.timer);


            if(this.targetRoom != null && !this.isFinished)
            {
                if(this.targetRoom.getShape().contains(this.simulationCharacter.getPosition()))
                {
                    if(this.targetSeat == null)
                    {
                        if(!this.isTeacher)
                        {
                            for(Seat seat : this.targetRoom.getStudentSeats())
                            {
                                if(!seat.isTaken())
                                {
                                    this.targetSeat = seat;
                                    this.targetSeat.setTaken(true);
                                    this.isFindingSeat = true;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            this.targetSeat = this.targetRoom.getTeacherSeat();
                            this.targetSeat.setTaken(true);
                            this.isFindingSeat = true;
                        }
                    }
                    else
                    {
                        if(this.isFindingSeat)
                        {
                            this.setActivePathfinderMap(MapGenerator.generatePathMap(((PathMap)this.pathMaps.values().toArray()[0]).getWidth(),
                                    ((PathMap)this.pathMaps.values().toArray()[0]).getHeight(), this.targetSeat.getCenterPoint(), this.simulationCharacter.getWorld().getTileMap().getCollisionLayer()));
                            this.isFindingSeat = false;
                        }

                        if(this.targetSeat.getShape().contains(this.simulationCharacter.getPosition()))
                        {
                            if(this.pathfinder.getCurrentMap() == null)
                            {
//                                this.simulationCharacter.setDirectionAngle(Math.atan2(this.targetSeat.getCenterPoint().getY() - this.simulationCharacter.getPosition().getY(),
//                                        this.targetSeat.getCenterPoint().getX() - this.simulationCharacter.getPosition().getX()));
                                this.simulationCharacter.setMovementSpeed(0);
                                this.simulationCharacter.setDirectionAngle((Math.PI / 2) * 3);
                                this.simulationCharacter.getAnimationManager().getAnimationStateMachine().setAnimationState(this.simulationCharacter.getAnimationManager().getAnimationStateMachine().getDefaultAnimationState());
                                this.simulationCharacter.setPosition(this.targetSeat.getCenterPoint());
                            }
                            else
                            {
                                this.setActivePathfinderMap(null);
                            }
                        }
                        else
                        {
                            this.simulationCharacter.setMovementSpeed(this.simulationCharacter.getMaxMovementSpeed());
                            this.pathfinder.update();
//                            this.simulationCharacter.setDirectionAngle(Math.atan2(this.targetSeat.getCenterPoint().getY() - this.simulationCharacter.getPosition().getY(),
//                                        this.targetSeat.getCenterPoint().getX() - this.simulationCharacter.getPosition().getX()));
                        }
                    }
                }
                else
                {
                    this.pathfinder.update();
                }
            }
            else
            {
                if(this.isFinished)
                {
                    this.pathfinder.update();
                }
                else
                {
                    if(this.timer >= startSeconds && this.timer <= endSeconds)
                    {
                        this.targetRoom = this.rooms.get(this.agenda.getClassroomById(this.scheduleItems.get(this.scheduleIndex).getClassroomId()).getName().toLowerCase());
                    }
                    else
                    {
                        this.targetRoom = this.rooms.get("aula");
                    }
                    this.setActivePathfinderMap(this.pathMaps.get(this.targetRoom.getName()));
                }
            }

            if(this.timer > endSeconds)
            {
                if(this.scheduleItems.size() != this.scheduleIndex + 1)
                {
                    this.scheduleIndex++;
                    this.targetRoom = null;
                    this.targetSeat.setTaken(false);
                    this.targetSeat = null;
                    this.setActivePathfinderMap(null);
                }
                else if(this.targetRoom != null)
                {
                    this.isFinished = true;
                    this.targetRoom = null;
                    this.targetSeat.setTaken(false);
                    this.targetSeat = null;
                    this.setActivePathfinderMap(MapGenerator.generatePathMap(((PathMap)this.pathMaps.values().toArray()[0]).getWidth(),
                            ((PathMap)this.pathMaps.values().toArray()[0]).getHeight(), this.simulationCharacter.getWorld().getTileMap().getSpawnPoint(), this.simulationCharacter.getWorld().getTileMap().getCollisionLayer()));

                }
            }
            else if(this.timer > startSeconds && this.timer < endSeconds && this.targetRoom.getName().equals("aula"))
            {
                this.targetRoom = null;
                this.targetSeat.setTaken(false);
                this.targetSeat = null;
                this.setActivePathfinderMap(null);
            }

//        if(this.timer > startSeconds && this.timer < endSeconds)
//        {
//            if(this.targetRoom != null)
//            {
//                if(this.targetRoom.getShape().contains(this.simulationCharacter.getPosition()))
//                {
//                    if(this.targetSeat == null)
//                    {
//                        if(!this.isTeacher)
//                        {
//                            for(Seat seat : this.targetRoom.getStudentSeats())
//                            {
//                                if(!seat.isTaken())
//                                {
//                                    this.targetSeat = seat;
//                                    this.targetSeat.setTaken(true);
//                                    break;
//                                }
//                            }
//                        }
//                        else
//                        {
//                            this.targetSeat = this.targetRoom.getTeacherSeat();
//                            this.targetSeat.setTaken(true);
//                        }
//                    }
//                    else
//                    {
//                        this.simulationCharacter.setDirectionAngle(Math.atan2(this.targetSeat.getCenterPoint().getY() - this.simulationCharacter.getPosition().getY(),
//                                                                                this.targetSeat.getCenterPoint().getX() - this.simulationCharacter.getPosition().getX()));
//                    }
//                }
//                else
//                {
//                    this.pathfinder.update();
//                }
//            }
//            else
//            {
//                this.targetRoom = this.rooms.get(this.agenda.getClassroomById(this.scheduleItems.get(this.scheduleIndex).getClassroomId()).getName().toLowerCase());
//                this.setActivePathfinderMap(this.pathMaps.get(this.targetRoom.getName()));
//            }
//        }
//        else if(this.timer > endSeconds)
//        {
//            if(this.scheduleItems.size() != this.scheduleIndex + 1)
//            {
//                this.scheduleIndex++;
//                this.targetRoom = null;
//                this.targetSeat.setTaken(false);
//                this.targetSeat = null;
//                this.setActivePathfinderMap(null);
//            }
//        }
        }
    }

    public void setActivePathfinderMap(PathMap pathMap){
        this.pathfinder.setCurrentMap(pathMap);
    }

    private int getSecondsFromLocalDateTime(LocalTime localTime){
        int seconds = 0;
        seconds += (localTime.getHour() * 60) * 60;
        seconds += localTime.getMinute() * 60;
        seconds += localTime.getSecond();
        return seconds;
    }
}
