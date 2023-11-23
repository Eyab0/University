#include "local.h"

#define NUM_OF_PLAYERS 4
#define NUM_OF_CHILDREN 5

#ifdef __APPLE_CC__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif


int NUM_OF_ROUNDS = 5,curr_round = 0;
float proc[4];
int curr_player = 0;
int min = 0, max = 1;
int start =0;
char sss[30]; 
FILE *fp;
int fd[2][2];
int children_pid[NUM_OF_CHILDREN];
int player_wait_signal[NUM_OF_CHILDREN] = {0};
float sum1, sum2;
int team1_points = 0, team2_points = 0;
char *range_file = "range.txt";
int child_number = 0;
int parent_pid;
void terminate_children();
void terminate_FIFO();
void initialize_pipes();
void manage_rounds();
void count_points();
void declare_winner();
void child_handler();
void read_pipe_str(int fd_read, int fd_write, char *read_value, long length);
void write_pipe_str(int fd_read, int fd_write, char *write_value, long length);
void kill_all();
void remove_files();
void validate_args(int argc, char *argv[]);
void startOpenGl();

void display();
void RoundNumber(int w);
void LineInTheMiddle();
void LineHorizontal();
void TeamOne();
void TeamTwo();
void viewportOne();
void viewportTwo();
void viewportMain();
void viewportMain2();
void footer();
void AllProcesses(float proc[]);
void renderBitMap(double x, double y, void *font, char *string);
void test();
void init();
void ending();
void timer(int t);


int main(int argc, char **argv)
{

	validate_args(argc,argv);
	pid_t child_pid;
	parent_pid = getpid();

	signal(SIGUSR2, child_handler);
	signal(SIGUSR1, kill_all);

	initialize_pipes();

	for (int i = 0; i < NUM_OF_CHILDREN; i++)
	{
		child_pid = fork();
		usleep(500);

		if (child_pid == -1)
		{
			printf("Error with forking\n");
			exit(-2);
		}

		if (getpid() == parent_pid)
		{
			children_pid[i] = child_pid;
		}

		char *rounds_str = malloc(sizeof(int));
		char *player_num = malloc(sizeof(int));

		sprintf(rounds_str, "%d", NUM_OF_ROUNDS);
		sprintf(player_num, "%d", i);

		if (child_pid == 0 && i < NUM_OF_PLAYERS)
		{
			if (execl("./child", range_file, rounds_str, player_num, NULL) == -1)
			{
				printf("Error with Executing child\n");
				exit(-3);
			}
			exit(1);
		}
		else if (child_pid == 0 && i == (NUM_OF_CHILDREN - 1))
		{

			char fd_write_str[10], fd_read_str[10];
			char fd_write_str2[10], fd_read_str2[10];

			sprintf(fd_write_str, "%d", fd[0][1]);
			sprintf(fd_read_str, "%d", fd[0][0]);
			sprintf(fd_write_str2, "%d", fd[1][1]);
			sprintf(fd_read_str2, "%d", fd[1][0]);
		
			if (execl("./co-processor", fd_write_str, fd_read_str, fd_write_str2, fd_read_str2, rounds_str, NULL) == -1)
			{
				printf("Error with Executing co-processor\n");
				exit(-4);
			}
			exit(EXIT_SUCCESS);
		}

		free(rounds_str);
		free(player_num);

	}
	glutInit(&argc, argv);
    startOpenGl();

	// manage_rounds();
	// declare_winner();
	// printf("\n");



	/* method 1
	for (int i = 0; i < NUM_OF_PLAYERS; i++) {
        int status;
        waitpid(pid_number[i], &status, 0);
        if (WIFEXITED(status)) {
            printf("Child process %d exited with status %d\n", i+1, WEXITSTATUS(status));
        }
    }*/
    // glutInit(&argc, argv);
    // startOpenGl();



	// terminate_children();
	// remove_files();
	// kill(parent_pid, SIGTERM);

	return 0;
}

/*void terminate_childs(int pid_number[], int num_of_childs)
{
	for (int i = 0; i < num_of_childs; i++)
	{
		kill(pid_number[i], SIGTERM);
	}
	return;
}*/

