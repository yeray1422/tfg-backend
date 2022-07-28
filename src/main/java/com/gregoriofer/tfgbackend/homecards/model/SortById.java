package com.gregoriofer.tfgbackend.homecards.model;

import java.util.Comparator;

public class SortById implements Comparator<HomeCards> {

    @Override
    public int compare(HomeCards hc1, HomeCards hc2) {
        return hc1.getId().compareTo(hc2.getId());
    }

}
