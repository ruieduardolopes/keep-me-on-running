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
    echo "Loading and compiling register and server code on Machine 01..."
    ssh sd0402@l040101-ws01.ua.pt 'updcode; compileregister; compileservers;'
    echo "Loading and compiling server code on Machines 02, 03, 04, 05 and 06..."
    for node in {02,03,04,05,06}; do
        echo "Working on machine number $node..."
        echo "Updating and compiling the code..."
        ssh sd0402@l040101-ws$node.ua.pt 'updcode; compileregister; compileservers;'
        echo "Machine number 0$node successfully configured."
    done
    echo "Loading and compiling client code on Machines 07, 08 and 09..."
    for node in {07,08,09}; do
        echo "Working on machine number $node..."
        echo "Updating and compiling the code..."
        ssh sd0402@l040101-ws$node.ua.pt 'updcode; compileregister; compileclients;'
        echo "Machine number 0$node successfully configured."
    done
    echo "All machines were successfully updated."
}

execute_code () {
    ssh sd0402@l040101-ws01.ua.pt 'startrmi > /dev/null' 2> /dev/null &
    echo "Executing RemoteRegistry & General Repository of Information on Machine01..."
    ssh sd0402@l040101-ws01.ua.pt 'runregister > /dev/null' 2> /dev/null &
    sleep 2
    ssh sd0402@l040101-ws01.ua.pt 'runserver general-repo > /dev/null' 2> /dev/null &
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
    ssh sd0402@l040101-ws09.ua.pt "runclient horses"
   #ssh sd0402@l040101-ws09.ua.pt "runclient horses && sleep 5 && ssh l040101-ws01.ua.pt 'pkill java; pkill rmiregistry;'"
}

killallentities () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        ssh sd0402@l040101-ws$node.ua.pt 'pkill java; pkill rmiregistry;' 2> /dev/null
    done
    echo "The RMI service was terminated."
}

deployall () {
    git add *
    git commit -m "Added the last version on alternative_tests"
    git push origin alternative_tests
    preparehippodrome
    execute_code
}

sendcode () {
    git add *
    git commit -m "Added the last version on alternative_tests"
    git push origin alternative_tests
}

shlastlog () {
    ssh sd0402@l040101-ws01.ua.pt 'cd /home/sd0402/keep-me-on-running/out/servers && cat $(ls | grep horse-run | tail -n1 )' | less
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
    mkdir ~/Public/registry
    mkdir ~/Public/registry/entities
    mkdir ~/Public/registry/hippodrome
    mkdir ~/Public/registry/registry
    cp -rf $(echo $WORK_PATH)out/registry/registry/Register.class ~/Public/registry/registry
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/*Interface.class ~/Public/registry/hippodrome
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/actions/Race.class ~/Public/registry/hippodrome/actions
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/responses/*.class ~/Public/registry/hippodrome/responses
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
    mkdir ~/Public/servers
    mkdir ~/Public/servers/entities
    mkdir ~/Public/servers/hippodrome
    mkdir ~/Public/servers/hippodrome/actions
    mkdir ~/Public/servers/hippodrome/responses
    cp -rf $(echo $WORK_PATH)out/servers/entities/*.class ~/Public/servers/entities
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/*Interface.class ~/Public/servers/hippodrome
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/actions/Race.class ~/Public/servers/hippodrome/actions
    cp -rf $(echo $WORK_PATH)out/servers/hippodrome/responses/*.class ~/Public/servers/hippodrome/responses
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