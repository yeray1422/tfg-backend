package com.gregoriofer.tfgbackend.pagelogos.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;

import com.gregoriofer.tfgbackend.pagelogos.model.PageLogos;
import com.gregoriofer.tfgbackend.pagelogos.model.SortById;

public class JSONTransformer {

    public static ArrayList<PageLogos> toPageLogosList(JSONArray json) {
        ArrayList<PageLogos> pageLogosList = new ArrayList<>();

        for (int i = 0; i < json.size(); i++) {
            pageLogosList.add(toPageLogo((LinkedHashMap) json.get(i)));
        }
        Collections.sort(pageLogosList, new SortById());

        return pageLogosList;
    }

    public static PageLogos toPageLogo(LinkedHashMap json) {
        PageLogos pageLogos = new PageLogos();

        pageLogos.setId(json.get(Constants.ID) != null ? Integer.parseInt(json.get(Constants.ID).toString()) : -1);
        pageLogos.setPage(json.get(Constants.PAGE) != null ? json.get(Constants.PAGE).toString() : Constants.EMPTY);
        pageLogos.setTitle(json.get(Constants.TITLE) != null ? json.get(Constants.TITLE).toString() : Constants.EMPTY);
        pageLogos.setLogo(json.get(Constants.LOGO) != null ? json.get(Constants.LOGO).toString() : Constants.EMPTY);
        pageLogos.setDescription(json.get(Constants.DESCRIPTION) != null ? json.get(Constants.DESCRIPTION).toString() : Constants.EMPTY);

        return pageLogos;
    }
    
}
