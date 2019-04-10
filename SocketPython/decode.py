from code import stringChecksum, is_num_type

def decodeUpdateString(string):
	if type(string) is type(""):	
		local_checksum = stringChecksum(string[:-4])
		try:
			received_sum = int(string[-4:])
			if received_sum == local_checksum:
				string = string.lower()

				if len(string) == 35:
					if string[0] == "u":
						data = []
						data.append(string[1:9])
						data.append(string[9:17])
						data.append(string[17:32])
						data.append(string[23:31])

						if "-" in data[1]:
							data[1] = data[1].replace("-", "", 1)
							data[1] = "-" + data[1]
						for split in data[1:]:
							float(split)
						data[2] = data[2][0:2] + ":" + data[2][2:4] + ":" + data[2][4:6]
						data[3] = data[3][0:2] + "-" + data[3][2:4] + "-" + data[3][4:]

						return data
				else:
					print("El mensaje no tiene la longitud correcta ("+str(len(string))+" != 35)")
					return False
			else:
				print("El checksum de la cadena no coincide")
				return False
		except ValueError:
			print("Error en valores numericos")
	else:
		return False

def decodeObserverString(string):
	if type(string) is type(""):	
		local_checksum = stringChecksum(string[:-4])
		try:
			received_sum = int(string[-4:])
			if received_sum == local_checksum:
				string = string.lower()

				if len(string) == 21:
					if string[0] == "r":
						data = []
						data.append(string[1:9])
						data.append(string[9:17])

						return data
				else:
					print("El mensaje no tiene la longitud correcta ("+str(len(string))+" != 35)")
					return False
			else:
				print("El checksum de la cadena no coincide")
				return False
		except ValueError:
			print("Error en valores numericos")
	else:
		return False