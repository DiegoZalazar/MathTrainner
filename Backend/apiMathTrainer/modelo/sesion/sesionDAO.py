import pymysql


class sesionDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def CreateSesionEjercicio(self, idModulo, nivelEjercicio):
        try:
            with self.connection.cursor() as cursor:
                #todo agregar en variables o otro metodo

                sql = "SELECT * FROM Ejercicio WHERE nivelEjercicio = %s and idModulo = %s ORDER BY RAND() LIMIT 3"
                cursor.execute(sql, (nivelEjercicio, idModulo,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None
    def FindAvance(self,idEstudiante,idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT avanceModulo FROM EstudianteModulo WHERE idEstudiante = %s and idModulo = %s"
                cursor.execute(sql, (idEstudiante, idModulo,))
                avance = cursor.fetchall()
                if avance:
                    return avance
                else:
                    return 0
        except pymysql.Error as e:
            print(f"Error: {e}")
            return None

    def CreateSesionLeccion(self, idModulo, nivelLeccion):
        try:
            with self.connection.cursor() as cursor:
                #todo agregar en variables o otro metodo

                sql = "SELECT * FROM Lecccion WHERE nivelLeccion = %s and idModulo = %s"
                cursor.execute(sql, (nivelLeccion, idModulo,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def __del__(self):
        self.connection.close()

