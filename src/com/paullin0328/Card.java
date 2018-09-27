package com.paullin0328;

import java.util.HashMap;

public class Card {
    private String suit;
    private int value;

    public Card(String cardSuit, int cardValue){
        suit=cardSuit;
        value=cardValue;
    }

    public Card(Card oldCard){
        suit = oldCard.getSuit();
        value = oldCard.getValue();
    }

    /*
    Returns the suit of the card
    @return suit    suit of the given card
     */
    public String getSuit() {
        return suit;
    }

    /*
    Returns the value of the card
    @return value   value of the given card
     */
    public int getValue(){
        return value;
    }

    /*
    Returns the point value of the card
    @return point   point value of the given card
     */
    public int getPointValue(){
        if(value==1){
            return 1;
        }
        else if(value>10){
            return 10;
        }
        else{
            return value;
        }
    }

    /*
    Returns the card's attributes as a string
    @return string  string containing value of card and suit
     */
    public String toString(){
        HashMap<Integer,String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, "A");
        valueMap.put(11, "J");
        valueMap.put(12, "Q");
        valueMap.put(13, "K");

        if (valueMap.containsKey(value)){
            return valueMap.get(value)+suit;
        }
        else{
            return value+suit;
        }
    }
}
