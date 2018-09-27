package com.paullin0328;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int numDecks;
        double reshuffle;
        Deck deck;

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Black Jack, good luck!");
        System.out.println("Press y to change any settings, otherwise press any other key to enter.");
        if(input.nextLine().equals("y")){
            System.out.println("Please enter the number of decks you wish to use in your game!");
            numDecks=input.nextInt();
            input.nextLine();

            System.out.println("Please enter the percentage(as a decimal) after which you want to reshuffle");
            reshuffle=input.nextDouble();
            input.nextLine();

            deck = new Deck(numDecks, reshuffle);
        }else{
            deck = new Deck();
        }

        Gameplay game = new Gameplay(deck);
        game.play();
    }

}
