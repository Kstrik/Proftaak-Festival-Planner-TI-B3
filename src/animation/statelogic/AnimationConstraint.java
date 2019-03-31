package animation.statelogic;

public class AnimationConstraint {
    private SpriteAnimationState originState;
    private SpriteAnimationState targetState;

    private ConstraintValue constraintValue;

    private Object transitionValue;

    public AnimationConstraint(SpriteAnimationState originState, SpriteAnimationState targetState, ConstraintValue constraintValue, Object transitionValue) {
        this.originState = originState;
        this.targetState = targetState;

        this.constraintValue = constraintValue;
        this.transitionValue = transitionValue;
    }

    public void changeState() {
        this.originState.stop();
        this.targetState.setAsCurrentState();
        this.targetState.start();
    }

    public boolean getEvaluation()
    {
        return this.constraintValue.evaluate(this.transitionValue);
    }
}
