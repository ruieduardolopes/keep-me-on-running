#!/bin/bash

# Method to revert the changes made to the configurations of the Simulation.
#
revertsim () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_revertsim_usage
        return 0
    fi
    if [ "$#" -ne 0 ]; then
        echo "Options not recognized." >&2
        print_revertsim_usage
        return 1
    fi

    cd $WORK_PATH
    git checkout --force tests

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to revert the configurations of made to the simulator." >&2
        cd -
        return 2
    fi
    cd -
    return 0
}

print_revertsim_usage () {
    echo "Usage: revertsim"
}

# Method to update the code on the repository.
#
updcode () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_updcode_usage
        return 0
    fi
    if [ "$#" -ne 0 ]; then
        echo "Options not recognized." >&2
        print_updcode_usage
        return 1
    fi

    cd $WORK_PATH
    git checkout --force tests
    git pull origin tests

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to update the code from the repository." >&2
        cd -
        return 2
    fi
    source /home/sd0402/.bashrc
    cd -
    return 0
}

print_updcode_usage () {
    echo "Usage: updcode"
}