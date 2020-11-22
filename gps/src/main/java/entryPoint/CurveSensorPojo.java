package entryPoint;

import java.util.List;

public class CurveSensorPojo {
	
	private List<String> UIArray;
	private List<CurveInfo> curveData;
	
	public List<String> getUIArray() {
		return UIArray;
	}
	public void setUIArray(List<String> uIArray) {
		UIArray = uIArray;
	}
	public List<CurveInfo> getCurveData() {
		return curveData;
	}
	public void setCurveData(List<CurveInfo> curveData) {
		this.curveData = curveData;
	}
	
	
}
