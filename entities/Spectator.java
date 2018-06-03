package entities;

import clients.*;
import hippodrome.*;
import hippodrome.responses.Response;
import lib.logging.Logger;

import java.util.Random;

/**
 * Implementation of a Spectator which will be watching an horse race and betting on it. This entity,
 * belonging to the {@link entities} package, must coordinate its actions throughout several shared
 * memory regions, while interacting with the {@link Broker} (which controlls its bets) and with the
 * {@link HorseJockey} (in which each Spectator will bet).
 *
 * @author Hugo Fragata
 * @author Rui Lopes
 * @see Broker
 * @see HorseJockey
 * @since 0.1
 * @version 2.0
 */
public class Spectator extends Thread {
    /**
     * Creates a Spectator with an {@code identification} and a given amount of {@code money}.
     *
     * @param identification number which identifies this {@code Spectator}.
     * @param money amount of money given to this {@code Spectator}.
     * @param numberOfRaces number of races which will happen in this event.
     * @throws InterruptedException if the communication channel is busy.
     */
    public Spectator(int identification, int money, int numberOfRaces, BettingCentreInterface bettingCentre, ControlCentreInterface controlCentre, PaddockInterface paddock, GeneralInformationRepositoryInterface repository) throws InterruptedException {
        try {
            this.repository = repository;
            this.identification = identification;
            this.repository.setSpectatorStatus(identification, state);
            this.money = money;
            this.repository.setSpectatorAmountOfMoney(this.identification, this.money);
            this.bettingCentre = bettingCentre;
            this.controlCentre = controlCentre;
            this.paddock = paddock;
            this.numberOfRaces = numberOfRaces;
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
    }

    /**
     * Definition of the Spectator's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Spectator}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.Paddock}, passing by the {@link hippodrome.Stable}.
     */
    @Override
    public void run() { // TODO : change the processing mechanism of the returns
        try {
            while (controlCentre.waitForTheNextRace(identification)) {              //x While a next race is about to happen:
                boolean isLastSpectator = paddock.goCheckHorses();                  //   the Paddock calls me to go check the horses and I retrieve if I'm the last to go;
                if (isLastSpectator) {                                              //   if I'm the last Spectator to come:
                    controlCentre.goCheckHorses();                                  //     then the Control Centre must know, in order to advance something else;
                }                                                                   //
                paddock.goCheckHorses(isLastSpectator);                             //   I then must change my state to Appraising the Horses;

                //money -= bettingCentre.placeABet(identification, bet(), horse()); //   Having changed my state, then I must place my bet on the horse on my choice;
                Response response = bettingCentre.placeABet(identification, bet(), horse());
                money -= response.bet;
                setSpectatorState(response.newSpectatorState);

                repository.setSpectatorAmountOfMoney(identification, money);        //
                controlCentre.goWatchTheRace(identification);                                     //   With the bet already placed, then I should go watch the race;
                if (bettingCentre.haveIWon(identification)) {                       //   If the I already know that I've won the race, then:
                    money += bettingCentre.goCollectTheGains(identification);       //     I should collect my gains at the Betting Centre;
                    repository.setSpectatorAmountOfMoney(identification, money);    //
                }                                                                   //
                raceNumber++;                                                       //   Then the number of races must increase;
                if (raceNumber == numberOfRaces) {                                  //   And if this is my last race:
                    break;                                                          //     then I should stop;
                }                                                                   //
            }                                                                       //
            controlCentre.relaxABit();                                              // And relax a bit.
            //shutdown();                                                           // TODO : shutdown invocation
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(3);
        }
    }

    /**
     * Sets the Spectator's state, from the possible available {@link SpectatorState}
     * enumeration.
     *
     * @param state Enumeration value represented by {@link SpectatorState}
     */
    public synchronized void setSpectatorState(SpectatorState state) {
        this.state = state;
    }

    /**
     * Returns the Spectator numerical identification between all the others Spectators.
     *
     * @return a numerical identification represented as an integer.
     */
    public synchronized int getIdentification() {
        return identification;
    }

    /**
     * Returns the amount of current money that this Spectator has available for new bets.
     *
     * @return the current amount of money, as an integer.
     */
    public synchronized int getMoney() {
        return money;
    }

    /**
     * Chooses a bet proportion randomly.
     *
     * @return a randomly generated bet.
     */
    private int bet() {
        double[] portion = {0.25, 0.375, 0.0625};
        Random random = new Random();
        return (int)(money * portion[random.nextInt(portion.length)]);
    }

    /**
     * Returns a random horse for this Spectator to bet.
     *
     * @return a random horse identifier, as an integer.
     * @throws InterruptedException if the communication channel is busy.
     */
    private int horse() throws InterruptedException {
        int value = -1;
        try {
            value = (new Random()).nextInt(bettingCentre.getNumberOfHorses());
        } catch (InterruptedException ie) {
            throw new InterruptedException();
        }
        return value;
    }

    /**
     * Send request to shutdown the hippodrome regions, since anything more is needed after then.
     * TODO : perform a shutdown
     *
     * @throws InterruptedException if the communication channel cannot be used.
     */
    /*private void shutdown() throws InterruptedException {
        try {
            bettingCentre.shutdown();
            controlCentre.shutdown();
            repository.shutdown();
            paddock.shutdown();
            new RacingTrackStub().shutdown();
            new StableStub().shutdown();
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }
    }*/

    /**
     * A representation of the Spectator's state given by the {@link SpectatorState}
     * enumeration.
     */
    private SpectatorState state = SpectatorState.WAITING_FOR_A_RACE_TO_START;

    /**
     * Proper Spectator numerical identification.
     */
    private int identification;

    /**
     * Current amount of this Spectator's money to spend on new bets.
     */
    private int money;

    /**
     * The identification of the race which the Spectators want to bet and watch.
     */
    private int raceNumber;

    /**
     * The number of races which this Spectator will attend and bet on a horse.
     */
    private int numberOfRaces;

    /**
     * The {@link BettingCentre} instance where this {@link Spectator} will perform its actions.
     */
    private BettingCentreInterface bettingCentre;

    /**
     * The {@link ControlCentre} instance where this {@link Spectator} will perform its actions.
     */
    private ControlCentreInterface controlCentre;

    /**
     * The {@link Paddock} instance where this {@link Spectator} will perform its actions.
     */
    private PaddockInterface paddock;

    /**
     * The {@link GeneralInformationRepository} instance where all this Spectator's actions will be reported.
     */
    private GeneralInformationRepositoryInterface repository;
}
