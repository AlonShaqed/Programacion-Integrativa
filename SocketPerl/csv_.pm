package csv_;

$DATA_LOG = 'data.csv';
$TRANS_LOG = 'transaction_log.csv';

sub list_to_csv{
	my @list = @_;
	$string = "";
	
	foreach (@list){
		if($_ == $list[-1]){
			$string .= $_;
		}else{
			$string .= $_ . ",";
		}
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
			print "$row\n";
			$found = $row; 
		}
	}

	if($found ne '')
	{
		return $found;
	}
	return 'Not found';
}

1;