#include <iostream>
#include <string.h>
#include <string>
#include <time.h>
#include <sstream>

using namespace std;

string trim(const string& str)
{
    size_t first = str.find_first_not_of(' ');
    if (string::npos == first)
    {
        return str;
    }
    size_t last = str.find_last_not_of(' ');
    return str.substr(first, (last - first + 1));
}

void to_lower_case(string& str)
{
	for(int i=0; str[i]!='\0'; i++)
		str[i]=(char)tolower(str[i]);
}

void clear_char_array(char* str, int size)
{
	for(int i=0;i<size;i++)
		str[i]=0;
}

void split_string(string* spliter,string st, char token) //error al enviar caracter '' y el token al principio
{
	int start=0, end=0, split=0;
	char str[32];

	for(int i=0; st[i]!='\0'; i++)
	{
		if(st[i]==token || st[i+1]=='\0')
		{
			end=i;
			if(st[i+1]=='\0')
				end++;

			clear_char_array(str, 32);
			
			for(int j=start;j<end;j++)
			{
				//cout<<j-start<<",";
				str[j-start]=st[j];
			}
			spliter[split]=str;
			split++;
			start=i+1;

		}
	}
}

bool is_float( string myString ) {
    std::istringstream iss(myString);
    float f;
    iss >> noskipws >> f; // noskipws considers leading whitespace invalid
    // Check the entire string was consumed and if either failbit or badbit is set
    return iss.eof() && !iss.fail(); 
}

string int_to_string(int a)
{
	stringstream ss;
	ss << a;

	return ss.str();
}

void time_and_date(int* timer)
{
	time_t t=time(0);

	struct tm tm= *localtime(&t);
	timer[0]=tm.tm_hour;
	timer[1]=tm.tm_min;
	timer[2]=tm.tm_sec;
	timer[3]=tm.tm_mday;
	timer[4]=tm.tm_mon+1;
	timer[5]=tm.tm_year+1900;
}

int static checksum(string text, int endindex=0)
{
	endindex=endindex!=0?endindex:text.length();

	int checksum=0;

	for(int i=0; i<endindex; i++)
		checksum+=(int)text[i];

	return checksum;
}

string complete_bytes_of(string character, string text, int bytes)
{
	int spaces=bytes-text.length();

	if(spaces>=0)
		for(int i=0; i<spaces; i++)
			text=character+text;
	else
	{
		cout<<"El <string> recibido excede tamano de bytes"<<endl;

		return "ERROR";
	}

	return text;
}
