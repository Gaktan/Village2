package village;

import java.util.ArrayList;
import java.util.List;

public class Chart2 {
	
	private List<ChartCurve> list;
	private int index;
	
	public Chart2() {
		list = new ArrayList<ChartCurve>();
		index = 0;
	}
	
	public void addChartCurve(ChartCurve cc){
		list.add(cc);
	}
	
	public void removeChartCurve(ChartCurve cc){
		list.remove(cc);
	}
}
