require 'socket'
load 'decode.rb'
load 'my_csv.rb'

$server = TCPServer.new 2000 # Server bound to port 2000

def receive_from_socket
	client = $server.accept
	return client.gets
end

def send_to_socket(message)
	client = $server.accept
	if message.is_a? String
		client.puts message
	else
		client.puts "ERROR"
	end
end


loop do
	from_client = receive_from_socket.chomp
	packet = decode_packet(from_client)
	unless packet == false or packet == nil
		if packet.length == 3
			line = findInFile($DATA_LOG, packet[1])
			if line == "Not found"
				toSend = codeUpdateString("notfound", 0)
			else
				toSend = codeFromLog(line)
			end
			send_to_socket(toSend)
		end
		writeToFile(packet[-1] ,list_to_csv(packet[0...-1]))
	end
end
