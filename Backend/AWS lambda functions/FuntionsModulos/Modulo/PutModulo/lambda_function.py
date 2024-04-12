import sys
import logging
import pymysql
import json
import os

# Configure mySQL RDS DB connection
conn = pymysql.connect(
        host = os.environ['HOST'],
        user = os.environ['USER'],
        passwd = os.environ['PASSWORD'],
        db = os.environ['DB'],
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
)


#  Lambda
def lambda_handler(event, context):
    if 'pathParameters' not in event:
        return {
            'statusCode': 400,
            'body': json.dumps('ID not found')
        }

    idModulo = event['pathParameters']['idModulo']
    try:
        # Extract Data from event
        data = json.loads(event['body'])

        nombreModulo = data['nombreModulo']

        query = f'UPDATE Modulo SET (nombreModulo) VALUES (${nombreModulo}) WHERE idModulo= ${idModulo}'

        cursor = conn.cursor()
        # Ejecuta la inserción
        cursor.execute(query)
        conn.commit()

        return {
            'statusCode': 200,
            'body': json.dumps('data update success')
        }
    #todo agregar caso cuanndo no existe el id buscado

    except Exception as e:
        return {
            'statusCode': 500,
            'body': json.dumps(f"Error: {str(e)}")
        }

    finally:
        # Cierra la conexión a la base de datos
        #todo ver si realmente se cierra la coneccion
        if conn:
            conn.close()

