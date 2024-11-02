package fpl.datn.damau_ph21672.dao;

import android.content.Context;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.ThongKeActivity;
import fpl.datn.damau_ph21672.database.DbHelper;

public class ThongKeDAO {
    DbHelper dbHelper;
    public ThongKeDAO(Context context){
        dbHelper= new DbHelper(context);
    }


}
