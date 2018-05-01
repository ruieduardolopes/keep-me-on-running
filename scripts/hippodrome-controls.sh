#!/bin/bash

# Method to change host address of a given `hippodrome` service or `entity`.
#
# Parameters:
#     $1 - is the `hippodrome` service or `entity`: `betting-centre`, `control-centre`,
#          `general-repo`, `paddock`, `racing-track`, `stable`, `broker`, `horses` or `spectators`.
#     $2 - is the IPv4 address (or the `localhost`) of the `hippodrome` service or `entity`.
#
chhost () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chhost_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chhost_usage
        return 1
    fi

    if [ "$1" = 'betting-centre' ]; then
        sed -ie 's/BETTING_CENTRE_HOST.*/BETTING_CENTRE_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'control-centre' ]; then
        sed -ie 's/CONTROL_CENTRE_HOST.*/CONTROL_CENTRE_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'general-repo' ]; then
        sed -ie 's/GENERAL_INFORMATION_REPOSITORY_HOST.*/GENERAL_INFORMATION_REPOSITORY_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'paddock' ]; then
        sed -ie 's/PADDOCK_HOST.*/PADDOCK_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'racing-track' ]; then
        sed -ie 's/RACING_TRACK_HOST.*/RACING_TRACK_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'stable' ]; then
        sed -ie 's/STABLE_HOST.*/STABLE_HOST = "$2";/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'broker' ]; then
        sed -ie 's/BROKER_HOST.*/BROKER_HOST = "$2";/g' configurations/ClientConfigurations.java
    elif [ "$1" = 'spectators' ]; then
        sed -ie 's/SPECTATORS_HOST.*/SPECTATORS_HOST = "$2";/g' configurations/ClientConfigurations.java
    elif [ "$1" = 'horses' ]; then
        sed -ie 's/HORSES_HOST.*/HORSES_HOST = "$2";/g' configurations/ClientConfigurations.java
    else
        echo "Invalid hippodrome service or entity. Please try one of the following ones"
        print_chhost_usage
    fi

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the address of $1 to $2." >&2
        return 2
    fi
    return 0
}

print_chhost_usage () {
    echo "Usage: chhost <hippodrome-service>|<entity> <address>"
    echo "Hippodrome Services:"
    echo "  - betting-centre  : The Betting Centre"
    echo "  - control-centre  : The Control Centre"
    echo "  - general-repo    : The General Repository of Information"
    echo "  - paddock         : The Paddock"
    echo "  - racing-track    : The Racing Track"
    echo "  - stable          : The Stable"
    echo "Entities:"
    echo "  - broker          : The Broker"
    echo "  - spectators      : The set of Spectators"
    echo "  - horses          : The set of Pairs Horse/Jockey"
}

# Method to change the port of a given `hippodrome` service or `entity`.
#
# Parameters:
#     $1 - is the `hippodrome` service or `entity`: `betting-centre`, `control-centre`,
#          `general-repo`, `paddock`, `racing-track`, `stable`, `broker`, `horses` or `spectators`.
#     $2 - is the port of the `hippodrome` service or `entity`.
#
chport () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chport_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chport_usage
        return 1
    fi

    if [ "$1" = 'betting-centre' ]; then
        sed -ie 's/BETTING_CENTRE_PORT.*/BETTING_CENTRE_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'control-centre' ]; then
        sed -ie 's/CONTROL_CENTRE_PORT.*/CONTROL_CENTRE_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'general-repo' ]; then
        sed -ie 's/GENERAL_INFORMATION_REPOSITORY_PORT.*/GENERAL_INFORMATION_REPOSITORY_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'paddock' ]; then
        sed -ie 's/PADDOCK_PORT.*/PADDOCK_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'racing-track' ]; then
        sed -ie 's/RACING_TRACK_PORT.*/RACING_TRACK_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'stable' ]; then
        sed -ie 's/STABLE_PORT.*/STABLE_PORT = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'broker' ]; then
        sed -ie 's/BROKER_PORT.*/BROKER_PORT = $2;/g' configurations/ClientConfigurations.java
    elif [ "$1" = 'spectators' ]; then
        sed -ie 's/SPECTATORS_PORT.*/SPECTATORS_PORT = $2;/g' configurations/ClientConfigurations.java
    elif [ "$1" = 'horses' ]; then
        sed -ie 's/HORSES_PORT.*/HORSES_PORT = $2;/g' configurations/ClientConfigurations.java
    else
        echo "Invalid hippodrome service or entity. Please try one of the following ones"
        print_chport_usage
    fi

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the port of $1 to $2." >&2
        return 2
    fi
    return 0
}

print_chport_usage () {
    echo "Usage: chport <hippodrome-service>|<entity> <port>"
    echo "Hippodrome Services:"
    echo "  - betting-centre  : The Betting Centre"
    echo "  - control-centre  : The Control Centre"
    echo "  - general-repo    : The General Repository of Information"
    echo "  - paddock         : The Paddock"
    echo "  - racing-track    : The Racing Track"
    echo "  - stable          : The Stable"
    echo "Entities:"
    echo "  - broker          : The Broker"
    echo "  - spectators      : The set of Spectators"
    echo "  - horses          : The set of Pairs Horse/Jockey"
}

# Method to change the time to sleep of a given `hippodrome` service thread.
#
# Parameters:
#     $1 - is the `hippodrome` service or `entity`: `betting-centre`, `control-centre`,
#          `general-repo`, `paddock`, `racing-track`, `stable`.
#     $2 - is the time to sleep, in milliseconds (ms), of the `hippodrome` service.
#
chsleeptime () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_chsleeptime_usage
        return 0
    fi
    if [ "$#" -ne 2 ]; then
        echo "Options not recognized." >&2
        print_chsleeptime_usage
        return 1
    fi

    if [ "$1" = 'betting-centre' ]; then
        sed -ie 's/BETTING_CENTRE_TIME_TO_SLEEP.*/BETTING_CENTRE_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'control-centre' ]; then
        sed -ie 's/CONTROL_CENTRE_TIME_TO_SLEEP.*/CONTROL_CENTRE_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'general-repo' ]; then
        sed -ie 's/GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP.*/GENERAL_INFORMATION_REPOSITORY_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'paddock' ]; then
        sed -ie 's/PADDOCK_TIME_TO_SLEEP.*/PADDOCK_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'racing-track' ]; then
        sed -ie 's/RACING_TRACK_TIME_TO_SLEEP.*/RACING_TRACK_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    elif [ "$1" = 'stable' ]; then
        sed -ie 's/STABLE_TIME_TO_SLEEP.*/STABLE_TIME_TO_SLEEP = $2;/g' configurations/SimulationConfigurations.java
    else
        echo "Invalid hippodrome service or entity. Please try one of the following ones"
        print_chsleeptime_usage
    fi

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to replace the port of $1 to $2." >&2
        return 2
    fi
    return 0
}

print_chsleeptime_usage () {
    echo "Usage: chsleeptime <hippodrome-service> <time-to-sleep-ms>"
    echo "Hippodrome Services:"
    echo "  - betting-centre  : The Betting Centre"
    echo "  - control-centre  : The Control Centre"
    echo "  - general-repo    : The General Repository of Information"
    echo "  - paddock         : The Paddock"
    echo "  - racing-track    : The Racing Track"
    echo "  - stable          : The Stable"
    echo "Time to Sleep:"
    echo "  - integer         : Amount of Time to be considered as thread sleep time, in milliseconds (ms)"
}