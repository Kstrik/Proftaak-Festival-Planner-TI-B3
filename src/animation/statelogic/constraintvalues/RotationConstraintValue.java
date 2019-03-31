package animation.statelogic.constraintvalues;

import animation.statelogic.ConstraintValue;
import animation.statelogic.transitionvalues.RotationClamp;

public class RotationConstraintValue extends ConstraintValue {
    public RotationConstraintValue(double rotation){
        super(rotation);
    }

    @Override
    public boolean evaluate(Object transitionValue) {
        RotationClamp rotationClamp = (RotationClamp)transitionValue;

        boolean result = rotationClamp.isBetween((double)this.value);

        if(result == rotationClamp.getTransitionValue()){
            return true;
        }
        return false;
    }
}
