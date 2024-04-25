import pymysql


class ExamenDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def CreateExam(self):
        try:
            with self.connection.cursor() as cursor:
                #todo agregar en variables o otro metodo
                ids = (1, 2, 3, 4, 5)
                sql = "SELECT * FROM Ejercicio WHERE idModulo IN %s"
                cursor.execute(sql, (ids,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def __del__(self):
        self.connection.close()

