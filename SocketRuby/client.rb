require 'socket'
load 'code.rb'

def send_to_socket(message)
	if message.is_a? String
		s = TCPSocket.new 'localhost', 2000
		s.puts message
	end
end

def send_and_receive_to_socket(message)
	if message.is_a? String
		s = TCPSocket.new 'localhost', 2000
		s.puts message

		return s.gets
	end
end
