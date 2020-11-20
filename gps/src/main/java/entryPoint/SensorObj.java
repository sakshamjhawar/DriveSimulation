package entryPoint;

//Our custom data object
public class SensorObj {

	//Place to store time
	private String timeOffset;
	//Place to store calculated value
	private String value;
	//Place to store type
	private String type;
	
	//constructor for intializations
	public SensorObj(String timeOffset, String value, String type) {
		super();
		this.timeOffset = timeOffset;
		this.value = value;
		this.type = type;
	}
	
	//setter and getters
	public String getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//custom toString for printing purpose
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return timeOffset + "\t" + type + "\t" + value + "\n";
	}
}
