#!/bin/bash

updatecoderemote () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        ssh sd0402@l040101-ws$node.ua.pt 'updcode'
    done
}
preparehippodrome () {
    for node in {01,02,03,04,05,06,07,08,09}; do
        ssh sd0402@l040101-ws$node.ua.pt 'updcode; compile_code'
    done
    ssh sd0402@l040101-ws01.ua.pt 'chhost general-repo l040101-ws01.ua.pt'
    ssh sd0402@l040101-ws02.ua.pt 'chhost betting-centre l040101-ws02.ua.pt'
    ssh sd0402@l040101-ws03.ua.pt 'chhost control-centre l040101-ws03.ua.pt'
    ssh sd0402@l040101-ws04.ua.pt 'chhost paddock l040101-ws04.ua.pt'
    ssh sd0402@l040101-ws05.ua.pt 'chhost racing-track l040101-ws05.ua.pt'
    ssh sd0402@l040101-ws06.ua.pt 'chhost stable l040101-ws06.ua.pt'
    ssh sd0402@l040101-ws07.ua.pt 'chhost broker l040101-ws07.ua.pt'
    ssh sd0402@l040101-ws08.ua.pt 'chhost spectators l040101-ws08.ua.pt'
    ssh sd0402@l040101-ws09.ua.pt 'chhost horses l040101-ws09.ua.pt'
    ssh sd0402@l040101-ws01.ua.pt 'chport general-repo 22411'
    ssh sd0402@l040101-ws02.ua.pt 'chport betting-centre 22412'
    ssh sd0402@l040101-ws03.ua.pt 'chport control-centre 22413'
    ssh sd0402@l040101-ws04.ua.pt 'chport paddock 22414'
    ssh sd0402@l040101-ws05.ua.pt 'chport racing-track 22415'
    ssh sd0402@l040101-ws06.ua.pt 'chport stable 22416'
    ssh sd0402@l040101-ws07.ua.pt 'chport broker 22417'
    ssh sd0402@l040101-ws08.ua.pt 'chport spectators 22418'
    ssh sd0402@l040101-ws09.ua.pt 'chport horses 22419'
}

execute_code () {
    ssh sd0402@l040101-ws01.ua.pt 'execserver general-repo' &
    ssh sd0402@l040101-ws02.ua.pt 'execserver betting-centre' &
    ssh sd0402@l040101-ws03.ua.pt 'execserver control-centre' &
    ssh sd0402@l040101-ws04.ua.pt 'execserver paddock' &
    ssh sd0402@l040101-ws05.ua.pt 'execserver racing-track' &
    ssh sd0402@l040101-ws06.ua.pt 'execserver stable' &
    ssh sd0402@l040101-ws07.ua.pt 'execclient broker' &
    ssh sd0402@l040101-ws08.ua.pt 'execclient spectators' &
    ssh sd0402@l040101-ws09.ua.pt 'execclient horses' 
}

deployall () {
    git checkout tests
    git merge dev
    git add *
    git commit -m "Added the last version on tests"
    git push origin tests
    preparehippodrome
    execute_code
}