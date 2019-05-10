#tcpserver.pl
use lib '/home/alonso/Dropbox/Tarea/PrograIntegrativa/Perl/'; #change to project's current directory
require decode;
require csv_;

use IO::Socket;

$| = 1;

$socket = new IO::Socket::INET (
                                  LocalHost => '127.0.0.1',
                                  LocalPort => '5000',
                                  Proto => 'tcp',
                                  Listen => 5,
                                  Reuse => 1
                               );
                                
die "Coudn't open socket" unless $socket;

print "TCPServer Waiting for client on port 5000\n";

while(1)
{
	$client_socket = "";
	$from_client = '';
	$client_socket = $socket->accept();
	$client_socket->recv($from_client,1024);
	
	@packet = decode::decode_packet($from_client);
	$length = @packet;
	$" = ',';
	print "pack: @packet, size: $length\n";
	if($length != 0){
		if($length == 3)
		{
			$line = csv_::findInFile($csv_::DATA_LOG);
			if($line == 'Not found'){
				$toSend = code::codeUpdateString("notfound",0);
			}else{
				$toSend = code::codeFromLog($line);
			}
			$client_socket->send($toSend);
		}
		print "packet(0,-1): @packet[0..-1]\n";
		csv_::writeToFile($packet[-1], csv_::list_to_csv(@packet[0..$#packet-1]));
	}
}