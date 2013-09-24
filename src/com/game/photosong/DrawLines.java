package com.game.photosong;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @category: View实现涂鸦、撤销以及重做功能
 * @author: 谢伟
 * @date: 2012-11-9
 * 
 */

public class DrawLines extends View implements OnClickListener{
	private float A,B,C,D;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;// 画布的画笔
	private Paint mPaint;// 真实的画笔
	private float mX, mY;// 临时点坐标
	private static final float TOUCH_TOLERANCE = 4;
	private boolean candraw = false;

	// 保存Path路径的集合,用List集合来模拟栈
	private static List<DrawPath> savePath;
	// 记录Path路径的对象
	private DrawPath dp;

	private int screenWidth, screenHeight;// 屏幕长宽

	private class DrawPath {
		public Path path;// 路径
		public Paint paint;// 画笔
	}
	public DrawLines(Context context,AttributeSet attrs) {
		super(context);
	}
	public DrawLines(Context context,int w,int h) {
		super(context);
		screenWidth = w;
		screenHeight = h;
		A = C = 0;//screenHeight/10;
		B = D = 0;//screenWidth /8;
		mBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Bitmap.Config.ARGB_8888);
		if(mBitmap == null){
			Log.v("Error","mBitmap is null");
		}
		// 保存一次一次绘制出来的图形
		mCanvas = new Canvas(mBitmap);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
		mPaint.setStrokeCap(Paint.Cap.SQUARE);// 形状
		mPaint.setStrokeWidth(8);// 画笔宽度
		mPaint.setColor(0xFF000000);// 画笔颜色
		Log.v("Change color", "" + Declare.getColorStatus());
		if(Declare.getColorStatus() == 1)
			mPaint.setColor(Color.RED);// 画笔颜色
		else if(Declare.getColorStatus() == 2)
			mPaint.setColor(Color.YELLOW);// 画笔颜色
		else
			mPaint.setColor(Color.LTGRAY);// 画笔颜色
		savePath = new ArrayList<DrawPath>();
		setWillNotDraw(false);
		if(mBitmap!=null){
			Log.v("RIGHT","mBitmap not null");
		}
	}
	
	@Override
	
	public void onDraw(Canvas canvas) {
		Log.v("B","screemWidth = " + screenWidth);
		Log.v("B","screemHeight = " + screenHeight);
		
		canvas.drawColor(Color.TRANSPARENT);
		// 将前面已经画过得显示出来
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		
		Log.v("Change color", "" + Declare.getColorStatus());
		if(Declare.getColorStatus() == 1)
			mPaint.setColor(Color.RED);// 画笔颜色
		else if(Declare.getColorStatus() == 2)
			mPaint.setColor(Color.YELLOW);// 画笔颜色
		else
			mPaint.setColor(Color.LTGRAY);// 画笔颜色
		
		if (mPath != null) {
			// 实时的显示
			canvas.drawPath(mPath, mPaint);
			
		}
	}
	private boolean inCanvas(float x, float y){
		if((y >= A && x >=B) && (y < (screenHeight - C) || x < (screenWidth - D))){
			return true; 
		}
		return false;
	}
	
	private void touch_start(float x, float y) {
		candraw = false;
		if (inCanvas(x, y)){
			candraw = true;
			Log.v("validTouch", "" + x + ", " + y);
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}
	}

	private void touch_move(float x, float y) {
		if(candraw && inCanvas(x, y) && y > mY){
			float dx = Math.abs(x - mX);
			float dy = Math.abs(mY - y);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				// 从x1,y1到x2,y2画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也是可以的)
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}
		
	}

	private void touch_up() {
		if(candraw){
			mPath.lineTo(mX, mY);
			mCanvas.drawPath(mPath, mPaint);
			// 将一条完整的路径保存下来(相当于入栈操作)
			savePath.add(dp);
			mPath = null;// 重新置空
		}
	}

	/**
	 * 撤销的核心思想就是将画布清空， 将保存下来的Path路径最后一个移除掉， 重新将路径画在画布上面。
	 */
	public void undo() {
		mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
		mCanvas.setBitmap(mBitmap);// 重新设置画布，相当于清空画布
		// 清空画布，但是如果图片有背景的话，则使用上面的重新初始化的方法，用该方法会将背景清空掉...
		if (savePath != null && savePath.size() > 0) {
			// 移除最后一个path,相当于出栈操作
			savePath.remove(savePath.size() - 1);

			Iterator<DrawPath> iter = savePath.iterator();
			while (iter.hasNext()) {
				DrawPath drawPath = iter.next();
				mCanvas.drawPath(drawPath.path, drawPath.paint);
			}
			invalidate();// 刷新

			
			
			
			/* 在这里保存图片纯粹是为了方便,保存图片进行验证 */
			String fileUrl = Environment.getExternalStorageDirectory()
					.toString() + "/android/data/test.png";
			try {
				FileOutputStream fos = new FileOutputStream(new File(fileUrl));
				mBitmap.compress(CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			

		}
	}

	/**
	 * 重做的核心思想就是将撤销的路径保存到另外一个集合里面(栈)， 然后从redo的集合里面取出最顶端对象， 画在画布上面即可。
	 */
	public void redo() {
		// TODO
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 每次down下去重新new一个Path
			mPath = new Path();
			// 每一次记录的路径对象是不一样的
			dp = new DrawPath();
			dp.path = mPath;
			dp.paint = mPaint;
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		
		
	}


}
