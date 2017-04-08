package com.example.admin.myat;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
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

import static com.example.admin.myat.R.id.calendarView;

public class MainActivity extends AppCompatActivity {


    private static  Date date;
    MaterialCalendarView materialCalendarView;

    RequestQueue requestQueue;

    String url ="http://203.124.96.117:8063/Service1.asmx/Attendance";





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      Calendar cal1 = Calendar.getInstance();
        //cal1.set(2017, 1, 3);
         //Calendar cal2 = Calendar.getInstance();
       // cal2.set(2017, 2, 3);

       HashSet<CalendarDay> setDays = getCalendarDaysSet(cal1);



        materialCalendarView= (MaterialCalendarView) findViewById(calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        requestQueue=Volley.newRequestQueue(this);

        materialCalendarView.addDecorator(new EventDecorator(R.color.colorAccent,setDays));

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                //SimpleDateFormatter formatter = new SimpleDateFormatter();
                SimpleDateFormat format=new SimpleDateFormat("dd//mm//yyyy");

                List<Event> events = new ArrayList<>();

                for(int i=0;i<response.length();i++){

                    try {
                        JSONObject object=response.getJSONObject(0);

                        String det=object.getString("Att_date");

                        Date date = format.parse(det);

                        Calendar calendar=Calendar.getInstance();

                        Date today = calendar.getTime();

                        materialCalendarView.getCurrentDate();







                        materialCalendarView.setCurrentDate(date);





                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                  for(Event event : events)
                  {
                     // EventDecorator eventDecorator=new EventDecorator(materialCalendarView,event.getDate());

                  }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "" +error, Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private HashSet<CalendarDay> getCalendarDaysSet(Calendar cal1) {

        HashSet<CalendarDay> setDays = new HashSet<>();
        //while (cal1.getTime().before(cal1.getTime())) {
          //  CalendarDay calDay = CalendarDay.from(2017,03,1);
           // setDays.add(calDay);
            //cal1.add(android.icu.util.Calendar.DATE, 1);
       // }

        return setDays;
    }

    //for (Event event : events) {
      //  EventDecorator eventDecorator = new EventDecorator(calendarView, event.getDate(), event.getColor());
      //  calendarView.addDecorator(eventDecorator);
   // }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private HashSet<CalendarDay> getCalendarDaysSet(Calendar cal1, android.icu.util.Calendar cal2) {
        HashSet<CalendarDay> setDays = new HashSet<>();
        while (cal1.getTime().before(cal2.getTime())) {
            CalendarDay calDay = CalendarDay.from(2017,03,1);
            setDays.add(calDay);
            //cal1.add(android.icu.util.Calendar.DATE, 1);
        }

        return setDays;
    }


    public class EventDecorator implements DayViewDecorator {


        private CalendarDay date;

        public EventDecorator(int colorGreen, HashSet<CalendarDay> setDays) {
            date = CalendarDay.from((MainActivity.date));
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {

            view.addSpan(new ForegroundColorSpan(Color.RED));

        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }    }



}
