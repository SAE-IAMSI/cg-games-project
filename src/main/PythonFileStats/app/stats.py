import oracledb
import os
from datetime import *
import datetime
import matplotlib.pyplot as plt
import math


dir = "client64Bit"
absolute = os.path.join(os.getcwd(), dir)
user="etusae1"
password = "3tuS43"
host = "162.38.222.149"
port = 1521
sid = "iut"

oracledb.init_oracle_client(lib_dir=absolute)

connexion = oracledb.connect(user= user, password= password, host= host, port= port, sid= sid)

def getNbPlayers():
    with connexion.cursor() as cursor:
        sql = """select getNumbersOfPlayers from dual"""
        for r in cursor.execute(sql) :
            return r[0]

def getTop10Departement():
    with connexion.cursor() as cursor:
        sql = """SELECT * FROM viewDepartementByPlayers WHERE ROWNUM <= 10"""
        cursor.execute(sql)
        result = cursor.fetchall()
        tab = []
        for r in result:
            tab.append(r[0])
    return tab

def getJoueursActifs():
    with connexion.cursor() as cursor:
        sql = """select getNbActivePlayer from dual"""
        for r in cursor.execute(sql) :
            return r[0]
        
def getScoreMoyenEntreDates(jeu, dateAvant, dateApres):
    with connexion.cursor() as cursor:
        sql = f"""select getAvgBetweenDate('{jeu}','{dateAvant}','{dateApres}') from dual"""
        for r in cursor.execute(sql) :
            return r[0]
        
def getTabScoreMoyenParSemaine(jeu, idGraph):
    itDate = date(2022, 11, 12) #la date qui sera itérée par delta
    d = datetime.date.today()
    delta = timedelta(weeks=1) #delta = une semaine
    nextDate = itDate + delta #nexDate = date courante + une semaine

    scores = [] #tableau à return
    scoreIncremente = 0
    startDate = str(itDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(itDate.strftime("%d/%m/%Y"))[8:10]
    while(itDate < d): #tant que itDate (date courante) n'atteint pas la date d'aujourd'hui
        startDate = str(itDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(itDate.strftime("%d/%m/%Y"))[8:10]
        endDate = str(nextDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(nextDate.strftime("%d/%m/%Y"))[8:10]
        print(startDate)
        itDate += delta
        nextDate += delta
        req = getScoreMoyenEntreDates(jeu, startDate, endDate)

        if(idGraph == 0):
            scores.append(req)

        elif(idGraph == 1):
            if(req is None):
                req = 0
            if(scoreIncremente > 0):
                scores.append((scoreIncremente + req)/(len(scores)+1)) 
                scoreIncremente += req 
            else:
                scores.append(scoreIncremente + req) 
                scoreIncremente += req
    scores, semaines = replaceNone(scores)
    print(sum(scores)/len(scores))
    return scores, semaines

def replaceNone(tab):
    '''
    Prend un tableau tab composé de int et/ou None
    Remplace les None par des 0
    Retourne deux tableaux :
        -Un tableau représentant tab avec les None remplacés par eds 0
        -Un tableau allant de 1 à len(tab)
    '''
    newTab = []
    semaines = []
    for i in range(len(tab)):
        if(tab[i] is None):
            newTab.append(0)
        else:
            newTab.append(tab[i])
        semaines.append(i+1)
    return newTab, semaines


def getScoreMoyen(jeu):
    today = datetime.date.today()
    date = str(today.strftime("%d/%m/%Y"))[0:5] + "/" + str(today.strftime("%d/%m/%Y"))[8:10]
    return getScoreMoyenEntreDates(jeu,'01/01/22',date)

def getTempsMoyenKR(dateAvant, dateApres):
    with connexion.cursor() as cursor:
        sql = f"""select getAvgTimeBetweenDates('{dateAvant}','{dateApres}') from dual"""
        for r in cursor.execute(sql) :
            return r[0]

def getJoueursParDepartements(numDepartement):
    with connexion.cursor() as cursor:
        sql = f"""SELECT getPlayersByDepartement({numDepartement}) FROM DUAL"""
        for r in cursor.execute(sql) :
            return r[0]
        

def getGrapheScoreMoyen(game, idGraph):
    '''
    game = idJeu
    idGraph = id du graphe (0 = par semaine (chaque semaine est indépendante) , 1 = "au fil du temps" (moyenne entre semaine 1 et 2, puis 1 2 3, etc...))
    '''
    scores, semaines = getTabScoreMoyenParSemaine(game, idGraph)
    print(scores, semaines)
    plt.plot(semaines, scores, color='red')
    plt.title(f'Scores moyen par semaines du jeu {game}', fontsize=14)
    plt.xlabel('Semaines', fontsize=14)
    plt.ylabel('Scores moyens', fontsize=14)
    plt.grid(True)
    plt.show()


getNbPlayers()
print(getTop10Departement())
print(getJoueursActifs())
print(getScoreMoyen('KR'))
print(getJoueursParDepartements(34))
print(getTempsMoyenKR('01/01/22', '01/01/24'))
print(getGrapheScoreMoyen('FF', 1))