void terminate_children()
{
	for (int i = 0; i < NUM_OF_CHILDREN; i++)
	{
		int status;
		kill(children_pid[i], SIGTERM);
		waitpid(children_pid[i], &status, 0);

        if (WIFEXITED(status)) {
            printf("Child (%d) process terminated with exit status %d\n", i, WEXITSTATUS(status));
        } else if (WIFSIGNALED(status)) {
            printf("Child (%d) process terminated by signal %d\n", i, WTERMSIG(status));
        }
	}
	return;
}
void initialize_pipes()
{
		if (pipe(fd[0]) == -1 || pipe(fd[1]) == -1)
		{
			perror("Pipe initialization error");
			exit(-1);
		}
}
void manage_rounds()
{
	// int curr_player = 0;
	// int min = 0, max = 1;
	// FILE *fp;
	// rounds
	while (1)
	{
		char children_generated_numbers[BUFSIZ];

		char teams_sum[BUFSIZ];
		//generates min and max
		srand(time(0) * (curr_round + 1) * 100);
		min = rand() % 1000;
		max = 1 + min + rand() % (1000);

		fp = fopen(range_file, "w");
		if (fp == NULL)
		{
			printf("Error opening file\n");
			exit(-5);
		}
		fprintf(fp, "%d,%d", min, max);
		fclose(fp);

		printf("Min (%d), Max (%d)\n\n\n", min, max);
		usleep(500);

		// players
		while (1)
		{
			//inform the child to start
			kill(children_pid[curr_player], SIGUSR1);
			//wait the child to generate a random number
			while (player_wait_signal[curr_player] == 0){pause();}

			float rand_float;
			//read the generated number

			char id_str[10];
			sprintf(id_str, "%d", children_pid[curr_player]);

			fp = fopen(id_str, "r");

			if (fp == NULL)
			{
				printf("Error opening file.\n");
				exit(-6);
			}
			fscanf(fp, "%f", &rand_float);
			fclose(fp);

			printf("From parent, child (%d) generated random float: |%f|\n", curr_player, rand_float);

			player_wait_signal[curr_player] = 0;
			curr_player++;
			char temp_str[BUFSIZ];
			if (curr_player == NUM_OF_PLAYERS)
			{
				sprintf(temp_str, "%f", rand_float);
				strcat(children_generated_numbers, temp_str);
				curr_player = 0;
				break;
			}
			else
			{
				sprintf(temp_str, "%f,", rand_float);
				strcat(children_generated_numbers, temp_str);
			}
			printf("\n");
			usleep(500);
			memset(temp_str, 0x0, BUFSIZ);
		}

		printf("\n");
		usleep(500);
		printf("From parent, generated numbers [%s]\n", children_generated_numbers);

		//writes the generated numbers to the pipe
		write_pipe_str(fd[0][0], fd[0][1], children_generated_numbers, strlen(children_generated_numbers));

		usleep(500);

		//inform the co-processor to start
		kill(children_pid[NUM_OF_CHILDREN - 1], SIGINT);

		//wait until the two summations are ready
		while (player_wait_signal[NUM_OF_CHILDREN - 1] == 0){pause();}
		usleep(500);

		//read the two summations from the co-processor using pipe
		read_pipe_str(fd[1][0], fd[1][1], teams_sum, BUFSIZ);

		sleep(1);
		printf("From parent, teams sum: [%s]\n", teams_sum);
		
		sscanf(teams_sum, "%f,%f", &sum1, &sum2);
		usleep(500);
		count_points();
		usleep(500);

		printf("\nTeam1 [%d] - Team2 [%d]\n", team1_points, team2_points);
		printf("\nRound(%d) finished\n\n", curr_round + 1);
		printf("**************************\n");

		
		curr_round++;
		if (curr_round == NUM_OF_ROUNDS)
		{
			break;
		}

		memset(children_generated_numbers, 0x0, BUFSIZ);
		memset(teams_sum, 0x0, BUFSIZ);
		sleep(1);
	}
}
void count_points()
{
	if (sum1 > sum2)
	{
		team1_points++;
	}
	else if (sum1 < sum2)
	{
		team2_points++;
	}
	else
	{
		team1_points++;
		team2_points++;
	}
}
void declare_winner()
{
	printf("\n------------------------------------------\n");
	if (team1_points > team2_points)
	{
		printf("Team1 is the winner!\n");
	}
	else if (team1_points < team2_points)
	{
		printf("Team2 is the winner!\n");
	}
	else
	{
		printf("Draw\n");
	}
	printf("\n------------------------------------------\n");
}
void child_handler()
{
	player_wait_signal[child_number] = 1;
	child_number++;
	child_number %= 5;
	return;
}
void kill_all(){
	terminate_children();
	kill(getpid(), SIGTERM);
}
void remove_files(){
	printf("\n");
	for (int i = 0; i < NUM_OF_PLAYERS; i++){
		char filename[50];
		sprintf(filename, "%d", children_pid[i]);
		if(remove(filename) == 0) {
        	printf("%s file deleted successfully.\n", filename);
		}
		else {
			printf("Unable to delete the file.\n");
    	}
	}
}

