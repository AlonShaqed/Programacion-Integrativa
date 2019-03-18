#include<stdio.h>
#include "utils.h"
#include <fstream>

void readFile(char* rfile)
{
	int c;
	FILE *file;
	file = fopen(rfile, "r");
	if (file) {
	    while ((c = getc(file)) != EOF)
	        putchar(c);
	    fclose(file);
	}
}

string read_last_string_from_file(char* rfile, string substr)
{
	int c, j=0, slashn=0;
	FILE *file;
	file = fopen(rfile, "r");
	char ch_line[255];
	string line, last="No encontrado";
	bool read=true;
	int found=-1;

	if (file) {
	    while ((c = getc(file)) != EOF)
	    {
	    	if(c!='\n' && read)
	    	{
	    		ch_line[j]=c;
	    		j++;
	    	}
	    	if(c=='\n')
	    	{
	    		read=false;
	    		line=ch_line;
	    		found=line.find(substr);
	    		slashn++;
	    		if(found!=-1)
	    		{
	    			last=line;
	    			//cout<<last<<endl;
	    		}
	    		j=0; clear_char_array(ch_line, 255); read=true; found=-1;
	    	}
	    }
	    fclose(file);
	}

	return last;
}

void write_to_file(string file, string write)
{
  std::ofstream outfile;

  outfile.open(file, std::ios_base::app);
  outfile << write;
}