#!/bin/bash

# Method to change the number of Pairs Horse/Jockey of the simulations.
#
# Parameters:
#     $1 - is the new number of pairs Horse/Jockey.
#
chhorses () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chhorses_usage
        return 0
    fi
    if [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chhorses_usage
        return 1
    fi

    sed -ie "s/NUMBER_OF_PAIRS_HORSE_JOCKEY.*/NUMBER_OF_PAIRS_HORSE_JOCKEY = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the number of pairs Horse/Jockey to $1." >&2
        return 2
    fi
    return 0
}

print_chhorses_usage () {
    echo "Usage: chhorses <number-of-horses>"
}

# Method to change the number of Spectators of the simulations.
#
# Parameters:
#     $1 - is the new number of Spectators.
#
chspec () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chspec_usage
        return 0
    fi
    if [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chspec_usage
        return 1
    fi

    sed -ie "s/NUMBER_OF_SPECTATORS.*/NUMBER_OF_SPECTATORS = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the number of Spectators to $1." >&2
        return 2
    fi
    return 0
}

print_chspec_usage () {
    echo "Usage: chspec <number-of-spectators>"
}

# Method to change the number of Races of the simulations.
#
# Parameters:
#     $1 - is the new number of Races or the option '-t'.
#     $2 - is the new number of Races in case option '-t' is used.
#
chraces () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chraces_usage
        return 0
    fi

    optionT=0
    if [[ "$#" -eq 2 && $1 = '-t' ]]; then
        optionT=1
    elif [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chraces_usage
        return 1
    fi

    if [ "$optionT" -eq 1 ]; then
        sed -ie "s/NUMBER_OF_TRACKS.*/NUMBER_OF_TRACKS = $2;/g" $WORK_PATH/configurations/SimulationConfigurations.java
        sed -ie "s/NUMBER_OF_RACES.*/NUMBER_OF_RACES = $2;/g" $WORK_PATH/configurations/SimulationConfigurations.java
    else
        sed -ie "s/NUMBER_OF_RACES.*/NUMBER_OF_RACES = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java
    fi

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the number of Races to $1." >&2
        return 2
    fi
    return 0
}

print_chraces_usage () {
    echo "Usage: chraces [-t] <number-of-races>"
    echo "Options:"
    echo "  -t   Produce the same change to NUMBER_OF_TRACKS (the number of tracks on a racing track)."
}

# Method to change the ability of the pair Horse/Jockey on the simulations.
#
# Parameters:
#     $1 - is the new minimum ability.
#     $2 - is the new maximum ability.
#
chability () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chability_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chability_usage
        return 1
    fi

    sed -ie "s/ABILITY_MIN_BOUND.*/ABILITY_MIN_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java
    sed -ie "s/ABILITY_MAX_BOUND.*/ABILITY_MAX_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the Pair Horse/Jockey's ability to $1 as minimum and $2 as maximum." >&2
        return 2
    fi
    return 0
}

print_chability_usage () {
    echo "Usage: chability <minimum-ability> <maximum-ability>"
}

# Method to change the amount of money of the Spectators on the simulations.
#
# Parameters:
#     $1 - is the new minimum amount of money.
#     $2 - is the new maximum amount of money.
#
chmoney () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chmoney_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chmoney_usage
        return 1
    fi

    sed -ie "s/MONEY_MIN_BOUND.*/MONEY_MIN_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java
    sed -ie "s/MONEY_MAX_BOUND.*/MONEY_MAX_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the Spectator's amount of money to $1 as minimum and $2 as maximum." >&2
        return 2
    fi
    return 0
}

print_chmoney_usage () {
    echo "Usage: chmoney <minimum-amount-of-money> <maximum-amount-of-money>"
}

# Method to change the distance of the Races on the simulations.
#
# Parameters:
#     $1 - is the new minimum distance.
#     $2 - is the new maximum distance.
#
chdistance () {
    if [[ $1 -eq '-h' && "$#" -eq 1 ]]; then
        print_chdistance_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chdistance_usage
        return 1
    fi

    sed -ie "s/TRACK_DISTANCE_MIN_BOUND.*/TRACK_DISTANCE_MIN_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java
    sed -ie "s/TRACK_DISTANCE_MAX_BOUND.*/TRACK_DISTANCE_MAX_BOUND = $1;/g" $WORK_PATH/configurations/SimulationConfigurations.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the Race's distance to $1 as minimum and $2 as maximum." >&2
        return 2
    fi
    return 0
}

print_chdistance_usage () {
    echo "Usage: chdistance <minimum-distance> <maximum-distance>"
}