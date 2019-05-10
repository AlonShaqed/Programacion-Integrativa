use lib '/home/alonso/Dropbox/Tarea/PrograIntegrativa/Perl/'; #change to project's current directory
require code;
require csv_;

package decode;

sub decodeUpdateString{
	my $string = $_[0];

	$local_checksum = code::stringChecksum(substr($string, 0, -4));
	$received_checksum = substr($string, -4, 4);
	if($local_checksum eq $received_checksum)
	{
		$string = lc($string);
		
		if(length($string) eq 35)
		{
			if(substr($string, 0,1) eq "u")
			{
				@data = ();
				push(@data, substr($string, 1,8));
				push(@data, substr($string, 9,8));
				push(@data, substr($string, 17,6));
				push(@data, substr($string, 23,8));
				push(@data, $csv_::DATA_LOG);

				if(index($data[1], '-') > 0)
				{
					$data[1] =~ tr/-/\x0/; $data[1] =~ s/[[:^print:]\s]//g;
					$data[1] = "-" . $data[1];
				}

				$data[2] = substr($data[2], 0, 2) . ':' . substr($data[2], 2, 2) . ':' . substr($data[2], 4, 2);
				$data[3] = substr($data[3], 0, 2) . '-' . substr($data[3], 2, 2) . '-' . substr($data[3], 4, 4);
				
				return @data;
			}
		}
		else{
			$l = length($string);
			print "El mensaje no tiene la longitud correcta $l != 35\n";
		}
	}
	else{
		print "El checksum no coincide\n";
	}
	return 0;
}

sub decodeObserverString{
	my $string = $_[0];

	$local_checksum = code::stringChecksum(substr($string, 0, -4));
	$received_checksum = substr($string, -4, 4);
	if($local_checksum eq $received_checksum)
	{
		$string = lc($string);
		
		if(length($string) eq 21)
		{
			if(substr($string, 0,1) eq "r")
			{
				@data = ();
				push(@data, substr($string, 1,8));
				push(@data, substr($string, 9,8));
				push(@data, $csv_::TRANS_LOG);

				return @data;
			}
		}
		else{
			$l = length($string);
			print "El mensaje no tiene la longitud correcta $l != 21\n";
		}
	}
	else{
		print "El checksum no coincide\n";
	}
	return 0;
}

sub decode_packet{
	my $string = $_[0];
	$char = substr($string, 0,1);

	if(lc($char) eq 'r')
	{
		return decodeObserverString($string);
	}
	if(lc($char) eq 'u')
	{
		return decodeUpdateString($string);
	}
	else{
		print "Codificacion desconocida\n";
		return 0;
	}
}

1;