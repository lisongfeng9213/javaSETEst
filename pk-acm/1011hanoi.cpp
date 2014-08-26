#include "stdio.h"
void hanoi (int n,char a,char b,char c)
{
	if (n==1)
	{
		printf("move %d from %c to %c\n",n,a,c);
	}
	else
	{
		hanoi(n-1,a,c,b);
		printf("move %d from %c to %c\n",n,a,c);
		hanoi(n-1,b,a,c);
	}
}
int main()
{
	char a1='a',a2='b',a3='c';
	hanoi(3,a1,a2,a3);
	return 0;
}