import datetime

def is_num_type(number):
	if type(number) is type(0) or type(number) is type(0.1):
		return True
	return False

def stringChecksum(string):
	if type(string) is type(""):
		sum = 0

		for char in string:
			sum += ord(char)

		return sum
	return 0

def padding(token, string, bytes_):
	padding = ""
	if type(token) is type("") and type(bytes_) is type(0) and type(string) is type(""):
		places = bytes_ - len(string)
		if(places >= 0):
			for i in range(places):
				padding += token
			return padding + string
		elif places < 0:
			return False
	elif type(token) is type("") and type(bytes_) is type(0) and is_num_type(string):
		string = str(string)
		zeros = bytes_ - len(string)
		token = "0"
		if(zeros >= 0):
			for i in range(zeros):
				padding += token
			return padding + string
		elif places < 0:
			return False
	else:
		return False

def codeUpdateString(sensor, data):
	if type(sensor) is type("") and is_num_type(data):
		code = "u"
		date = datetime.datetime.now()
		try:
			code += padding(" ", sensor.lower(), 8)
			code += padding("0", data, 8)
			code += date.strftime("%H%M%S%d%m%Y")
			code += str(stringChecksum(code))
		except TypeError:
			return False
		return code
	return False

def codeObserverString(observer, sensor):
	if type(observer) is type("") and type(sensor) is type(""):
		code = "r"
		try:
			code += padding(" ", observer.lower(), 8)
			code += padding(" ", sensor.lower(), 8)
			code += str(stringChecksum(code))
		except TypeError:
			return False
		return code
	return False

def codeFromLog(log, b=[8,8,6,8]):
	if type(log) is type(""):
		code = "u"
		parts = log.split(",")
		parts[-1] = parts[-1].replace("-", "")
		parts[-2] = parts[-2].replace(":", "")

		for i in range(len(parts)):
			print(parts[i])
			parts[i] = padding(" ", parts[i].lower().strip(), b[i])
			print(parts[i])

		for part in parts:
			code += part

		return code