void validate_args(int argc, char *argv[]){

    if (argc < 2)
    {
    	NUM_OF_ROUNDS =5;

    }
    else if(argc == 2)
    {
        NUM_OF_ROUNDS = atoi(argv[1]);
    }else{
    	    perror("\nErrot");
            // reset_stderr();
            exit(-1);
    }
}


void startOpenGl(){

  init();
  glutInitDisplayMode(GLUT_RGB);
  glutInitWindowPosition(450, 450);
  glutInitWindowSize(950, 750);
  glutCreateWindow("My first Program");
  glutDisplayFunc(display);

  glutTimerFunc(0, timer, 0);

  init();

  glutMainLoop();
}

void init(){
	glClearColor(1.0,1.0,1.0,1.0);

}



void display() {

	// Set every pixel in the frame buffer to the current clear color.
	glClear(GL_COLOR_BUFFER_BIT);
	glLoadIdentity();
	
	// Draw 
	int w = 0;
      	viewportMain();
      	viewportOne();
      	viewportTwo();
      	viewportMain2();

      	LineInTheMiddle();
      	LineHorizontal();

		

		
      	RoundNumber(w);
		for(int i=0;i<4;i++){
			printf("%f - ",proc[i]);
		}
		printf("\n");
		AllProcesses(proc);
      	TeamOne();
      	TeamTwo();
     

      	
      	footer();
      //	glScalef(0.001,0.001,0.01);
				//glutStrokeString(GLUT_STROKE_ROMAN, (unsigned char*)"The game over!");
	// Flush drawing command buffer to make drawing happen as soon as possible.
  			// glFlush();

 //  sleep(3);
 //  glClearColor(1.0,1.0,1.0,1.0);
	// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// glColor3f (0.0, 0.0, 0.0);

	// char *string3 = (char*)"Team 1 is the Winner !!";
	// 	// int width = glutBitmapLength(GLUT_BITMAP_HELVETICA_12, (const unsigned char*)string3);
 //  // float x = (float) (glutGet(GLUT_WINDOW_WIDTH) - width) / 2.0;
 //  // float y = (float) glutGet(GLUT_WINDOW_HEIGHT) / 2.0;
 //  // printf("%f-%f",x,y);
	// renderBitMap(-0.30, 0.0, GLUT_BITMAP_TIMES_ROMAN_24, string3);
 //  glFlush();
  	// test();

  	glutPostRedisplay();
    glutSwapBuffers();


}

