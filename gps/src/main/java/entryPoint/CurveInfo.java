package entryPoint;

public class CurveInfo {
	
	private String timeOffset;
	private String timeOffsetEnd;
	private String averageVehicleSpeed;
	private String gpsLatLongStart;
	private String gpsLatLongEnd;
	
	//true left ; false right
	private boolean direction;
	
	//true highspeed ; false lowspeed
	private boolean speedflag;

	public String getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}

	public String getAverageVehicleSpeed() {
		return averageVehicleSpeed;
	}

	public void setAverageVehicleSpeed(String averageVehicleSpeed) {
		this.averageVehicleSpeed = averageVehicleSpeed;
	}

	public String getGpsLatLongStart() {
		return gpsLatLongStart;
	}

	public void setGpsLatLongStart(String gpsLatLongStart) {
		this.gpsLatLongStart = gpsLatLongStart;
	}

	public String getGpsLatLongEnd() {
		return gpsLatLongEnd;
	}

	public void setGpsLatLongEnd(String gpsLatLongEnd) {
		this.gpsLatLongEnd = gpsLatLongEnd;
	}

	public boolean isDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public boolean isspeedflag() {
		return speedflag;
	}

	public void setspeedflag(boolean speed) {
		this.speedflag = speed;
	}

	public String getTimeOffsetEnd() {
		return timeOffsetEnd;
	}

	public void setTimeOffsetEnd(String timeOffsetEnd) {
		this.timeOffsetEnd = timeOffsetEnd;
	}
	
	
}
