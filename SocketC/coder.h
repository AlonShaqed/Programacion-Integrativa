#include <iostream>
#include <string.h>
#include <sstream>
#include "decoder.h"

class Coder
{
public:
	Coder(){}

	int main_menu()
	{
		int choose=0;

		cout<<"Cliente de Consulta:"<<endl;
		cout<<"\t1.Sensor\n\t2.Observador\n\t3. Salir"<<endl;

		try{
			cin>>choose;
		}
		catch (exception& e){cout<<"Error en captura"<<endl;}

		return choose;
	}

	string get_sensor_message()
	{
		string message="u", sensor, data;
		int date[6];
		float f=0;
		time_and_date(date);

		cout<<"Actualizacion de Sensores:"<<endl;
		cout<<"Escriba los siguientes datos como se piden:"<<endl;

		do{
			sensor="sensor";
			cout<<"Nombre del sensor:"<<endl;
			cin>>sensor;
			sensor=complete_bytes_of(" ",sensor, 8);
		}while(sensor=="ERROR");
		message+=sensor;

		do{
			data="";
			cout<<"Dato (flotante < 8 bytes):"<<endl;
			cin>>data;
			if(is_float(data))
				data=complete_bytes_of("0",data,8);
			else
			{
				data="ERROR";
				cout<<"El dato no es un numero"<<endl;
			}
		}while(data=="ERROR");
		message+=data;

		for(int i=0; i<5; i++)
			message+=complete_bytes_of("0",int_to_string(date[i]),2);
		message+=complete_bytes_of("0",int_to_string(date[5]),4);

		message+=complete_bytes_of("0", int_to_string(checksum(message)), 4);

		return message;
	}

	string code_smf_from_log(string log)
	{
		string pack[4], message="u";
		int token;

		if(log.find("No encontrado") != string::npos || log.find("ERROR") != string::npos)
			return "ERROR";

		split_string(pack, log, ',');

		message+=complete_bytes_of(" ", pack[0], 8);
		message+=complete_bytes_of("0", pack[1], 8);
		for(int i=0;i<2;i++)
		{
			if(pack[2].find(":")<pack[2].length())
			{
				token=pack[2].find(":");
				pack[2].erase(token,1);
			}
		}
		message+=pack[2];
		for(int i=0;i<2;i++)
		{
			if(pack[3].find("-")<pack[3].length())
			{
				token=pack[3].find("-");
				pack[3].erase(token,1);
			}
		}
		message+=pack[3];

		message+=complete_bytes_of("0", int_to_string(checksum(message)), 4);
		cout<<message<<endl;

		return message;
	}

	string  get_observer_message()
	{
		string message="r", observer, sensor;

		do{
			observer="";
			cout<<"Nombre del observador (< 8 caracteres)"<<endl;
			cin>>observer;
			observer=complete_bytes_of(" ", observer, 8);
		}while(observer=="ERROR");
		message+=observer;

		do{
			sensor="";
			cout<<"Nombre del sensor (< 8 caracteres)"<<endl;
			cin>>sensor;
			sensor=complete_bytes_of(" ", sensor, 8);
		}while(sensor=="ERROR");
		message+=sensor;

		message+=complete_bytes_of("0", int_to_string(checksum(message)), 4);

		return message;
	}

	string second_menu(string message)
	{
		switch(main_menu())
		{
			case 1: message=get_sensor_message(); return message;
			case 2: message=get_observer_message(); return message;
			case 3: return "Exit";
			default: cout<<"Esa opcion no esta disponible"<<endl;
		}

		return "Exit";
	}
};

/*int main()
{
	Codificador cod;
	string split[16];
	string str;

	cout<<cod.second_menu(str)<<endl;

	return 0;
}*/