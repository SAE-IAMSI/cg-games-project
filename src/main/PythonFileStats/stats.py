import oracledb
import os
import datetime

dir = "client64Bit"
absolute = os.path.join(os.getcwd(), dir)
user="etusae1"
password = "3tuS43"
host = "162.38.222.149"
port = 1521
sid = "iut"

oracledb.init_oracle_client(lib_dir=absolute)

global connexion
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

def getScoreMoyen(jeu):
    today = datetime.date.today()
    date = str(today.strftime("%d/%m/%Y"))[0:5] + "/" + str(today.strftime("%d/%m/%Y"))[8:10]
    return getScoreMoyenEntreDates(jeu,'01/01/22',date)

def getTempsMoyenKR(dateAvant, dateApres):
    with connexion.cursor() as cursor:
        sql = f"""select getAvgTimeBetweenDate('{dateAvant}','{dateApres}') from dual"""
        print(sql)
        for r in cursor.execute(sql) :
            return r[0]

def getJoueursParDepartements(numDepartement):
    with connexion.cursor() as cursor:
        sql = f"""SELECT getPlayersByDepartement({numDepartement}) FROM DUAL"""
        for r in cursor.execute(sql) :
            return r[0]

getNbPlayers()
print(getTop10Departement())
print(getJoueursActifs())
print(getScoreMoyen('KR'))
print(getJoueursParDepartements(34))
print(getTempsMoyenKR('01/01/22', '01/01/24'))