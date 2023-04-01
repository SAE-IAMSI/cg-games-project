import src.main.PythonFileStats.app.stats as s
import unittest as ut
from unittest.mock import Mock, patch

class TestImport(ut.TestCase):
    def testGetNbPlayers(self):
        """
        Test getNbPlayers
        :return:
        """
        self.assertEqual(114, s.getNbPlayers())

    @patch('s.getNbPla yers')
    def test_get_data(self, mock_db):
        mock_db.return_value.query_all_data.return_value = 19
        result = s.getNbPlayers()

        self.assertEqual(result, 19)
