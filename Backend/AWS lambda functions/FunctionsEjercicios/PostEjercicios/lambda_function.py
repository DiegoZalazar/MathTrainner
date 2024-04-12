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

        plantamientoEjercicio = data['planteamientoEjercicio']
        resolucionEjercicio = data['resolucionEjercicio']
        tiempoEjercicio = data['tiempoEjercicio']
        tipoEjercicio = data['tipoEjrecicio']
        nivelEjercio = data['nivelEjercicio']

        query = f"INSERT INTO Ejercicio (plantamientoEjercicio,resolucionEjercicio,tiempo,tipoEjercicio,nivelEjercio,idModulo) VALUES (${plantamientoEjercicio}, ${resolucionEjercicio}, ${tiempoEjercicio},${tipoEjercicio},${nivelEjercio},${id_Modulo})"

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

