#!/bin/bash

if [ "$SHELL" = '/bin/zsh' ]; then
    WORK_PATH="$( cd "$( dirname "${(%):-%N}" )" && pwd )/../"
else
    WORK_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/../"
fi

updatecoderemote () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        ssh sd0402@l040101-ws$node.ua.pt 'updcode'
    done
}

preparehippodrome () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        echo "Working on machine number $node."
        echo "Updating and compiling the code..."
        ssh sd0402@l040101-ws$node.ua.pt 'updcode; compileregister; compileservers; compileclients;'
    done
    echo "All update, compiling and RMI enabling were successfully applied on the machines."
}

execute_code () {
    ssh sd0402@l040101-ws01.ua.pt 'startrmi > /dev/null' 2> /dev/null &
    echo "Executing RemoteRegistry & General Repository of Information on Machine01..."
    ssh sd0402@l040101-ws01.ua.pt 'runregister &; runserver general-repo > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Racing Track on Machine02..."
    ssh sd0402@l040101-ws02.ua.pt 'runserver racing-track > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Paddock on Machine03..."
    ssh sd0402@l040101-ws03.ua.pt 'runserver paddock > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Betting Centre on Machine04..."
    ssh sd0402@l040101-ws04.ua.pt 'runserver betting-centre > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Stable on Machine05..."
    ssh sd0402@l040101-ws05.ua.pt 'runserver stable > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Control Centre on Machine06..."
    ssh sd0402@l040101-ws06.ua.pt 'runserver control-centre > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Broker on Machine07..."
    ssh sd0402@l040101-ws07.ua.pt 'runclient broker > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Spectators on Machine08..."
    ssh sd0402@l040101-ws08.ua.pt 'runclient spectators > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Pairs Horse/Jockey on Machine09..."
    ssh sd0402@l040101-ws09.ua.pt 'runclient horses'
}

killallentities () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        echo "Clearing execution of RMI on machine number $node..."
        ssh sd0402@l040101-ws$node.ua.pt "pkill rmi;" 2> /dev/null
    done
}

deployall () {
    git add *
    git commit -m "Added the last version on tests"
    git push origin tests
    preparehippodrome
    execute_code
}

shlastlog () {
    LOG_PATH="$(echo $WORK_PATH)out/server/"
    ssh sd0402@l040101-ws01.ua.pt 'cat $(echo $LOG_PATH)$(ls $LOG_PATH | grep horse-run | tail -n1)' | less
}

startrmi () {
    cd ~
    rmiregistry -J-Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/registry/" -J-Djava.rmi.server.useCodebaseOnly=true 22417 &
    cd -
}

