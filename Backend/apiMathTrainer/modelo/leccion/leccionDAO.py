import pymysql


class LeccionDAO:
    def __init__(self, host, user, password, database):
        self.connection = pymysql.connect(host=host,
                                          user=user,
                                          password=password,
                                          database=database,
                                          cursorclass=pymysql.cursors.DictCursor)

    def FindAll(self, idModulo):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Leccion WHERE idModulo = %s"
                cursor.execute(sql, (idModulo,))
                return cursor.fetchall()
        except pymysql.Error as e:
            print(f"Error al buscar todas las lecciones: {e}")
            return None

    def FindById(self, idLeccion):
        try:
            with self.connection.cursor() as cursor:
                sql = "SELECT * FROM Leccion WHERE idLeccion = %s"
                cursor.execute(sql, (idLeccion,))
                return cursor.fetchone()
        except pymysql.Error as e:
            print(f"Error al buscar la lección por ID: {e}")
            return None

    def Save(self, leccion):
        try:
            with self.connection.cursor() as cursor:
                sql = "INSERT INTO Leccion (tituloLeccion, descripcionLeccion, nivelLeccion, recursoMultimedia, idModulo) VALUES (%s, %s, %s, %s, %s)"
                cursor.execute(sql, (leccion["tituloLeccion"], leccion["descripcionLeccion"], leccion["nivelLeccion"],
                                     leccion["recursoMultimedia"], leccion["idModulo"]))
                self.connection.commit()
                return cursor.lastrowid
        except pymysql.Error as e:
            print(f"Error al guardar la lección: {e}")
            return None

    def Update(self, leccion):
        try:
            with self.connection.cursor() as cursor:
                sql = "UPDATE Leccion SET tituloLeccion = %s, descripcionLeccion = %s, nivelLeccion = %s, recursoMultimedia = %s, idModulo = %s WHERE idLeccion = %s"
                cursor.execute(sql, (leccion["tituloLeccion"], leccion["descripcionLeccion"], leccion["nivelLeccion"],
                                     leccion["recursoMultimedia"], leccion["idModulo"], leccion["idLeccion"]))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al actualizar la lección: {e}")
            return None

    def Delete(self, idLeccion):
        try:
            with self.connection.cursor() as cursor:
                sql = "DELETE FROM Leccion WHERE idLeccion = %s"
                cursor.execute(sql, (idLeccion,))
                self.connection.commit()
                return cursor.rowcount
        except pymysql.Error as e:
            print(f"Error al eliminar la lección: {e}")
            return None

    def __del__(self):
        self.connection.close()


# Ejemplo de uso:
if __name__ == "__main__":
    dao = LeccionDAO(host="localhost", user="usuario", password="contraseña", database="basedatos")

    # Insertar una nueva lección
    nueva_leccion_id = dao.save({
        "tituloLeccion": "Título de la Lección",
        "descripcionLeccion": "Descripción de la lección",
        "nivelLeccion": "Nivel de la lección",
        "recursoMultimedia": "URL del recurso multimedia",
        "idModulo": 1  # ID del módulo al que pertenece la lección
    })
    print("ID de la nueva lección:", nueva_leccion_id)

    # Buscar todas las lecciones
    lecciones = dao.findAll()
    print("Todas las lecciones:", lecciones)

    # Buscar una lección por ID
    leccion = dao.findById(nueva_leccion_id)
    print("Lección encontrada por ID:", leccion)

    # Actualizar una lección
    leccion["tituloLeccion"] = "Nuevo Título de la Lección"
    num_filas_actualizadas = dao.update(leccion)
    print("Número de filas actualizadas:", num_filas_actualizadas)

    # Eliminar una lección
    num_filas_eliminadas = dao.delete(nueva_leccion_id)
    print("Número de filas eliminadas:", num_filas_eliminadas)
