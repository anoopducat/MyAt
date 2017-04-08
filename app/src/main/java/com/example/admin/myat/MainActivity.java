package com.example.admin.myat;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.example.admin.myat.R.string.calendar;


public class MainActivity extends AppCompatActivity {


    private static  Date date;
    CalendarView simpleCalendarView;

    RequestQueue requestQueue;

    Calendar cal;



    String url ="http://203.124.96.117:8063/Service1.asmx/Attendance";

    String det;

    TextView tv1;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1= (TextView) findViewById(R.id.tv_de);


        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        requestQueue=Volley.newRequestQueue(this);

        JsonArrayRequest jsn=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject obj= (JSONObject) response.get(5);

                        det=obj.getString("Att_date");
                        tv1.setText(det);
                        SimpleDateFormat df=new SimpleDateFormat("dd/mm/yyyy");
                        Date da=df.parse(det);
                        long milis=da.getTime();
                        simpleCalendarView.setDate(milis);

                        //Toast.makeText(MainActivity.this, "" + det, Toast.LENGTH_SHORT).show();

                        //SimpleDateFormat format=new SimpleDateFormat("dd//mm//yyyy");

                       // Date date = format.parse(det);

                       // mills = date.getTime();




                        //cal.getInstance().setTime(date);

                       // cal=format.getCalendar();
                      // cal.setTime(date);

                        //simpleCalendarView.setDate(mills);




                       // simpleCalendarView.setDate(mills);








                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsn);








    }

    }
