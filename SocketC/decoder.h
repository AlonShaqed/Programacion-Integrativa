#include <iostream>
#include <string.h>
#include <sstream>
#include  "files.h"

class Decoder
{
public:
	string update[5], review[3], devoided[1];

	Decoder()
	{
		for(int i=0; i<5; i++)
			update[i]="";
		for(int i=0;i<3; i++)
			review[i]="";
		devoided[0]="Error in reading";
	}

	string* decode(string message)
	{
		string data="";
		int message_checksum=0;
		int dash=-1;

		message=message.substr(0, message.find((char)0));

		try{
			message_checksum=stoi(message.substr(message.length()-4));

			if(checksum(message, message.length()-4)==message_checksum)
			{
				message=trim(message);
				to_lower_case(message);

				switch(message[0])
				{
					case 'u':
					if(message.length()==35)
					{
						update[0]="data.csv";
						data=trim(message.substr(1,8));
						if(data=="        ")
							throw out_of_range("Nombres vacios");
						update[1]=data;
						data=message.substr(9,8);
						dash=data.find('-');
						if(dash<message.length())
						{
							data.erase(dash, 1);
							data="-"+data;
						}
						if(is_float(data)) update[2]=data;
						else throw out_of_range("data is not (float)");
						if(is_float(message.substr(17, 6)))
							update[3]=message.substr(17,2)+":"+message.substr(19,2)+":"+message.substr(21,2);
						else throw out_of_range("data is not (float)");
						if(is_float(message.substr(23, 8)))
							update[4]=message.substr(23,2)+"-"+message.substr(25,2)+"-"+message.substr(27,4);
						else throw out_of_range("data is not (float)");

						return update;
					}
					case 'r':
					if(message.length()==21)
					{
						review[0]="transaction.csv";
						data=message.substr(1,8);
						if(data=="        ")
							throw out_of_range("Nombres vacios");
						review[1]=trim(data);
						data=message.substr(9,8);
						if(data=="        ")
							throw out_of_range("Nombres vacios");
						review[2]=trim(data);

						return review;
					}
					default:
						cout<<"Codificacion desconocida. Mensaje descartado"<<endl;
				}
			}
			else throw invalid_argument("Checksum");
		}
		catch(invalid_argument& e)
			{cout<<"Error en checksum"<<endl;}
		catch(out_of_range& e)
			{
				cout<<"Error en <string> message: "<<endl;
				cerr << e.what() << endl;
			}

		return devoided;
	}

	void print_update()
	{
		cout<<"[";
		for(int i=0;i<5;i++)
			cout<<update[i]<<endl;
		cout<<"]"<<endl;
	}

	void print_review()
	{
		cout<<"[";
		for(int i=0;i<3;i++)
			cout<<review[i]<<endl;
		cout<<"]"<<endl;
	}

	void print_devoided()
	{
		cout<<"[";
		for(int i=0;i<1;i++)
			cout<<devoided[i]<<endl;
		cout<<"]"<<endl;
	}

	string update_index(int index)
	{
		return update[index];
	}

	string review_index(int index)
	{
		return review[index];
	}

	string write_update()
	{
		string ret;

		for(int i=1;i<5;i++)
			ret+=i==4?update[i]:update[i]+",";
		ret+="\n";

		return ret;
	}

	string write_review()
	{
		string ret;

		for(int i=1;i<3;i++)
			ret+=i==2?review[i]:review[i]+",";
		ret+="\n";

		return ret;
	}
};

/*int main()
{
	Decoder dec;
	string str="r wsenserrsensor31752";
	string* got=0;
	//str+=int_to_string(checksum(str));

	got=dec.decode(str);

	for(int i=0;i<3;i++)
		cout<<got[i]<<endl;
}*/