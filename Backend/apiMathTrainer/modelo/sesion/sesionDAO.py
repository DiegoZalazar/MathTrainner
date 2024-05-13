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
                avance = cursor.fetchone()
                if avance:
                    avance_decimal = avance["avanceModulo"]  # Obtener solo el valor decimal y redondearlo a dos decimales
                    return str(avance_decimal)  # Convertir el valor decimal a cadena y devolverlo
                else:
                    return 0.0
        except pymysql.Error as e:
            print(f"Error: {e}")
            return None

    def CreateSesionLeccion(self, idModulo, nivelLeccion):
        try:
            with self.connection.cursor() as cursor:
                #todo agregar en variables o otro metodo

                sql = "SELECT * FROM Leccion WHERE idModulo = %s LIMIT 1"
                cursor.execute(sql, (idModulo))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def updateAvance(self, data):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE EstudianteModulo  SET avanceModulo = %s  WHERE idModulo = %s and idEstudiante = %s"
                cursor.execute(sql, (data["avance"],data["idModulo"],data["idEstudiante"],))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def __del__(self):
        self.connection.close()

