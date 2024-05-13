import pymysql


class EjercicioDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def FindAll(self,idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Ejercicio WHERE idModulo = %s"
                cursor.execute(sql, (idModulo,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def FindById(self, idEjercicio):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Ejercicio WHERE idEjercicio = %s"
                cursor.execute(sql, (idEjercicio,))
                return cursor.fetchone()
        except pymysql.Error as e:
            print(f"Error al buscar el ejercicio por ID: {e}")
            return None

    def Save(self, ejercicio):
        try:
            with self.connection.cursor() as cursor:
                sql = "INSERT INTO Ejercicio (planteamientoEjercicio, cuerpo, tiempoEjercicio, tipoEjercicio, nivelEjercicio, respCorrectaEjercicio, respIncorrectasEjercicio, paresCorrectos, idModulo) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)"
                cursor.execute(sql, (
                    ejercicio["planteamientoEjercicio"], ejercicio["cuerpo"], ejercicio["tiempoEjercicio"], ejercicio["tipoEjercicio"], ejercicio["nivelEjercicio"], ejercicio["respCorrectaEjercicio"], ejercicio["respIncorrectasEjercicio"],
                    ejercicio["paresCorrectos"],ejercicio["idModulo"]
                ))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al guardar el ejercicio: {e}")
            return None

    def Update(self, ejercicio):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE Ejercicio SET planteamientoEjercicio =%s,cuerpo = %s, tiempoEjercicio =%s, tipoEjercicio=%s, nivelEjercicio=%s, respCorrectaEjercicio=%s, respIncorrectasEjercicio=%s, paresCorrectos=%s, idModulo=%s WHERE idEjercicio = %s"
                cursor.execute(sql, (
                    ejercicio["planteamientoEjercicio"], ejercicio["cuerpo"], ejercicio["tiempoEjercicio"], ejercicio["tipoEjercicio"],
                    ejercicio["nivelEjercicio"], ejercicio["respCorrectaEjercicio"],
                    ejercicio["respIncorrectasEjercicio"],
                    ejercicio["paresCorrectos"], ejercicio["idModulo"], ejercicio["idEjercicio"]))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al actualizar el ejercicio: {e}")
            return None

    def Delete(self, idEjercicio):
        try:
            with self.connection.cursor() as cursor:
                sql = "DELETE FROM Ejercicio WHERE idEjercicio = %s"
                cursor.execute(sql, (idEjercicio,))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al eliminar el ejercicio: {e}")
            return None

    def __del__(self):
        self.connection.close()

