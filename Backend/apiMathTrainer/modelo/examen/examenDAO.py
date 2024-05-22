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
                ids = (5, 6, 7, 8, 5)
                sql = "SELECT * FROM Ejercicio WHERE idModulo IN %s"
                cursor.execute(sql, (ids,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def SetNivel(self,data):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE EstudianteModulo SET avanceModulo = %s WHERE idModulo = %s and idEstudiante =%s"
                cursor.execute(sql, (data["avance"], data["idModulo"], data["idEstudiante"],))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def UpdateExamenInfo(self,data):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE EvaluacionDiagnostico SET realizado = %s WHERE idEstudiante = %s"
                cursor.execute(sql, (data["realizado"],data["idEstudiante"]))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def GetExamenInfo(self,data):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT realizado FROM EvaluacionDiagnostico WHERE idEstudiante = %s"
                cursor.execute(sql, (data["idEstudiante"]))
                resultado = cursor.fetchone()
                if resultado:
                    return bool(resultado["realizado"])
                else:
                    sql = "INSERT INTO EvaluacionDiagnostico (idEstudiante, realizado) VALUES (%s, %s)"
                    cursor.execute(sql, (data["idEstudiante"], False))
                    self.connection.commit()

                    sql = "SELECT realizado FROM EvaluacionDiagnostico WHERE idEstudiante = %s"
                    cursor.execute(sql, (data["idEstudiante"]))
                    resultado = cursor.fetchone()
                    if resultado:
                        return bool(resultado["realizado"])

        except pymysql.Error as e:
            print(f"Error al buscar todos los ejercicios: {e}")
            return None

    def __del__(self):
        self.connection.close()

