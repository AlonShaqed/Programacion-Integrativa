require 'socket'
load 'decode.rb'
load 'csv.rb'

$server = TCPServer.new 2000 # Server bound to port 2000

def receive_from_socket
	client = $server.accept
	return client.gets
end

def receive_and_send_from_socket(message)
	if message.is_a? String
		client = $server.accept
		ret = client.gets
		client.puts message

		return ret
	end

	return "Invalid message"
end


loop do
	from_client = receive_from_socket.chomp
	packet = decode_packet(from_client)
	
	unless packet == false or packet == nil
		if packet.length == 3
			line = findInLine($DATA_LOG, packet[1])
			if line == "Not found"
				toSend = codeUpdateString("notfound", 0)
			else
				toSend = codeFromLog(line)
			end
			receive_and_send_from_socket(toSend)
		end
		writeToFile(packet[-1], list_to_csv(packet[0...-1]))
	end
end