void test(){
	sleep(3);
  	glClearColor(1.0,1.0,1.0,1.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glColor3f (0.0, 0.0, 0.0);

	char *string3 = (char*)"Team 1 is the Winner !!";
		// int width = glutBitmapLength(GLUT_BITMAP_HELVETICA_12, (const unsigned char*)string3);
  // float x = (float) (glutGet(GLUT_WINDOW_WIDTH) - width) / 2.0;
  // float y = (float) glutGet(GLUT_WINDOW_HEIGHT) / 2.0;
  // printf("%f-%f",x,y);
	renderBitMap(-0.30, 0.0, GLUT_BITMAP_TIMES_ROMAN_24, string3);
  glFlush();
}

void renderBitMap(double x, double y, void *font, char *string) {
    char *c;
    glRasterPos2f(x, y);
    for (c = string; *c != '\0'; c++) {
        glutBitmapCharacter(font, *c);
    }
}
void RoundNumber(int w){

	glColor3f (0.0, 0.0, 0.0);
	// glRasterPos2f(-0.10, 0.8); //define position on the screen
	char string[12];
	snprintf(string,12,"Round %d",curr_round+1);
	// int len = strlen(string);
	// for (int i = 0; i < len; i++) {
 //    		glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, string[i]);
	// }
	// glutBitmapString(GLUT_BITMAP_TIMES_ROMAN_24, string);
	renderBitMap(-0.10, 0.8, GLUT_BITMAP_TIMES_ROMAN_24, string);

}

void LineInTheMiddle(){
	glColor3f (1.0, 0.0, 0.0);
	glPointSize(10.0);
	glBegin(GL_LINES);
	glVertex2f(0.0,-1.0);
	glVertex2f(0.0,0.7);
	glEnd();
}

void LineHorizontal(){
	glColor3f (1.0, 0.0, 0.0);
	glPointSize(1.0);
	glBegin(GL_LINES);
	glVertex2f(-1.0,0.7);
	glVertex2f(1.0,0.7);
	glEnd();

}

void footer(){
	glColor3f (1.0, 0.0, 0.0);
	glPointSize(1.0);
	glBegin(GL_LINES);
	glVertex2f(1.0,-0.7);
	glVertex2f(-1.0,-0.7);
	glEnd();
	glFlush();

}

void viewportOne(){
	// glColor3f (0.0, 0.5, 0.5);
	glPointSize(10.0);
	glBegin(GL_POLYGON);
	// glColor3f(0.97, 0.85, 0.85); glVertex2f(-1,0.7);
	// glColor3f(0.89, 0.75, 0.89); glVertex2f(-1,-1);
	// glColor3f(0.97, 0.85, 0.85); glVertex2f(0,-1.0);
	// glColor3f(0.89, 0.75, 0.89); glVertex2f(0,0.7);

	glColor3f(1.0f, 0.4f, 0.4f); glVertex2f(-1,0.7);
	glColor3f(1.0f, 0.4f, 0.4f); glVertex2f(-1,-1);
	glColor3f(1.0f, 0.4f, 0.4f); glVertex2f(0,-1.0);
	glColor3f(1.0f, 0.4f, 0.4f); glVertex2f(0,0.7);
	glEnd();

}

void viewportTwo(){
	// glColor3f (0.0, 0.5, 0.5);
	glPointSize(10.0);
	glBegin(GL_POLYGON);
	// glColor3f(0.6, 0.93, 0.62); glVertex2f(0,0.7);
	// glColor3f(0.91, 0.93, 0.6); glVertex2f(0,-1.0);
	// glColor3f(0.6, 0.93, 0.62); glVertex2f(1,-1);
	// glColor3f(0.91, 0.93, 0.6); glVertex2f(1,0.7);
	glColor3f(0.4f, 0.9f, 0.3f); glVertex2f(0,0.7);
	glColor3f(0.4f, 0.9f, 0.3f); glVertex2f(0,-1.0);
	glColor3f(0.4f, 0.9f, 0.3f); glVertex2f(1,-1);
	glColor3f(0.4f, 0.9f, 0.3f); glVertex2f(1,0.7);
	glEnd();


}

void viewportMain(){
	// glColor3f (0.0, 0.5, 0.5);
	glPointSize(10.0);
	glBegin(GL_POLYGON);
	glColor3f(0.83, 0.83, 0.78); glVertex2f(-1,1);
	glColor3f(0.79, 0.96, 1); glVertex2f(-1,0.7);
	glColor3f(0.83, 0.83, 0.78); glVertex2f(1,0.7);
	glColor3f(0.79, 0.96, 1); glVertex2f(1,1);
	glFlush();
    glEnd();

}

void viewportMain2(){
	// glColor3f (0.0, 0.5, 0.5);
	glPointSize(10.0);
	glBegin(GL_POLYGON);
	glColor3f(0.83, 0.83, 0.78); glVertex2f(1,-1);
	glColor3f(0.79, 0.96, 1); glVertex2f(1,-0.7);
	glColor3f(0.83, 0.83, 0.78); glVertex2f(-1,-0.7);
	glColor3f(0.79, 0.96, 1); glVertex2f(-1,-1);
	glEnd();


}

void TeamOne(){
	glColor3f (0.0, 0.0, 0.0);
	glRasterPos2f(-0.6, 0.5); // define position on the screen
	char string[6]="TEAM 1";
	int len = strlen(string);
	for (int i = 0; i < len; i++) {
    		glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, string[i]);
	}
}

void TeamTwo(){
	glColor3f (0.0, 0.0, 0.0);
	glRasterPos2f(0.4, 0.5); // define position on the screen
	char string[6]="TEAM 2";
	int len = strlen(string);
	for (int i = 0; i < len; i++) {
    		glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, string[i]);
	}
}

void AllProcesses(float proc[]){
	float x[]={-0.95,-0.95,0.05,0.05};
	float y[]={0.3,0.1,0.3,0.1};
	char string[30];
	for(int i = 1;i <=4;i++){
		glColor3f (0.0, 0.0, 0.0);
		// glRasterPos2f(x[i-1]+0.15, y[i-1]); // define position on the screen
		sprintf(string, "Child %d = %f",i,proc[i-1]);
		// // sprintf(string, "Process Number %d = ",i);
		// int len = (int)strlen(string);
  		// for (int j = 0; j < len; j++) {
    	// 		glutBitmapCharacter(GLUT_BITMAP_9_BY_15, string[j]);   			
  		// }
		// char s1[30];
		// sprintf(s1, "round (%d)", curr_round+1);
		renderBitMap(x[i-1]+0.15, y[i-1],GLUT_BITMAP_9_BY_15,string);

	}

	//  memset((char *) proc, 0, sizeof(proc));
	// char string2[12];
	// snprintf(string2,12,"Score \n1");
	char *string2 = (char*)"Score\n 1";
	renderBitMap(-0.60, -0.88, GLUT_BITMAP_TIMES_ROMAN_24, string2);


	char *string3 = (char*)"Score\n 0";
	renderBitMap(0.40, -0.88, GLUT_BITMAP_TIMES_ROMAN_24, string3);


}


