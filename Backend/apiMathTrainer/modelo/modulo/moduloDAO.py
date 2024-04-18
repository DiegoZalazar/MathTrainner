import pymysql


class ModuloDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def findAll(self):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Modulo"
                cursor.execute(sql)
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los módulos: {e}")
            return None

    def findById(self, idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Modulo WHERE idModulo = %s"
                cursor.execute(sql, (idModulo,))
                return cursor.fetchone()
        except pymysql.Error as e:
            print(f"Error al buscar el módulo por ID: {e}")
            return None

    def save(self, modulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "INSERT INTO Modulo (nombreModulo) VALUES (%s)"
                cursor.execute(sql, (modulo["nombreModulo"],))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al guardar el módulo: {e}")
            return None

    def update(self, modulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE Modulo SET nombreModulo = %s WHERE idModulo = %s"
                cursor.execute(sql, (modulo["nombreModulo"], modulo["idModulo"]))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al actualizar el módulo: {e}")
            return None

    def delete(self, idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "DELETE FROM Modulo WHERE idModulo = %s"
                cursor.execute(sql, (idModulo,))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al eliminar el módulo: {e}")
            return None

    def __del__(self):
        self.connection.close()



