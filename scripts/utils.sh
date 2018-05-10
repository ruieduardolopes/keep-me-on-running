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

# Method to update the code on the repository.
#
compile_code () {
    if [[ "$1" -eq '-h' && "$#" -eq 1 ]]; then
        print_compile_code_usage
        return 0
    fi
    if [ "$#" -ne 0 ]; then
        echo "Options not recognized." >&2
        print_compile_code_usage
        return 1
    fi

    cd $WORK_PATH
    javac -cp "lib/genclass.jar:" clients/ClientLauncher.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to compile the code." >&2
        cd -
        return 2
    fi

    javac -cp "lib/genclass.jar:" server/ServerLauncher.java

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to compile the code." >&2
        cd -
        return 3
    fi
    cd -
    return 0
}

print_compile_code_usage () {
    echo "Usage: compile_code"
}

# Method to execute the server.
#
execserver () {
    if [[ "$#" -eq 1 ]] && [[ "$1" -eq '-h' ]]; then
        print_execserver_usage
        return 0
    fi
    if [[ "$#" -ne 1 ]]; then
        echo "Options not recognized." >&2
        print_execserver_usage
        return 1
    fi

    cd $WORK_PATH
    java -cp "lib/genclass.jar:" server.ServerLauncher "$1"

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to run the code." >&2
        cd -
        return 2
    fi
    cd -
    return 0
}

print_execserver_usage () {
    echo "Usage: execserver <hippodrome-region>"
}

killscenario () {
    tokill=$(ps aux | grep java | grep sd0402 | awk '{ print $2 }'| column)
    for item in $tokill; do
        kill $item
    done
}

# Method to execute the client.
#
execclient () {
    if [[ "$#" -eq 1 ]] && [[ "$1" -eq '-h' ]]; then
        print_execclient_usage
        return 0
    fi
    if [[ "$#" -ne 1 ]]; then
        echo "Options not recognized." >&2
        print_execclient_usage
        return 1
    fi

    cd $WORK_PATH
    java -cp "lib/genclass.jar:" clients.ClientLauncher "$1"

    # Verification of the result
    if [[ "$?" -ne 0 ]]; then
        echo "Unable to run the code." >&2
        cd -
        return 2
    fi
    cd -
    return 0
}

print_execclient_usage () {
    echo "Usage: execclient <entity>"
}

