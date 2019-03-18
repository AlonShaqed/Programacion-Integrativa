#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include "coder.h"


void error(const char *msg)
{
    perror(msg);
    exit(0);
}

string client_socket(char* host, char* port, char* message)
{
    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[256];
    string s_buffer;

    clear_char_array(buffer, 256);

    portno = atoi(port);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");
    server = gethostbyname(host);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");
    
    bzero(buffer,256);
    strncpy(buffer, message, 255);

    n = write(sockfd,buffer,strlen(buffer));
    if (n < 0) 
         error("ERROR writing to socket");
    bzero(buffer,256);
    n = read(sockfd,buffer,255);
    if (n < 0) 
         error("ERROR reading from socket");
    //printf("%s\n",buffer);
    close(sockfd);

    s_buffer=buffer;
    s_buffer=s_buffer.substr(0, s_buffer.find((char)0));

    return s_buffer;
}

int main(int argc, char *argv[])
{
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    string message;
    string* get;
    Coder coder;
    Decoder dec; 

    do{
        message=coder.second_menu(message);

        char ch_message[message.size()];
        message.copy(ch_message, message.size());
        ch_message[message.size()] = '\0';

        cout<<"Sending..."<<endl;
        client_socket(argv[1],argv[2],ch_message);
        if(ch_message[0]=='r')
        {
            clear_char_array(ch_message, message.size());
            message="Received";
            message.copy(ch_message, message.size());

            cout<<"Waiting..."<<endl;
            message=client_socket(argv[1],argv[2],ch_message);
            get=dec.decode(message);

            if(get==dec.update)
             {
                cout<<"Server:";
                dec.print_update();
             }
        }
    }while(message!="Exit");
}
