import src.main.PythonFileStats.app.stats as s
import unittest as ut
import os

class Integration(ut.TestCase):
    s.user = "reinerk"
    s.password = "MDrlOl304"
    s.dir = "client64Bit"
    s.absolute = os.path.join(os.getcwd(), s.dir)

    def testNbGetPlayers(self):
        self.assertEqual(0,s.getNbPlayers())

    def testGetTop10DPT(self):
        listDPT = []
        self.assertEqual(listDPT,s.getTop10Departement())

    def testGetJoueurActifs(self):
        self.assertEqual(0,s.getJoueursActifs())

    def testGetScoreMoyenEntreDatesKR(self):
        self.assertEqual(None,s.getScoreMoyenEntreDates('KR','10/12/22','01/05/23'))

    def testGetScoreMoyen(self):
        self.assertEqual(None, s.getScoreMoyen('KR'))

    def testGetTempsMoyenKR(self):
        self.assertEqual(None, s.getTempsMoyenKR('01/01/22','01/01/24'))

    def testGetJoueursParDepartements(self):
        self.assertEqual(0,s.getJoueursParDepartements('95'))

    def testGetTabScoreMoyenParSemaine(self):
        self.assertEqual((None,None),s.getTabScoreMoyenParSemaine('KR',0))

    def testReplaceNone(self):
        tabNone = [1,2,None,None,4]
        tabSemaine = [1,2,3,4,5]
        tabSansNone = [1,2,0,0,4]

        self.assertEqual((tabSansNone,tabSemaine),s.replaceNone(tabNone))
