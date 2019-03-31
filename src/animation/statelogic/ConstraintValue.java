package animation.statelogic;

public abstract class ConstraintValue {

    protected Object value;

    public ConstraintValue(Object value){
        this.value = value;
    }

    public abstract boolean evaluate(Object transitionValue);

    public Object getValue(){
        return this.value;
    }

    public void setValue(Object value){
        this.value = value;
    }
}
