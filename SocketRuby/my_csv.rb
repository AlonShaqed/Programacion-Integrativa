def list_to_csv(list_)
	if list_.is_a? Array
		string = ""
		for element in list_
			if element.equal? list_[-1]
				string += element.to_s
			else
				string += element.to_s + ","
			end
		end
		return string
	end
end

def writeToFile(filepath, toWrite)
	if filepath.is_a? String and toWrite.is_a? String
		begin
			File.open(filepath, "a") do |line|
				line.puts "\r" + toWrite
			rescue Exception
				return false
			end
		end
			return true
	end
end

def findInFile(filepath, find)
	if filepath.is_a? String and find.is_a? String
		found = ""
		File.open(filepath).each do |line|
			if line.include? find
				found = line
			end
		end
		if found != ""
			return found
		else
			return "Not found"
		end
	end
	return false
end

#puts list_to_csv([4,5,"hgga","gh","hgga"])
