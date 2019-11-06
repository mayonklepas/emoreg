package com.bmkg.emoreg.lainlain;

/**
 * Created by Minami on 7/1/2018.
 */

public class Config {

    public static String url="http://182.16.249.150/rest/";
    public static String users="";
    public static String id="";
    public static String nip="";
    public static String nama="";
    public static String eselon="";
    public static String parent="";
    public static String upt="";
    public static String nama_unit_kerja="";
    public static String email="";
    public static String jabatan="";
    public static String password="";
    public static String playerid="";
    public static String key="bk201!@#";
    public static int status=0;
    public static String mastermail="informasipengguna@gmail.com";
    public static String masterpass="bk201!@#";
    public static String mastertujuan="imonks21@gmail.com";


    public static double doubleconvert(String data){
        double datadouble=0;
        try {
            datadouble=Double.parseDouble(data);
        }catch (Exception ex){
            datadouble=0;
        }
        return datadouble;
    }
}
