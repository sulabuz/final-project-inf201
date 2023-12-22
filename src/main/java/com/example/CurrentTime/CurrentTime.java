package com.example.CurrentTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime {
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(new Date());
    }
}
