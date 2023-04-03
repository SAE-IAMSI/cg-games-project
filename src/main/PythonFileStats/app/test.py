import oracledb
import os

dir = "src/main/java/games/project/statitisques/client64Bit"
absolute = os.path.join(os.getcwd(), dir)
print(absolute)

oracledb.init_oracle_client(lib_dir=absolute)
with oracledb.connect(user="etusae1",password="3tuS43",host="162.38.222.149", port=1521, sid="iut") as connection:
    with connection.cursor() as cursor:
        sql = """select 1 from dual"""
        for r in cursor.execute(sql) :
            print(r)

