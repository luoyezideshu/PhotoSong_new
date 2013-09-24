package com.game.photosong;

import android.app.Application;

public class Declare extends Application {
	private static int menu_status;
	private static int color_status;
	private static int undo_status;
	public static int screen_width;
	public static int screen_height;
	
	public Declare(){
		menu_status = 1;
		color_status = 1;
		undo_status = 1;
	}
	public static int getMenuStatus(){
		return menu_status;
	}
	public static void setMenuStatus(int ms){
		menu_status = ms;
	}
	public static int getColorStatus(){
		return color_status;
	}
	public static void setColorStatus(int cs){
		color_status = cs;
	}
	
	public static int getUndoStatus(){
		return undo_status;
	}
	public static void setUndoStatus(int us){
		undo_status = us;
	}
}
