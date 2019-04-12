DATA_LOG = "data.csv"
TRANS_LOG = "tansaction_log.csv"

def writeToFile(filepath, toWrite):
	if type(filepath) is type("") and type(toWrite) is type(""):
		try:
			file = open(filepath, "a+", encoding="utf-8")
			file.write(toWrite + "\n")
		except Exception:
			return False
		return True
	return False

def findInFile(filepath, find):
	if type(filepath) is type("") and type(find) is type(""):
		file = open(filepath, "r", encoding="utf-8")
		lines = file.readlines()

		found = ""

		for line in lines:
			if find in line:
				found = line
		if found != "":
			return found
		return "Not found"
	return False

def list_to_csv(list_):
	if type(list_) is type([]):
		string = ""
		for element in list_:
			if element is list_[-1]:
				string += str(element)
			else:
				string += str(element) + ","
		return string
