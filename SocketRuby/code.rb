
require 'date'

def stringChecksum(string)
	if string.is_a? String
		sum = 0

		string.split("").each do |char|
			sum += char.ord
		end
		return sum
	end
	return 0
end

def padding(token, string, bytes)
	padding = ""
	if token.is_a? String and string.is_a? String and bytes.is_a? Integer
		places = bytes - string.length
		if places > 0
			for i in 0..places-1
				padding += token
			end
			return padding + string
		elsif places == 0
			return string
		elsif places < 0
			return false
		end
	elsif string.is_a? Numeric and bytes.is_a? Numeric
		string = string.to_s
		zeros = bytes - string.length
		token = "0"
		if zeros > 0
			for i in 0..zeros-1
				padding += token
			end
			return padding + string
		elsif zeros == 0
			return string
		elsif places < 0
			return false
		end
	else
		return false
	end
end

def codeUpdateString(sensor, data)
	if sensor.is_a? String and data.is_a? Numeric
		code = "u"
		date = DateTime.now

		code += padding(" ", sensor.downcase, 8)
		code += padding("0", data, 8)
		code += date.strftime("%H%M%S%d%m%Y")
		code += padding("0",stringChecksum(code), 4)

		return code
	end
	return false
end

def codeObserverString(observer, sensor)
	if observer.is_a? String and sensor.is_a? String
		code = "r"
		code += padding(" ", observer.downcase, 8)
		code += padding(" ", sensor.downcase, 8)
		code += stringChecksum(code).to_s
		return code
	end
	return False
end

def codeFromLog(log, b=[8,8,6,8])
	if log.is_a? String
		code = "u"
		parts = log.split(",")
		parts[-1].tr!("-", "")
		parts[-2].tr!(":", "")

		for i in 0..parts.length-1
			parts[i] = padding(" ", parts[i].downcase.strip, b[i])
		end
		for parts in parts
			code += parts
		end
		code += padding("0", stringChecksum(code), 4).to_s

		return code
	end
	return false
end
