package animation.statelogic;

import animation.animations.SpriteAnimation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteAnimationState {
    private SpriteAnimation spriteAnimation;
    private ArrayList<AnimationConstraint> animationConstraints;
    private SpriteAnimationStateMachine stateMachine;

    public SpriteAnimationState(SpriteAnimation spriteAnimation) {
        this.spriteAnimation = spriteAnimation;

        this.animationConstraints = new ArrayList<AnimationConstraint>();
    }

    public void attachToStateMachine(SpriteAnimationStateMachine stateMachine) {
        if(stateMachine != null) {
            this.stateMachine = stateMachine;
        }
    }

    public void addConstraint(AnimationConstraint animationConstraint) {
        if(animationConstraint != null) {
            this.animationConstraints.add(animationConstraint);
        }
    }

    public void addConstraints(ArrayList<AnimationConstraint> animationConstraints) {
        if(animationConstraints != null && animationConstraints.size() != 0) {
            for(AnimationConstraint animationConstraint : animationConstraints) {
                if(animationConstraint != null) {
                    this.animationConstraints.add(animationConstraint);
                }
            }
        }
    }

    public void addConstraints(AnimationConstraint... animationConstraints) {
        if(animationConstraints != null && animationConstraints.length != 0) {
            for(AnimationConstraint animationConstraint : animationConstraints) {
                if(animationConstraint != null) {
                    this.animationConstraints.add(animationConstraint);
                }
            }
        }
    }

    public void update(double deltaTime) {
        boolean evaluation = false;

        if(this.animationConstraints != null) {
            for(AnimationConstraint animationConstraint : this.animationConstraints) {
                evaluation = animationConstraint.getEvaluation();

                if(evaluation) {
                    animationConstraint.changeState();
                    break;
                }
            }
        }

        if(!evaluation) {
            this.spriteAnimation.update(deltaTime);
        }
    }

    public void start() {
        if(this.spriteAnimation != null) {
            this.spriteAnimation.play();
        }
    }

    public void stop() {
        if(this.spriteAnimation != null) {
            this.spriteAnimation.stop();
        }
    }

    public BufferedImage getNextFrame() {
        return this.spriteAnimation.getNextFrame();
    }

    public void setAsCurrentState() {
        if(this.stateMachine != null) {
            this.stateMachine.setAnimationState(this);
        }
    }

    public SpriteAnimation getSpriteAnimation() {
        return this.spriteAnimation;
    }

    public ArrayList<AnimationConstraint> getAnimationConstraints() {
        return this.animationConstraints;
    }

    public void onAnimationStart()
    {

    }

    public void onAnimationEnd()
    {

    }

    public void onAnimationStopped()
    {

    }
}
