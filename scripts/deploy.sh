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
        ssh sd0402@l040101-ws$node.ua.pt 'updcode; compile_code'
        #echo "Updating the host addresses..."
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost general-repo l040101-ws01.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost betting-centre l040101-ws02.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost control-centre l040101-ws03.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost paddock l040101-ws04.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost racing-track l040101-ws05.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost stable l040101-ws06.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost broker l040101-ws07.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost spectators l040101-ws08.ua.pt'
        #ssh sd0402@l040101-ws$node.ua.pt 'chhost horses l040101-ws09.ua.pt'
        #echo "Updating the ports..."
        #ssh sd0402@l040101-ws$node.ua.pt 'chport general-repo 22411'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport betting-centre 22412'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport control-centre 22413'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport paddock 22414'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport racing-track 22415'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport stable 22416'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport broker 22417'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport spectators 22418'
        #ssh sd0402@l040101-ws$node.ua.pt 'chport horses 22419'
        echo "All configurations were successfully applied."
    done
}

execute_code () {
    echo "Executing General Repository of Information..."
    ssh sd0402@l040101-ws01.ua.pt 'execserver general-repo > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Betting Centre..."
    ssh sd0402@l040101-ws02.ua.pt 'execserver betting-centre > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Control Centre..."
    ssh sd0402@l040101-ws03.ua.pt 'execserver control-centre > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Paddock..."
    ssh sd0402@l040101-ws04.ua.pt 'execserver paddock > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Racing Track..."
    ssh sd0402@l040101-ws05.ua.pt 'execserver racing-track > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Stable..."
    ssh sd0402@l040101-ws06.ua.pt 'execserver stable > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Broker..."
    ssh sd0402@l040101-ws07.ua.pt 'execclient broker > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Spectators..."
    ssh sd0402@l040101-ws08.ua.pt 'execclient spectators > /dev/null' 2> /dev/null &
    sleep 2
    echo "Executing Pairs Horse/Jockey..."
    ssh sd0402@l040101-ws09.ua.pt 'execclient horses > /dev/null' 2> /dev/null
    echo "The race is over!"
}

killallentities () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        echo "Clearing execution on machine number $node..."
        ssh sd0402@l040101-ws$node.ua.pt "killscenario" 2> /dev/null
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
    ssh sd0402@l040101-ws01.ua.pt 'cat $WORK_PATH/$(ls $WORK_PATH | grep horse-run | tail -n1)' | less
}

startrmi () {
    cd ~
    rmiregistry8 -J-Djava.rmi.server.useCodebaseOnly=false 22420 &
    cd -
}

