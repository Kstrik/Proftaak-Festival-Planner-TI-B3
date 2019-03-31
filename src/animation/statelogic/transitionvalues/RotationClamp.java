package animation.statelogic.transitionvalues;

public class RotationClamp {
    public double minRotation;
    public double maxRotation;
    public boolean transitionValue;
    public String name;

    public RotationClamp(double minRotationInDegrees, double maxRotationInDegrees, boolean transitionValue, String name){
        this.minRotation = minRotationInDegrees;
        this.maxRotation = maxRotationInDegrees;
        this.transitionValue = transitionValue;
        this.name = name;
    }

    public boolean isBetween(double rotation){
        double rotationDegrees = Math.toDegrees(rotation);

        if(this.minRotation > this.maxRotation){
            if(rotationDegrees > this.minRotation && rotationDegrees <= 180 || rotationDegrees >= -179 && rotationDegrees <= this.maxRotation){
                return true;
            }
        }
        else{
            if(rotationDegrees > this.minRotation && rotationDegrees <= this.maxRotation){
                return true;
            }
        }

        return false;
    }

    public boolean getTransitionValue() {
        return this.transitionValue;
    }
}
