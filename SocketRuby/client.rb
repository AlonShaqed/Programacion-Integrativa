require 'socket'
load "decode.rb"
load 'code.rb'

def send_to_socket(message)
	if message.is_a? String
		s = TCPSocket.new 'localhost', 2000
		s.puts message
	end
end

def receive_from_socket
	s = TCPSocket.new 'localhost', 2000
	return s.gets
end

def l_gets(size) #limit char size gets
	got = String.new

	loop do
		got = gets.chomp
		break unless got.length > size
		puts "char limit overflowed (#{got.length} > #{size})"
	end
	return got
end

def update
	puts "Update=====------\nNombre del sensor (8 bytes) >"
	sensor = l_gets(8)
	puts "Dato (8 bytes: float) >"
	data = l_gets(8).to_f

	return codeUpdateString(sensor,data)
end

def query
	puts "Query=====------\nNombre del observador (8 bytes) >"
	sensor = l_gets(8)
	puts "Nombre del sensor (8 bytes) >"
	data = l_gets(8)

	return codeObserverString(sensor,data)
end


def menu
	puts "Select an option to request:\n\t1. Update\n\t2. Query\n\t3. Exit"
	select_ = gets.to_i

	if select_ == 1
		send_to_socket(update)
		return true
	elsif select_ == 2
		send_to_socket(query)
		log = receive_from_socket
		puts "Server: |#{decodeUpdateString(log.chomp)}|" rescue puts "No se pudo recibir desde el servidor :("
		return true
	else
		puts "Getting out..."
		if select_ != 3
			puts "option #{select_} not available"
		end

		return false
	end
end

loop do
	continue = menu
	unless continue == true
		break
	end
end
