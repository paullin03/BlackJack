package com.paullin0328;

import java.util.ArrayList;

public class Hand {
    private String playerName;
    private ArrayList<Card> currentHand;
    private double bet;
    private boolean surrender;

    /*
    Constructor for making a Hand object for the player
    Name of player is necessary, and so are the first two cards of the given hand.
     */
    public Hand(String name, Card firstCard, Card secondCard, double bet){
        playerName = name;
        currentHand = new ArrayList<Card>();
        currentHand.add(firstCard);
        currentHand.add(secondCard);
        this.bet = bet;
        surrender = false;
    }

    /*
    Constructor for making a Hand object
    Name of player is necessary, and so are the first two cards of the given hand.
     */
    public Hand(String name, Card firstCard, Card secondCard){
        playerName = name;
        currentHand = new ArrayList<Card>();
        currentHand.add(firstCard);
        currentHand.add(secondCard);
        this.bet = 0;
        surrender = false;
    }

    /*
    Adds card to currentHand
     */
    public void addCardToHand(Card card){
        currentHand.add(card);
    }

    /*
    Returns the name of the player who owns the hand
    @return playerName  name of player who owns the hand
     */
    public String getPlayerName(){
        return playerName;
    }

    /*
    Returns the bet the player made on the hand
    @return playerBet   bet made by player on the hand
     */
    public double getBet(){
        return bet;
    }

    /*
    Returns the current hand as an ArrayList
    @return currentHand     current hand of the player as an ArrayList
     */
    public ArrayList<Card> getCurrentHand(){
        return currentHand;
    }

    /*
    Returns the current hand as a string
    @return currentHand     current hand of the player as a string
     */
    public String getCurrentHandString(){
        String hand = "";
        for(int i=0;i<currentHand.size();i++){
            hand=hand + currentHand.get(i)+" ";
        }
        return hand;
    }

    /*
    Returns whether or not the player surrendered the hand
    @return surrender   true if the player surrendered
     */
    public boolean getSurrender(){
        return surrender;
    }

    /*
    Prints the designated player's hand and gives the point value
     */
    public void printPlayerHandDetails(){
        System.out.println(playerName+"'s hand is currently "+ getCurrentHandString());
        System.out.println("The point value is currently "+getHandPointValue());
    }

    /*
    Prints the first dealer card
     */
    public void printDealerHoleCard(){
        if(getHandPointValue()==21){
            System.out.println("The dealer has a Blackjack. "+getCurrentHand());
        }else {
            System.out.println("The dealer is showing " + currentHand.get(0));
        }
    }

    /*
    Returns the current point value of the hand as a string
    @return handPointValue      current hand value (maximum possible with A)
     */
    public int getHandPointValue(){
        String pointValueString;
        int pointValue = 0;
        int numAces = 0;

        for(int i=0;i<currentHand.size();i++){
            if(currentHand.get(i).getValue()==1){
                numAces++;
            }
            pointValue+=currentHand.get(i).getPointValue();
        }

        while(pointValue<= 11&&numAces>0){
            numAces--;
            pointValue+=10;
        }

        return pointValue;
    }

    /*
    Checks if the two-card initial hand has equal value
    @return equalValue    true if the initial hand has equal value
     */
    public boolean isEqualValue(){
        return currentHand.size()==2&&(currentHand.get(0).getPointValue()==currentHand.get(1).getPointValue());
    }

    /*
    Doubles the bet of the current hand
     */
    public void doublesBet(){
        bet*=2;
    }

    /*
    Halves the bet of the current hand
     */
    public void surrender(){
        bet/=2;
        surrender=true;
    }

    /*
    Returns the current number of cards in hand
    @return handSize    number of cards in the current hand
     */
    public int getHandSize(){
        return currentHand.size();
    }
}
