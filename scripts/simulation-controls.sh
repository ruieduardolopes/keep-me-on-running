#!/bin/bash

# Method to change the number of Pairs Horse/Jockey of the simulations.
#
# Parameters:
#     $1 - is the new number of pairs Horse/Jockey.
#
chhorses () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chhorses_usage
        return 0
    fi
    if [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chhorses_usage
        return 1
    fi

    sed -ie 's/NUMBER_OF_PAIRS_HORSE_JOCKEY.*/NUMBER_OF_PAIRS_HORSE_JOCKEY = $1;/g' configurations/SimulationConfigurations.java

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
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chspec_usage
        return 0
    fi
    if [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chspec_usage
        return 1
    fi

    sed -ie 's/NUMBER_OF_SPECTATORS.*/NUMBER_OF_SPECTATORS = $1;/g' configurations/SimulationConfigurations.java

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
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chraces_usage
        return 0
    fi

    optionT=0
    if [[ "$#" -eq 2 && "$1" = '-t' ]]; then
        optionT=1
    elif [ "$#" -ne 1 ]; then
        echo "Options not recognized." >&2
        print_chraces_usage
        return 1
    fi

    if [ "$optionT" -eq 1 ]; then
        sed -ie 's/NUMBER_OF_TRACKS.*/NUMBER_OF_TRACKS = $1;/g' configurations/SimulationConfigurations.java
    fi

    sed -ie 's/NUMBER_OF_RACES.*/NUMBER_OF_RACES = $1;/g' configurations/SimulationConfigurations.java

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


