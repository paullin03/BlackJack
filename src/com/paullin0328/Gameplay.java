package com.paullin0328;

import java.util.ArrayList;
import java.util.Scanner;

public class Gameplay {
    private Deck deck;
    private Scanner input = new Scanner(System.in);

    public Gameplay(Deck deck){
        this.deck=deck;
    }

    public void play(){
        Hand playerHand;
        Hand dealerHand;
        double chips;
        double bet;

        chips=promptBuyIn();

        do{
            bet = promptBet(chips);
            chips -= bet;

            playerHand = new Hand("Player",deck.getCard(),deck.getCard(), bet);
            dealerHand = new Hand("Dealer",deck.getCard(),deck.getCard());

            dealerHand.printDealerHoleCard();
            playerHand.printPlayerHandDetails();

            chips = playHand(playerHand,dealerHand, chips, dealerHand.getHandPointValue()!=21);

        }while(!checkSpecial("continuePlaying")&&chips>=1);
    }

    /*
    Simulates play for the dealer, hits if 16 or less and hits on soft 17s
    @param dealerHand   Hand object that describes the cards in the dealer's hand
     */
    public void dealerPlay(Hand dealerHand){
        if(dealerHand.getCurrentHandString().contains("A")&&dealerHand.getHandPointValue()==17){
            dealerHand.addCardToHand(deck.getCard());
        }
        while(dealerHand.getHandPointValue()<=16){
            dealerHand.addCardToHand(deck.getCard());
        }
        dealerHand.printPlayerHandDetails();
    }

    /*
    Checks to see if the dealer or the player wins and prints the appropriate line.
    @param dealerHand   hand of the dealer
    @param playerHand   hand of the player
    @return payout  payout from the hand (includes initial bet amount if the player wins/draws)
     */
    public double checkWin(Hand dealerHand, Hand playerHand){
        double payout;
        double bet;
        int playerPoints;
        int dealerPoints;

        bet=playerHand.getBet();
        playerPoints = playerHand.getHandPointValue();
        dealerPoints = dealerHand.getHandPointValue();

        if(playerHand.getSurrender()) {
            System.out.printf("You surrendered and lost $%.2f.\n", bet);
            payout = bet;
        }else if(playerPoints>21){
            System.out.printf("Sorry, you busted and lost $%.2f. Better luck next time!\n", bet);
            payout = 0;
        }else if(playerPoints==21&&playerHand.getHandSize()==2){
            if(dealerPoints==21&&dealerHand.getHandSize()==2) {
                System.out.println("You and the dealer both had Blackjack, push!");
                payout = bet;
            }else{
                payout = bet/2 * 5;
                System.out.printf("CONGRATS ON YOUR BLACKJACK! YOU WON $%.2f!\n", bet*3/2);
            }
        }else if(dealerPoints>21){
            System.out.printf("Dealer busted! YOU WON $%.2f!\n", bet);
            payout = bet*2;
        }else if(dealerPoints==playerPoints){
            System.out.println("Push, you both had "+playerPoints+"\n");
            payout = bet;
        }else if(dealerPoints>playerPoints){
            System.out.printf("Sorry, dealer had "+dealerPoints+ " which beat your "+playerPoints+". You lost $%.2f. \n", bet);
            payout = 0;
        }else {
            System.out.printf("YOU WIN! Dealer had a " + dealerPoints + " which was smaller than your " + playerPoints + ". You won $%.2f.\n", bet);
            payout = bet*2;
        }
        return payout;
    }

    /*
    Prompts the user for their bet while also printing the amount of chips that they have
    @return bet     amount that the user wishes to bet for the current hand
     */
    public double promptBet(double chips){
        double bet;

        System.out.printf("You currently have $%.2f.\nPlease enter the amount you would like to bet, whole numbers only!\n", chips);
        bet = input.nextInt();
        input.nextLine();

        while(bet>chips){
            System.out.printf("Sorry, your bet of $%.2f is more than your current chip total of $%.2f. Please enter a smaller amount.\n",bet, chips);
            bet = input.nextInt();
            input.nextLine();
        }

        return bet;
    }

