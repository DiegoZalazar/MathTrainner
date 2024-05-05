import pymysql


class TemaDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def findAll(self):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Tema"
                cursor.execute(sql)
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todos los módulos: {e}")
            return None

    def findById(self, idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Tema WHERE idTema = %s"
                cursor.execute(sql, (idModulo,))
                return cursor.fetchone()
        except pymysql.Error as e:
            print(f"Error al buscar el módulo por ID: {e}")
            return None

    def save(self, modulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "INSERT INTO Tema (titulo) VALUES (%s)"
                cursor.execute(sql, (modulo["titulo"]))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al guardar el módulo: {e}")
            return None

    def update(self, modulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE Tema SET titulo = %s  WHERE idTema = %s"
                cursor.execute(sql, (modulo["titulo"], modulo["idTema"]))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al actualizar el módulo: {e}")
            return None

    def delete(self, idTema):
        try:
            with self.connection.cursor() as cursor:
                sql = "DELETE FROM Tema WHERE idTema = %s"
                cursor.execute(sql, (idTema,))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al eliminar el módulo: {e}")
            return None

    def __del__(self):
        self.connection.close()



