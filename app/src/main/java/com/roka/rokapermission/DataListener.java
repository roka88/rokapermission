package com.roka.rokapermission;

import java.util.ArrayList;

/**
 * Created by roka on 2016. 9. 14..
 */
public interface DataListener {
    void denied(ArrayList<String> result);
    void granted(ArrayList<String> result);
}
