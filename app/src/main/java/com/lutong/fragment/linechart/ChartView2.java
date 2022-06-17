package com.lutong.fragment.linechart;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;


import com.lutong.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**曲线图
 * @description
 * @param
 * @return
 * @author lutong
 * @time 2021/9/9 11:16
 */


public class ChartView2 {
    public static LineChart chart;
    public static XAxis xAxis; //x轴
    public static YAxis leftYAxis; //y轴
    public static YAxis rightYaxis;
    public static double num_max = -30;
    public static double num_min = -120;

    public static List<Bean> list0 = new ArrayList<>();
    public static List<Bean> list1 = new ArrayList<>();
    public static List<Bean> list2 = new ArrayList<>();
    public static List<Bean> list11 = new ArrayList<>();
    public static List<Bean> curList = new ArrayList<>();

    public static void initData(LineChart chart,Context context,View view) {
        ChartView2.chart =chart;
        //0
        for (int i = 0; i < 30; i++) {
            list0.add(new Bean("", 0));
        }//0
        for (int i = 0; i < 30; i++) {
            list1.add(new Bean("", 0));
        }//0
        for (int i = 0; i < 30; i++) {
            list2.add(new Bean("", 0));
        }
        //初始化默认集合
        for (int i = 0; i < 30; i++) {
            list11.add(new Bean("", 0));
        }

        initChart(context);
        curList.clear();
        curList.addAll(list0);
        if (curList != null && curList.size() > 0) {
            showLineChart(curList, "星期", context.getResources().getColor(R.color.color_3853e8));
        }
    }


    private static String TAG="CHARTView";
    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public static void showLineChart(final List<Bean> dataList, String name, int color) {
        Log.e(TAG, "showLineChart: "+dataList.toString());
        //自定义x轴显示元素
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position = (int) value;
//                return getTime((int) value);
                if (position >= 0 && position < dataList.size()) {
                    return dataList.get(position).data;
                } else {
                    return "0";
                }
            }
        });
//        //自定义y轴显示元素 ，
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "";
            }
        });

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Bean data = dataList.get(i);
            Entry entry = new Entry(i, data.value);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //设置线的属性
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置数据
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);
    }


    /**
     * 添加曲线
     */
    public static void addLine(List<Bean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Bean data = dataList.get(i);
            Entry entry = new Entry(i, data.value);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart.getLineData().addDataSet(lineDataSet);
        chart.invalidate();
    }


    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private static void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {


        //内圈半径
        lineDataSet.setCircleHoleRadius(1.5f);
        // 内圈的颜色
//        lineDataSet.setCircleColor(R.color.white);//线颜色
//
        lineDataSet.setColor(color);        //外圈的颜色
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);      //外圈半径
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(color);//折线图填充的背景颜色
        lineDataSet.setFillAlpha(35);//折线图填充的颜色深度
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //显示点
        lineDataSet.setDrawCircles(false);
        //显示内容
        lineDataSet.setDrawValues(false);
        //自定义显示的点的内容
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                DecimalFormat df = new DecimalFormat(".00");
//                return df.format(value * 100) + "%";
                return (int) value + "";
            }
        });
        LineData data = new LineData(lineDataSet);
        data.setValueTextColor(Color.BLACK); //数据颜色
        data.setValueTextSize(15f);
//        if (mode == null) {
//            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
//            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        } else {
//            lineDataSet.setMode(mode);
//        }

    }

    public static void initChart(Context context) {
        //是否展示网格线
        chart.setDrawGridBackground(false);
        //是否显示边界
        chart.setDrawBorders(false);
        //是否可以拖动
        chart.setDragEnabled(false);
        //是否有触摸事件
        chart.setTouchEnabled(false);
        //两指放大缩小
        chart.setPinchZoom(false);
        //x轴动画
        chart.animateX(0);//设置为0代表没有闪屏状态
        //无数据时展示
        chart.setNoDataText("暂无数据");
        //背景色
        chart.setBackgroundColor(context.getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            chart.setOutlineSpotShadowColor(context.getResources().getColor(R.color.color_3853e8));
        }

//        chart.s
        //表格右下角的小标签隐藏
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        xAxis = chart.getXAxis(); //x轴
        leftYAxis = chart.getAxisLeft(); //y轴
        rightYaxis = chart.getAxisRight();






//        xAxis.setTextSize(13);
//        xAxis.setTextColor(Color.parseColor("#999999"));
        xAxis.setDrawGridLines(false); //是否显示网格
        xAxis.setAxisLineColor(Color.parseColor("#00ffffff"));
//        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//将坐标值设置在底部
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
        //设置X轴分割数量
//        xAxis.setLabelCount(6, false);

        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisLineColor(Color.parseColor("#00ffffff"));// 坐标轴颜色，设置为透明
//        leftYAxis.setTextColor(Color.parseColor("#00ffffff")); //坐标轴数值
        leftYAxis.setGridColor(Color.parseColor("#F0F0F0"));    // 横着的 网格线颜色，默认GRAY
//-30 -120
        leftYAxis.setGridLineWidth(1);//设置y轴对应的线宽
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setDrawGridLines(true);//坐标线
        leftYAxis.setLabelCount(3); //设置y轴分割数量
        leftYAxis.setAxisMaximum((float) num_max);

        leftYAxis.setAxisMinimum((float) num_min);
        leftYAxis.setInverted(true);//如果设置为true，该轴将被反转，这意味着最高值将在底部，顶部的最低值。
        //去除右侧Y轴
        rightYaxis.setEnabled(false);
        rightYaxis.setAxisMinimum(0f);
        rightYaxis.setDrawGridLines(false);

        /***折线图例 标签 设置***/
        Legend legend = chart.getLegend(); //图例
        // 设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm
        legend.setEnabled(false);// 是否绘制图例
        legend.setForm(Legend.LegendForm.LINE); //左下角表格名称前面的小标志
        legend.setTextSize(11f);
        legend.setTextColor(R.color.color_3853e8); //左下角表格名称
        legend.setYEntrySpace(20);  // 设置垂直图例间间距，默认0
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);

    }

}