compileregister () {
    cd $WORK_PATH
    javac8 -cp ".:lib/genclass.jar" registry/*.java hippodrome/*.java entities/*.java
    cd -
    cp $WORK_PATH/registry/Register.java $WORK_PATH/out/registry/registry
    mv $WORK_PATH/registry/*.class $WORK_PATH/out/registry/registry
    mv $WORK_PATH/configurations/RMIConfigurations.class $WORK_PATH/out/registry/configurations
    mv $WORK_PATH/hippodrome/*.class $WORK_PATH/out/registry/hippodrome
    mv $WORK_PATH/hippodrome/actions/*.class $WORK_PATH/out/registry/hippodrome/actions
    mv $WORK_PATH/hippodrome/responses/*.class $WORK_PATH/out/registry/hippodrome/responses
    mv $WORK_PATH/hippodrome/rollfilm/*.class $WORK_PATH/out/registry/hippodrome/rollfilm
    mv $WORK_PATH/entities/*.class $WORK_PATH/out/registry/entities
}

runregister () {
    cd $WORK_PATH/out/registry
    java8 -cp . -Djava.rmi.server.codebase="file://$(echo $WORK_PATH)out/registry/" -Djava.security.policy=java.policy -Djava.rmi.server.useCodebaseOnly=false registry.ServerRegisterRemoteObject
    cd -
}

compileservers () {
    cd $WORK_PATH
    javac8 -cp ".:lib/genclass.jar" server/*.java clients/*.java hippodrome/*.java
    cd -
    mv $(echo $WORK_PATH)configurations/*.class $(echo $WORK_PATH)out/servers/configurations
    mv $(echo $WORK_PATH)entities/*.class $(echo $WORK_PATH)out/servers/entities
    mv $(echo $WORK_PATH)hippodrome/*.class $(echo $WORK_PATH)out/servers/hippodrome
    mv $(echo $WORK_PATH)hippodrome/actions/*.class $(echo $WORK_PATH)out/servers/hippodrome/actions
    mv $(echo $WORK_PATH)hippodrome/responses/*.class $(echo $WORK_PATH)out/servers/hippodrome/responses
    mv $(echo $WORK_PATH)hippodrome/rollfilm/*.class $(echo $WORK_PATH)out/servers/hippodrome/rollfilm
    cp $(echo $WORK_PATH)hippodrome/*Interface.java $(echo $WORK_PATH)out/servers/hippodrome
    mv $(echo $WORK_PATH)lib/logging/*.class $(echo $WORK_PATH)out/servers/lib/logging
    cp $(echo $WORK_PATH)lib/genclass.jar $(echo $WORK_PATH)out/servers/lib
    mv $(echo $WORK_PATH)registry/*.class $(echo $WORK_PATH)out/servers/registry
    cp $(echo $WORK_PATH)registry/Register.java $(echo $WORK_PATH)out/servers/registry
    mv $(echo $WORK_PATH)server/*.class $(echo $WORK_PATH)out/servers/server
    mv $(echo $WORK_PATH)clients/*.class $(echo $WORK_PATH)out/servers/clients
}

runserver () {
    cd $WORK_PATH/out/servers
    java8 -cp . -Djava.rmi.server.codebase="file://$(echo $WORK_PATH)out/servers/" -Djava.security.policy=java.policy -Djava.rmi.server.useCodebaseOnly=false server.ServerLauncher $1
    cd -
}

compileclients () {
    cd $WORK_PATH
    javac8 -cp ".:lib/genclass.jar" clients/*.java hippodrome/*.java server/*.java
    cd -
    mv $(echo $WORK_PATH)configurations/*.class $(echo $WORK_PATH)out/clients/configurations
    mv $(echo $WORK_PATH)entities/*.class $(echo $WORK_PATH)out/clients/entities
    mv $(echo $WORK_PATH)hippodrome/*.class $(echo $WORK_PATH)out/clients/hippodrome
    mv $(echo $WORK_PATH)hippodrome/actions/*.class $(echo $WORK_PATH)out/clients/hippodrome/actions
    mv $(echo $WORK_PATH)hippodrome/responses/*.class $(echo $WORK_PATH)out/clients/hippodrome/responses
    mv $(echo $WORK_PATH)hippodrome/rollfilm/*.class $(echo $WORK_PATH)out/clients/hippodrome/rollfilm
    cp $(echo $WORK_PATH)hippodrome/*Interface.java $(echo $WORK_PATH)out/servers/hippodrome
    mv $(echo $WORK_PATH)lib/logging/*.class $(echo $WORK_PATH)out/clients/lib/logging
    cp $(echo $WORK_PATH)lib/genclass.jar $(echo $WORK_PATH)out/clients/lib
    mv $(echo $WORK_PATH)registry/*.class $(echo $WORK_PATH)out/clients/registry
    cp $(echo $WORK_PATH)registry/Register.java $(echo $WORK_PATH)out/clients/registry
    mv $(echo $WORK_PATH)server/*.class $(echo $WORK_PATH)out/clients/server
    mv $(echo $WORK_PATH)clients/*.class $(echo $WORK_PATH)out/clients/clients
}

runclient () {
    cd $WORK_PATH/out/clients
    java8 -cp . -Djava.rmi.server.codebase="file://$(echo $WORK_PATH)out/clients/" -Djava.rmi.server.useCodebaseOnly=false clients.ClientLauncher $1
    cd -
}