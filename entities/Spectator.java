package entities;

import hippodrome.BettingCentre;
import hippodrome.ControlCentre;
import hippodrome.GeneralInformationRepository;
import hippodrome.Paddock;

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
 * @version 0.1
 */
public class Spectator extends Thread {
    /**
     * Creates a Spectator with an {@code identification} and a given amount of {@code money}.
     *
     * @param identification number which identifies this {@code Spectator}.
     * @param money amount of money given to this {@code Spectator}.
     * @param bettingCentre the {@link BettingCentre} instance where this {@link Spectator} will perform its actions.
     * @param controlCentre the {@link ControlCentre} instance where this {@link Spectator} will perform its actions.
     * @param paddock the {@link Paddock} instance where this {@link Spectator} will perform its actions.
     */
    public Spectator(int identification, int money, int numberOfRaces, BettingCentre bettingCentre, ControlCentre controlCentre, Paddock paddock, GeneralInformationRepository repository) {
        this.identification = identification;
        repository.setSpectatorStatus(identification, state);
        this.money = money;
        repository.setSpectatorAmountOfMoney(this.identification, this.money);
        this.bettingCentre = bettingCentre;
        this.controlCentre = controlCentre;
        this.paddock = paddock;
        this.repository = repository;
        this.numberOfRaces = numberOfRaces;
    }

    /**
     * Definition of the Spectator's lifecycle.
     *
     * In a technical perspective this is reasoned by a thread definition function which
     * resumes all the specifications of a {@code Spectator}, since its displacement from the
     * {@link hippodrome.BettingCentre}, to the {@link hippodrome.Paddock}, passing by the {@link hippodrome.Stable}.
     */
    @Override
    public void run() {
        while (controlCentre.waitForTheNextRace(raceNumber)) {                               // while the next race has not started yet
            boolean isLastSpectator = paddock.goCheckHorses();                          //       the Paddock rises an alert for Spectators to go check the horses
            if (isLastSpectator) {                                                      //       if this is the last Spectator to come
                controlCentre.goCheckHorses();                                          //          the Broker on the Control Centre can proceed
            }                                                                           //           its actions;
            paddock.goCheckHorses(isLastSpectator);                                     //       the Paddock also wants to know if there is more Spectators to come
            money -= bettingCentre.placeABet(identification, bet(), horse());           //       on the Betting Centre the Spectator can place its bet
            controlCentre.goWatchTheRace(raceNumber);                                   //       then we go to the Control Centre (Watching Stand) to watch the race
            if (bettingCentre.haveIWon(identification)) {                               //       if the Control Centre approves that I won and if I am not tired
                money += bettingCentre.goCollectTheGains(identification);               //          then I must go to the Betting Centre and collect my gains;
            }
            raceNumber++;
            if (raceNumber == numberOfRaces) {
                break;
            }
        }
        controlCentre.relaxABit();                                                  // having all set, then I must relax;
    }

    /**
     * Sets the Spectator's state, from the possible available {@link SpectatorState}
     * enumeration.
     *
     * @param state Enumeration value represented by {@link SpectatorState}
     */
    public synchronized void setSpectatorState(SpectatorState state) {
        this.state = state;
        repository.setSpectatorStatus(this.identification, state);
        repository.newSnapshot();
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

    private int bet() {
        double[] portion = {0.25, 0.375, 0.0625};
        Random random = new Random();
        return (int)(money * portion[random.nextInt(portion.length)]);
    }

    /**
     * Returns a random horse for this Spectator to bet.
     *
     * @return a random horse identifier, as an integer.
     */
    private int horse() {
        return (new Random()).nextInt(bettingCentre.getNumberOfHorses());
    }

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
     * The {@link BettingCentre} instance where this {@link Spectator} will perform its actions.
     */
    private BettingCentre bettingCentre;

    /**
     * The {@link ControlCentre} instance where this {@link Spectator} will perform its actions.
     */
    private ControlCentre controlCentre;

    /**
     * The {@link Paddock} instance where this {@link Spectator} will perform its actions.
     */
    private Paddock paddock;

    /**
     * The {@link GeneralInformationRepository} instance where this {@link Spectator}
     * will write its current {@link SpectatorState}.
     */
    private GeneralInformationRepository repository;

    private int numberOfRaces;
}
