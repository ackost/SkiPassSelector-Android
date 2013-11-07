package com.example.skipassselector;

import java.text.NumberFormat;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;


public class BarGraph {

		public GraphicalView getView(Context context,XYSeries series)
		{		
			
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			dataset.clear();
			XYSeries s = new XYSeries("newS");
			s = series;
			dataset.addSeries(s);
			
			XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
			mRenderer.setChartTitle("Ticket Cost Comparison");
	        mRenderer.setXTitle("Ticket options");
	        mRenderer.setYTitle("Total cost ($)");
	        mRenderer.setAxesColor(Color.BLACK);
	        mRenderer.setApplyBackgroundColor(true);
	        mRenderer.setBackgroundColor(Color.WHITE);
	        mRenderer.setMarginsColor(Color.WHITE);
	        mRenderer.setZoomEnabled(true);
	        mRenderer.setBarSpacing(.5);
	        mRenderer.setMargins(new int[] {30, 50, 10, 15});
	        mRenderer.setShowLegend(true);
	        mRenderer.setLegendHeight(45);
	        mRenderer.setAxisTitleTextSize(16);
	        mRenderer.setChartTitleTextSize(20);
	        mRenderer.setLabelsTextSize(18);
	        mRenderer.addXTextLabel(1, "SP");
	        mRenderer.addXTextLabel(2, "AC");
	        mRenderer.addXTextLabel(3, "NCC");
	        mRenderer.addXTextLabel(4, "NCC + AC");
	        mRenderer.addXTextLabel(5, "Window");
	        mRenderer.setXLabels(0);
	        mRenderer.setBarWidth(50);
	        mRenderer.setXAxisMin(.5);
	        mRenderer.setXAxisMax(5.5);
	        mRenderer.setYAxisMin(0);
	        mRenderer.setYLabelsAlign(Align.RIGHT);
	        mRenderer.setLabelsColor(Color.BLACK);
	        mRenderer.setXLabelsColor(Color.BLACK);
	        mRenderer.setYLabelsColor(0, Color.BLACK);
	        double[] panLimits = {0, 0, 0, 1000};
	        	mRenderer.setPanLimits(panLimits);

			XYSeriesRenderer renderer = new XYSeriesRenderer();
			renderer.setChartValuesTextSize(16);
			renderer.setDisplayChartValues(true);
			renderer.setChartValuesFormat(NumberFormat.getCurrencyInstance());
			renderer.setGradientEnabled(true);
			renderer.setGradientStart(0, Color.GREEN);
			renderer.setGradientStop(300, Color.BLUE);
			
			mRenderer.addSeriesRenderer(renderer);
			
			GraphicalView barGraphView = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
			
			return barGraphView;
		}
		
		public XYSeries addNewSeries(String seriesLabel) {
			//debug console output
			//System.out.println("BarGraph addNewSeries called");
			
			double sp = 0;
			double ac = 0;
			double ncc = 0;
			double nccAc = 0;
			double wr = 0;
			
			sp = MainActivity.getSeasonPassTotal();
			ac = MainActivity.getAcRateTotal();
			ncc = MainActivity.getNccRateTotal();
			nccAc = MainActivity.getNccAcRateTotal();
			wr = MainActivity.getWindowRateTotal();
			
			double[] y = {sp, ac, ncc, nccAc, wr};
	
			// debug console output
			/*
			System.out.println("sp = " + sp);
			System.out.println("ac = " + ac);
			System.out.println("ncc = " + ncc);
			System.out.println("nccAc = " + nccAc);
			System.out.println("wr = " + wr);
			*/
			
			seriesLabel = "Ticket count = " + Integer.toString(MainActivity.datesAndTickets.size());
			
			XYSeries series = new XYSeries(seriesLabel);
			
			for (int i = 0; i < y.length; i++) {
				series.add((i + 1), y[i]);
			}
			return series;
		}

}
