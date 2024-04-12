import os
import json
import pymysql
import rds_config

# Configura la conexión a la base de datos
conn = pymysql.connect(
        host=rds_config.db_endpoint,
        user=rds_config.db_username,
        password=rds_config.db_password,
        db=rds_config.db_name,
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )
# Obtén la consulta SQL de las variables de entorno
query = os.environ['QUERY']

def lambda_handler(event, context):
    # Ejecuta la consulta SQL y obtén los resultados
    with conn.cursor() as cursor:
        cursor.execute(query)
        result = cursor.fetchall()
    # Devuelve los resultados como JSON
    return {
        'statusCode': 200,
        'body': json.dumps(result)
    }

# Cierra la conexión a la base de datos
    conn.close()


