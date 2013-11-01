package com.example.skipassselector;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.view.View;

public class BarGraph {

		public View getView(Context context)
		{
			// This block of acrobatics is necessary to transform the HashSet to an array
	//		HashSet<Integer> hs = MainActivity.getDatesSet(); 
	//		ArrayList<Integer> arrList = new ArrayList<Integer>(hs);
	//		Integer[] y = new Integer[arrList.size()];
	//		arrList.toArray(y);
			
			float wr = MainActivity.getWindowRateTotal();
	//		float ac = MainActivity.getAcRateTotal();
			// put ncc here
			// put ncc+ac here
			float sp = 449;
			
			
			float[] y = {sp,100,200,300,wr};
			
			CategorySeries series = new CategorySeries("Demo_bar_graph");
			
			for (int i = 0; i < y.length; i++) {
				series.add("Ticket type " + (i + 1), y[i]);
			}
			
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			dataset.addSeries(series.toXYSeries());
			
			XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			mRenderer.addSeriesRenderer(renderer);
			
			//TODO: change type to STACKED
			View barGraphView = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
							
			return barGraphView;
		}
}
