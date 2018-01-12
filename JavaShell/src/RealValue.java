
public class RealValue implements Value {

	private double value;

	public RealValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	@Override
	public Type getType() {
		return Type.REAL;
	}

	@Override
	public Value add(Value v2) {
		switch (v2.getType()) {
		case INTEGER: 
			return new RealValue(this.value + ((IntegerValue) v2).getValue());

		case REAL: 
			return new RealValue(this.value + ((RealValue) v2).getValue());

		default:
			throw new IllegalArgumentException("Type Error");
		}
	}

	@Override
	public Value subtract(Value v2) {
		switch (v2.getType()) {
		case INTEGER: 
			return new RealValue(this.value - ((IntegerValue) v2).getValue());

		case REAL: 
			return new RealValue(this.value - ((RealValue) v2).getValue());

		default:
			throw new IllegalArgumentException("Type Error");
		}
	}

	@Override
	public Value multiply(Value v2) {
		switch (v2.getType()) {
		case INTEGER: 
			return new RealValue(this.value * ((IntegerValue) v2).getValue());

		case REAL: 
			return new RealValue(this.value * ((RealValue) v2).getValue());

		default:
			throw new IllegalArgumentException("Type Error");
		}
	}

	@Override
	public Value divide(Value v2) {
		switch (v2.getType()) {
		case INTEGER: 
			return new RealValue(this.value / ((IntegerValue) v2).getValue());

		case REAL: 
			return new RealValue(this.value / ((RealValue) v2).getValue());

		default:
			throw new IllegalArgumentException("Type Error");
		}
	}

	public String toString() {
		return "REAL: "+value;
	}
}
