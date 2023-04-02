package com.yy.stock.bot.engine.core;

import java.util.Locale;

public class Internationalization {
    public String getCountryName(String code) {
//        String[] countryCodes = Locale.getISOCountries();
//        List<Country> list = new ArrayList<Country>(countryCodes.length);
//
//        for (String cc : countryCodes) {
//            list.add(new Country(cc.toUpperCase(), new Locale("", cc).getDisplayCountry()));
//        }
        return new Locale("", code).getDisplayCountry();
    }
}
