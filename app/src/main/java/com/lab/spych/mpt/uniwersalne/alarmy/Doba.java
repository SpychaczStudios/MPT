package com.lab.spych.mpt.uniwersalne.alarmy;

/**
 * Klasa do przeliczania czasu dnia. Podzieliłem dzień na 24 godziny po 60 minut. Nie ma sekund.@de
 * Created by spych on 2017-04-13.
 */

@Deprecated
class Doba {
    private long poczatekDoby;

    /**
     * Kalibruje zegar na daną dobę, dzięki obecnej godzinie dnia.
     * @param czas obecny czas dnia
     */
    public void setPoczatekDoby(Czas czas){
        poczatekDoby = System.currentTimeMillis() - czas.getGodzina()*3600000 - czas.getMinuta()*60000;
    }

    /**
     * @return czas w tym momencie
     */
    public Czas getCzasDnia(){
        Czas ret = new Czas();
        long minutyOdPoczatkuDoby = (System.currentTimeMillis() - poczatekDoby)/60000; ///ilość minut od początku doby
        ret.setGodzina((int) (minutyOdPoczatkuDoby/60));
        ret.setMinuta((int) minutyOdPoczatkuDoby%60);
        return ret;
    }

}
