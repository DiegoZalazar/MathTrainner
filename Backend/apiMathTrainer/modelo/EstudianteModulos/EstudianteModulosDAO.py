import pymysql


class EstudianteMdoulosDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def GetModulosEstudiante(self,idEstudiante):
        try:
            with self.connection.cursor() as cursor:
                #todo cambiar el avance de modulo por la consulta SQL

                sql = "SELECT * FROM Modulo JOIN EstudianteModulo ON Modulo.idModulo = EstudianteModulo.idModulo WHERE EstudianteModulo.idEstudiante = %s"

                cursor.execute(sql,(idEstudiante,))

                modulos = cursor.fetchall()
                for modulo in modulos:
                    modulo["avanceModulo"]= float(modulo["avanceModulo"])
                return modulos
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

    def ResetModulosAvance(self, idEstudiante):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE  EstudianteModulo SET  avanceModulo =%s WHERE idEstudiante = %s"
                cursor.execute(sql, (0.0, idEstudiante))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al actualizar el módulo: {e}")
            return None


    def __del__(self):
        self.connection.close()