void ending(){


	declare_winner();
	printf("\n");
	terminate_children();
	remove_files();
	sleep(3);
	kill(parent_pid, SIGTERM);



}

void timer(int t){


	glutPostRedisplay();
    glutTimerFunc(1000/60,timer,0);
    if(start==1){

    
    	char children_generated_numbers[BUFSIZ];
		char teams_sum[BUFSIZ];
		memset(children_generated_numbers, 0, sizeof(children_generated_numbers));
		//generates min and max
		srand(time(0) * (curr_round + 1) * 100);
		min = rand() % 1000;
		max = 1 + min + rand() % (1000);

		fp = fopen(range_file, "w");
		if (fp == NULL)
		{
			printf("Error opening file\n");
			exit(-5);
		}
		fprintf(fp, "%d,%d", min, max);
		fclose(fp);

		printf("Min (%d), Max (%d)\n\n\n", min, max);
		usleep(500);

		// players
		while (1)
		{
			//inform the child to start
			kill(children_pid[curr_player], SIGUSR1);
			//wait the child to generate a random number
			while (player_wait_signal[curr_player] == 0){pause();}

			float rand_float;
			//read the generated number

			char id_str[10];
			sprintf(id_str, "%d", children_pid[curr_player]);

			fp = fopen(id_str, "r");

			if (fp == NULL)
			{
				printf("Error opening file.\n");
				exit(-6);
			}
			fscanf(fp, "%f", &rand_float);
			fclose(fp);
			printf("From parent, child (%d) generated random float: |%f|\n", curr_player, rand_float);
			proc[curr_player] = rand_float;
			// sprintf(sss, "child (%d): |%f|\n", curr_player, rand_float);

			player_wait_signal[curr_player] = 0;
			curr_player++;
			char temp_str[BUFSIZ];
			// memset(temp_str, 0x0, BUFSIZ);
			// printf("(%s)\n",children_generated_numbers);

			if (curr_player == NUM_OF_PLAYERS)
			{
				sprintf(temp_str, "%f", rand_float);
				// printf("<%f>\n",rand_float);
				// printf("<<%s>>\n",temp_str);
				// printf("(%s)\n",children_generated_numbers);
				strcat(children_generated_numbers, temp_str);
				// printf("(%s)\n",children_generated_numbers);
				curr_player = 0;
				break;
			}
			else
			{
				sprintf(temp_str, "%f,", rand_float);
				// printf("<%f>\n",rand_float);
				// printf("<<%s>>\n",temp_str);
				// printf("(%s)\n",children_generated_numbers);
				strcat(children_generated_numbers, temp_str);
				// printf("(%s)\n",children_generated_numbers);
			}
			printf("\n");
			usleep(500);
			memset(temp_str, 0x0, BUFSIZ);
		}

		printf("\n");
		usleep(500);
		printf("From parent, generated numbers [%s]\n", children_generated_numbers);

		//writes the generated numbers to the pipe
		write_pipe_str(fd[0][0], fd[0][1], children_generated_numbers, strlen(children_generated_numbers));

		usleep(500);

		//inform the co-processor to start
		kill(children_pid[NUM_OF_CHILDREN - 1], SIGINT);

		//wait until the two summations are ready
		while (player_wait_signal[NUM_OF_CHILDREN - 1] == 0){pause();}
		usleep(500);

		//read the two summations from the co-processor using pipe
		read_pipe_str(fd[1][0], fd[1][1], teams_sum, BUFSIZ);

		sleep(1);
		printf("From parent, teams sum: [%s]\n", teams_sum);
		
		sscanf(teams_sum, "%f,%f", &sum1, &sum2);
		usleep(500);
		count_points();
		usleep(500);

		printf("\nTeam1 [%d] - Team2 [%d]\n", team1_points, team2_points);
		printf("\nRound(%d) finished\n\n", curr_round + 1);
		printf("**************************\n");

		
		curr_round++;
		if (curr_round == NUM_OF_ROUNDS)
		{
			ending();
		}

		memset(children_generated_numbers, 0x0, BUFSIZ);
		memset(teams_sum, 0x0, BUFSIZ);
		sleep(1);


}else{
	start=1;
}
}
