use lib '/home/alonso/Dropbox/Tarea/PrograIntegrativa/Perl/'; #change to project's current directory
require code;
require decode;

use IO::Socket;

sub l_gets{
    my $size = $_[0];

    print " >";
    do{
        $word = <STDIN>;
        if(length($word) > $size){
            print "char limit overflow\n >";
        }
    }while(length($word) > $size);

    return $word;
}

sub update{
    print "Update=====------\n";
    print "Nombre del sensor (8 bytes)";
    $sensor = l_gets(9);
    chomp $sensor;
    print "Dato (8 bytes: float)";
    $data = l_gets(9);
    chomp $data;
    $data = code::is_numeric $data;
    if($data == 0){
        print "Adv: El dato es equivalente a 0\n";
    }

    return code::codeUpdateString($sensor,$data);
}

sub query{
    print "Query=====------\n";
    print "Nombre del observador (8 bytes)";
    $sensor = l_gets(9);
    chomp $sensor;
    print "Nombre del sensor (8 bytes)";
    $data = l_gets(9);
    chomp $data;

    return code::codeObserverString($sensor,$data);
}

sub menu{
    print "Select an option to request:\n\t1. Update\n\t2. Query\n\t3. Exit\n";
    $select = l_gets(3);

    if($select == 1){
        $socket->send(update);
        return 1;
    }
    if($select ==2){
        $socket->send(query);
        print "Receiving...\n";
        $socket->recv($log, 1024);
        $" = ", ";
        chomp $log;
        @from_server = decode::decodeUpdateString($log)
            or die "No se pudo recibir del socket";
        print "Server: |@from_server|\n";
        return 1;
    }else{
        print "Exit.\n";
        if($select != 3){
            print "Operacion no disponible\n";
        }
    }
}

do{
    $socket = new IO::Socket::INET (
                                  PeerAddr  => '127.0.0.1',
                                  PeerPort  =>  5000,
                                  Proto => 'tcp',
                               )                
        or die "Couldn't connect to Server\n";

    $continue = menu;
}while($continue == 1);