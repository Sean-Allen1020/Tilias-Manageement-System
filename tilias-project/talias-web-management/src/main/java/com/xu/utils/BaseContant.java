package com.xu.utils;

public class BaseContant {

    private static ThreadLocal<Integer> td = new ThreadLocal<>();

    public static void setId(Integer id) {
        td.set(id);
    }
    public static Integer getId(){
        return td.get();
    }
    public static void cliearId(){
        td.remove();
    }
}
