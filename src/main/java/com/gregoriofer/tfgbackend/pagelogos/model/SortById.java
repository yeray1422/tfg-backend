package com.gregoriofer.tfgbackend.pagelogos.model;

import java.util.Comparator;

public class SortById implements Comparator<PageLogos> {

    @Override
    public int compare(PageLogos pg1, PageLogos pg2) {
        return pg1.getId().compareTo(pg2.getId());
    }
    
}
