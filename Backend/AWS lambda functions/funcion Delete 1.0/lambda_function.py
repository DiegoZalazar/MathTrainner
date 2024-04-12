import sys
import logging
import pymysql
import json
import os

# Configura la conexión a la base de datos MySQL en AWS RDS

conn = pymysql.connect(
        host = os.environ['HOST'],
        user = os.environ['USER'],
        passwd = os.environ['PASSWORD'],
        db = os.environ['DB'],
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
)


# Función Lambda
def lambda_handler(event, context):
    # Verifica si se proporcionó un ID en el evento
    if 'pathParameters' not in event:
        return {
            'statusCode': 400,
            'body': json.dumps('ID not found')
        }

    id_ejercicio = event['pathParameters']['idEjercicio']
    query = os.environ['QUERY']

    try:
        # Conexión a la base de datos
        cursor = conn.cursor()
        # Borra el registro
        cursor.execute(query, (id,))
        conn.commit()

        # Verifica si se eliminó algún registro
        if cursor.rowcount == 0:
            return {
                'statusCode': 404,
                'body': json.dumps(f"Not register found with: {id}")
            }

        return {
            'statusCode': 200,
            'body': json.dumps(f"Delete Success {id}")
        }

    except Exception as e:
        return {
            'statusCode': 500,
            'body': json.dumps(f"Error: {str(e)}")
        }




