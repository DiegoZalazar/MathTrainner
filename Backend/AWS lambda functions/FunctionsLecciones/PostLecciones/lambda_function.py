import sys
import logging
import pymysql
import json
import os

# Configure mySQL RDS DB connection
conn = pymysql.connect(
    host=os.environ['HOST'],
    user=os.environ['USER'],
    passwd=os.environ['PASSWORD'],
    db=os.environ['DB'],
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

    id_Modulo = event['pathParameters']['idModulo']
    try:
        # Extract Data from event
        data = json.loads(event['body'])

        tituloLeccion = data['tituloLeccion']
        descripcionLeccion = data['descripcionLeccion']
        nivelLeccion = data['nivelLeccion']
        #todo revisar si agregar campo video

        query = f"INSERT INTO Leccion (tituloLeccion,descripcionLeccion,tituloLeccion,idModulo) VALUES (${tituloLeccion}, ${descripcionLeccion}, ${nivelLeccion}, ${id_Modulo})"

        cursor = conn.cursor()
        # Ejecuta la inserción
        cursor.execute(query)
        conn.commit()

        return {
            'statusCode': 200,
            'body': json.dumps('data insert success')
        }

    except Exception as e:
        return {
            'statusCode': 500,
            'body': json.dumps(f"Error: {str(e)}")
        }

    finally:
        # Cierra la conexión a la base de datos
        # todo ver si realmente se cierra la coneccion
        if conn:
            conn.close()