    /*
    Prompts the user for their buy-in to the game
    @return buyIn   amount of chips that the user is buying in with
     */
    public double promptBuyIn(){
        double chips;
        System.out.println("How much would you like to buy in with?");
        chips=input.nextInt();
        input.nextLine();
        return chips;
    }


    public double playHand(Hand playerHand, Hand dealerHand, double chips, boolean canSurrender){
        if(chips>=playerHand.getBet()&&playerHand.isEqualValue()&&checkSpecial("split")) {
            chips -= playerHand.getBet();
            chips += playSplit(playerHand, dealerHand, chips);
        } else{
            chips = playNonSplit(playerHand,chips,canSurrender,true);
            dealerPlay(dealerHand);

            chips += checkWin(dealerHand,playerHand);
        }
        return chips;
    }

    public boolean checkSpecial(String situation){
        if(situation.equals("split")){
            System.out.println("Would you like to split at the cost of another bet equal to your original? Press y to split");
        }else if(situation.equals("doubleDown")){
            System.out.println("Would you like to double down? Your bet is doubled but you can only get one more card. Press y to double down.");
        }else if (situation.equals("surrender")) {
            System.out.println("Would you like to surrender at the cost of half your bet? Press y to surrender.");
        }else if(situation.equals("continuePlaying")){
            System.out.println("Do you want to quit? Press y to quit.");
        }
        return input.nextLine().equals("y");
    }

    public double playSplit(Hand playerHand, Hand dealerHand, double chips){
        double chipsRemaining = chips;
        ArrayList<Hand> hands = new ArrayList<Hand>();
        double totalPayout = 0;

        hands.add(new Hand("Player", playerHand.getCurrentHand().get(0), deck.getCard(), playerHand.getBet()));
        hands.get(0).printPlayerHandDetails();

        hands.add(new Hand("Player", playerHand.getCurrentHand().get(1), deck.getCard(), playerHand.getBet()));
        hands.get(1).printPlayerHandDetails();

        System.out.println("\n");
        if(playerHand.getCurrentHand().get(0).getValue()!=1) {
            for (int i = 0; i < hands.size(); i++) {
                if (hands.get(i).isEqualValue() && chipsRemaining >= playerHand.getBet() && hands.size() < 4) {
                    hands.get(i).printPlayerHandDetails();
                    if (checkSpecial("split")) {
                        hands.add(new Hand("Player", playerHand.getCurrentHand().get(0), deck.getCard(), playerHand.getBet()));
                        hands.set(i, new Hand("Player", playerHand.getCurrentHand().get(0),deck.getCard(),playerHand.getBet()));
                        chipsRemaining -= playerHand.getBet();
                        System.out.println("Your new hands have been added.\n");

                        hands.get(i).printPlayerHandDetails();
                        hands.get(hands.size() - 1).printPlayerHandDetails();
                    }
                }
            }
            for (int i = 0; i < hands.size(); i++) {
                hands.get(i).printPlayerHandDetails();
                chipsRemaining = playNonSplit(hands.get(i), chipsRemaining, false, true);
            }
        }else{
            for (int i = 0; i < hands.size(); i++) {
                chipsRemaining = playNonSplit(hands.get(i), chipsRemaining, false, false);
            }
        }
        dealerPlay(dealerHand);

        for(int i =0; i<hands.size();i++){
            totalPayout += checkWin(dealerHand, hands.get(i));
        }
        return totalPayout;
    }

    public double playNonSplit(Hand playerHand, double chips, boolean canSurrender, boolean canDouble){
        if(chips>=playerHand.getBet()&&checkSpecial("doubleDown")&&canDouble) {
            chips-=playerHand.getBet();
            playerHand.doublesBet();
            playerHand.addCardToHand(deck.getCard());
            playerHand.printPlayerHandDetails();
        } else if (canSurrender&&checkSpecial("surrender")) {
            playerHand.surrender();
        } else {
            System.out.println("Do you want to hit or stand? Press h for hit and anything else to stand");
            while (input.nextLine().equals("h")) {
                playerHand.addCardToHand(deck.getCard());
                playerHand.printPlayerHandDetails();
                System.out.println("Do you want to hit or stand? Press h for hit and anything else to stand");
            }
        }
        return chips;
    }
}
