package com.fu.abilitymapview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Fu.
 * QQ:908323236
 * 2017/10/25 15:53
 */

public class AbilityMapView extends View {

    private static final String TAG = "AbilityMapView";

    private AbilityBean data;  //元数据
    private int n;    //边的数量或者能力的个数
    private float R;    //最外圈的半径，顶点到中心点的距离
    private int intervalCount; //间隔数量，就把半径分为几段
    private float angle;     //两条顶点到中线点的线之间的角度

    private Paint linePaint;  //画线的笔
    private Paint textPaint;  //画文字的笔

    private int viewHeight;   //控件宽度
    private int viewWidth;    //控件高度
    private ArrayList<ArrayList<PointF>> pointsArrayList;  //存储多边形顶点数组的数组
    private ArrayList<PointF> abilityPoints;   //存储能力点的数组

    public AbilityMapView(Context context) {
        //这地方改为this,使得不管怎么初始化都会进入第三个构造函数中
        this(context, null);
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs) {
        //这地方改为this,使得不管怎么初始化都会进入第三个构造函数中
        this(context, attrs, 0);
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initSize(context);

        initPoints();

        initPaint(context);
    }

    /**
     * 初始化一些固定数据
     *
     * @param context
     */
    private void initSize(Context context) {
        n = 5;  //七条边
        R = dp2pxF(context, 100);  //半径暂时设为100dp
        intervalCount = 4;   //有四层
        angle = (float) ((2 * Math.PI) / n);     //一周是2π,这里用π，因为进制的问题，不能用360度,画出来会有问题

        //拿到屏幕的宽高，单位是像素
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        //控件设置为正方向
        viewWidth = screenWidth;
        viewHeight = screenWidth;

    }

