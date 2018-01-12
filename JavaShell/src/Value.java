
public interface Value {
		
	public Type  getType();
	public Value add(Value v2);
	public Value subtract(Value v2);
	public Value multiply(Value v2);
	public Value divide(Value v2);

}
