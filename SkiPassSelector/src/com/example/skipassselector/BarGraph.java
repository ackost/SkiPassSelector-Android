package com.example.skipassselector;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;

public class BarGraph {

		public GraphicalView getView(Context context)
		{
			// This block of acrobatics is necessary to transform the HashSet to an array
	//		HashSet<Integer> hs = MainActivity.getDatesSet(); 
	//		ArrayList<Integer> arrList = new ArrayList<Integer>(hs);
	//		Integer[] y = new Integer[arrList.size()];
	//		arrList.toArray(y);
			
			float wr = MainActivity.getWindowRateTotal();
			float ac = MainActivity.getAcRateTotal();
			float ncc = MainActivity.getNccRateTotal();
			float nccAc = MainActivity.getNccAcRateTotal();
			
			
			float[] y = {(float) 449.00, ac, ncc, nccAc, wr};
			
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
			GraphicalView barGraphView = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
							
			return barGraphView;
		}
}