    /**
     * 初始化多边形的所有点 每一圈7个点，有4圈
     */
    private void initPoints() {
        //一个数组中每个元素又一是一个点数组,有几个多边形就有几个数组
        pointsArrayList = new ArrayList<>();
        float x;
        float y;
        for (int i = 0; i < intervalCount; i++) {
            //创建一个存储点的数组
            ArrayList<PointF> points = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                float r = R * ((float) (4 - i) / intervalCount);  //每一圈的半径都按比例减少
                //这里减去Math.PI / 2 是为了让多边形逆时针旋转90度，所以后面的所有用到cos,sin的都要减
                x = (float) (r * Math.cos(j * angle - Math.PI / 2));
                y = (float) (r * Math.sin(j * angle - Math.PI / 2));
                points.add(new PointF(x, y));
            }
            pointsArrayList.add(points);
        }
    }

    /**
     * 初始化画笔
     *
     * @param context
     */
    private void initPaint(Context context) {
        //画线的笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置线宽度
        linePaint.setStrokeWidth(dp2px(context, 1f));

        //画文字的笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);  //设置文字居中
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sp2pxF(context, 14f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置控件的最终视图大小(宽高)
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //把画布的原点移动到控件的中心点
        canvas.translate(viewWidth / 2, viewHeight / 2);

        drawPolygon(canvas);

        drawOutLine(canvas);

        drawAbilityLine(canvas);

        drawAbilityText(canvas);

        //坐标轴x,y 辅助用
//        linePaint.setColor(Color.RED);
//        canvas.drawLine(-(viewWidth / 2), 0, viewWidth / 2, 0, linePaint);
//        canvas.drawLine(0, -(viewWidth / 2), 0, viewWidth / 2, linePaint);
    }

    /**
     * 绘制多边形边,每一圈都绘制
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        canvas.save();//保存画布当前状态(平移、放缩、旋转、裁剪等),和canvas.restore()配合使用

        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);  //设置为填充且描边

        Path path = new Path();  //路径
        for (int i = 0; i < intervalCount; i++) {  //循环、一层一层的绘制
            //每一层的颜色都都不同
            switch (i) {
                case 0:
                    linePaint.setColor(Color.parseColor("#D4F0F3"));
                    break;
                case 1:
                    linePaint.setColor(Color.parseColor("#99DCE2"));
                    break;
                case 2:
                    linePaint.setColor(Color.parseColor("#56C1C7"));
                    break;
                case 3:
                    linePaint.setColor(Color.parseColor("#278891"));
                    break;
            }
            for (int j = 0; j < n; j++) {   //每一层有n个点
                float x = pointsArrayList.get(i).get(j).x;
                float y = pointsArrayList.get(i).get(j).y;
                if (j == 0) {
                    //如果是每层的第一个点就把path的起点设置为这个点
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.close();  //设置为闭合的
            canvas.drawPath(path, linePaint);
            path.reset();   //清除path存储的路径
        }

        canvas.restore();
    }

    /**
     * 画轮廓线
     * 1.先画最外面的多边形轮廓
     * 2.再画顶点到中心的线
     *
     * @param canvas
     */
    private void drawOutLine(Canvas canvas) {
        canvas.save();//保存画布当前状态(平移、放缩、旋转、裁剪等),和canvas.restore()配合使用

        linePaint.setColor(Color.parseColor("#99DCE2"));
        linePaint.setStyle(Paint.Style.STROKE);  //设置空心的

        //先画最外面的多边形轮廓
        Path path = new Path();  //路径
        for (int i = 0; i < n; i++) {
            //只需要第一组的点
            float x = pointsArrayList.get(0).get(i).x;
            float y = pointsArrayList.get(0).get(i).y;
            if (i == 0) {
                //如果是第一个点就把path的起点设置为这个点
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close(); //闭合路径
        canvas.drawPath(path, linePaint);

        //再画顶点到中心的线
        for (int i = 0; i < n; i++) {
            float x = pointsArrayList.get(0).get(i).x;
            float y = pointsArrayList.get(0).get(i).y;
            canvas.drawLine(0, 0, x, y, linePaint); //起点都是中心点
        }

        canvas.restore();
    }

    /**
     * 画能力线
     *
     * @param canvas
     */
    private void drawAbilityLine(Canvas canvas) {
        canvas.save();

        //先把能力点初始化出来
        abilityPoints = new ArrayList<>();
        int[] allAbility = data.getAllAbility();
        for (int i = 0; i < n; i++) {
            float r = R * (allAbility[i] / 100.0f);  //能力值/100再乘以半径就是所占的比例
            float x = (float) (r * Math.cos(i * angle - Math.PI / 2));
            float y = (float) (r * Math.sin(i * angle - Math.PI / 2));
            abilityPoints.add(new PointF(x, y));
        }

        linePaint.setStrokeWidth(dp2px(getContext(), 2f));
        linePaint.setColor(Color.parseColor("#E96153"));
        linePaint.setStyle(Paint.Style.STROKE);  //设置空心的

        Path path = new Path();  //路径
        for (int i = 0; i < n; i++) {
            float x = abilityPoints.get(i).x;
            float y = abilityPoints.get(i).y;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();   //别忘了闭合

        canvas.drawPath(path, linePaint);

        canvas.restore();
    }

    /**
     * 画能力描述的文字
     *
     * @param canvas
     */
    private void drawAbilityText(Canvas canvas) {
        canvas.save();
        //先计算出坐标来
        ArrayList<PointF> textPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            float r = R + dp2pxF(getContext(), 15f);
            float x = (float) (r * Math.cos(i * angle - Math.PI / 2));
            float y = (float) (r * Math.sin(i * angle - Math.PI / 2));
            textPoints.add(new PointF(x, y));
        }
        //拿到字体测量器
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        String[] abilitys = AbilityBean.getAbilitys();
        for (int i = 0; i < n; i++) {
            float x = textPoints.get(i).x;
            //ascent:上坡度，是文字的基线到文字的最高处的距离
            //descent:下坡度,，文字的基线到文字的最低处的距离
            float y = textPoints.get(i).y - (metrics.ascent + metrics.descent) / 2;
            canvas.drawText(abilitys[i], x, y, textPaint);
        }

        canvas.restore();
    }

    /**
     * 传入元数据
     *
     * @param data
     */
    public void setData(AbilityBean data) {
        if (data == null) {
            return;
        }
        this.data = data;

        //View本身调用迫使view重画
        invalidate();
    }

    /**
     * 下面都是工具类，dp单位转px单位
     */
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }
}
