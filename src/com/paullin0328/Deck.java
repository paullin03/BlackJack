package com.paullin0328;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;
    private final String[] SUITS = {"\u2660", "\u2665", "\u2663", "\u2666"};
    private int numDecks;
    private double reshuffle;
    private final int DEFAULT_NUM_DECKS = 8;
    private final double DEFAULT_RESHUFFLE = 0.75;

    /*
    Constructor to change the number of decks used and the reshuffle percentage.
    */
    public Deck(int numberOfDecks, double customReshuffle){
        reshuffle = customReshuffle;
        numDecks=numberOfDecks;
        deck = new ArrayList<Card>();
        generateDeck(numDecks);
    }

    /*
    Default constructor with no changes to number of decks and reshuffle percentage.
     */
    public Deck(){
        reshuffle = DEFAULT_RESHUFFLE;
        numDecks = DEFAULT_NUM_DECKS;
        deck = new ArrayList<Card>();
        generateDeck(numDecks);
    }

    /*
    Generates the given number of decks, adds it to the deck, then shuffles the deck.
    @Param  numberOfDecks   the number of decks to generate
     */
    public void generateDeck(int numberOfDecks){
        for(int i = 0; i<numberOfDecks;++i) {
            for (String suit : SUITS) {
                for (int value = 1; value <= 13; ++value) {
                    deck.add(new Card(suit, value));
                }
            }
        }
        shuffleDeck();
    }

    /*
    Shuffles the current deck using Collections.shuffle()
     */
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    /*
    Clears the current deck
     */
    public void clearDeck(){
        deck.clear();
    }

    /*
    Draws a card and returns the drawn card. Will automatically shuffle if
     below the reshuffle point, or replenish deck if it is the last card drawn.
    @return card    the drawn card
     */
    public Card getCard(){
        Card drawnCard = new Card(deck.get(0));
        deck.remove(0);
        if(deck.size()<= reshuffle * 52 * numDecks){
            System.out.println("Reshuffle point reached, reshuffling "+numDecks+" decks.");
            clearDeck();
            generateDeck(numDecks);
        }
        else if (deck.size()==0){
            System.out.println("Ran out of cards, "+numDecks+" new decks added.");
            generateDeck(numDecks);
        }
        return drawnCard;
    }
}
