package com.example.admin.myat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 4/6/2017.
 */

public class Event  implements Serializable{
 private Date date;

    public Event(Date date) {
        this.date=date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
