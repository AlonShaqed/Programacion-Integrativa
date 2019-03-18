#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>

#include <string.h>
#include "coder.h"

#define BACKLOG 10

using namespace std;

void error(const char *msg)
{
    perror(msg);
    exit(1);
}

void sigchld_handler(int s)
{
    while (waitpid(-1, NULL, WNOHANG) > 0)
        ;
}

void *get_in_addr(struct sockaddr *sa)
{
    if (sa->sa_family == AF_INET)
        return &(((struct sockaddr_in*)sa)->sin_addr);
    return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

string server_socket(int sockfd, struct sockaddr_storage their_addr, socklen_t sin_size, char* port, char* message)
{
    int  new_fd;
    char s[INET6_ADDRSTRLEN];
    char buffer[256];
    int n;
    string s_buffer;

    if ((new_fd = accept(sockfd, (struct sockaddr *)&their_addr, &sin_size)) > 0)
    {
        clear_char_array(buffer,256);
        inet_ntop(their_addr.ss_family,
                get_in_addr((struct sockaddr *)&their_addr),
                s, sizeof s);
        //printf("server: got connection from %s\n", s);
        n = read(new_fd,buffer,255);
        if (n < 0) error("ERROR reading from socket");
        //printf("%s\n",buffer);
        n = write(new_fd,message,255); //response---------------
        if (n < 0) error("ERROR writing to socket");

        s_buffer=buffer;
        s_buffer=s_buffer.substr(0, s_buffer.find((char)0));

        if (fork() == 0)
        {
            close(sockfd);
            char input[] = "This is the response!";
            //if (send(new_fd, input, strlen(input), 0) == -1)
            //    perror("send");
            close(new_fd);
            exit(0);
        }

        close(new_fd);
    }
    return s_buffer;   
}

int main(int argc, char *argv[])
{
    if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }

    string message;
    Decoder dec;
    Coder cod;
    string* get;

    char ch_message[10]="got it";//[message.size()];
    char file[9]="data.csv";


    char* portno;
    int sockfd;
    struct addrinfo hints, *servinfo, *p;
    struct sockaddr_storage their_addr;
    socklen_t sin_size;
    struct sigaction sa;
    int yes=1;
    int rv;

    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;

    portno = argv[1];

    if ((rv = getaddrinfo(NULL, portno, &hints, &servinfo)) != 0)
    {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }

    for (p = servinfo; p != NULL; p = p->ai_next)
    {
        if ((sockfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1)
        {
            perror("server: socket");
            continue;
        }

        if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
        {
            perror("setsockopt");
            exit(1);
        }

        if (bind(sockfd, p->ai_addr, p->ai_addrlen) == -1)
        {
            close(sockfd);
            perror("server: bind");
            continue;
        }

        break;
    }

    if (p == NULL)
    {
        fprintf(stderr, "server: failed to bind\n");
        return 2;
    }

    freeaddrinfo(servinfo);

    if (listen(sockfd, BACKLOG) == -1)
    {
        perror("listen");
        exit(1);
    }

    sa.sa_handler = sigchld_handler;
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = SA_RESTART;
    if (sigaction(SIGCHLD, &sa, NULL) == -1)
    {
        perror("sigaction");
        exit(1);
    }

    printf("server: waiting for connections...\n");

    sin_size = sizeof their_addr;
    //----------My code
    while(true)
        {
            message=server_socket(sockfd, their_addr, sin_size, argv[1], ch_message);

            get=dec.decode(message);

            if(get==dec.update)
             {
                dec.print_update();
                write_to_file(dec.update_index(0), dec.write_update());
             }
             if(get==dec.review)
             {
                string trimmed=cod.code_smf_from_log(read_last_string_from_file(file, trim(dec.review_index(2))));
                char c_send[trimmed.size()];
                trimmed.copy(c_send, trimmed.size());
                c_send[trimmed.size()]='\0';


                dec.print_review();
                write_to_file(dec.review_index(0), dec.write_review());
                trimmed=trim(dec.review_index(2));
                sleep(10);
                server_socket(sockfd, their_addr, sin_size, argv[1], c_send);
             }
             if(get==dec.devoided)
             {
                dec.print_devoided();
             }
        }
}