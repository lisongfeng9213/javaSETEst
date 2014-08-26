#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <set>
#include <utility>
#include <algorithm>
using namespace std;

class Aprorir
{
public:
	void getL1(string &fileName);
	void makeC1();
	void aproiri_gen();
	void main_fun();
	void has_frequent_subset();
	int Min_support;
	void display();
	void isempty();
	void generate2C();
	void generateL();
private:
	set<vector<string>> D;//事物集合
	map<string, int> L1;
	map<string, int> C1;
	map<vector<string>, int> Ck_1;
	map<vector<string>, int> Lk_1;
	map<vector<string>, int> Ck;
	map<vector<string>, int> Lk;
};

void Aprorir::getL1(string &fileName)
{
	ifstream Filein;
	Filein.open(fileName);
	if (!Filein)
	{
		cout << "Open file failed!" << endl;
		exit(1);
	}
	vector<string> Dtemp;
	string wordline;
	while (getline(Filein, wordline))
	{
		Dtemp.push_back(wordline);//输入到一行中vector<string>；
	}
	vector<string> temp;//用于插入set<vector<string>>;
	vector<string>::iterator ite = Dtemp.begin();
	for (; ite != Dtemp.end(); ite++)
	{
		temp.clear();
		string tempstr = *ite;
		string word;
		while (tempstr.find('\\') != -1)
		{
			word = (tempstr.substr(0, tempstr.find('\\')));
			temp.push_back(word);
			++C1[word];
			tempstr = tempstr.substr(tempstr.find('\\') + 1, tempstr.size() - 1);
		}
		++C1[tempstr];
		temp.push_back(tempstr);
		sort(temp.begin(), temp.end());
		D.insert(temp);
	}

}
void Aprorir::makeC1()
{
	map<string, int>::iterator ite = C1.begin();
	while (ite != C1.end())
	{
		if (C1[ite->first] >= Min_support)
			L1.insert(make_pair(ite->first, ite->second));
		ite++;
	}
}
void Aprorir::display()
{
	cout << "FIRST" << endl;
	map<vector<string>, int>::iterator ite = Lk_1.begin();
	while (ite != Lk_1.end())
	{
		vector<string> a = ite->first;
		vector<string>::iterator ite2 = a.begin();
		while (ite2 != a.end())
		{
			cout << *ite2 << ' ';
			ite2++;
		}
		cout << endl;
		ite++;
	}
}
void Aprorir::generate2C()
{
	map<string, int> tempL1(L1);
	map<string, int>::iterator ite = L1.begin();
	while (ite != L1.end())
	{
		map<string, int>::iterator ite2 = tempL1.begin();
		for (; ite2 != tempL1.end(); ite2++)
		{
			if (ite->first<ite2->first)
			{
				vector<string> temp;
				temp.push_back(ite->first);
				temp.push_back(ite2->first);
				++Ck_1[temp];
			}
		}
		ite++;
	}
}
void Aprorir::generateL()//第一步，有生成的Ck_1，生成Lk_1,扫描D，第二部，求出L
{
	map<vector<string>, int> temp;
	map<vector<string>, int>::iterator Ck_1_ite = Ck_1.begin();
	for (; Ck_1_ite != Ck_1.end(); Ck_1_ite++)
	{
		vector<string> Ck_1_Vec = Ck_1_ite->first;
		set<vector<string>>::iterator D_ite = D.begin();
		int k = 0;
		for (; D_ite != D.end(); D_ite++)
		{
			vector<string> D_Vec = *D_ite;
			int posOne = 0;
			int posTwo = 0;
			while (posOne < Ck_1_Vec.size() && posTwo < D_Vec.size())
			{
				if (Ck_1_Vec[posOne] == D_Vec[posTwo])
				{
					posOne++;
					posTwo++;
				}
				else
					posTwo++;
			}
			if (posOne == Ck_1_Vec.size())
				k++;
		}
		if (k >= Min_support)
		{
			temp.insert(make_pair(Ck_1_Vec, k));
		}

	}
	Lk_1 = temp;

}
void Aprorir::main_fun()
{
	while (!Lk_1.empty())
	{
		display();
		map<vector<string>, int> tempLk_1(Lk_1);
		Ck.clear();
		map<vector<string>, int>::iterator Lk_1_ite1 = Lk_1.begin();
		while (Lk_1_ite1 != Lk_1.end())
		{
			vector<string> a = Lk_1_ite1->first;
			vector<string>::iterator Lk_1_a_ite2 = a.end();
			Lk_1_a_ite2--;
			map<vector<string>, int>::iterator Temp_Lk_1_ite1 = tempLk_1.begin();
			while (Temp_Lk_1_ite1 != tempLk_1.end())
			{
				vector<string> b = Temp_Lk_1_ite1->first;
				vector<string>::iterator Temp_Lk_1_b_ite2 = b.end();
				Temp_Lk_1_b_ite2--;
				if (*Lk_1_a_ite2 < *Temp_Lk_1_b_ite2)
				{
					vector<string> temp(a);
					temp.push_back(*Temp_Lk_1_b_ite2);
					++Ck[temp];
				}
				Temp_Lk_1_ite1++;

			}
			Lk_1_ite1++;
		}
		Ck_1 = Ck;
		generateL();
	}

}