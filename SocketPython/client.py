import socket

def send_to_socket(message):
	if type(message) is type(""):
		message=bytes(message,"utf-8")
		clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		clientsocket.connect(('localhost', 8089)) 
		  
		clientsocket.send(message)

def send_and_receive_to_socket(message):
	if type(message) is type(""):
		message = bytes(message,"utf-8")
		clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		clientsocket.connect(('localhost', 8089)) 
		  
		clientsocket.send(message)
		buf = clientsocket.recv(64)
		return buf
