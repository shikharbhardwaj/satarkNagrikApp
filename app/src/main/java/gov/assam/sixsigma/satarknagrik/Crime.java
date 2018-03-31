package gov.assam.sixsigma.satarknagrik;

public class Crime {

    public String c_type;
    public String c_year;
    public String c_month;
    public String c_date;
    public String c_hr;
    public String c_min;
    public String c_lat;
    public String c_lon;
    public String c_desc;


    public Crime(String c_type, String c_year, String c_month, String c_date, String c_hr, String c_min, String c_lat, String c_lon, String c_desc){
        this.c_type = c_type;
        this.c_year = c_year;
        this.c_month = c_month;
        this.c_date = c_date;
        this.c_hr = c_hr;
        this.c_min =c_min;
        this.c_lat = c_lat;
        this.c_lon = c_lon;
        this.c_desc = c_desc;
    }
}