package code;

use POSIX qw(strftime);
use Try::Tiny;

sub is_numeric{
	my $value = $_[0];

	if($value + 0 eq $value)
	{
	    return $value; #if is number, returns it itself
	} else {
	    return 0;
	}
}

sub stringChecksum{ #one arg: string
	$sum = 0;

	foreach $char (split(//, $_[0])){
  		$sum += ord($char);
	}

	return $sum;
}

sub padding{
	my $token = $_[0]; my $string = $_[1]; my $bytes = $_[2]; 
	$padding = "";

	if(is_numeric($string))
	{
		$token = "0";
	}

	$places = $bytes - length($string);
	if($places > 0)
	{
		my $i = 0;
		for($i=0;$i<$places;$i++)
		{
			$padding .= $token;
		}

		return $padding . $string;
	}
	elsif($places == 0)
	{
		return $string;
	}
	elsif($places < 0)
	{
		return "";
	}
}

sub codeUpdateString{
	my $sensor = $_[0]; my $data = $_[1];

	$code = "u";
	$date = strftime "%H%M%S%d%m%Y", localtime;

	$code .= padding(" ", lc($sensor), 8);
	$code .= padding("0", $data, 8);
	$code .= $date;
	$code .= padding("0", stringChecksum($code),4);

	return $code;
}

sub codeObserverString{
	my $observer = $_[0]; my $sensor = $_[1];

	$code = "r";

	$code .= padding(" ", lc($observer), 8);
	$code .= padding(" ", lc($sensor), 8);
	$code .= padding("0", stringChecksum($code), 4);

	return $code;
}

sub codeFromLog{
	my $log = $_[0];
	@b = (8,8,6,8);
	$code = "u";
	
	@parts = split(/\,/, $log);
	$parts[-1] =~ tr/-/\x0/; $parts[-1] =~ s/[[:^print:]\s]//g;
	$parts[-2] =~ tr/:/\x0/; $parts[-2] =~ s/[[:^print:]\s]//g;

	for(my $i=0;$i<@parts;$i++)
	{
		$parts[$i] = padding(" ",lc($parts[$i]),$b[$i]);
		$code .= $parts[$i];
	}

	$code .= padding("0", stringChecksum($code),4);
}

1;
