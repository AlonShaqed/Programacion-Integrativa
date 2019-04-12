#! /usr/bin/python3
import socket
from decode import *
from code import codeFromLog, codeUpdateString
from csv import *

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

serversocket.bind(('localhost', 8089))

serversocket.listen(5)		#become a server socket, maximum 5 connections

def receive_from_socket():
	connection, address = serversocket.accept()
	buf = connection.recv(64)
	if len(buf)> 0:
		return buf

def receive_and_send_from_socket(message):
	if type(message) is type(""):
		message = bytes(message, "utf-8")
		connection, address = serversocket.accept()
		buf = connection.recv(64)
		if len(buf)> 0:
			connection.send(message)
			return buf


while True:
	fromClient = receive_from_socket().decode("utf-8")
	packet = decode_packet(fromClient)
	if packet is not None or packet is not False:
		if len(packet) == 3:
			line = findInFile(DATA_LOG,packet[1])
			if line == "Not found":
				toSend = codeUpdateString("notfound", 0)
			else:
				toSend = codeFromLog(line)
			receive_and_send_from_socket(toSend)
		writeToFile(packet[-1], list_to_csv(packet[:-1]))
	#print(receive_and_send_from_socket("Hey, thanks"))