compileregister () {
    cd $WORK_PATH
    javac -cp . registry/*.java hippodrome/*.java entities/*.java
    cd -
    cp $(echo $WORK_PATH)registry/Register.java $(echo $WORK_PATH)out/registry/registry/
    mv $(echo $WORK_PATH)registry/*.class $(echo $WORK_PATH)out/registry/registry/
    mv $(echo $WORK_PATH)configurations/RMIConfigurations.class $(echo $WORK_PATH)out/registry/configurations/
    mv $(echo $WORK_PATH)hippodrome/*.class $(echo $WORK_PATH)out/registry/hippodrome/
    mv $(echo $WORK_PATH)hippodrome/actions/*.class $(echo $WORK_PATH)out/registry/hippodrome/actions/
    mv $(echo $WORK_PATH)hippodrome/responses/*.class $(echo $WORK_PATH)out/registry/hippodrome/responses/
    mv $(echo $WORK_PATH)hippodrome/rollfilm/*.class $(echo $WORK_PATH)out/registry/hippodrome/rollfilm/
    mv $(echo $WORK_PATH)entities/*.class $(echo $WORK_PATH)out/registry/entities/
    rm -rf ~/Public/registry
    cp -rf $(echo $WORK_PATH)out/registry ~/Public/
}

runregister () {
    cd $(echo $WORK_PATH)out/registry
    java -cp . -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/registry/" -Djava.security.policy=java.policy -Djava.rmi.server.useCodebaseOnly=true registry.ServerRegisterRemoteObject
    cd -
}

compileservers () {
    cd $WORK_PATH
    javac -cp ".:lib/genclass.jar" server/*.java clients/*.java hippodrome/*.java
    cd -
    mv $(echo $WORK_PATH)configurations/*.class $(echo $WORK_PATH)out/servers/configurations/
    mv $(echo $WORK_PATH)entities/*.class $(echo $WORK_PATH)out/servers/entities/
    mv $(echo $WORK_PATH)hippodrome/*.class $(echo $WORK_PATH)out/servers/hippodrome/
    mv $(echo $WORK_PATH)hippodrome/actions/*.class $(echo $WORK_PATH)out/servers/hippodrome/actions/
    mv $(echo $WORK_PATH)hippodrome/responses/*.class $(echo $WORK_PATH)out/servers/hippodrome/responses/
    mv $(echo $WORK_PATH)hippodrome/rollfilm/*.class $(echo $WORK_PATH)out/servers/hippodrome/rollfilm/
    cp $(echo $WORK_PATH)hippodrome/*Interface.java $(echo $WORK_PATH)out/servers/hippodrome/
    mv $(echo $WORK_PATH)lib/logging/*.class $(echo $WORK_PATH)out/servers/lib/logging/
    cp $(echo $WORK_PATH)lib/genclass.jar $(echo $WORK_PATH)out/servers/lib/
    mv $(echo $WORK_PATH)registry/*.class $(echo $WORK_PATH)out/servers/registry/
    cp $(echo $WORK_PATH)registry/Register.java $(echo $WORK_PATH)out/servers/registry/
    mv $(echo $WORK_PATH)server/*.class $(echo $WORK_PATH)out/servers/server/
    mv $(echo $WORK_PATH)clients/*.class $(echo $WORK_PATH)out/servers/clients/
    rm -rf ~/Public/servers
    cp -rf $(echo $WORK_PATH)out/servers ~/Public/
}

runserver () {
    cd $(echo $WORK_PATH)out/servers
    java -cp . -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/servers/" -Djava.security.policy=java.policy -Djava.rmi.server.useCodebaseOnly=true server.ServerLauncher $1
    cd -
}

compileclients () {
    cd $WORK_PATH
    javac -cp ".:lib/genclass.jar" clients/*.java hippodrome/*.java server/*.java
    cd -
    mv $(echo $WORK_PATH)configurations/*.class $(echo $WORK_PATH)out/clients/configurations/
    mv $(echo $WORK_PATH)entities/*.class $(echo $WORK_PATH)out/clients/entities/
    mv $(echo $WORK_PATH)hippodrome/*.class $(echo $WORK_PATH)out/clients/hippodrome/
    mv $(echo $WORK_PATH)hippodrome/actions/*.class $(echo $WORK_PATH)out/clients/hippodrome/actions/
    mv $(echo $WORK_PATH)hippodrome/responses/*.class $(echo $WORK_PATH)out/clients/hippodrome/responses/
    mv $(echo $WORK_PATH)hippodrome/rollfilm/*.class $(echo $WORK_PATH)out/clients/hippodrome/rollfilm/
    cp $(echo $WORK_PATH)hippodrome/*Interface.java $(echo $WORK_PATH)out/servers/hippodrome/
    mv $(echo $WORK_PATH)lib/logging/*.class $(echo $WORK_PATH)out/clients/lib/logging/
    cp $(echo $WORK_PATH)lib/genclass.jar $(echo $WORK_PATH)out/clients/lib/
    mv $(echo $WORK_PATH)registry/*.class $(echo $WORK_PATH)out/clients/registry/
    cp $(echo $WORK_PATH)registry/Register.java $(echo $WORK_PATH)out/clients/registry/
    mv $(echo $WORK_PATH)server/*.class $(echo $WORK_PATH)out/clients/server/
    mv $(echo $WORK_PATH)clients/*.class $(echo $WORK_PATH)out/clients/clients/
}

runclient () {
    cd $(echo $WORK_PATH)out/clients
    java -cp . clients.ClientLauncher $1
    cd -
}