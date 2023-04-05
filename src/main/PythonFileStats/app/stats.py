import oracledb
import os
from datetime import *
import datetime
import matplotlib.pyplot as plt
import math

dir = "../test/client64Bit"
absolute = os.path.join(os.getcwd(), dir)
user = "etusae1"
password = "3tuS43"
host = "162.38.222.149"
port = 1521
sid = "iut"


def createConnexion(u, p, h, po, s) -> oracledb.Connection:
    """
    Crée une connexion à la base de données\n
    :return: connexion: oracledb.Connection
    """
    oracledb.init_oracle_client(lib_dir=absolute)
    connexion = oracledb.connect(user=u, password=p, host=h, port=po, sid=s)
    return connexion


def getNbPlayers() -> int:
    '''
    Renvoie le nombre de joueurs sur le PGI\n
    :return: r[0] : int
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = """select getNumbersOfPlayers from dual"""
        for r in cursor.execute(sql):
            return r[0]


def getTop10Departement() -> list:
    '''
    Renvoie les 10 départements avec le plus de joueurs\n
    :return: tab: un tableau de string
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = """SELECT * FROM viewDepartementByPlayers WHERE ROWNUM <= 10"""
        cursor.execute(sql)
        result = cursor.fetchall()
        tab = []
        for r in result:
            tab.append(r[0])
    return tab


def getJoueursActifs() -> int:
    '''
    Renvoie le nombre de joueurs actifs sur le PGI\n
    :return: r[0] : int
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = """select getNbActivePlayer from dual"""
        for r in cursor.execute(sql):
            return r[0]


def getScoreMoyenEntreDates(jeu: str, dateAvant: str, dateApres: str) -> float:
    '''
    Renvoie la moyenne de score sur le jeu donnée entre deux dates données\n
    :param jeu: code du jeu
    :param dateAvant: date de début de la période
    :param dateApres: date de fin de la période
    :return: r[0]: float
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = f"""select getAvgBetweenDate('{jeu}','{dateAvant}','{dateApres}') from dual"""
        for r in cursor.execute(sql):
            return r[0]


def getTabScoreMoyenParSemaine(jeu: str, idGraph):
    # a refactor passer en tuples ou alors virer les semaines
    """
    Renvoie tableau de scores et le tableau de semaines associé \n
    :param jeu: code de jeu voulu pour le graphe
    :param idGraph: 0 = par semaine (chaque semaine est indépendante) , 1 = "au fil du temps" (moyenne entre semaine 1 et 2, puis 1 2 3, etc...)
    :return: scores: list et semaines:list
    """
    itDate = date(2022, 11, 12)  # la date qui sera itérée par delta
    d = datetime.date.today()
    delta = timedelta(weeks=1)  # delta = une semaine
    nextDate = itDate + delta  # nexDate = date courante + une semaine

    scores = []  # tableau à return
    scoreIncremente = 0
    startDate = str(itDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(itDate.strftime("%d/%m/%Y"))[8:10]
    while (itDate < d):  # tant que itDate (date courante) n'atteint pas la date d'aujourd'hui
        startDate = str(itDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(itDate.strftime("%d/%m/%Y"))[8:10]
        endDate = str(nextDate.strftime("%d/%m/%Y"))[0:5] + "/" + str(nextDate.strftime("%d/%m/%Y"))[8:10]
        itDate += delta
        nextDate += delta
        req = getScoreMoyenEntreDates(jeu, startDate, endDate)

        if (idGraph == 0):
            scores.append(req)

        elif (idGraph == 1):
            if (req is None):
                req = 0
            if (scoreIncremente > 0):
                scores.append((scoreIncremente + req) / (len(scores) + 1))
                scoreIncremente += req
            else:
                scores.append(scoreIncremente + req)
                scoreIncremente += req
    scores, semaines = replaceNone(scores)
    return scores, semaines


def replaceNone(tab: list) -> (list, list):
    '''
    Remplace les None par des 0 et renvoie un tableau allant de 1 à len(tab)\n
    :param tab: un tableau contenant des None ou des int
    :return: (newTab, semaines): (list,list), newTab: un tableau contenant des 0 ou des int, semaines: un tableau allant de 1 à len(tab)
    '''
    newTab = []
    semaines = []
    for i in range(len(tab)):
        if (tab[i] is None):
            newTab.append(0)
        else:
            newTab.append(tab[i])
        semaines.append(i + 1)
    return newTab, semaines


def getScoreMoyen(jeu: str) -> float:
    '''
    Renvoie la moyenne de socre sur le jeu donnée entre le 1er janvier 2022 et aujourd'hui\n
    :param jeu: code du jeu sur lequel on veut la moyenne
    :return: getScoreMoyenEntreDates(jeu,'01/01/22',date) : float
    '''
    today = datetime.date.today()
    date = str(today.strftime("%d/%m/%Y"))[0:5] + "/" + str(today.strftime("%d/%m/%Y"))[8:10]
    return getScoreMoyenEntreDates(jeu, '01/01/22', date)


def getTempsMoyenKR(dateAvant: str, dateApres: str) -> float:
    '''
    Renvoie le temps moyen pour finir une partie de Koala Rock entre deux dates\n
    :param dateAvant: date de début de période
    :param dateApres: date de fin de période
    :return: r[0] : float
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = f"""select getAvgTimeBetweenDates('{dateAvant}','{dateApres}') from dual"""
        for r in cursor.execute(sql):
            return r[0]


def getJoueursParDepartements(numDepartement: str) -> int:
    '''
    Renvoie le nombre de joueurs sur un département donné\n
    :param numDepartement: numéro du département sur lequel on veut le nombre de joueurs
    :return: r[0] : int
    '''
    connexion = createConnexion(user, password, host, port, sid)
    with connexion.cursor() as cursor:
        sql = f"""SELECT getPlayersByDepartement({numDepartement}) FROM DUAL"""
        for r in cursor.execute(sql):
            return r[0]


def getGrapheScoreMoyen(game: str, idGraph: int = 0):
    '''
    Affiche un graphe représentant les scores moyens par semaine du jeu donné\n
    :param game: code du jeu
    :param idGraph: 0 = par semaine (chaque semaine est indépendante) , 1 = "au fil du temps" (moyenne entre semaine 1 et 2, puis 1 2 3, etc...)
   '''
    scores, semaines = getTabScoreMoyenParSemaine(game, idGraph)
    plt.plot(semaines, scores, color='red')
    plt.title(f'Scores moyen par semaines du jeu {game}', fontsize=14)
    plt.xlabel('Semaines', fontsize=14)
    plt.ylabel('Scores moyens', fontsize=14)
    plt.grid(True)
    plt.show()
