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
		lines = file.readLines()

		found = ""

		for line in lines:
			if find in lines:
				found = line
		if found != "":
			return found
		return "Not found"
	return False