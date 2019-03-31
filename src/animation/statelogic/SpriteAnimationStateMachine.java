package animation.statelogic;

import animation.animations.SpriteAnimation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteAnimationStateMachine {
    private ArrayList<SpriteAnimationState> animationStates;
    private BufferedImage animatedImage;
    private SpriteAnimationState currentAnimationState;

    private SpriteAnimationState defaultAnimationState;

    public SpriteAnimationStateMachine(BufferedImage animatedImage, SpriteAnimationState defaultAnimationState) {
        this.animatedImage = animatedImage;

        this.animationStates = new ArrayList<SpriteAnimationState>();

        this.attachAnimationState(defaultAnimationState);
        this.defaultAnimationState = defaultAnimationState;
        this.currentAnimationState = this.defaultAnimationState;
        this.currentAnimationState.start();
    }

    public void setAnimationState(SpriteAnimationState animationState) {
        if(animationState != null) {
            for(SpriteAnimationState state : this.animationStates) {
                if(state.hashCode() == animationState.hashCode()) {
                    this.currentAnimationState = animationState;
                }
            }
        }
        else {
            this.currentAnimationState = this.defaultAnimationState;
        }
    }

    public void update(double deltaTime) {
        if(this.currentAnimationState != null) {
            this.currentAnimationState.update(deltaTime);
            this.animatedImage = this.currentAnimationState.getNextFrame();
        }
    }

    public BufferedImage getLastFrame() {
        return this.animatedImage;
    }

    public void attachAnimationState(SpriteAnimationState spriteAnimationState) {
        if(spriteAnimationState != null) {
            spriteAnimationState.attachToStateMachine(this);
            this.animationStates.add(spriteAnimationState);
        }
    }

    public SpriteAnimationState getDefaultAnimationState() {
        return this.defaultAnimationState;
    }
}
