load "code.rb"
load "csv.rb"

$DATA_LOG = "data.csv"
$TRANS_LOG = "transaction_log.csv"

class String
  def numeric?
    Float(self) != nil rescue false
  end
end

def decodeUpdateString(string)
	if string.is_a? String
		local_checksum = stringChecksum(string[0,string.length-4])
		received_sum = string[-4,4].to_i
		if local_checksum == received_sum
			string.downcase!
			if string.length == 35
				if string[0] == "u"
					data = Array.new
					data.append(string[1,8].strip)
					data.append(string[9,8])
					data.append(string[17,6])
					data.append(string[23,8])
					data.append($DATA_LOG)

					if data[1].include? "-"
						data[1].sub!("-","")
						data[1] = "-" + data[1]
					end
					for split in data[1,3]
						if split.numeric? == false
							return false
						end
					end
					data[2] = data[2][0,2] + ":" + data[2][2,2] + ":" +data[2][4,2]
					data[3] = data[3][0,2] + "-" + data[3][2,2] + "-" +data[3][4,4]

					return data
				end
			else
				puts "El mensaje no tiene la longitud correcta #{string.length} != 35"
			end
		else
			puts "El checksum no coincide"
		end
	end
	return false
end

def decodeObserverString(string)
	if string.is_a? String
		local_checksum = stringChecksum(string[0,string.length-4])
		received_sum = string[-4,4].to_i
		if local_checksum == received_sum
			string.downcase!
			if string.length == 21
				if string[0] == "r"
					data = Array.new
					data.append(string[1,8].strip)
					data.append(string[9,8].strip)
					data.append($TRANS_LOG)

					return data
				end
			else
				puts "El mensaje no tiene la longitud correcta #{string.length} != 21"
			end
		else
			puts "El checksum no coincide"
		end
	end
	return false
end

def decode_packet(string)
	if string[0] == "r"
		return decodeObserverString(string)
	elsif string[0] == "u"
		return decodeUpdateString(string)
	else
		puts "Codificacion desconocida\n"
		return false
	end
end
