package csv_;

$DATA_LOG = 'data.csv';
$TRANS_LOG = 'transaction_log.csv';

sub list_to_csv{
	my @list = @_;
	$string = "";
	
	foreach (@list){
		$string .= $_ . ",";
	}
	return $string;
}

sub writeToFile{
	my $filepath = $_[0]; my $toWrite = $_[1];

	open(my $fh, '>>:encoding(UTF-8)', $filepath)
		or die "Could not open file '$filename' $!";

	say $fh $toWrite;
	close $fh;
}

sub findInFile{
	my $filepath = $_[0]; my $find = $_[1];

	$found = '';
	open(my $fh, '<:encoding(UTF-8)', $filepath)
		or die "Could not open file '$filename' $!";
	while (my $row = <$fh>)
	{
		chomp $row;
		if(index($row,$find) >= 0)
		{
			$found = $row; 
		}
	}

	if($found ne '')
	{
		print "found: $found\n";
		return $found;
	}
	return 'Not found';
}

1;
