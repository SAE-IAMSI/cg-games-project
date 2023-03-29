import src.main.PythonFileStats.app.stats as s
import unittest as ut

class TestImport(ut.TestCase):
    def testGetNbPlayers(self):
        '''
        Test getNbPlayers
        :return:
        '''
        self.assertEqual(114, s.getNbPlayers())