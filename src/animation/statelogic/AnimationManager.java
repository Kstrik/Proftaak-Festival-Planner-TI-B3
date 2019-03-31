package animation.statelogic;

import animation.animations.SpriteAnimation;
import com.sun.istack.internal.NotNull;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AnimationManager {
    private SpriteAnimationStateMachine animationStateMachine;
    private HashMap<String, SpriteAnimation> animations;
    private HashMap<String, SpriteAnimationState> animationStates;
    private HashMap<String, AnimationConstraint> animationConstraints;

    public AnimationManager(@NotNull BufferedImage image, @NotNull SpriteAnimationState spriteAnimationState){
        this.animations = new HashMap<String, SpriteAnimation>();
        this.animationStates = new HashMap<String, SpriteAnimationState>();
        this.animationConstraints = new HashMap<String, AnimationConstraint>();
        this.animationStateMachine = new SpriteAnimationStateMachine(image, spriteAnimationState);

        this.addAnimationState("default", spriteAnimationState);
    }

    public void update(double deltaTime){
        this.animationStateMachine.update(deltaTime);
    }

    public SpriteAnimationStateMachine getAnimationStateMachine() {
        return this.animationStateMachine;
    }

    private void addAnimation(String animationName, @NotNull SpriteAnimation spriteAnimation){
        if(!animationName.isEmpty()){
            this.animations.put(animationName, spriteAnimation);
        }
    }

    private void addAnimationConstraint(String animationName, @NotNull AnimationConstraint animationConstraint){
        if(!animationName.isEmpty()){
            this.animationConstraints.put(animationName + "Constraint", animationConstraint);
        }
    }

    public void addAnimationState(String animationName, @NotNull SpriteAnimationState spriteAnimationState){
        if(!animationName.isEmpty()){
            this.animationStates.put(animationName + "State", spriteAnimationState);
            this.addAnimation(animationName, spriteAnimationState.getSpriteAnimation());
            for(AnimationConstraint animationConstraint : spriteAnimationState.getAnimationConstraints()){
                this.addAnimationConstraint(animationName, animationConstraint);
            }
            this.animationStateMachine.attachAnimationState(spriteAnimationState);
        }
    }

    public SpriteAnimation getAnimation(String animationName){
        return this.animations.get(animationName);
    }

    public SpriteAnimationState getAnimationState(String animationStateName){
        return this.animationStates.get(animationStateName);
    }
}
